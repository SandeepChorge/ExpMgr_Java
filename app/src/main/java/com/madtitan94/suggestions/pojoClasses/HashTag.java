package com.madtitan94.suggestions.pojoClasses;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "hash_tags")
public class HashTag {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String tagName;

    private String detail;

    private String createdAt;

    private int status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public HashTag(long id, String tagName, String detail, String createdAt, int status) {
        this.id = id;
        this.tagName = tagName;
        this.detail = detail;
        this.createdAt = createdAt;
        this.status = status;
    }
    @Ignore
    public HashTag( String tagName, String detail, String createdAt, int status) {
        this.tagName = tagName;
        this.detail = detail;
        this.createdAt = createdAt;
        this.status = status;
    }
}
