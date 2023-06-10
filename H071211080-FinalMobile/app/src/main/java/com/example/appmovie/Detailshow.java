package com.example.appmovie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Detailshow extends AppCompatActivity {

    private ImageView img2,img3,back;
    private TextView tv4,tv5,tv6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailshow);
        img2 = findViewById(R.id.img2);
        img3 = findViewById(R.id.img3);
        tv4 = findViewById(R.id.tv4);
        tv5 = findViewById(R.id.tv5);
        tv6 = findViewById(R.id.tv6);
        back  = findViewById(R.id.backbutton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Detailshow.this,MainActivity.class);
                startActivity(intent);
            }
        });
        getDataApi();
    }

    private void getDataApi() {
        if (isNetworkAvailable()) {
            Intent intent = getIntent();
            String tvid = intent.getStringExtra("tv_id");
            Toast.makeText(this, tvid, Toast.LENGTH_SHORT).show();
            Call<Data4> call = ApiConfig.getApiService().getTVShowDetails(Integer.valueOf(tvid), "35254a98cc59f9518caf1bacbf0f5792");
            call.enqueue(new Callback<Data4>() {
                @Override
                public void onResponse(Call<Data4> call, Response<Data4> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(Detailshow.this, "test", Toast.LENGTH_SHORT).show();
                        if (response.body() != null) {
                            Tvshow tv = response.body().getData4();
                            String judul = getIntent().getStringExtra("judul");
                            String rating = getIntent().getStringExtra("rating");
                            String synopsis = getIntent().getStringExtra("synopsis");
                            String backdropPath = getIntent().getStringExtra("backdrop");
                            String poster = getIntent().getStringExtra("poster");
                            tv4.setText(judul);
                            tv5.setText(rating);
                            tv6.setText(synopsis);
                            Glide.with(Detailshow.this)
                                    .load("https://image.tmdb.org/t/p/w500" + backdropPath)
                                    .into(img2);
                            Glide.with(Detailshow.this)
                                    .load("https://image.tmdb.org/t/p/w500" + poster)
                                    .into(img3);
                        }
                    } else {
                        Toast.makeText(Detailshow.this, "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Data4> call, Throwable t) {
                    Toast.makeText(Detailshow.this, "Unable to fetch data!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
