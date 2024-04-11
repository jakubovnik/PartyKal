package com.example.partykal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class CardTestActivity extends AppCompatActivity { // Místo pro editování karet
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
    public void refreshValuesBtn(View view){ // Funkce pro tlačítko
        refreshValues();
    }
    public void refreshValues(){ // Zobrazí celkový počet karet, počet karet podobných vyhledávání a název nejpodobnější karty
        String input = et_add_card_name.getText().toString();
        String temp_string;
        temp_string = "Similar card: " + dbm.getSimilarCardTitle(input);
        tv_card_display_name.setText(temp_string);
        temp_string = "All " + getResources().getString(R.string.tv_card_amount) + dbm.getSimilarCardCount("");
        tv_card_amount.setText(temp_string);
        temp_string = "Similar " + getResources().getString(R.string.tv_card_amount) + dbm.getSimilarCardCount(input);
        tv_entry_amount.setText(temp_string);
    }
    public void addCardBtn(View view){// Přidá kartu (pokud není vše vyplněno, nebo už existuje karta se stejným jménem, hodí toast error)
        if(
                et_add_card_name.getText().toString().isEmpty() ||
                et_add_card_description.getText().toString().isEmpty() ||
                et_add_card_points.getText().toString().isEmpty()
        ){
            CharSequence text = getResources().getString(R.string.toast_edit_text_not_filled);
            Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        Card card = new Card("","", 0);
        card.title = et_add_card_name.getText().toString();
        card.description = et_add_card_description.getText().toString();
        card.points = Integer.parseInt(et_add_card_points.getText().toString());



        if(dbm.getSimilarCardTitle(card.title).equals(card.title)){
            CharSequence text = getResources().getString(R.string.toast_card_already_exists);
            Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        dbm.addCard(card);
        et_add_card_name.setText("");
        et_add_card_description.setText("");
        et_add_card_points.setText("");
        refreshValues();
    }
    public void clearAllCardsBtn(View view){//
        dbm.clearAllCards(et_add_card_name.getText().toString());
        if(dbm.getSimilarCardCount("") > 0){
            CharSequence text = getResources().getString(R.string.toast_enter_passcode);
            Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        et_add_card_name.setText("");
        refreshValues();
    }
    public void importCardsBtn(View view){
        //bruh wtf is going on with the qr scan library? why is everything deprecated?
        IntentIntegrator intentIntegrator = new IntentIntegrator(CardTestActivity.this);
        intentIntegrator.setOrientationLocked(true);
        intentIntegrator.setPrompt("Scan a qr code");
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        intentIntegrator.initiateScan();
    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) { // většina zkopírována ze stránky: https://easyonecoder.com/android/basic/QRCodeScanner
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(intentResult != null){
            String json = intentResult.getContents();
            if(json != null){
                Gson gson = new Gson();
                Type cardListType = new TypeToken<ArrayList<Card>>(){}.getType(); //řádek zkopírován z chatGPT
                ArrayList<Card> cards = gson.fromJson(json, cardListType);
                for(Card card : cards){
                    dbm.addCard(card);
                }
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }
        refreshValues();
    }
    public void exportCardsBtn(View view){
        if(dbm.getSimilarCardCount("")<1){
            CharSequence text = getResources().getString(R.string.toast_not_enough_cards);
            Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        Intent intent = new Intent(CardTestActivity.this, QRExportActivity.class);
        startActivity(intent);
    }
}