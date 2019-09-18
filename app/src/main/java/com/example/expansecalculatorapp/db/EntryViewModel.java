package com.example.expansecalculatorapp.db;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.expansecalculatorapp.model.Entry;

import java.util.List;

public class EntryViewModel extends AndroidViewModel {

    private EntryRepository entryRepository;
    private LiveData<List<Entry>> listLiveData;

    public EntryViewModel(@NonNull Application application) {
        super(application);
        entryRepository = new EntryRepository(application);
        listLiveData = entryRepository.getAllEntries();
    }

    public LiveData<List<Entry>> getAllEntries() {
        return listLiveData;
    }

    public void insert(Entry entry) {
        entryRepository.insert(entry);
    }

    public void update(Entry entry) {
        entryRepository.update(entry);
    }
    public void deleteByID(Entry entry) {
        entryRepository.deleteByID(entry);
    }

    public Entry getById(int id) {
        return entryRepository.getByID(id);
    }
}
