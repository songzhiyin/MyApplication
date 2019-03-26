package com.szy.myapplication.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by Administrator on 2018/3/2 0002.
 * app信息管理器
 */

public class PreferenManager {
    private static Context mContext;
    private static SharedPreferences preferences = null;
    private static Gson gson = new GsonBuilder().create();

    public static void init(Context context) {
        mContext = context;
        preferences = mContext.getSharedPreferences("SpkiWorkflow", Context.MODE_PRIVATE);
    }


    public static void saveBluetoothAddress(String message) {
        preferences.edit().putString("BluetoothAddress", message).commit();
    }


    public static String getBluetoothAddress() {
        return preferences == null ? "" : preferences.getString("BluetoothAddress", "");
    }

    public static void saveBluetoothName(String message) {
        preferences.edit().putString("BluetoothName", message).commit();
    }


    public static String getBluetoothName() {
        return preferences == null ? "" : preferences.getString("BluetoothName", "");
    }

}
