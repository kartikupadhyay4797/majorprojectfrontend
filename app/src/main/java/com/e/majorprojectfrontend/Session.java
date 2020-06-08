package com.e.majorprojectfrontend;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;

public class Session {
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "WaterOnClickSharedPreferences";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // User name (make variable public to access from outside)
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAILID="emailId";
    private static final String KEY_ID="id";
    private static final String KEY_ADDRESS="address";
    private static final String KEY_TYPE="type";

    // Constructor
    public Session(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     * */
    public void createLoginSession(String name,String emailId,String id,String address,String type){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_EMAILID,emailId);
        editor.putString(KEY_ID,id);
        editor.putString(KEY_ADDRESS,address);
        editor.putString(KEY_TYPE,type);
//        editor.putString("url",url);

        // commit changes
        editor.commit();
    }
//    public String getURL(){
//        return pref.getString("url","");
//    }
//    public void setURL(String url){
//        editor.remove("url");
//        editor.putString("url",url);
//        editor.commit();
//    }

    public String getType(){
        return pref.getString("type","");
    }

    public String getAddress() {
        return pref.getString("address","");
    }


    public void setAddress(String address) {
        editor.remove(KEY_ADDRESS);
        editor.putString("address",address);
        editor.commit();
    }
    public void setUserName(String address) {
        editor.remove(KEY_NAME);
        editor.putString(KEY_NAME,address);
        editor.commit();
    }
    public void setUserEmailId(String address) {
        editor.remove(KEY_EMAILID);
        editor.putString(KEY_EMAILID,address);
        editor.commit();
    }
    public void setUserId(String address) {
        editor.remove(KEY_ID);
        editor.putString(KEY_ID,address);
        editor.commit();
    }

    public String getUserName(){
        return pref.getString(KEY_NAME,null);
    }
    public String getUserEmailId(){
        return pref.getString(KEY_EMAILID,null);
    }

    public String getUserId() {
        return pref.getString(KEY_ID,null);
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, Login.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }

    }

    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, Login.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}
