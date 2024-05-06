package com.myapp.expensemanager.fragments;

import static android.graphics.Color.TRANSPARENT;

import static com.myapp.expensemanager.utils.Constants.EXPENSE;
import static com.myapp.expensemanager.utils.Constants.INCOME;
import static com.myapp.expensemanager.utils.Constants.categoryList;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.myapp.expensemanager.R;
import com.myapp.expensemanager.activities.MainActivity;
import com.myapp.expensemanager.adapters.AccountAdapter;
import com.myapp.expensemanager.adapters.CategoryAdapter;
import com.myapp.expensemanager.databinding.FragmentAddTransactionBinding;
import com.myapp.expensemanager.databinding.ListViewBinding;
import com.myapp.expensemanager.databinding.RowItemBinding;
import com.myapp.expensemanager.models.Account;
import com.myapp.expensemanager.models.Category;
import com.myapp.expensemanager.models.Transactions;
import com.myapp.expensemanager.viewmodel.MainViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


public class AddFragmentTransaction extends BottomSheetDialogFragment {


    public AddFragmentTransaction() {
        // Required empty public constructor
    }

    Transactions transactions;
//    MainViewModel mainViewModel;

//    Calendar calendar;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
    }

    FragmentAddTransactionBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddTransactionBinding.inflate(inflater);


//        transactionsFragment = new TransactionsFragment();
        transactions = new Transactions();

//        calendar = Calendar.getInstance();

        binding.transIncome.setOnClickListener(view -> {
            binding.transIncome.setBackground(getContext().getDrawable(R.drawable.income_selector));
            binding.transExpense.setBackground(getContext().getDrawable(R.drawable.default_selector));
            binding.transIncome.setTextColor(getContext().getColor(R.color.greenColor));
            binding.transExpense.setTextColor(getContext().getColor(R.color.defaultColor));

            transactions.setType(INCOME);

        });

        binding.transExpense.setOnClickListener(view -> {
            binding.transExpense.setBackground(getContext().getDrawable(R.drawable.expense_selector));
            binding.transIncome.setBackground(getContext().getDrawable(R.drawable.default_selector));
            binding.transExpense.setTextColor(getContext().getColor(R.color.redColor));
            binding.transIncome.setTextColor(getContext().getColor(R.color.defaultColor));

            transactions.setType(EXPENSE);

        });

        binding.transDate.setOnClickListener(view -> {
            // Code to be executed when the EditText (transDate) is clicked

            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext());

            // Creating a DatePickerDialog, a pre-built dialog for picking a date

            datePickerDialog.setOnDateSetListener((datePicker, i, i1, i2) -> {

                // Setting a listener for when the date is set in the DatePickerDialog

                Calendar calendar = Calendar.getInstance();
//                Calendar calendar = Calendar.getInstance();: This creates a Calendar instance representing the current date and time. It will be used to manipulate and format the selected date.
                // Creating a Calendar instance to manipulate and format the selected date

                calendar.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
                calendar.set(Calendar.MONTH, datePicker.getMonth());
                calendar.set(Calendar.YEAR, datePicker.getYear());
                // Setting the selected date to the Calendar instance

//                The Calendar class itself does not handle formatting for display. You typically use a SimpleDateFormat or another formatting mechanism to convert a Calendar instance into a formatted string:
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM, yyyy");
                // Creating the date format
//                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd, MMMM, yyyy");: Creates a date format pattern that defines how the date should be displayed.
//String formattedDate = simpleDateFormat.format(calendar.getTime());: Formats the selected date using the pattern and the Calendar instance.

                String formattedDate = simpleDateFormat.format(calendar.getTime());
                // Formatting the date using the pattern and the Calendar instance

                binding.transDate.setText(formattedDate);
                // Setting the formatted date to the EditText (transDate)

                transactions.setDate(calendar.getTime());
                transactions.setId(calendar.getTime().getTime());
            });
            datePickerDialog.show();
            // Displaying the DatePickerDialog to let the user pick a date
        });

        binding.transCategory.setOnClickListener(view -> {

            ListViewBinding listViewBinding = ListViewBinding.inflate(inflater);


            AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
            alertDialog.setView(listViewBinding.getRoot());
//            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.R.color.transparent));



            CategoryAdapter categoryAdapter = new CategoryAdapter(getContext(), categoryList, new CategoryAdapter.CategoryClickListener() {
                @Override
                public void OnCategoryClickListener(Category category) {
                    binding.transCategory.setText(category.getCategoryName());
                    transactions.setCategory(category.getCategoryName());
                    alertDialog.dismiss();

                }
            });
            listViewBinding.listView.setLayoutManager(new GridLayoutManager(getContext(), 3));
            listViewBinding.listView.setAdapter(categoryAdapter);

            alertDialog.show();

        });

        binding.transAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListViewBinding listViewBinding = ListViewBinding.inflate(inflater);


                AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                alertDialog.setView(listViewBinding.getRoot());
//                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.R.color.transparent));

                List<Account> accounts = new ArrayList<>();
                accounts.add(new Account("Bank", 2222));
                accounts.add(new Account("Cash", 2222));
                accounts.add(new Account("Card", 2222));
                accounts.add(new Account("Other", 2222));
//                accounts.add(new Account("I don't know", 2222));

                AccountAdapter adapter = new AccountAdapter(getContext(), accounts, new AccountAdapter.AccountClickListener() {
                    @Override
                    public void OnAccountClickListener(Account account) {
                        binding.transAccount.setText(account.getAccountName());
                        transactions.setAccount(account.getAccountName());
                        alertDialog.dismiss();
                    }
                });
                listViewBinding.listView.setLayoutManager(new LinearLayoutManager(getContext()));
                listViewBinding.listView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
                listViewBinding.listView.setAdapter(adapter);

                alertDialog.show();

            }
        });

//        binding.transNote.setText(binding.transNote.getText().toString());



        binding.saveBtn.setOnClickListener(view -> {
            String amountString = binding.transAmount.getText().toString().trim();
            String note = binding.transNote.getText().toString();

            if (amountString.isEmpty()) {
                Toast.makeText(getContext(), "Please add a valid Amount!", Toast.LENGTH_LONG).show();
            }
             try {

                 double amount = Double.parseDouble(amountString);

                 if (transactions != null && transactions.getType() != null && transactions.getAccount() != null && transactions.getCategory() != null) {

                     if (transactions.getType().equals(EXPENSE)) {
                         transactions.setAmount(amount * -1);
                     } else {
                         transactions.setAmount(amount);
                     }

                     transactions.setNote(note);


                     ((MainActivity) getActivity()).viewModel.addTransaction(transactions);
                     ((MainActivity) getActivity()).getTransactions();
//
//            ((TransactionsFragment)requireActivity().getSupportFragmentManager().findFragmentById(R.id.content)).viewModel.addTransaction(transactions);
//            ((TransactionsFragment)requireActivity().getSupportFragmentManager().findFragmentById(R.id.content)).getTransaction();

//            ((TransactionsFragment)get);


//            notifyAll();
//            mainViewModel.getTransactions(calendar);

                     dismiss();

                 } else {
                     Toast.makeText(getContext(), "Invalid Transaction!", Toast.LENGTH_SHORT).show();

                 }
             } catch (NumberFormatException e) {
                 Toast.makeText(getContext(), "Invalid Number found!!", Toast.LENGTH_SHORT).show();
             }

//             dismiss();

        });

        return binding.getRoot();
    }
}