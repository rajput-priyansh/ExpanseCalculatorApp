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

    public void update (Entry entry) {
        new updateAsyncTask(entryDao).execute(entry);
    }
    public void deleteByID (Entry entry) {
        new deleteAsyncTask(entryDao).execute(entry);
    }
    public Entry getByID (int id) {
        return entryDao.getByID(id);
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

    private static class updateAsyncTask extends AsyncTask<Entry, Void, Void> {

        private EntryDao mAsyncTaskDao;

        updateAsyncTask(EntryDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Entry... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Entry, Void, Void> {

        private EntryDao mAsyncTaskDao;

        deleteAsyncTask(EntryDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Entry... params) {
            mAsyncTaskDao.deleteId(params[0].getId());
            return null;
        }
    }
}
