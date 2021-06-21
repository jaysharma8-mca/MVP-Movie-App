package com.mvpmovieapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mvpmovieapp.contract.MovieListContract;
import com.mvpmovieapp.model.Movie;
import com.mvpmovieapp.presenter.MoviePresenter;
import com.mvpmovieapp.service.MovieListModel;
import com.mvpmovieapp.view.MovieListAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieListContract.View {

    private MoviePresenter moviePresenter;
    private RecyclerView rvMovieList;
    private List<Movie> movieList;
    private MovieListAdapter movieListAdapter;
    private ProgressBar pbLoading;
    private int pageNo = 1;

    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvMovieList = findViewById(R.id.rvMovieList);
        pbLoading = findViewById(R.id.pbLoading);

        movieList = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(this);
        rvMovieList.setLayoutManager(linearLayoutManager);
        rvMovieList.setHasFixedSize(true);
        moviePresenter = new MoviePresenter(this);
        moviePresenter.requestDataFromServer();

    }

    @Override
    public void showProgress() {
        pbLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        pbLoading.setVisibility(View.GONE);
    }

    @Override
    public void setDataToRecyclerview(List<Movie> movieListArray) {
        movieList.addAll(movieListArray);
        movieListAdapter = new MovieListAdapter(movieList, MainActivity.this);
        rvMovieList.setAdapter(movieListAdapter);
    }

    @Override
    public void onResponseFailure(Throwable throwable) {
        Log.e("ERROR", throwable.getMessage());
        Toast.makeText(MainActivity.this, "Error in getting the data", Toast.LENGTH_LONG).show();
    }
}