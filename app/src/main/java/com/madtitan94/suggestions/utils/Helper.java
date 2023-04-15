package com.madtitan94.suggestions.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;

public class Helper {

    public static String getCurrentDate(){
    SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String datePrefix = date.format(System.currentTimeMillis());
    return datePrefix;
    }

    public static void showToast(Context context,String msg){
        Toast.makeText(context, ""+msg, Toast.LENGTH_SHORT).show();
    }

    public static void appendLog(String str){
        Log.e("_ ",""+str);
    }

}
