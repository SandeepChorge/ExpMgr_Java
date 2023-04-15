package com.madtitan94.suggestions.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.madtitan94.suggestions.db.converters.IntArrTypeConverters;
import com.madtitan94.suggestions.db.converters.IntTypeConverters;
import com.madtitan94.suggestions.db.dao.HashTagDao;
import com.madtitan94.suggestions.db.dao.TransactionDao;
import com.madtitan94.suggestions.pojoClasses.HashTag;
import com.madtitan94.suggestions.pojoClasses.Transaction;

@Database(entities = {HashTag.class, Transaction.class},
        version = 52, exportSchema = true)
@TypeConverters({IntTypeConverters.class, IntArrTypeConverters.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract TransactionDao transactionDao();

    public abstract HashTagDao hashTagDao();


    private static AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "ExpMgrDB")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    @Override
    public void clearAllTables() {

    }
}
