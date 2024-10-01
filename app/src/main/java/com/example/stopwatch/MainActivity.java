package com.example.stopwatch;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView timeView;
    private final Handler handler = new Handler();
    private long startTime = 0L;
    private boolean isRunning = false;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timeView = findViewById(R.id.time_view);
        Button startButton = findViewById(R.id.start_button);
        Button stopButton = findViewById(R.id.stop_button);
        Button resetButton = findViewById(R.id.reset_button);

        startButton.setOnClickListener(v -> {
            if (!isRunning) {
                startTime = System.currentTimeMillis();
                handler.postDelayed(updateTimer, 0);
                isRunning = true;
            }
        });

        stopButton.setOnClickListener(v -> {
            if (isRunning) {
                handler.removeCallbacks(updateTimer);
                isRunning = false;
            }
        });

        resetButton.setOnClickListener(v -> {
            handler.removeCallbacks(updateTimer);
            isRunning = false;
            timeView.setText("00:00:00");
        });
    }

    private final Runnable updateTimer = new Runnable() {
        @SuppressLint("DefaultLocale")
        public void run() {
            long elapsedTime = System.currentTimeMillis() - startTime;
            int seconds = (int) (elapsedTime / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;
            int milliseconds = (int) (elapsedTime % 1000);
            timeView.setText(String.format("%02d:%02d:%03d", minutes, seconds, milliseconds));
            handler.postDelayed(this, 0);
        }
    };
}