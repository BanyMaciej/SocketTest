package com.mb.sockettest;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class TryActivity extends AppCompatActivity {

    TextView wifiInfo;

    WifiManager wifiManager;

    BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_try);

        wifiInfo = (TextView) findViewById(R.id.wifiInfo);
        setupWifi();

        wifiInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wifiManager.startScan();
            }
        });
    }

    public double calculateDistance(double decibels, double frequency) {
        double ex = (-27.55 - (20* Math.log10(frequency)) + Math.abs(decibels)) / 20;
        return Math.pow(10.00, ex);
    }

    public void setupWifi() {
        wifiManager =(WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                List<ScanResult> results = wifiManager.getScanResults();
                //for( ScanResult s : results ) {
                if (results.size() > 0) {
                    ScanResult s = results.get(0);
                    String text =  "ssid: " + s.SSID + ", distance: " + calculateDistance(s.level, s.frequency) + "\n";
                    Log.d("DEBUG wifiInfo", text);
                    wifiInfo.setText(wifiInfo.getText() + text);
                } else {
                    Log.d("DEBUG wifiInfo", "no network");
                }
                //}
            }
        };

        registerReceiver(broadcastReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
        }
    }
}

