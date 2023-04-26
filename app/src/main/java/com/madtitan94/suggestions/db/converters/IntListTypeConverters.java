package com.madtitan94.suggestions.db.converters;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

public class IntListTypeConverters implements Serializable {

    @TypeConverter // note this annotation
    public String fromOptionValuesList(List<Integer> hashTags) {
        if (hashTags == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Integer>>() {
        }.getType();
        String json = gson.toJson(hashTags, type);
        return json;
    }

    @TypeConverter // note this annotation
    public List<Integer> toOptionValuesList(String optionValuesString) {
        if (optionValuesString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Integer>>() {
        }.getType();
        List<Integer> tags = gson.fromJson(optionValuesString, type);
        return tags;
    }

}
