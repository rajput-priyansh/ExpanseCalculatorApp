package com.example.expansecalculatorapp.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expansecalculatorapp.R;
import com.example.expansecalculatorapp.interfaces.RecyclerViewOnClickListener;
import com.example.expansecalculatorapp.model.Category;
import com.example.expansecalculatorapp.util.AppConstant;

import java.util.ArrayList;


public class AdapterCategoryList extends RecyclerView.Adapter<AdapterCategoryList.MyViewHolder> {

    private Context context;
    private ArrayList<Category> categories;

    private RecyclerViewOnClickListener<Category> vehicleRecyclerViewOnClickListener;

    public AdapterCategoryList(Context context, ArrayList<Category> categories, RecyclerViewOnClickListener<Category> recyclerViewOnClickListener) {
        this.context = context;
        this.categories = categories;
        this.vehicleRecyclerViewOnClickListener = recyclerViewOnClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_category, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final Category category = categories.get(position);

        holder.tvTitle.setText(category.getName());

        int iconDrawable = -1;

        switch (category.getValue()) {
            case AppConstant.INCOME_SALARY:
                iconDrawable = R.drawable.ic_work_primary_24dp;
                break;
            case AppConstant.INCOME_STORE:
                iconDrawable = R.drawable.ic_store_primary_24dp;
                break;
            case AppConstant.INCOME_REWARD:
                iconDrawable = R.drawable.ic_stars_primary_24dp;
                break;
            case AppConstant.INCOME_OTHER:
                iconDrawable = R.drawable.ic_other_primary_24dp;
                break;
            case AppConstant.EXPENSE_HEALTH:
                iconDrawable = R.drawable.ic_healing_primary_24dp;
                break;
            case AppConstant.EXPENSE_FOOD:
                iconDrawable = R.drawable.ic_food_primary_24dp;
                break;
            case AppConstant.EXPENSE_BILL:
                iconDrawable = R.drawable.ic_bill_primary_24dp;
                break;
            case AppConstant.EXPENSE_SHOPPING:
                iconDrawable = R.drawable.ic_local_grocery_store_primary_24dp;
                break;
            case AppConstant.EXPENSE_HOTEL:
                iconDrawable = R.drawable.ic_local_hotel_primary_24dp;
                break;
            case AppConstant.EXPENSE_ENTERTAINMENT:
                iconDrawable = R.drawable.ic_fun_primary_24dp;
                break;
            case AppConstant.EXPENSE_OTHER:
                iconDrawable = R.drawable.ic_other_primary_24dp;
                break;
            case AppConstant.EXPENSE_FUEL:
                iconDrawable = R.drawable.ic_local_gas_station_primary_24dp;
                break;

        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            holder.ivIcon.setImageDrawable(context.getResources().getDrawable(iconDrawable, context.getTheme()));
        } else {
            holder.ivIcon.setImageDrawable(context.getResources().getDrawable(iconDrawable));
        }


        holder.llMain.setOnClickListener(v -> vehicleRecyclerViewOnClickListener.onClick(category,position,holder.ivIcon));
    }


    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle;
        private ImageView ivIcon;
        private LinearLayout llMain;

        public MyViewHolder(View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            ivIcon = itemView.findViewById(R.id.ivIcon);
            llMain = itemView.findViewById(R.id.llMain);

        }
    }
}
