package com.example.partykal;

import android.content.Context;
import android.content.SharedPreferences;

public class PM {// Preferences manager pro správu bodů a času
    private static final String PREF_NAME = "PartyKalPreferences";
    private static final String POINTS_KEY = "points";
    private static final String TIME_KEY = "time_limit";
    public static final int DEFAULT_TIME_LIMIT = 20 * 60;
    private PM(){}
    private static SharedPreferences getSP(Context context){
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }
    public static void setTimer(Context context, int time){
        getSP(context).edit().putInt(TIME_KEY, time).apply();
    }
    public static int getTimer(Context context){
        if(getSP(context).contains(TIME_KEY)){
            return getSP(context).getInt(TIME_KEY, 0);
        }
        setTimer(context, DEFAULT_TIME_LIMIT);
        return DEFAULT_TIME_LIMIT;
    }
    public static void setPoints(Context context, int points){
        getSP(context).edit().putInt(POINTS_KEY, points).apply();
    }
    public static int getPoints(Context context){
        if(!getSP(context).contains(POINTS_KEY)){
            return 0;
        }
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
