package com.example.janikagahalot.authorstorydetails;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.example.janikagahalot.authorstorydetails.model.AuthorDetails;

import java.util.HashSet;

public class MainActivity extends AppCompatActivity implements AuthorDetailsAdapter.onFollowButtonClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        private AuthorDetails[] details;
        private RecyclerView mRecyclerView;
        private AuthorDetailsAdapter authorDetailsAdapter;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onFollowButtonClicked(boolean following, HashSet<Integer> set) {

    }
}
