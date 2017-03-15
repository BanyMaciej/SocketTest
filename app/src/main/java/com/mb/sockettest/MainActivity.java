package com.mb.sockettest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.net.ServerSocket;
import java.security.Permission;
import java.security.Permissions;
import java.util.jar.Manifest;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



         findViewById(R.id.serverButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ServerActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.clientButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ClientActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.soundTestButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TryActivity.class);
                startActivity(intent);
            }
        });
    }


}
