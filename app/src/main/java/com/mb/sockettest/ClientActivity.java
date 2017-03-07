package com.mb.sockettest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ClientActivity extends AppCompatActivity {

    ClientTask clientTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        clientTask = new ClientTask(this);

        findViewById(R.id.sendButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 clientTask.execute();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clientTask.timer.cancel();
    }
}
