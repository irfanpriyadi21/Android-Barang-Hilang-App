package com.example.barang_hilang;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {
    SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;
    public static final String NAME = "NAME";
    public Context context;
    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "LOGIN";
    private static final  String LOGIN = "IS_LOGIN";

    public SessionManager(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }
    public static void createSession(String name){
        editor.putBoolean(LOGIN, true);
        editor.putString(NAME, name);
        editor.apply();
    }
    public boolean isLogin(){
        return sharedPreferences.getBoolean(LOGIN,false);
    }
    public void checkLogin(){
        if (!this.isLogin()){
            Intent i = new Intent(context, MainActivity.class);
            context.startActivity(i);
            ((dashboard) context).finish();
        }
    }
    public HashMap<String, String> getUserDetail(){

        HashMap<String, String> user = new HashMap<>();
        user.put(NAME, sharedPreferences.getString(NAME,null));
        return user;
    }
    public void logout(){
        editor.clear();
        editor.commit();
        Intent i = new Intent(context, MainActivity.class);
        context.startActivity(i);
        ((dashboard) context).finish();
    }
}
