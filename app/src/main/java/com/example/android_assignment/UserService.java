package com.example.android_assignment;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface UserService
{
    @GET("rest/v2/region/asia")
    Call<List<Data>> getPosts();
}
