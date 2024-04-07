package com.example.partykal;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

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
        while (true) {
            card_title = card_cursor.getString(1);
            card_description = card_cursor.getString(2);
            card_points = card_cursor.getInt(3);
            cards.add(new Card(card_title, card_description, card_points));
            if (!card_cursor.moveToNext()) {
                break;
            }
        }
        Gson gson = new Gson();
        String json = gson.toJson(cards);
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = qrCodeWriter.encode(json, BarcodeFormat.QR_CODE, 512, 512);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            iv_qr_code.setImageBitmap(barcodeEncoder.createBitmap(bitMatrix));
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
}