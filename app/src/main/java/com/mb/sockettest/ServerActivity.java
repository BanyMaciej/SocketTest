package com.mb.sockettest;

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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        serverThread.interrupt();
    }
}
