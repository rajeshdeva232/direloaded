package com.cog.Dropinn.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import static com.cog.Dropinn.Static.Constants.MY_PREFS_NAME;

/**
 * Created by Farman on 7/20/2017.
 */

public class SharedPrefrenceHelper {
    private SharedPreferences _pref;
    private static final String PREF_FILE = MY_PREFS_NAME;
    private SharedPreferences.Editor _editorPref;

    public SharedPrefrenceHelper(Context context) {
        _pref = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        _editorPref = _pref.edit();
    }

    public void saveString(String key, String value) {
        _editorPref.putString(key, value);
        _editorPref.commit();
    }

    public String getString(String key) {
        return _pref.getString(key, "");
    }

    public void saveInt(String key, int value) {
        _editorPref.putInt(key, value);
        _editorPref.commit();
    }

    public int getInt(String key) {
        return _pref.getInt(key, 0);
    }

    public void clearSharedRefrence() {
        _pref.edit().clear().commit();
    }
}
