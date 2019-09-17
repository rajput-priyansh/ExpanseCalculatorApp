package com.example.expansecalculatorapp.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expansecalculatorapp.R;
import com.example.expansecalculatorapp.db.EntryViewModel;
import com.example.expansecalculatorapp.model.Entry;

import java.util.List;

public class EntryListFragment extends BaseFragment {

    private RecyclerView rvEntry;
    private TextView tvEmpty;

    public static EntryListFragment getInstance() {

        return new EntryListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fView = inflater.inflate(R.layout.fragment_enty_list, container, false);
        return fView;
    }

    @Override
    protected void initData() {
        super.initData();

    }

    @Override
    protected void initUi() {
        super.initUi();

        rvEntry = fView.findViewById(R.id.rvEntry);
        tvEmpty = fView.findViewById(R.id.tvEmpty);

        rvEntry.setVisibility(View.GONE);
        tvEmpty.setVisibility(View.VISIBLE);
    }


}
