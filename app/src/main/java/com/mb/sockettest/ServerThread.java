package com.mb.sockettest;

import android.app.Activity;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Maciek on 2017-03-07.
 */

public class ServerThread extends Thread {

    Activity activity;

    int Counter = 0;

    MediaPlayer mPlayer;
    Timer timer = new Timer();

    ServerSocket serverSocket;

    public ServerThread(Activity activity) {
        this.activity = activity;
    }

    static int serverPORT = 4321;

    @Override
    public void run() {
        Socket socket = null;
        DataInputStream inStream = null;
        DataOutputStream outStream = null;

        try {
            serverSocket = new ServerSocket(serverPORT);
            mPlayer = MediaPlayer.create(activity, R.raw.test);
            for(;;) {
                socket = serverSocket.accept();
                socket.setTcpNoDelay(true);

                inStream = new DataInputStream(socket.getInputStream());
                outStream = new DataOutputStream(socket.getOutputStream());

                int AMOUNT = inStream.readInt();

                for( int i = 0; i < AMOUNT; i++ ) {
                    inStream.readChar();
                    outStream.writeChar('a');
                }

                if( inStream.readChar() == 's' ) {
                    outStream.writeChar('s');
                    startCounter();
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

//    private void startCounter() {
//
//        timer.scheduleAtFixedRate(new TimerTask() {
//            boolean colored = false;
//
//            @Override
//            public void run() {
//                Counter++;
//                mPlayer.start();
//                activity.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        if( !colored ) {
//                            activity.findViewById(R.id.startButton).setBackgroundColor(Color.BLUE);
//                            colored = true;
//                        } else {
//                            activity.findViewById(R.id.startButton).setBackgroundColor(Color.WHITE);
//                            colored = false;
//                        }
//                    }
//                });
//                Log.d("TIME", Counter + "");
//            }
//        }, 0, 1000);
//
//        activity.findViewById(R.id.stopTimerBtn_Server).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                timer.cancel();
//            }
//        });
//
//    }

    private void startCounter() {
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

