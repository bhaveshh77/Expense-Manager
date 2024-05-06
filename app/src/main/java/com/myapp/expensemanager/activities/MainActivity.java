package com.myapp.expensemanager.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationBarView;
import com.myapp.expensemanager.R;
import com.myapp.expensemanager.databinding.ActivityMainBinding;
import com.myapp.expensemanager.fragments.StatsFragment;
import com.myapp.expensemanager.fragments.TransactionsFragment;
import com.myapp.expensemanager.models.Transactions;
import com.myapp.expensemanager.utils.Constants;
import com.myapp.expensemanager.viewmodel.MainViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    public MainViewModel viewModel;
//    Transactions transactions;

    Calendar calendar;
//    TransactionsFragment transactionsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        setSupportActionBar(binding.toolBar);
        getSupportActionBar().setTitle("Transactions");

//        transactionsFragment.getTransaction();

        calendar = Calendar.getInstance();
//        calendar = Calendar.

        Constants.setCategoryList();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content, new TransactionsFragment());
        fragmentTransaction.commit();

        binding.bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                if (item.getItemId() == R.id.transactions) {
                    getSupportFragmentManager().popBackStack();
                    Constants.SELECTED_TAB = 0;

                } else if (item.getItemId() == R.id.stats) {
                    transaction.replace(R.id.content, new StatsFragment());
                    transaction.addToBackStack(null);
                    Constants.SELECTED_TAB_STATS= 0;
                }

                transaction.commit();
                return true;
            }
        });

    }

    public void getTransactions() {
        viewModel.getTransactions(calendar);
    }



//    public void getTransaction() {
//        viewModel.getTransactions(transactionsFragment.calendar);
//    }

//    public void deleteTransaction() {
//        viewModel.deleteTransactions(transactions);
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

//    private void updateDate() {
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM, yyyy");
//        binding.currentDate.setText(simpleDateFormat.format(calendar.getTime()));
//        viewModel.getTransactions(calendar);
//
//    }


}