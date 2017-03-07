package com.mb.sockettest;

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Maciek on 2017-03-07.
 */

public class ClientTask extends AsyncTask<Void, Void, Void> {


    int SocketPORT = 4321;
    String SocketAddress = "192.168.0.101";

    long timeDiff = -1;
    Activity callingActivity;

    int Counter = 0;

    public ClientTask(Activity activity) {
        callingActivity = activity;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        Socket socket= null;
        DataInputStream inStream = null;
        DataOutputStream outStream = null;

        long sendTime, receiveTime;

        String outMessage = "";

        int AMOUNT = 10;

        try {
            socket = new Socket(SocketAddress, SocketPORT);
            socket.setTcpNoDelay(true);

            inStream = new DataInputStream(socket.getInputStream());
            outStream = new DataOutputStream(socket.getOutputStream());
            Log.d("DEBUG", "start");
            long suma = 0;
            outStream.writeInt(AMOUNT);
            int c = 0;
            for(int i = 0; i < AMOUNT; i++ ) {
                long t1 = System.nanoTime();
                outStream.writeChar('a');
                inStream.readChar();
                long t2 = System.nanoTime();
                long diff = t2-t1;
                Log.d("DEBUG", "timeDiff: " + diff);
                if( diff < 100000000L ) {
                    c++;
                    suma += diff;
                }
            }
            long avg = suma/c;
            Log.d("DEBUG", c + ", average: " + avg);

            outStream.writeChar('s');
            startCounter((int) (avg/2000000));

        } catch (IOException e) {
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

        return null;
    }

    private void startCounter(int delay) {

        final Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            boolean colored = false;
            @Override
            public void run() {
                Counter++;
                callingActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if( !colored ) {
                            callingActivity.findViewById(R.id.sendButton).setBackgroundColor(Color.BLUE);
                            colored = true;
                        } else {
                            callingActivity.findViewById(R.id.sendButton).setBackgroundColor(Color.WHITE);
                            colored = false;
                        }
                    }
                });
                Log.d("TIME-D", Counter + "");
            }
        }, delay, 1000);

        callingActivity.findViewById(R.id.stopTimerBtn_Client).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer.cancel();
            }
        });


    }
}