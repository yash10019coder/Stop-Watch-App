package com.yash10019coder.stopwatchapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private Button start, stop, resume, reset;
    private long seconds = 0;
    private boolean isStopWatchRunning = false;
    private boolean wasStopWatchRunning=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState!=null){
            seconds = savedInstanceState.getLong("seconds");
            isStopWatchRunning = savedInstanceState.getBoolean("bool");
            wasStopWatchRunning=savedInstanceState.getBoolean("bool2");
            if (wasStopWatchRunning)
            {
                isStopWatchRunning=true;
                wasStopWatchRunning=false;
            }
        }
        setContentView(R.layout.activity_main);
        runStopwatch();
        textView = findViewById(R.id.textView);
        start = findViewById(R.id.button);
        stop = findViewById(R.id.button2);
        resume = findViewById(R.id.button3);
        reset = findViewById(R.id.button4);
    }

    private void runStopwatch() {
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                textView.setText(secondToReadabelTime(seconds));
//                Log.i("TAG", "run: running "+textView.getText());
                if(isStopWatchRunning)
                seconds++;
                handler.postDelayed(this, 1);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (isStopWatchRunning==false) {
                    isStopWatchRunning = true;
//                }
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isStopWatchRunning = false;
                wasStopWatchRunning=true;
            }
        });
        resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(wasStopWatchRunning==true) {
                    isStopWatchRunning = true;
                    wasStopWatchRunning=false;
////                    runStopwatch();
                }
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isStopWatchRunning = false;
//                wasStopWatchRunning=false;
                seconds = 0;
                textView.setText(secondToReadabelTime(seconds));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (wasStopWatchRunning) {
            isStopWatchRunning = true;
            wasStopWatchRunning = false;
        }
//        runStopwatch();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        if (isStopWatchRunning)
//        {
            isStopWatchRunning=false;
            wasStopWatchRunning=true;
//        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(isStopWatchRunning)
            isStopWatchRunning=false;
        wasStopWatchRunning=true;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (wasStopWatchRunning)
        {
            isStopWatchRunning=true;
            wasStopWatchRunning=false;
        }
    }
    //    @Override
//    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        seconds=savedInstanceState.getLong("seconds");
//        isStopWatchRunning=savedInstanceState.getBoolean("bool");
//        wasStopWatchRunning=savedInstanceState.getBoolean("bool2");
//        Log.i("TAG", "onRestoreInstanceState: seconds "+seconds);
//        Log.i("TAG", "onRestoreInstanceState: "+isStopWatchRunning);
//        Log.i("TAG", "onRestoreInstanceState: "+wasStopWatchRunning);
//    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("seconds",seconds);
        outState.putBoolean("bool",isStopWatchRunning);
        outState.putBoolean("bool2",wasStopWatchRunning);
    }


    protected String secondToReadabelTime(long t) {
        String s;
        long rem = 0;
        long div = 0;
        div =(long) (t / 58.21);
        rem =(long) (t % 58.21);
        s = String.valueOf(rem);

        t = div;
        div = t / 60;
        rem = t % 60;
        s = String.valueOf(rem) + " : " + s;

        t = div;
        div = t / 60;
        rem = t % 60;
        s = String.valueOf(rem) + " : " + s;

        return s;
    }
}