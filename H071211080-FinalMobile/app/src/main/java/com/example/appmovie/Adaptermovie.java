package com.example.appmovie;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class Adaptermovie extends RecyclerView.Adapter<Adaptermovie.ViewHolder> {

    Context context;
    private List<Movie> dataPerson;

    public Adaptermovie(Context context, List<Movie> dataPerson) {
        this.context = context;
        this.dataPerson = dataPerson;
    }

    @NonNull
    @Override
    public Adaptermovie.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adaptermovie.ViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        Movie movieResponse = dataPerson.get(position);
        holder.setData(movieResponse, context);

    }

    @Override
    public int getItemCount() {
        return dataPerson.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Name, date;
        ImageView Profile;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.tv1);
            date = itemView.findViewById(R.id.tv2);
            Profile = itemView.findViewById(R.id.img1);
        }

        public void setData(Movie movie, Context context) {
            String name = movie.getTitle();
            Name.setText(name);
            date.setText(movie.getReleaseDate());
            Glide.with(itemView.getContext()).load("https://image.tmdb.org/t/p/w500" + movie.getPosterPath())
                    .into(Profile);
            itemView.setOnClickListener(view -> {
                Intent intent = new Intent(itemView.getContext(), Detail.class);
                intent.putExtra("movie_id", movie.getId());
                intent.putExtra("rating", movie.getRating());
                intent.putExtra("synopsis", movie.getSynopsis());
                intent.putExtra("backdrop", movie.getBackdropPath());
                intent.putExtra("judul", movie.getTitle());
                intent.putExtra("poster", movie.getPosterPath());
                itemView.getContext().startActivity(intent);
            });
        }
    }
}
