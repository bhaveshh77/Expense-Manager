package com.myapp.expensemanager.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.myapp.expensemanager.activities.MainActivity;
import com.myapp.expensemanager.fragments.TransactionsFragment;
import com.myapp.expensemanager.models.Transactions;
import com.myapp.expensemanager.utils.Constants;
import com.myapp.expensemanager.utils.Helper;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainViewModel extends AndroidViewModel {

//    Calendar calendar;

//    transactions is a special kind of variable that notifies observers (like UI components) when its value changes.
//In this case, it holds a list of Transactions retrieved from the Realm database.
    public MutableLiveData<RealmResults<Transactions>> transactions = new MutableLiveData<>();
    public MutableLiveData<RealmResults<Transactions>> newCategoryTransactions = new MutableLiveData<>();
    public MutableLiveData<Double> totalIncome = new MutableLiveData<>();
    public MutableLiveData<Double> totalExpense = new MutableLiveData<>();
    public MutableLiveData<Double> totalAmount = new MutableLiveData<>();


    Realm realm;
    Calendar calendar;


//    Extends ViewModel Capabilities:
//
//Like the regular ViewModel, the AndroidViewModel is used to manage and persist UI-related data across configuration changes in a lifecycle-aware way.
//Access to Application Context:
//
//The primary addition is that the AndroidViewModel allows easy access to the application context, which is particularly useful when you need to interact with resources, assets, or services that are tied to the application.

    public MainViewModel(@NonNull Application application) {
        super(application);
        Realm.init(application);
        setUpDataBase();
//        deleteAll();we
    }

    public void getStatsTransactions(Calendar calendar, String type) {

//        System.out.println("Here, calendar is null");

        this.calendar = calendar;

        //        calendar.set(Calendar.DAY_OF_MONTH, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        RealmResults<Transactions> newTransactions = null;



        if (Constants.SELECTED_TAB_STATS == Constants.DAILY) {




//        Date startDate = calendar.getTime();
//
////        calendar.set(Calendar.HOUR_OF_DAY, 0);
////        calendar.set(Calendar.MINUTE, 0);
////        calendar.set(Calendar.SECOND, 0);
////        calendar.set(Calendar.MILLISECOND, 0);
//
//        calendar.add(Calendar.HOUR_OF_DAY, 24);
//        Date endDate = calendar.getTime(); // We can also use this approach which is more convenient.

//        RealmResults<Transactions> newTransactions = realm.where(Transactions.class)
////                .greaterThanOrEqualTo("date", calendar.getTime())
//                .between("date", startDate, endDate) // this basically means, 00:00 + 24 hours and eventually creates the new Date.
////                Here, date is name of the variable, and it should match the Transactions exactly!!
//                .findAll();

            newTransactions = realm.where(Transactions.class)
                    .greaterThanOrEqualTo("date", calendar.getTime())
                    .lessThan("date", new Date((calendar.getTime().getTime()) + (24 * 60 * 60 * 1000))) // this basically means, 00:00 + 24 hours and eventually creates the new Date.
//                Here, date is name of the variable, and it should match the Transactions exactly!!
                    .equalTo("type", type)
                    .findAll();



        } else if (Constants.SELECTED_TAB_STATS == Constants.MONTHLY) {

            calendar.add(Calendar.MONTH, 0);
            calendar.set(Calendar.DAY_OF_MONTH,
                    calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
            Date startMonth = calendar.getTime();
//
//            LocalDate monthBegin = LocalDate.now().withDayOfMonth(1));
//            LocalDate monthEnd = LocalDate.now().plusMonths(1).withDayOfMonth(1).minusDays(1);

            calendar.set(Calendar.DAY_OF_MONTH,
                    calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            calendar.set(Calendar.MILLISECOND, 999);

//            calendar.set(Calendar.HOUR_OF_DAY, 0);
//            calendar.set(Calendar.MINUTE, 0);
//            calendar.set(Calendar.SECOND, 0);
//            calendar.set(Calendar.MILLISECOND, 0);

            Date endMonth = calendar.getTime();

            newTransactions = realm.where(Transactions.class)
                    .greaterThanOrEqualTo("date", startMonth)
                    .lessThan("date", endMonth)
                    .equalTo("type", type)
                    .findAll();
//                Here, date is name of the variable, and it should match the Transactions exactly!!


        }

//


//        getTransactions Method:
//
//Retrieves transactions from the Realm database.
//Updates the transactions LiveData with the new data.
//This method is likely called when you want to refresh the displayed transactions.
//        RealmResults<Transactions> newTransactions = realm.where(Transactions.class)
//                .equalTo("date", calendar.getTime())
////                Here, date is name of the variable, and it should match the Transactions exactly!!
//                .findAll();
        newCategoryTransactions.setValue(newTransactions);
    }

    public void getTransactions(Calendar calendar) {

//        System.out.println("Here, calendar is null");

        this.calendar = calendar;

        //        calendar.set(Calendar.DAY_OF_MONTH, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        RealmResults<Transactions> newTransactions = null;

        double income = 0;
        double expense = 0;
        double total = 0;

        if (Constants.SELECTED_TAB == Constants.DAILY) {




//        Date startDate = calendar.getTime();
//
////        calendar.set(Calendar.HOUR_OF_DAY, 0);
////        calendar.set(Calendar.MINUTE, 0);
////        calendar.set(Calendar.SECOND, 0);
////        calendar.set(Calendar.MILLISECOND, 0);
//
//        calendar.add(Calendar.HOUR_OF_DAY, 24);
//        Date endDate = calendar.getTime(); // We can also use this approach which is more convenient.

//        RealmResults<Transactions> newTransactions = realm.where(Transactions.class)
////                .greaterThanOrEqualTo("date", calendar.getTime())
//                .between("date", startDate, endDate) // this basically means, 00:00 + 24 hours and eventually creates the new Date.
////                Here, date is name of the variable, and it should match the Transactions exactly!!
//                .findAll();

           newTransactions = realm.where(Transactions.class)
                    .greaterThanOrEqualTo("date", calendar.getTime())
                    .lessThan("date", new Date((calendar.getTime().getTime()) + (24 * 60 * 60 * 1000))) // this basically means, 00:00 + 24 hours and eventually creates the new Date.
//                Here, date is name of the variable, and it should match the Transactions exactly!!
                    .findAll();

            income = realm.where(Transactions.class)
                    .greaterThanOrEqualTo("date", calendar.getTime())
                    .lessThan("date", new Date((calendar.getTime().getTime()) + (24 * 60 * 60 * 1000)))
                    .equalTo("type", Constants.INCOME)
                    .sum("amount")
                    .doubleValue();

            expense = realm.where(Transactions.class)
                    .greaterThanOrEqualTo("date", calendar.getTime())
                    .lessThan("date", new Date((calendar.getTime().getTime()) + (24 * 60 * 60 * 1000)))
                    .equalTo("type", Constants.EXPENSE)
                    .sum("amount")
                    .doubleValue();

            total = realm.where(Transactions.class)
                    .greaterThanOrEqualTo("date", calendar.getTime())
                    .lessThan("date", new Date((calendar.getTime().getTime()) + (24 * 60 * 60 * 1000)))
                    .sum("amount")
                    .doubleValue();



        } else if (Constants.SELECTED_TAB == Constants.MONTHLY) {

            calendar.add(Calendar.MONTH, 0);
            calendar.set(Calendar.DAY_OF_MONTH,
                    calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
            Date startMonth = calendar.getTime();
//
//            LocalDate monthBegin = LocalDate.now().withDayOfMonth(1));
//            LocalDate monthEnd = LocalDate.now().plusMonths(1).withDayOfMonth(1).minusDays(1);

            calendar.set(Calendar.DAY_OF_MONTH,
                    calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            calendar.set(Calendar.MILLISECOND, 999);

//            calendar.set(Calendar.HOUR_OF_DAY, 0);
//            calendar.set(Calendar.MINUTE, 0);
//            calendar.set(Calendar.SECOND, 0);
//            calendar.set(Calendar.MILLISECOND, 0);

            Date endMonth = calendar.getTime();

            newTransactions = realm.where(Transactions.class)
                    .greaterThanOrEqualTo("date", startMonth)
                    .lessThan("date", endMonth)
                    .findAll();
//                Here, date is name of the variable, and it should match the Transactions exactly!!

            income = realm.where(Transactions.class)
                    .greaterThanOrEqualTo("date", startMonth)
                    .lessThan("date", endMonth)
                    .equalTo("type", Constants.INCOME)
                    .sum("amount")
                    .doubleValue();

            expense = realm.where(Transactions.class)
                    .greaterThanOrEqualTo("date", startMonth)
                    .lessThan("date", endMonth)
                    .equalTo("type", Constants.EXPENSE)
                    .sum("amount")
                    .doubleValue();

            total = realm.where(Transactions.class)
                    .greaterThanOrEqualTo("date", startMonth)
                    .lessThan("date", endMonth)
                    .sum("amount")
                    .doubleValue();


        }

        totalIncome.setValue(income);
        totalExpense.setValue(expense);
        totalAmount.setValue(total);

//


//        getTransactions Method:
//
//Retrieves transactions from the Realm database.
//Updates the transactions LiveData with the new data.
//This method is likely called when you want to refresh the displayed transactions.
//        RealmResults<Transactions> newTransactions = realm.where(Transactions.class)
//                .equalTo("date", calendar.getTime())
////                Here, date is name of the variable, and it should match the Transactions exactly!!
//                .findAll();
        transactions.setValue(newTransactions);
    }

    public void setUpDataBase() {
//        setUpDataBase Method:
//
//Sets up the Realm database.
//Initializes the realm variable, which is an instance of the Realm database.
//This is usually called once to ensure that the database is ready to be used.
        realm = Realm.getDefaultInstance();
    }

    public void deleteTransactions(Transactions transactions) {
        realm.beginTransaction();
        transactions.deleteFromRealm();
        realm.commitTransaction();

        getTransactions(calendar);
//        System.out.println("Here again");

    }

    public void addTransaction(Transactions transactions) {
        realm.beginTransaction();

        realm.copyToRealmOrUpdate(transactions);
        realm.commitTransaction();
    }

//    public void deleteAll() {
//        realm.beginTransaction();
//
//        realm.deleteAll();
//        realm.commitTransaction();
//    }
    public void addTransactions() {
        realm.beginTransaction();



//        The actual transaction would be done in between these two methods...!
        realm.copyToRealmOrUpdate((new Transactions(Constants.INCOME, "Bank", "bullshit", "Asset", new Date(), 1221, new Date().getTime())));
        realm.copyToRealmOrUpdate((new Transactions(Constants.EXPENSE, "Cash ", "bullshit", "Business", new Date(), -1221, new Date().getTime())));
        realm.copyToRealmOrUpdate((new Transactions(Constants.INCOME, "Bank", "bullshit", "Rent", new Date(), 1661, new Date().getTime())));
        realm.copyToRealmOrUpdate((new Transactions(Constants.EXPENSE, "Card", "bullshit", "Investment", new Date(), -1221, new Date().getTime())));
        realm.copyToRealmOrUpdate((new Transactions(Constants.INCOME, "Other", "bullshit", "Saving", new Date(), 1221, new Date().getTime())));
        //        realm.deleteAll();


        realm.commitTransaction();
    }


//    In summary, your MainViewModel manages the interaction with the Realm database, provides methods to retrieve and add transactions, and uses LiveData (transactions) to communicate changes to the UI components that observe it. It's a central component for handling data related to transactions in your Android application.


}
