package com.myapp.expensemanager.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myapp.expensemanager.R;
import com.myapp.expensemanager.databinding.AccountItemBinding;
import com.myapp.expensemanager.models.Account;

import java.util.List;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.AccountViewHolder> {

    Context context;
    List<Account> accounts;

    public interface AccountClickListener{
        void OnAccountClickListener(Account account);
    }

    AccountClickListener accountClickListener;

    public AccountAdapter(Context context, List<Account> accounts, AccountClickListener accountClickListener) {
        this.context = context;
        this.accounts = accounts;
        this.accountClickListener = accountClickListener;
    }

    @NonNull
    @Override
    public AccountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AccountViewHolder(LayoutInflater.from(context).inflate(R.layout.account_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AccountViewHolder holder, int position) {

        Account account = accounts.get(position);

        holder.binding.accountName.setText(account.getAccountName());

        holder.itemView.setOnClickListener(view -> {
            accountClickListener.OnAccountClickListener(account);
        });

    }

    @Override
    public int getItemCount() {
        return accounts.size();
    }

    class AccountViewHolder extends RecyclerView.ViewHolder {


        AccountItemBinding binding;
        public AccountViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = AccountItemBinding.bind(itemView);
        }
    }
}
