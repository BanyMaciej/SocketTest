package com.mb.sockettest;

import android.app.Activity;
import android.media.MediaPlayer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;

public class ServerThread extends Thread {

    private Activity callingActivity;

    MediaPlayer mPlayer;
    private Timer timer = new Timer();

    ServerSocket serverSocket;

    public ServerThread(Activity callingActivity) {
        this.callingActivity = callingActivity;
    }

    static int serverPORT = 4321;
    int songName = R.raw.ticktock;

    @Override
    public void run() {
        Socket socket = null;
        DataInputStream inStream = null;
        DataOutputStream outStream = null;

        try {
            serverSocket = new ServerSocket(serverPORT);
            mPlayer = MediaPlayer.create(callingActivity, songName);

            for(;;) {
                socket = serverSocket.accept();
                socket.setTcpNoDelay(true);

                inStream = new DataInputStream(socket.getInputStream());
                outStream = new DataOutputStream(socket.getOutputStream());

                int AMOUNT = inStream.readInt();

                for( int i = 0; i < AMOUNT; i++ ) {
                    //chars
//                    inStream.readChar();
//                    outStream.writeChar('a');
                    //bytes
                    inStream.readByte();
                    outStream.writeByte(MyConstans.TEST_SIGNAL);
                }
                //chars
//                if( inStream.readChar() == 's' ) {
//                    outStream.writeChar('s');
//                    startPlayer();
//                }

                //bytes
                if( inStream.readByte() == MyConstans.START_SIGNAL ) {
                    //outStream.writeByte(MyConstans.START_SIGNAL);
                    startPlayer();
                    //interrupt();
                }
            }

        } catch ( IOException e ) {
            e.printStackTrace();
        } finally {
            if( socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if( inStream != null ) {
                try {
                    inStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if( outStream != null ) {
                try {
                    outStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void startPlayer() {
        mPlayer.start();
    }

    @Override
    public void interrupt() {
        super.interrupt();
        mPlayer.release();
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        timer.cancel();
    }
}

