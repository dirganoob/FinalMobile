package com.example.appmovie;

import android.content.Context;
import android.content.Intent;
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

    public Adaptermovie(Context context, List<Movie>dataPerson) {
        this.context = context;
        this.dataPerson = dataPerson;
    }
    @NonNull
    @Override
    public Adaptermovie.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adaptermovie.ViewHolder holder, int position) {
        Movie movieResponse = dataPerson.get(position);
        String name = movieResponse.getTitle();
        holder.Name.setText(name);
        holder.date.setText(movieResponse.getReleaseDate());
        Glide.with(holder.itemView.getContext())
                .load("https://image.tmdb.org/t/p/w500" + movieResponse.getPosterPath())// Set an error image
                .into(holder.Profile);


        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(holder.itemView.getContext(), Detail.class);
            intent.putExtra("id", movieResponse.getId());
            holder.itemView.getContext().startActivity(intent);
        });
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
    }
}

