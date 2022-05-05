package com.example.storeapplication.utiles;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.widget.AppCompatRadioButton$InspectionCompanion;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.fragment.app.FragmentActivity;

public class SessionManager {
    private Application application;
    private final SharedPreferences sharedPreferences;

    {
        assert false;
        sharedPreferences = application.getSharedPreferences("storeApplication",Context.MODE_PRIVATE);
    }

    final String USER_TOKEN = "user_token";


    public void saveAuthToken(String token) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_TOKEN,token);
        editor.apply();
    }

    public String fetchAuthToken(){
        return sharedPreferences.getString(USER_TOKEN,null);
    }
}

