package com.example.partykal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBM extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "party_kal.db";
    private static final int DATABASE_VERSION = 2;
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
                "description TEXT," +
                "points INTEGER NOT NULL)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CARD_TABLE_NAME);
        onCreate(db);
    }
    public void addCard(Card card){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", card.title);
        contentValues.put("description", card.description);
        contentValues.put("points", card.points);
        db.insert(CARD_TABLE_NAME, null, contentValues);
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
    public String getSimilarCardTitle(String title){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ CARD_TABLE_NAME +" WHERE title LIKE '%"+ title + "%';", null);
        String result;
        if(cursor != null){
            if(cursor.moveToFirst()){
                int i = cursor.getColumnIndex("title");
                result = cursor.getString(i);
            }else{
                result = "";
            }
            cursor.close();
        }else{
            result = "";
        }
        return result;
    }
    public int getSimilarCardCount(String title){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ CARD_TABLE_NAME +" WHERE title LIKE '%"+ title + "%';", null);
        int result = 0;
        if(cursor != null){
            result = cursor.getCount();
            if(result > 1){
                cursor.moveToFirst();
                int i = cursor.getColumnIndex("title");
                if(cursor.getString(i).equals(title)){
                    result = 1;
                }
            }
            cursor.close();
        }
        return result;
    }
    public Card getRandomCard(Context context){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM " +
                CARD_TABLE_NAME,
                null);
        if(cursor != null) {
            if (cursor.getCount() > 0) {
                int randomInt = (int) (Math.random() * cursor.getCount());
                cursor.moveToPosition(randomInt);
                int title_index = cursor.getColumnIndex("title");
                int desc_index = cursor.getColumnIndex("description");
                int points_index = cursor.getColumnIndex("points");
                String title_string = cursor.getString(title_index);
                String desc_string = cursor.getString(desc_index);
                int points_int = cursor.getInt(points_index);
                cursor.close();
                return new Card(
                        title_string,
                        desc_string,
                        points_int
                );
            }
            cursor.close();
        }
        return new Card("error", context.getResources().getString(R.string.description_no_cards_in_database),0);
    }
    public void clearAllCards(String password){
        SQLiteDatabase db = this.getWritableDatabase();
        if(password.equals("confirm")){
            db.execSQL("DELETE FROM " + CARD_TABLE_NAME);
        }
        db.close();
    }
}
