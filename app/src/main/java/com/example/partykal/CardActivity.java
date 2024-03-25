package com.example.partykal;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class CardActivity extends AppCompatActivity {
    DBM dbm;
    EditText et_add_card;
    TextView tv_card_display_name;
    TextView tv_entry_amount;
    TextView tv_card_amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        dbm = new DBM(this);
        et_add_card = findViewById(R.id.et_add_card);
        tv_card_display_name = findViewById(R.id.tv_card_display_name);
        tv_entry_amount = findViewById(R.id.tv_entry_amount);
        tv_card_amount = findViewById(R.id.tv_card_amount);
    }
    public void refreshValues(View view){
        Cursor result = dbm.getCards();
        String temp_string;
        temp_string = R.string.tv_card_amount + Integer.toString(result.getCount());
        tv_card_amount.setText(temp_string);
        result = dbm.getSimilarCardTitle(et_add_card.getText().toString());
        temp_string = R.string.tv_card_amount + Integer.toString(result.getCount());
        tv_entry_amount.setText(temp_string);
    }
    public void addCard(View view){
        if(et_add_card.getText().toString().isEmpty()){
            return;
        }
        Card card = new Card("","");
        card.title = et_add_card.getText().toString();
        card.description = "bruh";
        dbm.addCard(card);
        et_add_card.setText("");
    }
}