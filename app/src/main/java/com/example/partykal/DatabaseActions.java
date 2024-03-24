package com.example.partykal;

public class DatabaseActions {
    private DatabaseActions(){}

    public static class Actions{
        public static final String TABLE_NAME = "cards";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_DESCRIPTION = "description";

        public static final String CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        COLUMN_TITLE + " TEXT," +
                        COLUMN_DESCRIPTION + " TEXT)";
        public static final String DROP_TABLE =
                "DROP TABLE " + TABLE_NAME;
    }
}
