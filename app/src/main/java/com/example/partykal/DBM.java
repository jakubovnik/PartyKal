package com.example.partykal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBM extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "party_kal.db";
    private static final int DATABASE_VERSION = 1;
    private static final String CARD_TABLE_NAME = "card";

    public DBM(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " +
                CARD_TABLE_NAME + "(" +
                "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "title TEXT NOT NULL UNIQUE," +
                "description TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CARD_TABLE_NAME);
        onCreate(db);
    }
    public Boolean addCard(Card card){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", card.title);
        contentValues.put("description", card.description);
        long result = db.insert(CARD_TABLE_NAME, null, contentValues);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }
    public Boolean updateCard(Card card){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("description", card.description);
        Cursor cursor = db.rawQuery("SELECT * FROM "+ CARD_TABLE_NAME +" WHERE title = ?", new String[]{card.title});
        if(cursor.getCount()>0) {
            long result = db.update(CARD_TABLE_NAME, contentValues, "title=?", new String[]{card.title});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }else{
            return false;
        }
    }
    public Boolean deleteCard(Card card){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ CARD_TABLE_NAME +" WHERE title = ?", new String[]{card.title});
        if(cursor.getCount()>0) {
            long result = db.delete(CARD_TABLE_NAME, "title=?", new String[]{card.title});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }else{
            return false;
        }
    }
    public Cursor getCards(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM "+ CARD_TABLE_NAME, null);
    }
}
