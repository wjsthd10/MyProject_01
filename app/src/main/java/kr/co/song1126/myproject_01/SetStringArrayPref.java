package kr.co.song1126.myproject_01;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.ArrayAdapter;

import org.json.JSONArray;

import java.util.ArrayList;

public class SetStringArrayPref {
    static ArrayAdapter<String> adapter;
    static ArrayList<String> array=new ArrayList<>();
    static final String SETTINGS_JSON="settings_item_json";

    static void getStringArrayPref(Context context, String key, ArrayList<String> values){
        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor=prefs.edit();
        JSONArray a=new JSONArray();
    }

}
