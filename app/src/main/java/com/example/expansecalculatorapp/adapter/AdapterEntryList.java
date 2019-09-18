package com.example.expansecalculatorapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expansecalculatorapp.R;
import com.example.expansecalculatorapp.model.Entry;
import com.example.expansecalculatorapp.model.EntryHeader;
import com.example.expansecalculatorapp.model.WrapperEntry;
import com.example.expansecalculatorapp.util.AppConstant;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class AdapterEntryList extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<WrapperEntry> wrapperEntries;


    public AdapterEntryList(Context context, ArrayList<WrapperEntry> wrapperEntries) {
        this.context = context;
        this.wrapperEntries = wrapperEntries;
    }

    @Override
    public int getItemViewType(int position) {
        return wrapperEntries.get(position).getType();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        if (viewType == WrapperEntry.TYPE_DAILY_HEADER) {

            return new EntryHeaderDailyViewHolder(layoutInflater.inflate(R.layout.item_day, parent, false));
        } else {

            return new EntryViewHolder(layoutInflater.inflate(R.layout.item_entry, parent, false));
        }
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == WrapperEntry.TYPE_DAILY_HEADER) {

            ((EntryHeaderDailyViewHolder) holder).setDetails(wrapperEntries.get(position), position);
        } else {

            ((EntryViewHolder) holder).setDetails(wrapperEntries.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return wrapperEntries.size();
    }

    public void setWrapperEntries(ArrayList<WrapperEntry> wrapperEntries) {
        this.wrapperEntries = wrapperEntries;
        notifyDataSetChanged();
    }

    public class EntryViewHolder extends RecyclerView.ViewHolder {

        private TextView tvCategory;
        private TextView tvNote;
        private TextView tvAccount;
        private TextView tvAmt;
        private TextView tvTime;

        private SimpleDateFormat sdf;

        public EntryViewHolder(View itemView) {
            super(itemView);

            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvNote = itemView.findViewById(R.id.tvNote);
            tvAccount = itemView.findViewById(R.id.tvAccount);
            tvAmt = itemView.findViewById(R.id.tvAmt);
            tvTime = itemView.findViewById(R.id.tvTime);

            sdf = new SimpleDateFormat("hh:mm a");
        }

        public void setDetails(WrapperEntry wrapperEntry) {

            if (wrapperEntry.getEntry() != null) {
                Entry entry = wrapperEntry.getEntry();

                int iconDrawable = -1;
                String category = "";

                switch (entry.getCategory()) {
                    case AppConstant.INCOME_SALARY:
                        iconDrawable = R.drawable.ic_work_primary;
                        category = "SALARY";
                        break;
                    case AppConstant.INCOME_STORE:
                        iconDrawable = R.drawable.ic_store_primary;
                        category = "STORE";
                        break;
                    case AppConstant.INCOME_REWARD:
                        iconDrawable = R.drawable.ic_stars_primary;
                        category = "REWARD";
                        break;
                    case AppConstant.INCOME_OTHER:
                    case AppConstant.EXPENSE_OTHER:
                        iconDrawable = R.drawable.ic_other_primary;
                        category = "OTHER";
                        break;
                    case AppConstant.EXPENSE_HEALTH:
                        iconDrawable = R.drawable.ic_healing_primary;
                        category = "HEALTH";
                        break;
                    case AppConstant.EXPENSE_FOOD:
                        iconDrawable = R.drawable.ic_food_primary;
                        category = "FOOD";
                        break;
                    case AppConstant.EXPENSE_BILL:
                        iconDrawable = R.drawable.ic_bill_primary;
                        category = "BILL";
                        break;
                    case AppConstant.EXPENSE_SHOPPING:
                        iconDrawable = R.drawable.ic_local_grocery_store_primary;
                        category = "SHOPPING";
                        break;
                    case AppConstant.EXPENSE_HOTEL:
                        iconDrawable = R.drawable.ic_local_hotel_primary;
                        category = "HOTEL";
                        break;
                    case AppConstant.EXPENSE_ENTERTAINMENT:
                        iconDrawable = R.drawable.ic_fun_primary;
                        category = "ENTERTAINMENT";
                        break;
                    case AppConstant.EXPENSE_FUEL:
                        iconDrawable = R.drawable.ic_local_gas_station_primary;
                        category = "FUEL";
                        break;

                }

                tvCategory.setText(category);
                tvCategory.setCompoundDrawablesWithIntrinsicBounds(iconDrawable, 0, 0, 0);

                if (entry.getNote() != null && entry.getNote().trim().length() > 0) {

                    tvNote.setText(entry.getNote());
                } else {

                    tvNote.setText("NA");
                }

                tvAccount.setText(AppConstant.arrayAccountType[entry.getAccount()]);

                tvAmt.setText(String.valueOf(entry.getAmount()));

                if (entry.getType() == AppConstant.ENTRY_INCOME) {
                    tvAmt.setTextColor(Color.parseColor("#8bbe1b"));
                    tvAmt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_rupee_indian_income, 0, 0, 0);
                } else {
                    tvAmt.setTextColor(Color.parseColor("#ba160c"));
                    tvAmt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_rupee_indian_expense, 0, 0, 0);
                }

                long timestampLong = entry.getTimeStamp();
                Date d = new Date(timestampLong);
                Calendar c = Calendar.getInstance();
                c.setTime(d);
                tvTime.setText(sdf.format(c.getTime()));

            }

        }
    }

    public class EntryHeaderDailyViewHolder extends RecyclerView.ViewHolder {

        private TextView tvDD;
        private TextView tvMonYear;
        private TextView tvDay;
        private TextView tvTotalIncome;
        private TextView tvTotalExpense;
        private View sep;

        public EntryHeaderDailyViewHolder(View itemView) {
            super(itemView);

            tvDD = itemView.findViewById(R.id.tvDD);
            tvMonYear = itemView.findViewById(R.id.tvMonYear);
            tvDay = itemView.findViewById(R.id.tvDay);
            tvTotalIncome = itemView.findViewById(R.id.tvTotalIncome);
            tvTotalExpense = itemView.findViewById(R.id.tvTotalExpense);
            sep = itemView.findViewById(R.id.sep);

        }

        public void setDetails(WrapperEntry wrapperEntry, int pos) {

            EntryHeader header = wrapperEntry.getEntryHeader();
            long timestampLong = header.getTimeStamp();
            Date d = new Date(timestampLong);
            Calendar c = Calendar.getInstance();
            c.setTime(d);

            sep.setVisibility(pos == 0 ? View.GONE : View.VISIBLE);

            SimpleDateFormat sdfDD = new SimpleDateFormat("dd");
            tvDD.setText(sdfDD.format(c.getTime()));

            SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");
            tvMonYear.setText(sdf.format(c.getTime()));
            SimpleDateFormat sdfDay = new SimpleDateFormat("EEE");
            tvDay.setText(sdfDay.format(c.getTime()));

            tvTotalExpense.setText(String.valueOf(header.getToalExpense()));
            tvTotalIncome.setText(String.valueOf(header.getToalIncome()));

            if (wrapperEntry.getDiplayType() == WrapperEntry.DISPLAY_TYPE_ALL) {
                tvTotalExpense.setVisibility(View.VISIBLE);
                tvTotalIncome.setVisibility(View.VISIBLE);
            } else if (wrapperEntry.getDiplayType() == WrapperEntry.DISPLAY_TYPE_INCOME) {
                tvTotalExpense.setVisibility(View.GONE);
                tvTotalIncome.setVisibility(View.VISIBLE);
            } else {
                tvTotalExpense.setVisibility(View.VISIBLE);
                tvTotalIncome.setVisibility(View.GONE);
            }

        }
    }
}
