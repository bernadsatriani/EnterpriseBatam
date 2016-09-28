package com.bpbatam;

/**
 * Created by hiantohendry on 2/1/16.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import java.util.Locale;

public class SessionManager {

    public static final String TAG = SessionManager.class.getSimpleName();

    // Shared Preferences
    private SharedPreferences pref;

    // Editor for Shared preferences
    private SharedPreferences.Editor editor;

    // Context
    private Context context;

    // Shared pref mode
    private int PRIVATE_MODE = 0;

    private static final String EMPTY_STRING = "";

    // Shared key name
    public static final String GCM_REG_ID = "gcm_registration_id";
    public static final String TOKEN = "token";
    public static final String REFRESH_TOKEN = "refresh_token";
    public static final String LANGUAGE = "language";
    public static final String USER_ACCOUNT = "user_account";


    private final String SESSION_PREFERENCE = "session_pref";

    public SessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(SESSION_PREFERENCE, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Clear all preference data
     */
    public void clearAllPreference() {
        editor.clear();
        editor.apply();
    }

    /**
     * Store string data to preferences
     *
     * @param key
     * @param value
     */
    public void putStringData(String key, String value) {
        editor.putString(key, value);
        editor.apply();
    }

    /**
     * Get string data from preference
     *
     * @param key
     * @return
     */
    public String getStringData(String key) {
        return pref.getString(key, EMPTY_STRING);
    }


    /**
     * Remove string data from
     *
     * @param key
     */
    public void removeStringData(String key) {
        editor.remove(key);
        editor.apply();
    }

    /**
     * Put Boolean data to preference
     *
     * @param key
     * @param value
     */
    public void putBooleanData(String key, Boolean value) {
        editor.putBoolean(key, value);
        editor.apply();
    }

    /**
     * Get Boolean data from preference
     * <br>Default value : FALSE
     *
     * @param key
     * @return
     */
    public Boolean getBooleanData(String key) {
        return pref.getBoolean(key, Boolean.FALSE);
    }

    /**
     * Put Integer data to preference
     *
     * @param key
     * @param value
     */
    public void putIntegerData(String key, Integer value) {
        editor.putInt(key, value);
        editor.apply();
    }

    /**
     * Get Integer data from preference
     * <br>Default value : 0
     *
     * @param key
     * @return
     */
    public Integer getIntegerData(String key) {
        return pref.getInt(key, 0);
    }


    /**
     * Put Float data to preference
     *
     * @param key
     * @param value
     */
    public void putFloatData(String key, Float value) {
        editor.putFloat(key, value);
        editor.apply();
    }

    /**
     * Get Float data from preference
     * <br>Default value : 0
     *
     * @param key
     * @return
     */
    public Float getFloatData(String key) {
        return pref.getFloat(key, 0);
    }

    /**
     * Remove data from preference
     *
     * @param key
     */
    public void removeData(String key) {
        editor.remove(key);
        editor.apply();
    }

    /**
     * Put list of object to shared preference
     * <br>list object must be converted to json string
     */


    /**
     * Get gcm key
     */
    public String getGcmKey() {
        return getStringData(GCM_REG_ID);
    }

    public void setGcmKey(String GcmKey) {
        putStringData(GCM_REG_ID, GcmKey);
    }


    public boolean isUserLogon() {
        String userSession = getStringData(SessionManager.USER_ACCOUNT);
        if (userSession.equals("")) {
            return false;
        }

        return true;
    }


    public void setLanguage(String language) {

        putStringData(LANGUAGE, language);
    }

    public String getLanguage() {
        return getStringData(LANGUAGE);
    }

    public void setToken(String token) {
        putStringData(TOKEN, token);
    }

    public String getToken() {
        return getStringData(TOKEN);
    }

    public void setRefreshToken(String refreshToken) {
        putStringData(REFRESH_TOKEN, refreshToken);
    }

    public String getRefreshToken() {
        return getStringData(REFRESH_TOKEN);
    }





    public void setLocale(String lang) {
        Locale myLocale;
        myLocale = new Locale(lang);
        Resources res = context.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
    }

}

