package com.example.expansecalculatorapp.model;

public class EntryHeader {
    private long timeStamp;
    private double toalIncome;
    private double toalExpense;

    private String startFormateDate;
    private String endFormateDate;

    private String month;

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public double getToalIncome() {
        return toalIncome;
    }

    public void setToalIncome(double toalIncome) {
        this.toalIncome = toalIncome;
    }

    public double getToalExpense() {
        return toalExpense;
    }

    public void setToalExpense(double toalExpense) {
        this.toalExpense = toalExpense;
    }

    public String getStartFormateDate() {
        return startFormateDate;
    }

    public void setStartFormateDate(String startFormateDate) {
        this.startFormateDate = startFormateDate;
    }

    public String getEndFormateDate() {
        return endFormateDate;
    }

    public void setEndFormateDate(String endFormateDate) {
        this.endFormateDate = endFormateDate;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}
