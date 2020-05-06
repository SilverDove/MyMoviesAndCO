package com.example.mymoviesco;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<Movie> movies;
    private Context context;

    public static final String IMAGE_URL_BASE_PATH="http://image.tmdb.org/t/p/w342//";

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        /*Elements for one item*/
        public ImageView mImageMovie;//contains ImageView of our item
        public TextView mTitle;//contains TextView of our item
        public TextView mDate;
        public TextView mDescription;

        public ImageView mImageRating;//contains ImageView of our item
        public TextView mRatings;//contains TextView of our item

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            /*Assign references to our views*/
            mImageMovie = itemView.findViewById(R.id.movie_image);
            mTitle = itemView.findViewById(R.id.title);
            mDate = itemView.findViewById(R.id.date);
            mDescription = itemView.findViewById(R.id.description);

            mImageRating = itemView.findViewById(R.id.rating_image);
            mRatings = itemView.findViewById(R.id.rating);

        }
    }

    public MyAdapter (List<Movie> m, Context c){//Get the information from the list to the adapter
        movies=m;
        context=c;
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
        Movie currentMovie = movies.get(position);//get item at a specific position
        String image_url = IMAGE_URL_BASE_PATH + movies.get(position).getPoster_path();

        /*Pass information to our views*/
        Picasso.with(context)
                .load(image_url)
                .placeholder(android.R.drawable.sym_def_app_icon)
                .error(android.R.drawable.sym_def_app_icon)
                .into(holder.mImageMovie);

        holder.mTitle.setText(currentMovie.getTitle());
        holder.mDescription.setText(currentMovie.getOverview());
        holder.mDate.setText(currentMovie.getRelease_date());
        holder.mRatings.setText(Double.toString(currentMovie.getVote_average()));

    }

    @Override
    public int getItemCount() {//Number of items in our list
        return movies.size();
    }
}
