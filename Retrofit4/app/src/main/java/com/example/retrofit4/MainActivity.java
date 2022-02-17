package com.example.retrofit4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://openapi.airkorea.or.kr/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitService service1 = retrofit.create(RetrofitService.class);

        Call<List<PostResult>> call = service1.getPosts();

        call.enqueue(new Callback<List<PostResult>>() {
            @Override
            public void onResponse(Call<List<PostResult>> call, Response<List<PostResult>> response) {
                if (response.isSuccessful()) {

                }
            }

            @Override
            public void onFailure(Call<List<PostResult>> call, Throwable t) {

            }
        });
    }
}