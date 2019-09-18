package com.example.expansecalculatorapp.interfaces;

import com.example.expansecalculatorapp.model.Entry;

public interface EntryEvent {

    public void onEdit(Entry entry);
    public void onDelete(Entry entry);
}
