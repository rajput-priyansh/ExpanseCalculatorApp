package com.example.expansecalculatorapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expansecalculatorapp.activities.EntryDetailActivity;
import com.example.expansecalculatorapp.activities.MainActivity;
import com.example.expansecalculatorapp.R;
import com.example.expansecalculatorapp.adapter.AdapterEntryList;
import com.example.expansecalculatorapp.interfaces.EntryEvent;
import com.example.expansecalculatorapp.interfaces.MainActivityOperation;
import com.example.expansecalculatorapp.model.Entry;
import com.example.expansecalculatorapp.model.EntryHeader;
import com.example.expansecalculatorapp.model.WrapperEntry;
import com.example.expansecalculatorapp.util.AppConstant;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EntryListFragment extends BaseFragment {

    public static final int ALL = 1;
    public static final int INCOME = 2;
    public static final int EXPENSE = 3;

    private RecyclerView rvEntry;
    private TextView tvEmpty;

    private AdapterEntryList adapterEntryList;

    private ArrayList<WrapperEntry> wrapperEntries;

    private MainActivityOperation mainActivityOperation;

    private int type;

    public static EntryListFragment getInstance(int type) {

        EntryListFragment fragment = new EntryListFragment();
        fragment.type = type;
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        /*if (adapterEntryList != null) {
            updateData(mainActivityOperation.getAllEntries());
        }*/
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() == null) {
            throw new IllegalStateException("Host activity must implement HostActionInterFace.");
        }
        mainActivityOperation = (MainActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fView = inflater.inflate(R.layout.fragment_enty_list, container, false);

        initData();

        initUi();

        return fView;
    }

    @Override
    protected void initData() {
        super.initData();

        wrapperEntries = new ArrayList<>();

    }

    @Override
    protected void initUi() {
        super.initUi();

        rvEntry = fView.findViewById(R.id.rvEntry);
        tvEmpty = fView.findViewById(R.id.tvEmpty);

        tvEmpty.setText(R.string.please_wait);

        adapterEntryList = new AdapterEntryList(getContext(), wrapperEntries, new EntryEvent() {
            @Override
            public void onEdit(Entry entry) {
                mainActivityOperation.onEntryUpdate(entry);
            }

            @Override
            public void onDelete(Entry entry) {
                mainActivityOperation.onEntryDelete(entry);
            }
        });

        rvEntry.setLayoutManager(new LinearLayoutManager(getContext()));
        rvEntry.setAdapter(adapterEntryList);
    }

    @Override
    public void updateData(List<Entry> entries) {
        super.updateData(entries);
        if (wrapperEntries == null)
            return;

        WrapperEntry wrapperEntry = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = "";
        wrapperEntries.clear();

        switch (type) {

            case ALL:
                for (Entry entry : entries) {
                    long timestampLong = entry.getTimeStamp();
                    Date d = new Date(timestampLong);
                    Calendar c = Calendar.getInstance();
                    c.setTime(d);

                    if (!strDate.equals(sdf.format(c.getTime()))) {
                        strDate = sdf.format(c.getTime());

                        wrapperEntry = new WrapperEntry();
                        wrapperEntry.setDiplayType(WrapperEntry.DISPLAY_TYPE_ALL);
                        wrapperEntry.setType(WrapperEntry.TYPE_DAILY_HEADER);
                        EntryHeader entryHeader = new EntryHeader();
                        entryHeader.setTimeStamp(entry.getTimeStamp());
                        double[] data = getTotalIncomeExpanse(entries, strDate);
                        entryHeader.setToalIncome(data[0]);
                        entryHeader.setToalExpense(data[1]);
                        wrapperEntry.setEntryHeader(entryHeader);
                        wrapperEntries.add(wrapperEntry);


                        wrapperEntry = new WrapperEntry();
                        wrapperEntry.setDiplayType(WrapperEntry.DISPLAY_TYPE_ALL);
                        wrapperEntry.setType(WrapperEntry.TYPE_ENTRY);
                        wrapperEntry.setEntry(entry);
                        wrapperEntries.add(wrapperEntry);

                    } else {
                        wrapperEntry = new WrapperEntry();
                        wrapperEntry.setDiplayType(WrapperEntry.DISPLAY_TYPE_ALL);
                        wrapperEntry.setType(WrapperEntry.TYPE_ENTRY);
                        wrapperEntry.setEntry(entry);
                        wrapperEntries.add(wrapperEntry);
                    }

                }
                break;
            case INCOME:

                for (Entry entry : entries) {
                    if (entry.getType() == AppConstant.ENTRY_INCOME) {
                        long timestampLong = entry.getTimeStamp();
                        Date d = new Date(timestampLong);
                        Calendar c = Calendar.getInstance();
                        c.setTime(d);

                        if (!strDate.equals(sdf.format(c.getTime()))) {
                            strDate = sdf.format(c.getTime());

                            wrapperEntry = new WrapperEntry();
                            wrapperEntry.setDiplayType(WrapperEntry.DISPLAY_TYPE_INCOME);
                            wrapperEntry.setType(WrapperEntry.TYPE_DAILY_HEADER);
                            EntryHeader entryHeader = new EntryHeader();
                            entryHeader.setTimeStamp(entry.getTimeStamp());
                            double[] data = getTotalIncomeExpanse(entries, strDate);
                            entryHeader.setToalIncome(data[0]);
                            entryHeader.setToalExpense(data[1]);
                            wrapperEntry.setEntryHeader(entryHeader);
                            wrapperEntries.add(wrapperEntry);


                            wrapperEntry = new WrapperEntry();
                            wrapperEntry.setDiplayType(WrapperEntry.DISPLAY_TYPE_INCOME);
                            wrapperEntry.setType(WrapperEntry.TYPE_ENTRY);
                            wrapperEntry.setEntry(entry);
                            wrapperEntries.add(wrapperEntry);

                        } else {
                            wrapperEntry = new WrapperEntry();
                            wrapperEntry.setDiplayType(WrapperEntry.DISPLAY_TYPE_INCOME);
                            wrapperEntry.setType(WrapperEntry.TYPE_ENTRY);
                            wrapperEntry.setEntry(entry);
                            wrapperEntries.add(wrapperEntry);
                        }
                    }

                }
                break;
            case EXPENSE:
                for (Entry entry : entries) {
                    if (entry.getType() == AppConstant.ENTRY_EXPENSE) {

                        long timestampLong = entry.getTimeStamp();
                        Date d = new Date(timestampLong);
                        Calendar c = Calendar.getInstance();
                        c.setTime(d);

                        if (!strDate.equals(sdf.format(c.getTime()))) {
                            strDate = sdf.format(c.getTime());

                            wrapperEntry = new WrapperEntry();
                            wrapperEntry.setDiplayType(WrapperEntry.DISPLAY_TYPE_EXPENSE);
                            wrapperEntry.setType(WrapperEntry.TYPE_DAILY_HEADER);
                            EntryHeader entryHeader = new EntryHeader();
                            entryHeader.setTimeStamp(entry.getTimeStamp());
                            double[] data = getTotalIncomeExpanse(entries, strDate);
                            entryHeader.setToalIncome(data[0]);
                            entryHeader.setToalExpense(data[1]);
                            wrapperEntry.setEntryHeader(entryHeader);
                            wrapperEntries.add(wrapperEntry);


                            wrapperEntry = new WrapperEntry();
                            wrapperEntry.setDiplayType(WrapperEntry.DISPLAY_TYPE_EXPENSE);
                            wrapperEntry.setType(WrapperEntry.TYPE_ENTRY);
                            wrapperEntry.setEntry(entry);
                            wrapperEntries.add(wrapperEntry);

                        } else {
                            wrapperEntry = new WrapperEntry();
                            wrapperEntry.setDiplayType(WrapperEntry.DISPLAY_TYPE_EXPENSE);
                            wrapperEntry.setType(WrapperEntry.TYPE_ENTRY);
                            wrapperEntry.setEntry(entry);
                            wrapperEntries.add(wrapperEntry);
                        }
                    }

                }
                break;
        }

        if (wrapperEntries.size() == 0) {
            tvEmpty.setText(R.string.empty);
            tvEmpty.setVisibility(View.VISIBLE);
            rvEntry.setVisibility(View.GONE);
        } else {
            tvEmpty.setVisibility(View.GONE);
            rvEntry.setVisibility(View.VISIBLE);
            adapterEntryList.setWrapperEntries(wrapperEntries);
        }
    }

    private double[] getTotalIncomeExpanse(List<Entry> entries, String strDate) {
        double[] data = new double[2];
        double income = 0;
        double expense = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        for (Entry entry : entries) {
            long timestampLong = entry.getTimeStamp();
            Date d = new Date(timestampLong);
            Calendar c = Calendar.getInstance();
            c.setTime(d);

            if (strDate.equals(sdf.format(c.getTime()))) {
                if (entry.getType() == AppConstant.ENTRY_INCOME) {
                    income += entry.getAmount();
                } else {
                    expense += entry.getAmount();
                }
            }
        }

        data[0] = income;
        data[1] = expense;

        return data;
    }
}
