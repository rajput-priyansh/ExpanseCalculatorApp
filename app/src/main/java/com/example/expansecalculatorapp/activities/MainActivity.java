package com.example.expansecalculatorapp.activities;

import android.content.Intent;
import android.os.Bundle;

import com.example.expansecalculatorapp.R;
import com.example.expansecalculatorapp.fragments.EntryListFragment;
import com.example.expansecalculatorapp.interfaces.MainActivityOperation;
import com.example.expansecalculatorapp.model.Entry;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements MainActivityOperation {

    public static final int NEW_ENTRY_ACTIVITY_REQUEST_CODE = 1;

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private ViewPagerAdapter adapter;

    private List<Entry> entries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        initData();

        initUi();

        entryViewModel.getAllEntries().observe(this, entries -> {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (adapter != null) {
                        EntryListFragment entryListAll = (EntryListFragment) adapter.getItem(0);
                        EntryListFragment entryListIncome = (EntryListFragment) adapter.getItem(1);
                        EntryListFragment entryListExpense = (EntryListFragment) adapter.getItem(2);
                        entryListAll.updateData(entries);
                        entryListIncome.updateData(entries);
                        entryListExpense.updateData(entries);
                    }
                }
            }, 500);

        });
    }

    @Override
    protected void initData() {
        super.initData();

        entries = new ArrayList<>();
    }

    @Override
    protected void initUi() {
        super.initUi();

        toolbar = findViewById(R.id.toolbar);
        tabLayout = findViewById(R.id.tab);
        viewPager = findViewById(R.id.viewPager);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Expanse Calculator");
            getSupportActionBar().setElevation(0);
        }

        setSupportActionBar(toolbar);

        setupViewPager();

        tabLayout.setupWithViewPager(viewPager);

        FloatingActionButton fab = findViewById(R.id.fabAdd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EntryDetailActivity.class);
                startActivityForResult(intent, NEW_ENTRY_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.d("TESTP", "onActivityResult() called with: requestCode = [" + requestCode + "], resultCode = [" + resultCode + "], data = [" + data + "]");
        if (requestCode == NEW_ENTRY_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public ArrayList<Entry> getAllEntries() {
        return null;
    }

    @Override
    public void onEntryUpdate() {

    }
    private void setupViewPager() {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(EntryListFragment.getInstance(EntryListFragment.ALL), "ALL");
        adapter.addFragment(EntryListFragment.getInstance(EntryListFragment.INCOME), "INCOME");
        adapter.addFragment(EntryListFragment.getInstance(EntryListFragment.EXPENSE), "EXPENSE");
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
