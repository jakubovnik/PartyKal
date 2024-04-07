package com.example.partykal;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.util.ArrayList;

public class CardTestActivity extends AppCompatActivity {
    DBM dbm;
    EditText et_add_card_name;
    EditText et_add_card_description;
    EditText et_add_card_points;
    TextView tv_card_display_name;
    TextView tv_entry_amount;
    TextView tv_card_amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardtest);
        dbm = new DBM(this);
        et_add_card_name = findViewById(R.id.et_add_card_name);
        et_add_card_description = findViewById(R.id.et_add_card_description);
        et_add_card_points = findViewById(R.id.et_add_card_points);
        tv_card_display_name = findViewById(R.id.tv_card_display_name);
        tv_entry_amount = findViewById(R.id.tv_entry_amount);
        tv_card_amount = findViewById(R.id.tv_card_amount);
        refreshValues();
    }
    public void refreshValuesBtn(View view){
        refreshValues();
    }
    public void refreshValues(){
        String input = et_add_card_name.getText().toString();
        String temp_string;
        temp_string = "Similar card: " + dbm.getSimilarCardTitle(input);
        tv_card_display_name.setText(temp_string);
        temp_string = "All " + getResources().getString(R.string.tv_card_amount) + dbm.getSimilarCardCount("");
        tv_card_amount.setText(temp_string);
        temp_string = "Similar " + getResources().getString(R.string.tv_card_amount) + dbm.getSimilarCardCount(input);
        tv_entry_amount.setText(temp_string);
    }
    public void addCardBtn(View view){
        if(et_add_card_name.getText().toString().isEmpty()){
            return;
        }
        Card card = new Card("","", 0);
        card.title = et_add_card_name.getText().toString();
        card.description = et_add_card_description.getText().toString();
        card.points = Integer.parseInt(et_add_card_points.getText().toString());
        dbm.addCard(card);
        et_add_card_name.setText("");
        et_add_card_description.setText("");
        et_add_card_points.setText("");
    }
    public void importCardsBtn(View view){/*
        Intent intent = new Intent(CardTestActivity.this, QRImportActivity.class);
        startActivity(intent);*/
    }
    public void exportCardsBtn(View view){
        Intent intent = new Intent(CardTestActivity.this, QRExportActivity.class);
        startActivity(intent);
    }
}