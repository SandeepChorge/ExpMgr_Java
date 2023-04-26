package com.madtitan94.suggestions.pojoClasses;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "transactions")
public class Transaction {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String detail;

    private String createdAt;

    private Double amount;

    private int type;

    private int status;

/*

    private List<String> tagsNames;

    public List<String> getTagsNames() {
        return tagsNames;
    }

    public void setTagsNames(List<String> tagsNames) {
        this.tagsNames = tagsNames;
    }

    private List<Integer> tags;

    public List<Integer> getTags() {
        return tags;
    }

    public void setTags(List<Integer> tags) {
        this.tags = tags;
    }
*/

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Transaction(){
    }

    @Ignore
    public Transaction(String detail, String createdAt, Double amount, int type, int status/*, List<Integer> tags*/) {
        this.detail = detail;
        this.createdAt = createdAt;
        this.amount = amount;
        this.type = type;
        this.status = status;
        //this.tags = tags;
    }
}
