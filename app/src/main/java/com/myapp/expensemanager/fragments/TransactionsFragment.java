package com.myapp.expensemanager.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.myapp.expensemanager.R;
import com.myapp.expensemanager.activities.MainActivity;
import com.myapp.expensemanager.adapters.TransactionAdapter;
import com.myapp.expensemanager.databinding.FragmentTransactionsBinding;
import com.myapp.expensemanager.models.Transactions;
import com.myapp.expensemanager.utils.Constants;
import com.myapp.expensemanager.utils.Helper;
import com.myapp.expensemanager.viewmodel.MainViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import io.realm.Realm;
import io.realm.RealmResults;


public class TransactionsFragment extends Fragment {

    public TransactionsFragment() {
        // Required empty constructor for the fragment
    }

    MainViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

//        onCreate:
//
//Called when the fragment is being created.
//Ideal for one-time setup tasks.
//A good place to initialize the ViewModel because you may need the ViewModel before the UI is created.


    }
    Transactions transactions;

    FragmentTransactionsBinding binding;

    Calendar calendar;
    //    Realm realm;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentTransactionsBinding.inflate(inflater);




//        setUpDataBase();
        calendar = Calendar.getInstance();

        updateDate();


//        getTransaction();


        binding.floatingActionButton.setOnClickListener(c -> {
            new AddFragmentTransaction().show(getParentFragmentManager(), null);
        });

        binding.prevBtn.setOnClickListener(view -> {
            if (Constants.SELECTED_TAB == Constants.DAILY) {
                calendar.add(Calendar.DATE, -1);
            } else if (Constants.SELECTED_TAB == Constants.MONTHLY) {
                calendar.add(Calendar.MONTH, -1);
            }
            updateDate();
        });

        binding.nextBtn.setOnClickListener(view -> {
            if (Constants.SELECTED_TAB == Constants.DAILY) {
                calendar.add(Calendar.DATE, 1);
            } else if (Constants.SELECTED_TAB == Constants.MONTHLY) {
                calendar.add(Calendar.MONTH, 1);
            }
            updateDate();
        });


//        viewModel.getTransactions(calendar);

        binding.tabLayout2.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                System.out.println("Selected tab text: " + tab.getText());

                if (tab.getText().toString().trim().equals("Daily")) {
                    Constants.SELECTED_TAB = 0;
                    System.out.println("Daily selected");
                    updateDate();
                } else if(tab.getText().equals("Monthly")) {
                    Constants.SELECTED_TAB = 1;
                    System.out.println("Monthly selected");
                    updateDate();
                }

            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

//        RealmResults<Transactions> transactions = realm.where(Transactions.class).findAll();

//        viewModel.addTransaction();
        binding.transList.setLayoutManager(new LinearLayoutManager(getContext()));

        viewModel.transactions.observe(getViewLifecycleOwner(), new Observer<RealmResults<Transactions>>() {
            @Override
            public void onChanged(RealmResults<Transactions> transactions) {
                TransactionAdapter adapter = new TransactionAdapter(getContext(), transactions);

//                So, whenever the data in the transactions LiveData changes (e.g., when you add new transactions through viewModel.addTransactions()), the onChanged method is triggered, and it updates the RecyclerView with the new data using the TransactionAdapter. This is a common pattern in Android development to observe data changes and update the UI accordingly.

                binding.transList.setAdapter(adapter);

                if (transactions.size() > 0) {
                    binding.imageView2.setVisibility(View.GONE);
                } else {
                    binding.imageView2.setVisibility(View.VISIBLE);
                }
            }
        });


        viewModel.totalIncome.observe(getViewLifecycleOwner(), aDouble -> {
            binding.totalIncome.setText(String.valueOf(aDouble));
        });

        viewModel.totalExpense.observe(getViewLifecycleOwner(), aDouble -> {
            binding.totalExpense.setText(String.valueOf(aDouble));
        });

        viewModel.totalAmount.observe(getViewLifecycleOwner(), aDouble -> {
            binding.totalAmount.setText(String.valueOf(aDouble));
        });

        viewModel.getTransactions(calendar);


        return binding.getRoot();
    }

    private void updateDate() {
        if (Constants.SELECTED_TAB == Constants.DAILY) {
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM, yyyy");
            binding.currentDate.setText(Helper.formatDate(calendar.getTime()));
        } else if (Constants.SELECTED_TAB == Constants.MONTHLY) {
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM, yyyy");
            binding.currentDate.setText(Helper.formatDateByMonth(calendar.getTime()));
        }

        viewModel.getTransactions(calendar);

    }

//    public void getTransaction() {
//        viewModel.getTransactions(calendar);
//    }
//
//    public void deleteTransaction() {
//        viewModel.deleteTransactions(transactions);
//    }


}