package com.myapp.expensemanager.models;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Transactions extends RealmObject {

    private String type, account, note, category;
    private Date date;
    private double amount;
    @PrimaryKey
    private long id;

    public Transactions() {
    }

    public Transactions(String type, String account, String note, String category, Date date, double amount, long id) {
        this.type = type;
        this.account = account;
        this.note = note;
        this.category = category;
        this.date = date;
        this.amount = amount;
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
