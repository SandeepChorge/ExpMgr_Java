package com.madtitan94.suggestions.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.madtitan94.suggestions.pojoClasses.HashTag;

import java.util.List;

@Dao
public interface HashTagDao {
    @Query("SELECT * FROM `hash_tags` WHERE id LIKE :tagId")
    HashTag getTag(int tagId);

    @Query("SELECT * FROM `hash_tags` WHERE tagName LIKE '%' || :search || '%'")
    List<HashTag> getTagsByName(String search);

    @Query("SELECT * FROM `hash_tags` WHERE tagName LIKE '%' || :search || '%' AND id NOT IN (:mapped)")
    List<HashTag> getTagsByNameExclude(String search,long[] mapped);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(HashTag tag);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<HashTag> tags);

    @Query("DELETE from hash_tags")
    void deleteAll();
}
