package com.example.partykal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onStartBtn(View view){
        Intent intent = new Intent(MainActivity.this, CardActivity.class);
        PM.resetPoints(this);
        startActivity(intent);
    }
    public void cardTestBtn(View view){
        Intent intent = new Intent(MainActivity.this, CardTestActivity.class);
        startActivity(intent);
    }
}