package com.example.partykal;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class CardActivity extends AppCompatActivity {
    DBM dbm;
    EditText et_add_card;
    TextView tv_entry_amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        dbm = new DBM(this);
        et_add_card = findViewById(R.id.et_add_card);
        tv_entry_amount = findViewById(R.id.tv_entry_amount);
    }
    public void addCard(View view){
        if(et_add_card.getText().toString().isEmpty()){
            return;
        }
        Card card = new Card("","");
        card.title = et_add_card.getText().toString();
        card.description = "bruh";
        dbm.addCard(card);
        Cursor result = dbm.getCards();
        tv_entry_amount.setText(Integer.toString(result.getCount()));
    }
}