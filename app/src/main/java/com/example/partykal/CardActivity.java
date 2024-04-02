package com.example.partykal;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class CardActivity extends AppCompatActivity {
    DBM dbm;
    TextView tv_points;
    TextView tv_card_name;
    TextView tv_card_description;
    TextView tv_card_points;
    boolean card_finished;
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
        changeCard();
    }
    public void addTenPoints(View view){
        PM.addPoints(this, 10);
        String temp_string = getResources().getString(R.string.tv_points) + PM.getPoints(this);
        tv_points.setText(temp_string);
    }
    public void changeCardBtn(View view){
        changeCard();
    }
    public void changeCard(){
        Card card = dbm.getRandomCard();
        tv_card_name.setText(card.title);
        tv_card_description.setText(card.description);
        tv_card_points.setText(Integer.toString(card.points));
    }
    public void switchCardState(View view){

    }
}