package com.madtitan94.suggestions.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.madtitan94.suggestions.pojoClasses.Transaction;

import java.util.List;

@Dao
public interface TransactionDao {
    @Query("SELECT * FROM `transactions` WHERE id =:transactionId")
    Transaction getTransaction(String transactionId);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert( Transaction transaction);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Transaction>transactions );
}
