package com.madtitan94.suggestions.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.madtitan94.suggestions.pojoClasses.HashTag;
import com.madtitan94.suggestions.pojoClasses.TagToTransaction;
import com.madtitan94.suggestions.pojoClasses.Transaction;

import java.util.List;

@Dao
public interface TransactionDao {
    @Query("SELECT * FROM `transactions` WHERE id =:transactionId")
    Transaction getTransaction(String transactionId);

  @Query("SELECT * FROM `transactions`")
    Transaction getTransactions();

/*  @Query("select * from hash_tags where id in (select tags from transactions where id = 1)")
  List<HashTag> getTags();*/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert( Transaction transaction);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Transaction>transactions );

    @Query("delete from tag_to_transactions where transactionId =:transactionId")
    void deleteTransactionMappings(long transactionId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMappings(List<TagToTransaction> mappings);

    /*select group_concat(tagName,",") as mappedTags from hash_tags where id in (select tagId from tag_to_transactions where transactionId = 3)*/

}
