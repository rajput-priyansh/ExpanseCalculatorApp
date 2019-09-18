package com.example.expansecalculatorapp.db;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.expansecalculatorapp.model.Entry;
import com.example.expansecalculatorapp.model.Word;


@Database(entities = {Entry.class, Word.class}, version = 2, exportSchema = false)
public abstract class ExpanseRoomDatabase extends RoomDatabase {

    private static ExpanseRoomDatabase INSTANCE;

    public abstract WordDao wordDao();
    public abstract EntryDao entryDao();

    public static ExpanseRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ExpanseRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ExpanseRoomDatabase.class,
                            "entry_database")
                            .addCallback(callback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback callback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };

}