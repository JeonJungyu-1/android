package com.example.retrofit3;


import com.google.gson.annotations.SerializedName;

public class PostResult {

    @SerializedName("userId")
    private int userId;

    @SerializedName("id")
    private int id;

    private String title;

    @SerializedName("body")
    private String bodyValue;

    @Override
    public String toString() {
        return "PostResult{" +
                "userId=" + userId +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", bodyValue='" + bodyValue + '\'' +
                '}';
    }
}
