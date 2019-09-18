package com.example.expansecalculatorapp.interfaces;


import com.example.expansecalculatorapp.model.Entry;

import java.util.ArrayList;

public interface MainActivityOperation {
    ArrayList<Entry> getAllEntries();
    void onEntryUpdate();
}
