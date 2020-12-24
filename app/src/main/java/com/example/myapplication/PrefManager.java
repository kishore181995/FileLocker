package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.lang.reflect.Type;
import java.util.ArrayList;

public class PrefManager {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "anr-welcome";

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    private static final String IS_USER_FIRST_TIME_LAUNCH = "IsUserirstTimeLaunch";
    private static final String USER_PIN = "user-token";
    private static final String USER_SECRET_ANSWER = "userName";
    private static final String USER_IMAGES = "userImage";
    private static final String USER_EMAIL ="email";
    private static final String MOB ="mobile";
    private static final String LOCATION_CHECK ="location";

    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public void setUserToken(String userToken)
    {
        editor.putString(USER_PIN,userToken);
        editor.commit();
    }

    public void setUserName(String userName)
    {
        editor.putString(USER_SECRET_ANSWER,userName);
        editor.commit();
    }



    public void setUserEmail(String userEmail)
    {
        editor.putString(USER_EMAIL,userEmail);
        editor.commit();
    }

    public void setUserMobile(String userMOb)
    {
        editor.putString(MOB,userMOb);
        editor.commit();
    }

    public void setIsUserFirstTimeLaunch(boolean isUserFirstTime) {
        editor.putBoolean(IS_USER_FIRST_TIME_LAUNCH, isUserFirstTime);
        editor.commit();
    }

    public boolean isUserFirstTimeLaunch()
    {
        return pref.getBoolean(IS_USER_FIRST_TIME_LAUNCH, true);
    }

    public void setLocationCheck(boolean locationCheck) {
        editor.putBoolean(LOCATION_CHECK, locationCheck);
        editor.commit();
    }

    public boolean LocationCheck()
    {
        return pref.getBoolean(LOCATION_CHECK, false);
    }

    public String getUserToken()
    {
        return pref.getString(USER_PIN,"");
    }

    public String getUserName()
    {
        return pref.getString(USER_SECRET_ANSWER,"");
    }

    public String getUserEmail()
    {
        return pref.getString(USER_EMAIL,"");
    }

    public String getUserMobile()
    {
        return pref.getString(MOB,"");
    }

//    public void createSession(String userName, String userImageUrl, String accessToken, String userEmail, String userMobile, String favcount,  String userId) {
//
//
//        this.pref.edit().putString(KEY_ACCESS_TOKEN, accessToken).apply();
//        this.pref.edit().putString(KEY_USER_ID, userId).apply();
//        this.pref.edit().putString(KEY_USER_EMAIL, userEmail).apply();
//        this.pref.edit().putString(KEY_USER_SECRET_ANSWER, userName).apply();
//        this.pref.edit().putString(KEY_USER_MOBILE, userMobile).apply();
//        this.pref.edit().putString(KEY_USER_IMAGE, userImageUrl).apply();
//        this.pref.edit().putString(FAV_COUNT,favcount).apply();
//    }


    public boolean isFirstTimeLaunch() {

        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }


    private static final String KEY_USER_SECRET_ANSWER = "user_name";
    private static final String KEY_USER_IMAGE = "profile_path";
    private static final String KEY_ACCESS_TOKEN = "user_token";
    private static final String KEY_USER_EMAIL = "user_email";
    private static final String KEY_USER_MOBILE = "mobile";
    private static final String FAV_COUNT = "fav_count";

    private static final String KEY_USER_ID = "user_id";

    public void saveArrayList(ArrayList<String> list, String key){
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();     // This line is IMPORTANT !!!
    }

    public ArrayList<String> getArrayList(String key){
        Gson gson = new Gson();
        String json = this.pref.getString(key, null);
        Type type = (Type) new TypeToken<ArrayList<String>>() {}.getType();
        return gson.fromJson(json, (java.lang.reflect.Type) type);
    }
}
