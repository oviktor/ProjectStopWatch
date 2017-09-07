package com.example.oviktor.stopwatch;

import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.TextView;
import android.view.KeyEvent;


import java.util.Timer;
import java.util.TimerTask;
import java.lang.Object;
import java.util.logging.LogRecord;

public class MainActivity extends AppCompatActivity {

    private volatile int seconds = 0;
    private volatile boolean running = false;
    TextView textView;
    Timer timer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView)findViewById(R.id.time_view);

        Button start = (Button) findViewById(R.id.btnStart);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickStart();
            }
        });

        Button stop = (Button) findViewById(R.id.btnStop);
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickStop();
            }
        });

        Button reset = (Button) findViewById(R.id.btnReset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickReset();
            }
        });
        runTimer();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int action = event.getAction();
        int keyCode = event.getKeyCode();
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                if (action == KeyEvent.ACTION_UP) {
                    if (event.getEventTime() - event.getDownTime() > ViewConfiguration.getLongPressTimeout()) {
                        onClickStart();
                    } else {
                        onClickStart();
                    }
                }
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if (action == KeyEvent.ACTION_UP) {
                    if (event.getEventTime() - event.getDownTime() > ViewConfiguration.getLongPressTimeout()) {
                        onClickReset();
                    } else {
                        onClickStop();
                    }
                }
                return true;
            default:
                return super.dispatchKeyEvent(event);
        }
    }

    @Override
    public void onDestroy(){
        if(timer!=null)
            timer.cancel();
        super.onDestroy();
    }

    public void onClickStart() {
        running = true;
    }

    public void onClickStop() {
        running = false;
    }

    public void onClickReset() {
        running = false;
        seconds = 0;

    }

    private void runTimer(){


        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                if (running)
                seconds++;

               final int hours = seconds/3600;
               final int minutes = (seconds%3600)/60;
               final int sec = seconds%60;
               //final double milisec = seconds/1000;

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(String.format("%d:%02d:%02d", hours, minutes, sec));

                    }
                });


            }
        }, 0, 1000);
    }
}
