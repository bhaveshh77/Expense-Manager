package com.myapp.expensemanager.utils;

import com.myapp.expensemanager.R;
import com.myapp.expensemanager.models.Category;

import java.util.ArrayList;
import java.util.List;

public class Constants {

    public static String INCOME = "INCOME";
    public static String EXPENSE = "EXPENSE";

    public static List<Category> categoryList;

    public static int DAILY = 0;
    public static int MONTHLY = 1;
    public static int CALENDAR = 2;
    public static int SUMMARY = 3;
    public static int NOTES = 4;

    public static int SELECTED_TAB = 0;
    public static int SELECTED_TAB_STATS = 0;
    public static String SELECTED_TAB_TYPE = Constants.INCOME;


    public static void setCategoryList() {
        categoryList = new ArrayList<>();
        categoryList.add(new Category("Rent", R.drawable.transaction, R.color.category1));
        categoryList.add(new Category("Investment", R.drawable.transaction, R.color.category2));
        categoryList.add(new Category("Loan", R.drawable.transaction, R.color.category3));
        categoryList.add(new Category("Saving", R.drawable.transaction, R.color.category4));
        categoryList.add(new Category("Business", R.drawable.transaction, R.color.category6));
        categoryList.add(new Category("Asset", R.drawable.transaction, R.color.category6));

    }

    public static Category getCategoryDetails(String categoryDetails) {
        for (Category cat: categoryList) {
            if(cat.getCategoryName().equals(categoryDetails)) {
                return cat;
            }

        }
        return null;
    }

    public static int getColor(String accountName) {
//        if (accountName != null && !accountName.isEmpty()) {
            switch (accountName) {
                case "Bank":
                    return R.color.bank_color;
                case "Cash":
                    return R.color.cash_color;
                case "Card":
                    return R.color.card_color;
                default:
                    return R.color.other_color;
            }

    }

//        else {
//                return R.color.other_color;
//        }

//    }

}

