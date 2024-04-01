package com.example.partykal;

import android.content.Context;
import android.content.SharedPreferences;

public class PM {
    private static final String PREF_NAME = "PartyKalPreferences";
    private static final String POINTS_KEY = "points";
    private PM(){}
    private static SharedPreferences getSP(Context context){
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }
    public static void setPoints(Context context, int points){
        getSP(context).edit().putInt(POINTS_KEY, points).apply();
    }
    public static int getPoints(Context context){
        return getSP(context).getInt(POINTS_KEY, 0);
    }
    public static int addPoints(Context context, int added_points){
        int points = getPoints(context) + added_points;
        setPoints(context, points);
        return points;
    }
    public static void resetPoints(Context context){
        setPoints(context, 0);
    }
}
