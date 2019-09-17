package com.example.expansecalculatorapp.db;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.expansecalculatorapp.model.Entry;

import java.util.List;

public class EntryRepository {

    private EntryDao entryDao;
    private LiveData<List<Entry>> mAllentry;

    public EntryRepository(Application application) {
        ExpanseRoomDatabase db = ExpanseRoomDatabase.getDatabase(application);
        entryDao = db.entryDao();
        mAllentry = entryDao.getAllEntries();
    }

    LiveData<List<Entry>> getAllEntries() {
        return mAllentry;
    }


    public void insert (Entry entry) {
        new insertAsyncTask(entryDao).execute(entry);
    }

    private static class insertAsyncTask extends AsyncTask<Entry, Void, Void> {

        private EntryDao mAsyncTaskDao;

        insertAsyncTask(EntryDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Entry... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
