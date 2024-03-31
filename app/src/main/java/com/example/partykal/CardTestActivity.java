package com.example.partykal;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CardTestActivity extends AppCompatActivity {
    DBM dbm;
    EditText et_add_card;
    TextView tv_card_display_name;
    TextView tv_entry_amount;
    TextView tv_card_amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardtest);
        dbm = new DBM(this);
        et_add_card = findViewById(R.id.et_add_card);
        tv_card_display_name = findViewById(R.id.tv_card_display_name);
        tv_entry_amount = findViewById(R.id.tv_entry_amount);
        tv_card_amount = findViewById(R.id.tv_card_amount);
    }
    public void refreshValues(View view){
        String input = et_add_card.getText().toString();
        String temp_string;
        temp_string = "Similar card: " + dbm.getSimilarCardTitle(input);
        tv_card_display_name.setText(temp_string);
        temp_string = "All " + getResources().getString(R.string.tv_card_amount) + dbm.getSimilarCardCount("");
        tv_card_amount.setText(temp_string);
        temp_string = "Similar " + getResources().getString(R.string.tv_card_amount) + dbm.getSimilarCardCount(input);
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