package com.example.retrofit4;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RetrofitService {
    @GET("posts")
    Call<List<PostResult>> getPosts();

    @GET("posts/{id}")
    Call<List<PostResult>> getPost(@Path("id") String id);

}
