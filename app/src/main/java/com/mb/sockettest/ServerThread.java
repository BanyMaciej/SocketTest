package com.mb.sockettest;

import android.app.Activity;
import android.util.Log;
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

    public ServerThread(Activity activity) {
        this.activity = activity;
    }

    ServerSocket serverSocket;
    long inData = 0;
    long x1p = 0, x1k = 0, yk = 0;

    static int serverPORT = 4321;

    @Override
    public void run() {
        Socket socket = null;
        DataInputStream inStream = null;
        DataOutputStream outStream = null;

        try {
            serverSocket = new ServerSocket(serverPORT);
            for(;;) {
                socket = serverSocket.accept();
                socket.setTcpNoDelay(true);

                inStream = new DataInputStream(socket.getInputStream());
                outStream = new DataOutputStream(socket.getOutputStream());

                int AMOUNT = inStream.readInt();

                for( int i = 0; i < AMOUNT; i++ ) {
                    inStream.readByte();
                    outStream.writeByte(1);
                }

                outStream.writeChar('s');
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


}

