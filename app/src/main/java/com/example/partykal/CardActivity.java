package com.example.partykal;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.Timer;
import java.util.TimerTask;

public class CardActivity extends AppCompatActivity {
    private Timer timer;
    private int remainingSeconds;
    private int TIME_LIMIT;
    DBM dbm;
    TextView tv_points;
    TextView tv_card_name;
    TextView tv_card_description;
    TextView tv_card_points;
    TextView tv_time_remaining;
    ConstraintLayout cl_card;
    boolean card_finished = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        dbm = new DBM(this);
        tv_points = findViewById(R.id.tv_points);
        String temp_string = getResources().getString(R.string.tv_points) + PM.getPoints(this);
        tv_points.setText(temp_string);
        tv_card_name = findViewById(R.id.tv_card_name);
        tv_card_description = findViewById(R.id.tv_card_description);
        tv_card_points = findViewById(R.id.tv_card_points);
        tv_time_remaining = findViewById(R.id.tv_time_remaining);
        cl_card = findViewById(R.id.cl_card);
        TIME_LIMIT = PM.getTimer(this);
        changeCard();
    }
    private void startTimerTask() {// Jediná funkce zkopírovaná z ChatGPT
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Update remaining time and display in TextView
                        int minutes = remainingSeconds / 60;
                        int seconds = remainingSeconds % 60;
                        String remainingTime = String.format("%02d:%02d", minutes, seconds);
                        tv_time_remaining.setText(getResources().getString(R.string.tv_time_remaining) + remainingTime);
                        // Decrease remaining time by 1 second
                        remainingSeconds--;
                        // Stop timer if remaining time reaches 0
                        if (remainingSeconds < 0) {
                            timer.cancel(); // Stop the timer
                            tv_time_remaining.setText(getResources().getString(R.string.tv_time_remaining) + "00:00");
                            changeCard();
                        }
                    }
                });
            }
        }, 0, 1000); // Execute every 1000 milliseconds (1 second)
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }
    public void changeCard(){ // vybere náhodnou kartu a přidá body (pokud je karta označená)
        if(card_finished){
            PM.addPoints(this, Integer.parseInt(tv_card_points.getText().toString()));
            String temp_string = getResources().getString(R.string.tv_points) + PM.getPoints(this);
            tv_points.setText(temp_string);
        }
        setCardStateOff();
        Card card = dbm.getRandomCard(this);
        tv_card_name.setText(card.title);
        tv_card_description.setText(card.description);
        tv_card_points.setText(Integer.toString(card.points));
        remainingSeconds = TIME_LIMIT;
        startTimerTask();
    }
    public void setCardStateOn(){
        cl_card.setBackground(getDrawable(R.drawable.card_border_on));
        card_finished = true;
    }
    public void setCardStateOff(){
        cl_card.setBackground(getDrawable(R.drawable.card_border_off));
        card_finished = false;
    }
    public void switchCardState(View view){ // přepne kartu na hotovou/nedokončenou
        if(!card_finished){
            setCardStateOn();
        }else{
            setCardStateOff();
        }
    }
}