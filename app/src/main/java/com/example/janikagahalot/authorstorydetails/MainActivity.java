package com.example.janikagahalot.authorstorydetails;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.janikagahalot.authorstorydetails.model.AuthorDetails;
import com.example.janikagahalot.authorstorydetails.model.PreFetchLayoutManager;
import com.google.gson.Gson;
import com.google.gson.JsonParser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements AuthorDetailsAdapter.onFollowButtonClickListener {

    private AuthorDetails[] details;
    private RecyclerView mRecyclerView;
    private AuthorDetailsAdapter authorDetailsAdapter;
    private HashMap<String, ArrayList<Integer>> commons = new HashMap<>();

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
            updateCommons();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        mRecyclerView = (RecyclerView) findViewById(R.id.author_details_list);
        mRecyclerView.setLayoutManager(new PreFetchLayoutManager(this));
        authorDetailsAdapter = new AuthorDetailsAdapter(this, details, this);
        mRecyclerView.setAdapter(authorDetailsAdapter);
    }

    private void updateCommons() {
        for(Integer i = 1; i < details.length; i++){
            String key = details[i].getDb();
            if(commons.containsKey(key)){
                commons.get(key).add(i);
            }
            else {
                ArrayList<Integer> list  = new ArrayList<>();
                list.add(i);
                commons.put(key, list);
            }
        }
    }

    @Override
    public void onFollowAll(boolean following) {
        onFollowButton(following, null);
    }

    @Override
    public void onFollowButton(boolean following,String key){
            if(following) {
                String text = getString(R.string.following);
                Drawable drawable = getDrawable(R.drawable.followed_button_background);
                updateButtons(following,key, text, drawable);
            }
            else {
                String text = getString(R.string.follow);
                Drawable drawable = getDrawable(R.drawable.follow_button_background);
                updateButtons(following, key, text, drawable);
            }
    }

    private void updateButtons(boolean following, String key, String text, Drawable drawable) {
        GridLayoutManager pflm  = (GridLayoutManager) mRecyclerView.getLayoutManager();
        int first = pflm.findFirstVisibleItemPosition();
        int last =  pflm.findLastVisibleItemPosition();
        ArrayList<Integer> positions = new ArrayList<>();
        if(key == null) {
            for(Integer  i = 0; i< details.length; i++){
                positions.add(i);
            }
        }
        else {
            positions = commons.get(key);
        }
        for (int pos : positions) {
            authorDetailsAdapter.notifyItemChanged(pos);
            details[pos].setIsFollowing(following);
            if (pos >= first && pos <= last) {
                View view = pflm.findViewByPosition(pos);
                Button button = (Button) view.findViewById(R.id.user_follow_button);
                button.setText(text);
                button.setBackground(drawable);
            }
        }
    }
}
