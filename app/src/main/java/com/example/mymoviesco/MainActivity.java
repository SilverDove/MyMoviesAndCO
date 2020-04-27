package com.example.mymoviesco;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;//contains recycler view created in our XML layout
    private RecyclerView.Adapter mAdapter;//bridge between our data and our recycler view
    private RecyclerView.LayoutManager mLayoutManager;//aligning items in our list

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Items> listItems = new ArrayList<>();
        listItems.add(new Items(R.drawable.ic_movie, "Gladiator", "Crowe portrays Roman general Maximus Decimus Meridius, who is betrayed when Commodus, the ambitious son of Emperor Marcus Aurelius, murders his father and seizes the throne. Reduced to slavery, Maximus becomes a gladiator and rises through the ranks of the arena to avenge the murders of his family and his emperor."));
        listItems.add(new Items(R.drawable.ic_live_tv, "BBC", "The BBC is a British organization which broadcasts programmes on radio and television. BBC is an abbreviation for 'British Broadcasting Corporation'. The concert will be broadcast live by the BBC."));
        listItems.add(new Items(R.drawable.ic_person, "Robin Williams", "American actor and comedian"));

        /*Initialization*/
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);//Recycler view doesn't change in size (Increase performance)
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new MyAdapter(listItems);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

    }
}
