package com.example.appmovie;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class Fragmentone extends Fragment {

    private RecyclerView recyclerView;
    private LinearLayout retryicon;

    private ImageView im2;
    private ProgressBar progressBar;

    private Handler handler;

    private ImageView Retryy;
    public static ArrayList<Movie> dataPerson = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragmentone, container, false);

        retryicon = view.findViewById(R.id.retryicon);
        recyclerView = view.findViewById(R.id.recyclerView);
        progressBar = view.findViewById(R.id.pb1);
        Retryy = view.findViewById(R.id.retry);
        im2 = view.findViewById(R.id.imageView2);
        im2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment2 thirdFragment = new Fragment2();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_container, thirdFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        handler = new Handler();

        loading();

        getDataApi();
        return view;
    }

    private void getDataApi() {
        if (isNetworkAvailable()) {
            progressBar.setVisibility(View.VISIBLE);
            retryicon.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

            //call -> untuk mengambil data di reqres.in disini dia mengambil data perpage
            Call<Data> call = ApiConfig.getApiService().getPopularMovies("35254a98cc59f9518caf1bacbf0f5792");
            call.enqueue(new Callback<Data>() {
                @Override
                public void onResponse(Call<Data> call, Response<Data> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            List<Movie> userResponse = response.body().getData(); // Assuming movie list is stored in the "data" field
                            Adaptermovie adapter = new Adaptermovie(getContext(), userResponse);
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
                    Toast.makeText(getContext(), "Unable to fetch data!", Toast.LENGTH_SHORT).show();
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
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

}
