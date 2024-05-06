package com.myapp.expensemanager.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myapp.expensemanager.R;
import com.myapp.expensemanager.databinding.CategoryItemBinding;
import com.myapp.expensemanager.models.Category;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    Context context;
    List<Category> categoryList;


    public interface CategoryClickListener{
        void OnCategoryClickListener(Category category);
    }

    CategoryClickListener categoryClickListener;

    public CategoryAdapter(Context context, List<Category> categoryList, CategoryClickListener categoryClickListener) {
        this.context = context;
        this.categoryList = categoryList;
        this.categoryClickListener = categoryClickListener;
    }


    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryViewHolder(LayoutInflater.from(context).inflate(R.layout.category_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {

        Category category = categoryList.get(position);
        holder.binding.categoryText.setText(category.getCategoryName());
        holder.binding.categoryImage.setImageResource(category.getCategoryImage());

        holder.binding.categoryImage.setImageTintList(context.getColorStateList(category.getCategoryColor()));

        holder.itemView.setOnClickListener(view ->

                categoryClickListener.OnCategoryClickListener(category));

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {

        CategoryItemBinding binding;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = CategoryItemBinding.bind(itemView);
        }
    }

}
