package com.example.mstarttask_hussam.Controller.Network;


import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface RetrofitServices {

    @GET
    @Headers({"Accept: application/json"})
    Call<ResponseBody> get(@Url String url, @QueryMap Map<String, Object> params);
}
