package com.example.janikagahalot.authorstorydetails;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.janikagahalot.authorstorydetails.model.AuthorDetails;
import com.example.janikagahalot.authorstorydetails.model.PreFetchLayoutManager;
import com.google.gson.Gson;
import com.google.gson.JsonParser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity implements AuthorDetailsAdapter.onFollowButtonClickListener {

    private AuthorDetails[] details;
    private RecyclerView mRecyclerView;
    private AuthorDetailsAdapter authorDetailsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        JsonParser jsonParser = new JsonParser();
        String json;
        try {
            InputStream inputStream = getResources().openRawResource(R.raw.data);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");
            details  = new Gson().fromJson(json, AuthorDetails[].class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        mRecyclerView = (RecyclerView) findViewById(R.id.author_details_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        authorDetailsAdapter = new AuthorDetailsAdapter(this, details, this);
        mRecyclerView.setAdapter(authorDetailsAdapter);
    }



    @Override
    public void onFollowButton(boolean following,HashSet<Integer> positions, AuthorDetails authorDetail){
        details[0].setIsFollowing(following);
        for(int i= 1; i< details.length; i++) {
            if (details[i].getDb().equals(authorDetail.getDb())) {
                details[i].setIsFollowing(following);
            }
            if(following) {
                String text = getString(R.string.following);
                Drawable drawable = getDrawable(R.drawable.followed_button_background);
                updateButtons(positions, text, drawable);
            }
            else {
                String text = getString(R.string.follow);
                Drawable drawable = getDrawable(R.drawable.follow_button_background);
                updateButtons(positions, text, drawable);
            }

        }
    }
@Override
public void onAuthorButtonClicked(boolean following, HashSet<Integer> set){
    for(int i= 1; i< details.length; i++) {
            details[i].setIsFollowing(following);
        }
    if(following) {
        String text = getString(R.string.following);
        Drawable drawable = getDrawable(R.drawable.followed_button_background);
        updateButtons(set, text, drawable);
    }
    else {
        String text = getString(R.string.follow);
        Drawable drawable = getDrawable(R.drawable.follow_button_background);
        updateButtons(set, text, drawable);
    }

}


    private void updateButtons(HashSet<Integer> positions, String text, Drawable drawable){
        LinearLayoutManager pflm  = (LinearLayoutManager) mRecyclerView.getLayoutManager();
        int first = pflm.findFirstVisibleItemPosition();
        int last =  pflm.findLastVisibleItemPosition();
       // int pos= 0;
               for (int pos : positions) {
                   if (pos-1 >= first && pos-1 <= last) {
                       View view = pflm.findViewByPosition(pos-1);
                       Button button = (Button) view.findViewById(R.id.user_follow_button);
                       button.setText(text);
                       button.setBackground(drawable);
                   }
               }
    }

}
