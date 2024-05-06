package com.myapp.expensemanager.adapters;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.inputmethodservice.Keyboard;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.myapp.expensemanager.R;
import com.myapp.expensemanager.activities.MainActivity;
import com.myapp.expensemanager.databinding.RowItemBinding;
import com.myapp.expensemanager.fragments.TransactionsFragment;
import com.myapp.expensemanager.models.Category;
import com.myapp.expensemanager.models.Transactions;
import com.myapp.expensemanager.utils.Constants;
import com.myapp.expensemanager.utils.Helper;
import com.myapp.expensemanager.viewmodel.MainViewModel;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {

    Context context;
    RealmResults<Transactions> transactionsList;
//    MainViewModel viewModel;


    public TransactionAdapter(Context context, RealmResults<Transactions> transactionsList) {
        this.context = context;
        this.transactionsList = transactionsList;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        viewModel = new ViewModelProvider(get).get(MainViewModel.class);
        return new TransactionViewHolder(LayoutInflater.from(context).inflate(R.layout.row_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {

        Transactions transactions = transactionsList.get(position);

        holder.binding.acntType.setText(transactions.getAccount());
        holder.binding.acntType.setBackgroundTintList(context.getColorStateList(Constants.getColor(transactions.getAccount())));
        holder.binding.transDate.setText(Helper.formatDate(transactions.getDate()));
        holder.binding.transactionCategory.setText(transactions.getCategory());

        if (transactions.getType().equals(Constants.INCOME)) {
            holder.binding.transMoney.setTextColor(context.getColor(R.color.greenColor));
        } else if (transactions.getType().equals(Constants.EXPENSE)) {
            holder.binding.transMoney.setTextColor(context.getColor(R.color.redColor));
        }

//        transactions.getId();

        Category transactionCategory = Constants.getCategoryDetails(transactions.getCategory());
//        assert transactionCategory != null;
        holder.binding.imageView.setImageResource(transactionCategory.getCategoryImage());

        holder.binding.imageView.setImageTintList(context.getColorStateList(transactionCategory.getCategoryColor()));

        holder.binding.transMoney.setText(String.valueOf(transactions.getAmount()));  // Important

        holder.itemView.setOnLongClickListener(view -> {

            AlertDialog dialog = new AlertDialog.Builder(context).create();
            dialog.setTitle("Delete Transaction");
            dialog.setMessage("Are you sure to delete this transaction?");
            dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes", (dialogInterface, i) -> {
//                ((TransactionsFragment)).deleteTransactions(transactions);
                ((MainActivity)context).viewModel.deleteTransactions(transactions);

            });

            dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialog.dismiss();
                }
            });
            dialog.show();

            return false;
        });


    }

    @Override
    public int getItemCount() {
        return transactionsList.size();
    }

    class TransactionViewHolder extends RecyclerView.ViewHolder{


        RowItemBinding binding;
        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);

            binding = RowItemBinding.bind(itemView);
        }
    }


}
