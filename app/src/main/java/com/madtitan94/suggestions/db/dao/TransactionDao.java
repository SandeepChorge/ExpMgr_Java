package com.madtitan94.suggestions.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.madtitan94.suggestions.pojoClasses.HashTag;
import com.madtitan94.suggestions.pojoClasses.TagToTransaction;
import com.madtitan94.suggestions.pojoClasses.TranasctionListItem;
import com.madtitan94.suggestions.pojoClasses.Transaction;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface TransactionDao {
    @Query("SELECT * FROM `transactions` WHERE id =:transactionId")
    Transaction getTransaction(String transactionId);

  @Query("Select *,(select group_concat(tagName) from hash_tags where id in (select tagId from tag_to_transactions where transactionId = t.id)) as tags from transactions t")
  List<TranasctionListItem> getTransactions();

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

  /*@Query("select * from transactions where date(createdAt) BETWEEN '2023-04-25' AND '2023-04-25'")
  List<PosOrderSummary> getTransactionData(String start, String end);*/
}
