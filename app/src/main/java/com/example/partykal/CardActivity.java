package com.example.partykal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class CardActivity extends AppCompatActivity {
    DBM dbm;
    TextView tv_points;
    TextView tv_card_name;
    TextView tv_card_description;
    TextView tv_card_points;
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
        cl_card = findViewById(R.id.cl_card);
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
        if(card_finished){
            PM.addPoints(this, Integer.parseInt(tv_card_points.getText().toString()));
            String temp_string = getResources().getString(R.string.tv_points) + PM.getPoints(this);
            tv_points.setText(temp_string);
        }
        setCardStateOff();
        Card card = dbm.getRandomCard();
        tv_card_name.setText(card.title);
        tv_card_description.setText(card.description);
        tv_card_points.setText(Integer.toString(card.points));
    }
    public void setCardStateOn(){
        cl_card.setBackground(getDrawable(R.drawable.card_border_on));
        card_finished = true;
    }
    public void setCardStateOff(){
        cl_card.setBackground(getDrawable(R.drawable.card_border_off));
        card_finished = false;
    }
    public void switchCardState(View view){
        if(!card_finished){
            setCardStateOn();
        }else{
            setCardStateOff();
        }
    }
}