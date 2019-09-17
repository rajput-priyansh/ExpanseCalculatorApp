package com.example.expansecalculatorapp.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import com.example.expansecalculatorapp.model.Entry;

import java.util.List;

@Dao
public interface EntryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Entry entry);

    @Query("DELETE FROM entry_table")
    void deleteAll();

    @Query("SELECT * from entry_table ORDER BY timeStamp ASC")
    LiveData<List<Entry>> getAllEntries();

    @Query("SELECT * from entry_table WHERE type = :type ORDER BY timeStamp ASC")
    LiveData<List<Entry>> getEntriesByType(int type);

    @Query("SELECT * from entry_table WHERE category = :category ORDER BY timeStamp ASC")
    LiveData<List<Entry>> getEntriesByCategory(int category);
}
