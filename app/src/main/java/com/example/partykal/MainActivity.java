package com.example.partykal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText et_set_time_limit;
    TextView tv_last_score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_set_time_limit = findViewById(R.id.et_set_time_limit);
        et_set_time_limit.setText("" + (PM.getTimer(this)/60));
        tv_last_score = findViewById(R.id.tv_last_score);
        tv_last_score.setText(getResources().getString(R.string.tv_last_score) + PM.getPoints(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        tv_last_score.setText(getResources().getString(R.string.tv_last_score) + PM.getPoints(this));
        et_set_time_limit.setText("" + (PM.getTimer(this)/60));
    }

    public void onStartBtn(View view){// Zapne View s náhodnou kartou (a resetuje body)
        Intent intent = new Intent(MainActivity.this, CardActivity.class);
        PM.resetPoints(this);
        if(!et_set_time_limit.getText().toString().isEmpty()) {
            if (Integer.parseInt(et_set_time_limit.getText().toString()) > 0) {
                PM.setTimer(this, Integer.parseInt(et_set_time_limit.getText().toString())*60);
            } else {
                PM.setTimer(this, PM.DEFAULT_TIME_LIMIT);
            }
        }else{
            PM.setTimer(this, PM.DEFAULT_TIME_LIMIT);
        }
        startActivity(intent);
    }
    public void cardTestBtn(View view){// Zapne View pro upravování karet
        Intent intent = new Intent(MainActivity.this, CardTestActivity.class);
        startActivity(intent);
    }
}