package com.example.expansecalculatorapp.activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expansecalculatorapp.R;
import com.example.expansecalculatorapp.adapter.AdapterCategoryList;
import com.example.expansecalculatorapp.db.EntryViewModel;
import com.example.expansecalculatorapp.interfaces.RecyclerViewOnClickListener;
import com.example.expansecalculatorapp.model.Category;

import java.util.ArrayList;

public class BaseActivity extends AppCompatActivity {

    protected EntryViewModel entryViewModel;

    interface SingleSelectionDialogItemSelect {
        public void onItemSelect(int index);
    }

    private ProgressDialog progressDialog;

    /**
     * init the data.
     */
    protected void initData() {

        entryViewModel = ViewModelProviders.of(this).get(EntryViewModel.class);

    }

    /**
     * init the UI.
     */
    protected void initUi() {
        progressDialog = new ProgressDialog(this, R.style.AppCompatAlertDialogStyle);
    }

    /**
     * Created to open and show progressDialog box.
     *
     * @param message
     */
    protected void showProgressDialog(String message) {

        if (progressDialog != null) {
            progressDialog.setMessage(message);
            progressDialog.setCancelable(false);
            if (!progressDialog.isShowing()) {
                progressDialog.show();
            }
        }


    }

    /**
     * Created to close opened progress dialog.
     */
    protected void cancelProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    /**
     * Created to print message
     * @param s
     */
    protected void toastMessage(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    /**
     * Created to opne single selection opt
     * @param title
     * @param singleSelectionDialogItemSelect
     * @param items
     */
    protected void openSingleChoiceDialog(String title, SingleSelectionDialogItemSelect singleSelectionDialogItemSelect, String[] items) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        singleSelectionDialogItemSelect.onItemSelect(which);
                        dialog.dismiss();
                    }
                });
        builder.show();
    }

    protected void openCustomSingleChoiceDialog(SingleSelectionDialogItemSelect singleSelectionDialogItemSelect, ArrayList<Category> categories) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.recycle_grid, null, false);
        builder.setView(recyclerView);
        AlertDialog dialog = builder.show();
        AdapterCategoryList adapterCategoryList = new AdapterCategoryList(this, categories, new RecyclerViewOnClickListener<Category>(){

            @Override
            public void onClick(Category data, int position, View view) {
                singleSelectionDialogItemSelect.onItemSelect(position);
                dialog.dismiss();
            }
        });
        recyclerView.setAdapter(adapterCategoryList);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
    }



}
