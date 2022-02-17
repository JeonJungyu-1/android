package com.example.retrofit3;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RetrofitService {

    @GET("posts/{post}")
    Call<PostResult> getPosts(@Path("post") String post);
}
