package com.madtitan94.suggestions.db.converters;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.madtitan94.suggestions.pojoClasses.HashTag;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

public class IntArrTypeConverters implements Serializable {

    @TypeConverter // note this annotation
    public String fromOptionValuesList(int[] hashTags) {
        if (hashTags == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<int[]>() {
        }.getType();
        String json = gson.toJson(hashTags, type);
        return json;
    }

    @TypeConverter // note this annotation
    public int[] toOptionValuesList(String optionValuesString) {
        if (optionValuesString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<int[]>() {
        }.getType();
        int[] tags = gson.fromJson(optionValuesString, type);
        return tags;
    }

}
