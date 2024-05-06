package com.myapp.expensemanager.models;

public class Account {

    private String accountName;
    private int accountAmount;

    public Account(String accountName, int accountAmount) {
        this.accountName = accountName;
        this.accountAmount = accountAmount;
    }


    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public int getAccountAmount() {
        return accountAmount;
    }

    public void setAccountAmount(int accountAmount) {
        this.accountAmount = accountAmount;
    }
}
