package com.marinemammalapp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by adheesh on 08/09/18.
 */

public class AppPreferences {


    private static final String APP_SHARED_PREFS = AppPreferences.class.getSimpleName();
    private SharedPreferences _sharedPrefs;
    private SharedPreferences.Editor _prefsEditor;

    private static String USER_NAME = "user_name";
    private static String PHONE_NUMBER = "phone_number";


    public AppPreferences(Context context) {
        try {
            this._sharedPrefs = context.getSharedPreferences(APP_SHARED_PREFS, Activity.MODE_PRIVATE);
            this._prefsEditor = _sharedPrefs.edit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearPreferences() {
//		Editor editor =
//				getSharedPreferences(APP_SHARED_PREFS, Context.MODE_PRIVATE).edit();
        try {
            _prefsEditor.clear();
            _prefsEditor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String getUserName() {
        return _sharedPrefs.getString(USER_NAME, "");
    }

    public void setUserName(String username) {
        _prefsEditor.putString(USER_NAME, username);
        _prefsEditor.commit();
    }

    public String getPhNum() {
        return _sharedPrefs.getString(PHONE_NUMBER, "");
    }

    public void setPhNum(String ph_num) {
        _prefsEditor.putString(PHONE_NUMBER, ph_num);
        _prefsEditor.commit();
    }



}
