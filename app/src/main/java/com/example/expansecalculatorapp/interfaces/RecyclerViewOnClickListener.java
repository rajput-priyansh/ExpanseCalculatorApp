package com.example.expansecalculatorapp.interfaces;

import android.view.View;

public interface RecyclerViewOnClickListener<T> {
    void onClick(T data, int position, View view);
}
