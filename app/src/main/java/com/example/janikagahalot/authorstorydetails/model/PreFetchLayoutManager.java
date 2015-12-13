package com.example.janikagahalot.authorstorydetails.model;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by janikagahalot on 12/13/15.
 */
public class PreFetchLayoutManager extends LinearLayoutManager {

    public PreFetchLayoutManager(Context context) {
        super(context);
    }

    @Override
    protected int getExtraLayoutSpace(RecyclerView.State state) {
        return 500;
    }
}