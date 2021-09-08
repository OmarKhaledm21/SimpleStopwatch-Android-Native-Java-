package com.projects.activitylifecycle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private int seconds = 0;
    private boolean running;
    private boolean wasRunning;

    /** LIFECYCLE **/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState!=null){
            seconds = (int) savedInstanceState.get("SECONDS");
            running = (boolean) savedInstanceState.get("STATUS");
            wasRunning = (boolean) savedInstanceState.get("past_status");
        }

        runTimer();
    }

    @Override
    protected void onStart() {
        super.onStart();
        running = wasRunning;
        if(wasRunning){
            Toast.makeText(this, "Stopwatch resumed!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("SECONDS",seconds);
        outState.putBoolean("STATUS",running);
        outState.putBoolean("past_status",wasRunning);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        wasRunning = running;
        running = false;
        if (wasRunning) {
            Toast.makeText(this, "Stopwatch paused!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        //Called when application is closed completely.
        super.onDestroy();
    }

    /** BUTTONS AND METHODS **/

    public void onClickStart(View view) {
        running = true;
    }

    public void onClickStop(View view) {
        running = false;
    }

    public void onClickReset(View view) {
        running = false;
        seconds = 0;
    }

    private void runTimer(){
        final TextView timeView = (TextView) findViewById(R.id.counterView);
        final Handler handler = new Handler();

        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds/3600;
                int minutes = (seconds%3600)/60;
                int secs = seconds%60;

                String time = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, secs);
                timeView.setText(time);
                if (running) {
                    seconds++;
                }
                handler.postDelayed(this,1000);
            }
        });

    }
}