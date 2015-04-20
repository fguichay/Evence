package edu.nyit.evence.db;

/**
 * Created by Frank on 3/1/2015.
 */
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import java.util.HashMap;

import edu.nyit.evence.Login;

public class SessionManager {
    // LogCat tag
    private static String TAG = SessionManager.class.getSimpleName();

    // Shared Preferences
    SharedPreferences pref;

    // editor
    Editor editor;

    // context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "EvencePref";

    // all shared preferences keys
    private static final String IS_LOGGEDIN = "IsLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_USER_ID = "id";

    // Email address (make variable public to access from outside)
    public static final String KEY_EMAIL = "email";

    // event ID
    public static final String EVENT_ID = "id";

    // constructor
    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createEvent(String id){
        editor.putString(EVENT_ID, id); // Storing string
        editor.commit(); //
    }

    public void createLoginSession(String email, String id) {

        //storing boolean login value
        editor.putBoolean(IS_LOGGEDIN, true);

        // Storing email in pref
        editor.putString(KEY_EMAIL, email);

        // Storing name in pref
        editor.putString(KEY_USER_ID, id);

        // commit changes
        editor.commit();

        Log.d(TAG, "User login session modified!");
    }

    /**
     * Get stored session data
     * */
    public HashMap <String, String> getUserDetails() {
        //Use hashmap to store user credentials
        HashMap <String, String> user = new HashMap<String, String>();
        //user name
        user.put(KEY_USER_ID, pref.getString(KEY_USER_ID, null));
        //user email
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        //event id
        user.put(EVENT_ID, pref.getString(EVENT_ID, null));

        return user;
    }

    /**
     * Check login method will check user login status
     * If false it will redirect user to login page
     * Else do anything
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

    // Check for login
    public boolean isLoggedIn(){

        return pref.getBoolean(IS_LOGGEDIN, false);
    }

}