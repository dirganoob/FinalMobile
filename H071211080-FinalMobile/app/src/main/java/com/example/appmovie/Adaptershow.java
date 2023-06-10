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

public class Adaptershow extends RecyclerView.Adapter<Adaptershow.ViewHolder> {

    Context context;
    private List<Tvshow> dataPerson;

    public Adaptershow(Context context, List<Tvshow> dataPerson) {
        this.context = context;
        this.dataPerson = dataPerson;
    }

    @NonNull
    @Override
    public Adaptershow.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adaptershow.ViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        Tvshow tvResponse = dataPerson.get(position);
        holder.setData(tvResponse, context);

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

        public void setData(Tvshow tv, Context context) {
            String name = tv.getName();
            Name.setText(name);
            date.setText(tv.getFirstAirDate());
            Glide.with(itemView.getContext()).load("https://image.tmdb.org/t/p/w500" + tv.getPosterPath())
                    .into(Profile);
            itemView.setOnClickListener(view -> {
                Intent intent = new Intent(itemView.getContext(), Detailshow.class);
                intent.putExtra("tv_id", tv.getId());
                intent.putExtra("rating", tv.getRating());
                intent.putExtra("synopsis", tv.getOverview());
                intent.putExtra("backdrop", tv.getBackdropPath());
                intent.putExtra("judul", tv.getName());
                intent.putExtra("poster", tv.getPosterPath());
                itemView.getContext().startActivity(intent);
            });
        }
    }
}
