package com.example.mymoviesco;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private ArrayList<Items> mListItems;

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        /*Elements for one item*/
        public ImageView mImageView;//contains ImageView of our item
        public TextView mTitle;//contains TextView of our item
        public TextView mDescription;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            /*Assign references to our views*/
            mImageView = itemView.findViewById(R.id.imageView);
            mTitle = itemView.findViewById(R.id.title);
            mDescription = itemView.findViewById(R.id.description);
        }
    }

    public MyAdapter (ArrayList<Items> listItems){//Get the information from the list to the adapter
        mListItems=listItems;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {//Pass layout of our card to the adapter
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.items,parent,false);
        MyViewHolder mvh = new MyViewHolder(v);

        return mvh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {//Assign values to the views
        Items currentItem = mListItems.get(position);//get item at a specific position

        /*Pass information to our views*/
        holder.mImageView.setImageResource(currentItem.getImage());
        holder.mTitle.setText(currentItem.getTitle());
        holder.mDescription.setText(currentItem.getDescription());

    }

    @Override
    public int getItemCount() {//Number of items in our list
        return mListItems.size();
    }
}
