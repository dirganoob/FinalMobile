package com.example.appmovie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayout retryicon;
    private ProgressBar progressBar;

    private Handler handler;

    private ImageView Retryy;
    public static ArrayList<Movie> dataPerson = new ArrayList<>();

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        retryicon = findViewById(R.id.retryicon);
        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.pb1);
        Retryy = findViewById(R.id.retry);
        GridLayoutManager layoutManager = new GridLayoutManager(MainActivity.this, 2);
        recyclerView.setLayoutManager(layoutManager);
        handler = new Handler();

        loading();

        getDataApi();

    }
    private void getDataApi() {
        if (isNetworkAvailable()) {
            progressBar.setVisibility(View.VISIBLE);
            retryicon.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

            //call -> untuk mengambil data di reqres.in disini dia mengambil data perpage
            Call<Data> call =ApiConfig.getApiService().getPopularMovies("35254a98cc59f9518caf1bacbf0f5792");
            call.enqueue(new Callback<Data>() {
                @Override
                public void onResponse(Call<Data> call, Response<Data> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            List<Movie> userResponse = response.body().getData(); // Assuming movie list is stored in the "data" field
                            Adaptermovie adapter = new Adaptermovie(MainActivity.this, userResponse);
                            recyclerView.setAdapter(adapter);
                        } else {
                            if (response.errorBody() != null) {
                                Log.e("MainActivity", "onFailure: " + response.errorBody().toString());
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<Data> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Unable to fetch data!", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Retry();

        }
    }

    private void loading() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(() -> {
            try { //simulate process in background thread
                for (int i = 0; i <= 10; i++) {
                    Thread.sleep(100);
                    int percentage = i * 10;
                    handler.post(() -> {
                        //update ui in main thread
                        if (percentage == 100) {
                            progressBar.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                        } else {
                            progressBar.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                        }
                    });
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
    private void Retry() {
        retryicon.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);

        Retryy.setOnClickListener(view -> {
            retryicon.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            handler.postDelayed(() -> {
                progressBar.setVisibility(View.VISIBLE);
//                recyclerView.setVisibility(View.VISIBLE);
                getDataApi();
            }, 500);

        });
    }



    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}