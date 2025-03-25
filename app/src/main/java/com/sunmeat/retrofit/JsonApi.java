package com.sunmeat.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface JsonApi {
    @GET("joke/Programming")
    Call<JokeModel> getJoke(@Query("type") String type);
}