package com.mb.sockettest;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class SoundTestActivity extends AppCompatActivity {
    MediaPlayer mPlayer;
    MediaRecorder mRecorder;
    Timer timer;

    boolean firstCheck = true;
    long startTime, endTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound_test);

        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mRecorder.setOutputFile("/dev/null");
        try {
            mRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mRecorder.start();

        mPlayer = MediaPlayer.create(this, R.raw.tick);
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                int amp = mRecorder.getMaxAmplitude();
                if( amp > 500 ) {
                    if( firstCheck ) {
                        firstCheck = false;
                        endTime = System.nanoTime();
                        //Log.d("DEBUG", "amp: " + amp + ", time: " + System.nanoTime());
                    }
                }
            }
        }, 0, 20);

        findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTime = System.nanoTime();
                Log.d("DEBUG", "time: " + startTime);
                mPlayer.start();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if( timer != null )
                            timer.cancel();
                        if( mRecorder != null )
                            mRecorder.stop();
                        calcDiff();
                    }
                }, 1000);
            }
        });
    }

    private void calcDiff() {
        Log.d("DEBUG", "timeDiff: " + (endTime - startTime));
    }
}
