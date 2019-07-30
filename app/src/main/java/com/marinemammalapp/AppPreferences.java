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
    private static String MAMMAL_HAS_FIN  = "mammal_has_fin";
    private static String MAMMAL_HAS_BEAK = "mammal_has_beak";
    private static String MAMMAL_COLOR_GREY = "mammal_color_grey";
    private static String MAMMAL_COLOR_PINK = "mammal_color_pink";
    private static String MAMMAL_IS_ALIVE = "mammal_is_alive";
    private static String IMAGE_URL = "image_url";
    private static String VERSION_CODE = "1";




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

    public String getMammalHasFin() {
        return _sharedPrefs.getString(MAMMAL_HAS_FIN, "");
    }

    public void setMammalHasFin(String isMammalHasFin){
        _prefsEditor.putString(MAMMAL_HAS_FIN, isMammalHasFin);
        _prefsEditor.commit();
    }

    public String getMammalHasBeak() {
        return _sharedPrefs.getString(MAMMAL_HAS_BEAK, "");
    }

    public void setMammalHasBeak(String isMammalHasBeak){
        _prefsEditor.putString(MAMMAL_HAS_BEAK, isMammalHasBeak);
        _prefsEditor.commit();
    }

    public String getMammalColorGrey() {
        return _sharedPrefs.getString(MAMMAL_COLOR_GREY, "");
    }

    public void setMammalColorGrey(String isMammalColorGrey){
        _prefsEditor.putString(MAMMAL_COLOR_GREY, isMammalColorGrey);
        _prefsEditor.commit();
    }

    public String getMammalColorPink() {
        return _sharedPrefs.getString(MAMMAL_COLOR_PINK, "");
    }

    public void setMammalColorPink(String isMammalColorPink){
        _prefsEditor.putString(MAMMAL_COLOR_PINK, isMammalColorPink);
        _prefsEditor.commit();
    }

    public String getMammalAlive() {
        return _sharedPrefs.getString(MAMMAL_IS_ALIVE, "");
    }

    public void setMammalAlive(String isMammalAlive){
        _prefsEditor.putString(MAMMAL_IS_ALIVE, isMammalAlive);
        _prefsEditor.commit();
    }

    public String getImageUrl() {
        return _sharedPrefs.getString(IMAGE_URL, "");
    }

    public void setImageUrl(String imageUrl){
        _prefsEditor.putString(IMAGE_URL, imageUrl);
        _prefsEditor.commit();
    }

    public String getVersionCode() {
        return _sharedPrefs.getString(VERSION_CODE, "1");
    }

    public void setVersionCode(String versionCode){
        _prefsEditor.putString(VERSION_CODE, versionCode);
        _prefsEditor.commit();
    }



}
