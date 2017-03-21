package com.mb.sockettest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class ServerActivity extends AppCompatActivity {
    ServerThread serverThread;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);

        serverThread = new ServerThread(this);

        findViewById(R.id.startButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( !serverThread.isAlive() ) {
                    serverThread.start();
                }
            }
        });

        findViewById(R.id.playButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                serverThread.mPlayer.start();
            }
        });

        findViewById(R.id.stopButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                serverThread.mPlayer.stop();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            serverThread.interrupt();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            serverThread.interrupt();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
