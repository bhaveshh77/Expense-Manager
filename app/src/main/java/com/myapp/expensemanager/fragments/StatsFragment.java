package com.myapp.expensemanager.fragments;

import static com.myapp.expensemanager.utils.Constants.EXPENSE;
import static com.myapp.expensemanager.utils.Constants.INCOME;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anychart.AnyChart;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.anychart.core.ui.Title;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
import com.google.android.material.tabs.TabLayout;
import com.myapp.expensemanager.R;
import com.myapp.expensemanager.databinding.FragmentStatsBinding;
import com.myapp.expensemanager.models.Transactions;
import com.myapp.expensemanager.utils.Constants;
import com.myapp.expensemanager.utils.Helper;
import com.myapp.expensemanager.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.RealmResults;


public class StatsFragment extends Fragment {

    public StatsFragment() {
        // Required empty public constructor
    }


    MainViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

    }
    FragmentStatsBinding binding;
    Calendar calendar;
    //    Realm realm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentStatsBinding.inflate(inflater);


        Pie pie = AnyChart.pie();

        calendar = Calendar.getInstance();

        updateDate();

        viewModel.getStatsTransactions(calendar, Constants.SELECTED_TAB_TYPE);


        binding.prevBtn.setOnClickListener(view -> {
            if (Constants.SELECTED_TAB_STATS == Constants.DAILY) {
                calendar.add(Calendar.DATE, -1);
            } else if (Constants.SELECTED_TAB_STATS == Constants.MONTHLY) {
                calendar.add(Calendar.MONTH, -1);
            }
            updateDate();
        });

        binding.nextBtn.setOnClickListener(view -> {
            if (Constants.SELECTED_TAB_STATS == Constants.DAILY) {
                calendar.add(Calendar.DATE, 1);
            } else if (Constants.SELECTED_TAB_STATS == Constants.MONTHLY) {
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
                    Constants.SELECTED_TAB_STATS = 0;
                    System.out.println("Daily selected");
                    updateDate();
                } else if(tab.getText().toString().trim().equals("Monthly")) {
                    Constants.SELECTED_TAB_STATS = 1;
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

        binding.transIncome.setOnClickListener(view -> {
            binding.transIncome.setBackground(getContext().getDrawable(R.drawable.income_selector));
            binding.transExpense.setBackground(getContext().getDrawable(R.drawable.default_selector));
            binding.transIncome.setTextColor(getContext().getColor(R.color.greenColor));
            binding.transExpense.setTextColor(getContext().getColor(R.color.defaultColor));

            Constants.SELECTED_TAB_TYPE = INCOME;
            updateDate();

        });

        binding.transExpense.setOnClickListener(view -> {
            binding.transExpense.setBackground(getContext().getDrawable(R.drawable.expense_selector));
            binding.transIncome.setBackground(getContext().getDrawable(R.drawable.default_selector));
            binding.transExpense.setTextColor(getContext().getColor(R.color.redColor));
            binding.transIncome.setTextColor(getContext().getColor(R.color.defaultColor));

            Constants.SELECTED_TAB_TYPE = EXPENSE;
            updateDate();

        });

        viewModel.newCategoryTransactions.observe(getViewLifecycleOwner(), new Observer<RealmResults<Transactions>>() {
            @Override
            public void onChanged(RealmResults<Transactions> transactions) {
                if (transactions.size() > 0) {
                    binding.imageView3.setVisibility(View.GONE);
                    binding.anyChart.setVisibility(View.VISIBLE);
                    List<DataEntry> data = new ArrayList<>();


                    Map<String, Double> categoryMap = new HashMap<>();

                    for(Transactions transactions1: transactions) {
                        String category = transactions1.getCategory();
                        double amount = transactions1.getAmount();

                        if(categoryMap.containsKey(category)) {
//                        // If the category is already in the 'categoryMap', update its total.
                            double currentTotal = categoryMap.get(category).doubleValue();
                            currentTotal += Math.abs(amount);
                            categoryMap.put(category, currentTotal);
                        } else {
//                   // If the category is not in the 'categoryMap', add it with the current amount.
                            categoryMap.put(category, Math.abs(amount));
//                            he absolute value of a number is its distance from zero on the number line, irrespective of its sign (positive or negative). In other words, it gives the positive value of a number.
                        }

//                        This part checks whether the categoryMap (which keeps track of total amounts for each category) already contains the current category. If it does, it updates the total by adding the current amount to the existing total. If the category is not in the map, it adds the category with the current amount.
//
//In simpler terms, for each transaction, we're organizing the data by category and keeping a running total of the amounts spent in each category. If we've seen the category before, we update its total; if it's a new category, we add it to our records. This helps later when creating visualizations or summaries, like the pie chart in your original code.
                    }

                    for(Map.Entry<String, Double> entry: categoryMap.entrySet()) {
//                            // Code inside this loop is executed for each entry (category, total amount) in 'categoryMap'.
                        data.add(new ValueDataEntry(entry.getKey(), entry.getValue()));
//                        For each entry in categoryMap, we create a new ValueDataEntry. This is likely a data structure used in a chart library (perhaps AnyChart, given the method name data) to represent a data point.
//
//entry.getKey() gets the category (e.g., "Food").
//entry.getValue() gets the total amount spent in that category.
//So, we're converting each category and its total amount into a format that can be used to display data on a chart.
                    }
                    pie.data(data);

                } else {
                    binding.imageView3.setVisibility(View.VISIBLE);
                    binding.anyChart.setVisibility(View.GONE);
                }
//                pie.data(data);
            }
        });



//        data.add(new ValueDataEntry("Apples", 6371664));
//        data.add(new ValueDataEntry("Pears", 789622));
//        data.add(new ValueDataEntry("Bananas", 7216301));
//        data.add(new ValueDataEntry("Grapes", 1486621));
//        data.add(new ValueDataEntry("Oranges", 1200000));
//
//        pie.data(data);
//
//        pie.title("Fruits imported in 2015 (in kg)");
//
//        pie.labels().position("outside");
//
//        pie.legend().title().enabled(true);
//        final Title retailChannels = pie.legend().title()
//                .text("Retail channels")
//                .padding(0d, 0d, 10d, 0d);
//
//        pie.legend()
//                .position("center-bottom")
//                .itemsLayout(LegendLayout.HORIZONTAL)
//                .align(Align.CENTER);

        binding.anyChart.setChart(pie);

        viewModel.getStatsTransactions(calendar, Constants.SELECTED_TAB_TYPE);


        return binding.getRoot();
    }

    private void updateDate() {
        if (Constants.SELECTED_TAB_STATS == Constants.DAILY) {
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM, yyyy");
            binding.currentDate.setText(Helper.formatDate(calendar.getTime()));
        } else if (Constants.SELECTED_TAB_STATS == Constants.MONTHLY) {
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM, yyyy");
            binding.currentDate.setText(Helper.formatDateByMonth(calendar.getTime()));
        }

        viewModel.getStatsTransactions(calendar, Constants.SELECTED_TAB_TYPE);

    }

//    int intValue = -5;
//double doubleValue = -10.75;
//
//int absInt = Math.abs(intValue);
//double absDouble = Math.abs(doubleValue);
//
//System.out.println("Absolute value of " + intValue + " is " + absInt);
//System.out.println("Absolute value of " + doubleValue + " is " + absDouble);
//In this example, the Math.abs() method is used to find the absolute values of an integer (intValue) and a double (doubleValue). The output will be:
//
//csharp
//Copy code
//Absolute value of -5 is 5
//Absolute value of -10.75 is 10.75
}