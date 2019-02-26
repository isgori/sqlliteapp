package com.example.sqlliteapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.sqlliteapp.models.User;

import java.util.ArrayList;
import java.util.List;

public class list_data extends AppCompatActivity  implements Adapter_RecycleView.ItemClickListener{

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_data);



        // specify an adapter (see also next example)
        // set up the RecyclerView
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new Adapter_RecycleView(this, User.dataDB);
        ((Adapter_RecycleView) mAdapter).setClickListener(this);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        try {
            Intent openActivity = new Intent(list_data.this, data_user.class);
            openActivity.putExtra("position",position);
            startActivity(openActivity);
        }catch (Exception ex){

        }
    }
}
