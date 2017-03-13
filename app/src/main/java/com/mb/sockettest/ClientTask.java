package com.mb.sockettest;

import android.app.Activity;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
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
    MediaPlayer mPlayer;
    Timer timer;

    public ClientTask(Activity activity, String SocketAddress) {
        callingActivity = activity;
        this.SocketAddress = SocketAddress;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        Socket socket= null;
        DataInputStream inStream = null;
        DataOutputStream outStream = null;

        int AMOUNT = 3;

        try {
            socket = new Socket(SocketAddress, SocketPORT);
            socket.setTcpNoDelay(true);

            mPlayer = MediaPlayer.create(callingActivity, R.raw.tick);

            inStream = new DataInputStream(socket.getInputStream());
            outStream = new DataOutputStream(socket.getOutputStream());
            Log.d("DEBUG", "start");
            long suma = 0;
            outStream.writeInt(AMOUNT);
            int c = 0;
            long t1 = 0, t2 = 0;
            for(int i = 0; i < AMOUNT; i++ ) {
                t1 = System.nanoTime();
                outStream.writeChar('a');
                inStream.readChar();
                t2 = System.nanoTime();
                long diff = t2-t1;
                Log.d("DEBUG", "timeDiff: " + diff);
                if( diff < 100000000L ) {
                    c++;
                    suma += diff;
                }
            }


            outStream.writeChar('s');
            startCounter((int) (suma/(2000000*c)));


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

//    private void startCounter(int delay) {
//        timer = new Timer();
//        timer.scheduleAtFixedRate(new TimerTask() {
//            boolean colored = false;
//            @Override
//            public void run() {
//                Counter++;
//                mPlayer.start();
//                callingActivity.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        if( !colored ) {
//                            callingActivity.findViewById(R.id.sendButton).setBackgroundColor(Color.BLUE);
//                            colored = true;
//                        } else {
//                            callingActivity.findViewById(R.id.sendButton).setBackgroundColor(Color.WHITE);
//                            colored = false;
//                        }
//                    }
//                });
//
//                if( callingActivity.isFinishing() ) {
//                    Log.d("DEBUG", "is finishing");
//                    timer.cancel();
//                }
//              //  Log.d("TIME-D", Counter + "");
//            }
//        }, delay, 1000);
//
//        callingActivity.findViewById(R.id.stopTimerBtn_Client).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                timer.cancel();
//            }
//        });
//
//    }

    private void startCounter(int delay) {
        Log.d("DEBUG", "Delay: " + delay);
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                mPlayer.start();

            }
        }, delay);

    }


}
