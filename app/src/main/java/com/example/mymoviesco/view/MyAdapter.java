package com.example.mymoviesco.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymoviesco.R;
import com.example.mymoviesco.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> implements Filterable {
    private List<Movie> movies;
    private List<Movie> moviesListFull;
    private Context context;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemListener(OnItemClickListener listener){
            mListener = listener;
    }

    public static final String IMAGE_URL_BASE_PATH="https://image.tmdb.org/t/p/w342//";

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        /*Elements for one item*/
        public ImageView mImageMovie;//contains ImageView of our item
        public TextView mTitle;//contains TextView of our item
        public TextView mDate;
        public TextView mDescription;

        public ImageView mImageRating;//contains ImageView of our item
        public TextView mRatings;//contains TextView of our item

        public MyViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            /*Assign references to our views*/
            mImageMovie = itemView.findViewById(R.id.movie_image);
            mTitle = itemView.findViewById(R.id.title);
            mDate = itemView.findViewById(R.id.date);
            mDescription = itemView.findViewById(R.id.description);

            mImageRating = itemView.findViewById(R.id.rating_image);
            mRatings = itemView.findViewById(R.id.rating);

            //When we click on the item
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    if(listener != null){
                        int position = getAdapterPosition();//Provide the position of the item
                        if(position != RecyclerView.NO_POSITION){//Verify that the position is valid
                            listener.onItemClick(position);
                        }
                    }
                }
            });

        }
    }

    public MyAdapter (List<Movie> m, Context c){//Get the information from the list to the adapter
        movies=m;
        context=c;
        moviesListFull = new ArrayList<>(m);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {//Pass layout of our card to the adapter
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.items,parent,false);
        MyViewHolder mvh = new MyViewHolder(v, mListener);

        return mvh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {//Assign values to the views
        Movie currentMovie = movies.get(position);//get item at a specific position
        String image_url;
        if(currentMovie.getPoster_path() == null){//If there is no picture available
            image_url = "https://cdn.pixabay.com/photo/2015/12/22/04/00/photo-1103595_960_720.png";
        }else{
            image_url = IMAGE_URL_BASE_PATH + currentMovie.getPoster_path();
        }

        /*Pass information to our views*/
        Picasso.with(context)
                .load(image_url)
                .placeholder(android.R.drawable.sym_def_app_icon)
                .error(android.R.drawable.sym_def_app_icon)
                .resize(400, 500)
                .centerInside()
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

    @Override
    public Filter getFilter() {
        return moviesFilter;
    }

    private Filter moviesFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Movie> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0){ //If our inout field is empty
                //Show all the movies from the database
                filteredList.addAll(moviesListFull);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Movie m : moviesListFull){
                    if(m.getTitle().toLowerCase().contains(filterPattern)){
                        filteredList.add(m);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {
            movies.clear();
            movies.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
