package com.mb.sockettest;

import android.app.Activity;
import android.os.AsyncTask;
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
    String SocketAddress = "192.168.0.102";

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
                outStream.writeByte(1);
                inStream.readByte();
                long t2 = System.nanoTime();
                long diff = t2-t1;
                Log.d("DEBUG", "timeDiff: " + diff);
                if( diff < 100000000L ) {
                    c++;
                    suma += diff;
                }
            }
            Log.d("DEBUG", c + ", average: " + (suma/c));

            if( inStream.readChar() == 's' ) {
                startCounter();
            }



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

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        callingActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(callingActivity, "timeDiff:" + timeDiff, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void startCounter() {

        final Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Counter++;
                Log.d("TIME", Counter + "");
            }
        }, 0, 1000);

        callingActivity.findViewById(R.id.stopTimerBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer.cancel();
            }
        });

    }
}
