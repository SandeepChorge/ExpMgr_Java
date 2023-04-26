package com.madtitan94.suggestions.pojoClasses;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tag_to_transactions")
public class TagToTransaction {
    @PrimaryKey(autoGenerate = true)
    private long id;

    long transactionId;

    long tagId;

    int status;

    String createdDate;

    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    public long getTagId() {
        return tagId;
    }

    public void setTagId(long tagId) {
        this.tagId = tagId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public TagToTransaction(long transactionId, long tagId, int status, String createdDate) {
        this.transactionId = transactionId;
        this.tagId = tagId;
        this.status = status;
        this.createdDate = createdDate;
    }
}
