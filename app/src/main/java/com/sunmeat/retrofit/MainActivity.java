package com.sunmeat.retrofit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView recycler;
    ProgressBar progressBar;
    JokeAdapter adapter;
    ArrayList<JokeModel> jokes = new ArrayList<>();
    private static final int TOTAL_JOKES = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setStatusBarColor(0xFF000000);

        recycler = findViewById(R.id.posts_recycle_view);
        progressBar = findViewById(R.id.progressBar);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        recycler.setLayoutManager(lm);

        adapter = new JokeAdapter(jokes);
        recycler.setAdapter(adapter);

        RetrofitClient.BASE_URL = "https://v2.jokeapi.dev/";
        JsonApi service = RetrofitClient.getRetrofitInstance().create(JsonApi.class);
        loadJokes(service);
    }

    private void loadJokes(JsonApi service) {
        var executor = Executors.newSingleThreadExecutor();
        var handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            int jokeCount = 0;
            while (jokeCount < TOTAL_JOKES) {
                try {
                    Call<JokeModel> call = service.getJoke("");
                    Response<JokeModel> response = call.execute();
                    if (response.isSuccessful() && response.body() != null) {
                        JokeModel joke = response.body();
                        handler.post(() -> {
                            jokes.add(joke);
                            adapter.notifyItemInserted(jokes.size() - 1);
                        });
                        jokeCount++;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            handler.post(() -> {
                progressBar.setVisibility(ProgressBar.GONE);
                if (jokes.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Не удалось загрузить шутки", Toast.LENGTH_LONG).show();
                }
            });
            executor.shutdown();
        });
    }
}