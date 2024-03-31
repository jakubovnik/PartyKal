package com.example.partykal;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class CardActivity extends AppCompatActivity {
    TextView tv_points;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        tv_points = findViewById(R.id.tv_points);
        String temp_string = getResources().getString(R.string.tv_points) + PM.getPoints(this);
        tv_points.setText(temp_string);
    }
    public void addTenPoints(View view){
        PM.addPoints(this, 10);
        String temp_string = getResources().getString(R.string.tv_points) + PM.getPoints(this);
        tv_points.setText(temp_string);
    }
}