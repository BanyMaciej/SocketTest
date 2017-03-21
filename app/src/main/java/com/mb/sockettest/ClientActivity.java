package com.mb.sockettest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.Timer;

public class ClientActivity extends AppCompatActivity {
    Timer timer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);



        findViewById(R.id.sendButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ip = ((EditText) findViewById(R.id.ipEditText)).getText().toString();
                ClientTask clientTask =new ClientTask(ClientActivity.this,ip);
                clientTask.execute();
                try {
                    timer = clientTask.timer;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("DEBUG", "destroyed");
        if( timer != null ) {
            timer.cancel();
        }
    }
}
