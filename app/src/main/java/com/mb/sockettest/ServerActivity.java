package com.mb.sockettest;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ToggleButton;

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

//        findViewById(R.id.playButton).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mPlayer.start();
//            }
//        });
//
//        findViewById(R.id.stopButton).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mPlayer.stop();
//            }
//        });
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
