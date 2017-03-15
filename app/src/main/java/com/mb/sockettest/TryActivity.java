package com.mb.sockettest;

import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class TryActivity extends AppCompatActivity {

    TextView lt, gt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_try);

        lt = (TextView) findViewById(R.id.lt);
        gt = (TextView) findViewById(R.id.gt);


    }
}
