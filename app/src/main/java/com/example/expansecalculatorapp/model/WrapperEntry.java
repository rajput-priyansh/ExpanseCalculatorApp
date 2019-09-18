package com.example.expansecalculatorapp.model;

public class WrapperEntry {

    public static final int TYPE_DAILY_HEADER = 1;
    public static final int TYPE_ENTRY = 2;

    public static final int DISPLAY_TYPE_ALL = 1;
    public static final int DISPLAY_TYPE_INCOME = 2;
    public static final int DISPLAY_TYPE_EXPENSE = 3;


    private int type;
    private int diplayType;
    private EntryHeader entryHeader;
    private Entry entry;


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public EntryHeader getEntryHeader() {
        return entryHeader;
    }

    public void setEntryHeader(EntryHeader entryHeader) {
        this.entryHeader = entryHeader;
    }

    public Entry getEntry() {
        return entry;
    }

    public void setEntry(Entry entry) {
        this.entry = entry;
    }

    public int getDiplayType() {
        return diplayType;
    }

    public void setDiplayType(int diplayType) {
        this.diplayType = diplayType;
    }
}
