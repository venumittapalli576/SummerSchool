package com.developmentapps.summerschool.Register.memberRegister;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Date;

public class SessionHandler {
    private static final String PREF_NAME = "UserSession";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_EXPIRES = "expires";
    private static final String KEY_FULL_NAME = "full_name";
    private static final String KEY_Email = "Email";
    private static final String KEY_FATHERNAME = "Fathername";
    private static final String KEY_AGE = "Age";
    private static final String KEY_SELECTEDCOURSE = "Selectedcourse";
    private static final String KEY_ADDRESS = "Address";
    private static final String KEY_LOCATION = "Location";
    private static final String KEY_PHONENUMBER = "Phonenumber";
    private static final String KEY_EMPTY = "";
    private Context mContext;
    private SharedPreferences.Editor mEditor;
    private SharedPreferences mPreferences;

    public SessionHandler(Context mContext) {
        this.mContext = mContext;
        mPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        this.mEditor = mPreferences.edit();
    }

    /**
     * Logs in the user by saving user details and setting session
     *
     * @param username
     * @param fullName
     */
    public void loginUser(String username, String fullName,String Email,String Phonenumber,String Selectedcourse,String Fathername,String Location,String Address,String Age) {
        mEditor.putString(KEY_USERNAME, username);
        mEditor.putString(KEY_FULL_NAME, fullName);
        mEditor.putString(KEY_Email,Email);
        mEditor.putString(KEY_PHONENUMBER,Phonenumber);
       mEditor.putString(KEY_ADDRESS,Address);
        mEditor.putString(KEY_AGE,Age);
        mEditor.putString(KEY_FATHERNAME,Fathername);
       mEditor.putString(KEY_LOCATION,Location);
        mEditor.putString(KEY_SELECTEDCOURSE,Selectedcourse);
        Date date = new Date();

        //Set user session for next 7 days
        long millis = date.getTime() + (7 * 24 * 60 * 60 * 1000);
        mEditor.putLong(KEY_EXPIRES, millis);
        mEditor.commit();
    }

    /**
     * Checks whether user is logged in
     *
     * @return
     */
    public boolean isLoggedIn() {
        Date currentDate = new Date();

        long millis = mPreferences.getLong(KEY_EXPIRES, 0);

        /* If shared preferences does not have a value
         then user is not logged in
         */
        if (millis == 0) {
            return false;
        }
        Date expiryDate = new Date(millis);

        /* Check if session is expired by comparing
        current date and Session expiry date
        */
        return currentDate.before(expiryDate);
    }

    /**
     * Fetches and returns user details
     *
     * @return user details
     */
    public User getUserDetails() {
        //Check if user is logged in first
        if (!isLoggedIn()) {
            return null;
        }
        User user = new User();
        user.setUsername(mPreferences.getString(KEY_USERNAME, KEY_EMPTY));
        user.setFullName(mPreferences.getString(KEY_FULL_NAME, KEY_EMPTY));
        user.setEmail(mPreferences.getString(KEY_Email,KEY_EMPTY));
        user.setPhonenumber(mPreferences.getString(KEY_PHONENUMBER,KEY_EMPTY));
        user.setFathername(mPreferences.getString(KEY_FATHERNAME,KEY_EMPTY));
        user.setAge(mPreferences.getString(KEY_AGE,KEY_EMPTY));
        user.setSelectedcourse(mPreferences.getString(KEY_SELECTEDCOURSE,KEY_EMPTY));
        user.setAddress(mPreferences.getString(KEY_ADDRESS,KEY_EMPTY));
        user.setLocation(mPreferences.getString(KEY_LOCATION,KEY_EMPTY));
        user.setSessionExpiryDate(new Date(mPreferences.getLong(KEY_EXPIRES, 0)));

        return user;
    }

    /**
     * Logs out user by clearing the session
     */
    public void logoutUser(){
        mEditor.clear();
        mEditor.commit();
    }

}