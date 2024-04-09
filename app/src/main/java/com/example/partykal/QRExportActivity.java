package com.example.partykal;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.ArrayList;

public class QRExportActivity extends AppCompatActivity {
    DBM dbm;
    ImageView iv_qr_code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrexport);
        dbm = new DBM(this);
        iv_qr_code = findViewById(R.id.iv_qr_code);
        generateQRCode();
    }
    private void generateQRCode() {
        Cursor card_cursor = dbm.getCards();
        card_cursor.moveToFirst();
        ArrayList<Card> cards = new ArrayList<>();
        String card_title;
        String card_description;
        int card_points;
        int title_index = card_cursor.getColumnIndex("title");
        int description_index = card_cursor.getColumnIndex("description");
        int points_index = card_cursor.getColumnIndex("points");
        while (true) {
            card_title = card_cursor.getString(title_index);
            card_description = card_cursor.getString(description_index);
            card_points = card_cursor.getInt(points_index);
            cards.add(new Card(card_title, card_description, card_points));
            if (!card_cursor.moveToNext()) {
                break;
            }
        }
        Gson gson = new Gson();
        String json = gson.toJson(cards);
        if(json.length() > 2950){
            CharSequence text;
            text = getResources().getString(R.string.toast_qr_exceeded);
            text += " " + json.length() + "/" + 2953;
            Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
            toast.show();
            finish();
        }
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = qrCodeWriter.encode(json, BarcodeFormat.QR_CODE, 1024, 1024);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            iv_qr_code.setImageBitmap(barcodeEncoder.createBitmap(bitMatrix));
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
}