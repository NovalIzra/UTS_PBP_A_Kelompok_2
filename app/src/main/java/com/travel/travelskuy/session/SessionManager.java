package com.travel.travelskuy.session;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SessionManager {

    static final String KEY_USER_TEREGISTER = "user", KEY_PASS_TEREGISTER = "pass";
    static final String KEY_USERNAME_SEDANG_LOGIN = "Username_logged_in";
    static final String KEY_STATUS_SEDANG_LOGIN = "Status_logged_in";
    static final String KEY_ISLOGIN = "Login";

    private static SharedPreferences getSharedPreference(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setIsLogin(Context context, Boolean tipe){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putBoolean(KEY_ISLOGIN, tipe);
        editor.apply();
    }


    public static Boolean getIsLogin(Context context){
        return getSharedPreference(context).getBoolean(KEY_ISLOGIN,false);
    }




    public static void setIsUsername(Context context, String tipe){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString("username", tipe);
        editor.apply();
    }
    public static String getIsUsername(Context context){
        return getSharedPreference(context).getString("username","");
    }


    public static void setIsPassword(Context context, String tipe){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString("password", tipe);
        editor.apply();
    }

    public static String getIsPassword(Context context){
        return getSharedPreference(context).getString("password","");
    }

    public static void setIsFoto(Context context, String tipe){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString("foto", tipe);
        editor.apply();
    }

    public static String getIsFoto(Context context){
        return getSharedPreference(context).getString("foto","");
    }

    public static void setIsEmail(Context context, String tipe){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString("email", tipe);
        editor.apply();
    }

    public static String getIsEmail(Context context){
        return getSharedPreference(context).getString("email","");
    }




    public static void clearLoggedInUser(Context context) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.remove(KEY_USERNAME_SEDANG_LOGIN);
        editor.remove(KEY_STATUS_SEDANG_LOGIN);
        editor.apply();
    }
}
