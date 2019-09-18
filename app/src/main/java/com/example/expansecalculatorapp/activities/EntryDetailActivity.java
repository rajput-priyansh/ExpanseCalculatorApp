package com.example.expansecalculatorapp.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.example.expansecalculatorapp.R;
import com.example.expansecalculatorapp.db.EntryDao;
import com.example.expansecalculatorapp.db.EntryViewModel;
import com.example.expansecalculatorapp.model.Category;
import com.example.expansecalculatorapp.model.Entry;
import com.example.expansecalculatorapp.util.AppConstant;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class EntryDetailActivity extends BaseActivity {

    public static final String EXTRA_REPLY = "com.example.expansecalculatorapp.REPLY";

    private TextView tvDate;
    private TextView tvTime;
    private TextView tvType;
    private TextView tvAccount;
    private TextView tvCategory;

    private EditText etAmt;
    private EditText etNote;

    private Calendar selectedDate;

    private SimpleDateFormat sdfDate;
    private SimpleDateFormat sdfTime;

    private Entry entry;

    private int entryID;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_entry_detail);

        initData();

        initUi();
    }

    @Override
    protected void initData() {
        super.initData();

        sdfDate = new SimpleDateFormat("dd/MM/YYYY");
        sdfTime = new SimpleDateFormat("hh:mm a");

        entryID = getIntent().getIntExtra("ID", 0);

        if (entryID > 0) {
            new getAsyncTask().execute();
        } else {

            entry = new Entry();
        }

        if (entry == null)
            entry = new Entry();

    }

    private class getAsyncTask extends AsyncTask<Integer, Entry, Entry> {

        @Override
        protected Entry doInBackground(Integer... integers) {
            entry = entryViewModel.getById(entryID);
            return entry;
        }

        @Override
        protected void onPostExecute(Entry entry) {
            updateUi();
        }
    }

    @Override
    protected void initUi() {
        super.initUi();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Entry Detail");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setElevation(0);
        }

        tvDate = findViewById(R.id.tvDate);
        tvTime = findViewById(R.id.tvTime);
        tvType = findViewById(R.id.tvType);
        tvAccount = findViewById(R.id.tvAccount);
        tvCategory = findViewById(R.id.tvCategory);
        etAmt = findViewById(R.id.etAmt);
        etNote = findViewById(R.id.etNote);

        tvDate.setOnClickListener(view -> {

            if (selectedDate == null)
                selectedDate = Calendar.getInstance();

            DatePickerDialog datePickerDialog = new DatePickerDialog(EntryDetailActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                    selectedDate.set(Calendar.YEAR, year);
                    selectedDate.set(Calendar.MONTH, month);
                    selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    updateDateTimeUi();

                }
            }, selectedDate.get(Calendar.YEAR), selectedDate.get(Calendar.MONTH), selectedDate.get(Calendar.DAY_OF_MONTH));

            datePickerDialog.show();

        });

        tvTime.setOnClickListener(view -> {

            if (selectedDate == null)
                selectedDate = Calendar.getInstance();

            TimePickerDialog timePickerDialog = new TimePickerDialog(EntryDetailActivity.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                    selectedDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    selectedDate.set(Calendar.MINUTE, minute);

                    updateDateTimeUi();
                }
            }, selectedDate.get(Calendar.HOUR_OF_DAY), selectedDate.get(Calendar.MINUTE), false);

            timePickerDialog.show();

        });

        tvType.setOnClickListener(view -> {
            openSingleChoiceDialog("Select Entry Type", new SingleSelectionDialogItemSelect() {
                @Override
                public void onItemSelect(int index) {
                    entry.setType(index);
                    tvType.setText(AppConstant.arrayEntryType[index]);

                    //Reset the category
                    tvCategory.setText(R.string.select_category);
                    tvCategory.setCompoundDrawablesWithIntrinsicBounds( 0, 0, 0, 0);
                    entry.setCategory(-1);
                }
            }, AppConstant.arrayEntryType);
        });

        tvAccount.setOnClickListener(view -> {
            openSingleChoiceDialog("Select Account", new SingleSelectionDialogItemSelect() {
                @Override
                public void onItemSelect(int index) {
                    entry.setAccount(index);
                    tvAccount.setText(AppConstant.arrayAccountType[index]);
                }
            }, AppConstant.arrayAccountType);
        });

        tvCategory.setOnClickListener(view -> {

            ArrayList<Category> categories = new ArrayList<>();

            if (entry.getType() == AppConstant.ENTRY_INCOME) {
                categories.add(new Category("SALARY", AppConstant.INCOME_SALARY));//,
                categories.add(new Category("STORE",  AppConstant.INCOME_STORE));//,
                categories.add(new Category("REWARD", AppConstant.INCOME_REWARD));//,
                categories.add(new Category("OTHER", AppConstant.INCOME_OTHER));//R.drawable.ic_other_primary_24dp,

            } else if (entry.getType() == AppConstant.ENTRY_EXPENSE) {

                categories.add(new Category("HEALTH", AppConstant.EXPENSE_HEALTH));//R.drawable.ic_healing_primary_24dp,
                categories.add(new Category("FOOD", AppConstant.EXPENSE_FOOD));//R.drawable.ic_food_primary_24dp,
                categories.add(new Category("BILL", AppConstant.EXPENSE_BILL));//R.drawable.ic_bill_primary_24dp,
                categories.add(new Category("FUEL", AppConstant.EXPENSE_FUEL));//R.drawable.ic_local_gas_station_primary_24dp,
                categories.add(new Category("SHOPPING", AppConstant.EXPENSE_SHOPPING));//R.drawable.ic_local_grocery_store_primary_24dp,
                categories.add(new Category("HOTEL", AppConstant.EXPENSE_HOTEL));//R.drawable.ic_local_hotel_primary_24dp,
                categories.add(new Category("ENTERTAINMENT", AppConstant.EXPENSE_ENTERTAINMENT));//R.drawable.ic_fun_primary_24dp,
                categories.add(new Category("OTHER", AppConstant.EXPENSE_OTHER));// R.drawable.ic_other_primary_24dp,

            } else {
                toastMessage("Please select Type..");
                return;
            }

            openCustomSingleChoiceDialog(new SingleSelectionDialogItemSelect() {
                @Override
                public void onItemSelect(int index) {
                    entry.setCategory(categories.get(index).getValue());
                    tvCategory.setText(categories.get(index).getName());
                    int iconDrawable = -1;
                    switch (categories.get(index).getValue()) {
                        case AppConstant.INCOME_SALARY:
                            iconDrawable = R.drawable.ic_work_primary;
                            break;
                        case AppConstant.INCOME_STORE:
                            iconDrawable = R.drawable.ic_store_primary;
                            break;
                        case AppConstant.INCOME_REWARD:
                            iconDrawable = R.drawable.ic_stars_primary;
                            break;
                        case AppConstant.INCOME_OTHER:
                            iconDrawable = R.drawable.ic_other_primary;
                            break;
                        case AppConstant.EXPENSE_HEALTH:
                            iconDrawable = R.drawable.ic_healing_primary;
                            break;
                        case AppConstant.EXPENSE_FOOD:
                            iconDrawable = R.drawable.ic_food_primary;
                            break;
                        case AppConstant.EXPENSE_BILL:
                            iconDrawable = R.drawable.ic_bill_primary;
                            break;
                        case AppConstant.EXPENSE_SHOPPING:
                            iconDrawable = R.drawable.ic_local_grocery_store_primary;
                            break;
                        case AppConstant.EXPENSE_HOTEL:
                            iconDrawable = R.drawable.ic_local_hotel_primary;
                            break;
                        case AppConstant.EXPENSE_ENTERTAINMENT:
                            iconDrawable = R.drawable.ic_fun_primary;
                            break;
                        case AppConstant.EXPENSE_OTHER:
                            iconDrawable = R.drawable.ic_other_primary;
                            break;
                        case AppConstant.EXPENSE_FUEL:
                            iconDrawable = R.drawable.ic_local_gas_station_primary;
                            break;

                    }

                    tvCategory.setCompoundDrawablesWithIntrinsicBounds( iconDrawable, 0, 0, 0);
                }
            }, categories);

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_save) {

            if (validate()) {
                entry.setAmount(Double.valueOf(etAmt.getText().toString()));
                entry.setNote(etNote.getText().toString());
                entry.setTimeStamp(selectedDate.getTimeInMillis());

                entryViewModel.insert(entry);

                Intent replyIntent = new Intent();
                replyIntent.putExtra(EXTRA_REPLY, "Done");
                setResult(RESULT_OK, replyIntent);
                finish();

                return true;
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private void updateUi() {
        Log.d("TESTP", "EntryDetailActivity updateUi() called"+entry);
        if (entry == null)
            return;
        long timestampLong = entry.getTimeStamp();
        Date d = new Date(timestampLong);
        selectedDate = Calendar.getInstance();
        selectedDate.setTime(d);

        tvType.setText(AppConstant.arrayEntryType[entry.getType()]);
        tvAccount.setText(AppConstant.arrayAccountType[entry.getAccount()]);

        String category = "";
        int iconDrawable = -1;
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
        tvCategory.setCompoundDrawablesWithIntrinsicBounds( iconDrawable, 0, 0, 0);

        etAmt.setText(String.valueOf(entry.getAmount()));

        if (entry.getNote() != null)
            etNote.setText(String.valueOf(entry.getNote()));

        updateDateTimeUi();

    }

    private void updateDateTimeUi() {

        tvDate.setText(sdfDate.format(selectedDate.getTime()));
        tvTime.setText(sdfTime.format(selectedDate.getTime()));
    }

    private boolean validate() {
        if (selectedDate == null){
            toastMessage("Please select Date or Time..");
            return false;
        }

        if (entry.getType() < 0) {
            toastMessage("Please select entry Type..");
            return false;
        }

        if (entry.getAccount() < 0) {
            toastMessage("Please select entry Account..");
            return false;
        }

        if (entry.getCategory() < 0) {
            toastMessage("Please select entry Category..");
            return false;
        }

        if (etAmt.getText() == null || etAmt.getText().toString().trim().length() == 0) {
            toastMessage("Please insert Amount..");
            return false;
        }

        return true;
    }

}
