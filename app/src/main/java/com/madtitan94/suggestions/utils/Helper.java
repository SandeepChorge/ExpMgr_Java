package com.madtitan94.suggestions.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.madtitan94.suggestions.pojoClasses.DateFilter;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Helper {

    public static String DT_getCurrentDate(){
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

    public static DateFilter DT_getFilterDate(int type){
        DateFilter dateFilter = new DateFilter();
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        Date firstDateOfWeek,lastDateOfWeek;
        /*calendar.setFirstDayOfWeek(Calendar.MONDAY);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
*/
        switch (type){
            case 1:
                dateFilter.setStartDate(date.format(System.currentTimeMillis()));
                dateFilter.setEndDate(date.format(System.currentTimeMillis()));
                break;// date.format(System.currentTimeMillis());

            case 2:
                calendar = Calendar.getInstance();
                //calendar.setFirstDayOfWeek(Calendar.MONDAY);
                int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

                //First Day of week
                calendar.add(Calendar.DAY_OF_WEEK, 1 - dayOfWeek);
                firstDateOfWeek = calendar.getTime();
                //appendLog(String.format("firstDateOfWeek " + date.format(firstDateOfWeek)));

                //Last Day of week
                calendar.add(Calendar.DAY_OF_WEEK, 6);
                lastDateOfWeek = calendar.getTime();
                //appendLog(String.format("lastDateOfWeek " + date.format(lastDateOfWeek)));

                dateFilter.setStartDate(date.format(firstDateOfWeek));
                dateFilter.setEndDate(date.format(lastDateOfWeek));
                break;


            case 3:
// calculate the last day of the week
                calendar = Calendar.getInstance();
                calendar.setFirstDayOfWeek(Calendar.MONDAY);
                dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

                calendar.add(Calendar.DAY_OF_WEEK, 6-dayOfWeek);
                lastDateOfWeek = calendar.getTime();
                break;//return date.format(lastDateOfWeek);

            default:Log.e("default","case");
                 String datePrefix = date.format(System.currentTimeMillis());
                 break;
        //return datePrefix;
        }

        dateFilter.setType(type);
        Helper.appendLog("dateFilter for type "+type+" IS "+new Gson().toJson(dateFilter));
        return dateFilter;
    }

    public static String getMoneyFormat(double value) {
        String COUNTRY = "IN";
        String LANGUAGE = "en";
        //USD  COUNTRY = "US";
        //EUR COUNTRY = "EU";
        //INR COUNTRY = "IN";
        //COUNTRY = "US";

        String formattedMoney = "";
        try {
            formattedMoney = NumberFormat.getCurrencyInstance(new Locale(LANGUAGE, COUNTRY)).format((value));
        } catch (Exception e) {
            return formattedMoney;
        }
        return formattedMoney;
    }

    public static String DT_getDateForDisplay(String strdate) {
        String formattedDate = "";
        try {
            DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            DateFormat targetFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm aa");
            Date date = null;
            try {
                date = originalFormat.parse(strdate);
                formattedDate = targetFormat.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            return formattedDate;
        }
        return formattedDate;
    }
}
