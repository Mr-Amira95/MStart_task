package com.example.mstarttask_hussam.Model.basic;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.mstarttask_hussam.Controller.Network.HttpHelper;
import com.example.mstarttask_hussam.Controller.Network.RetrofitServices;
import com.example.mstarttask_hussam.Model.utilits.AppConstants;
import com.example.mstarttask_hussam.Model.utilits.PreferencesUtils;
import com.example.mstarttask_hussam.Views.Activities.MainActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyApplication extends Application {

    private static MyApplication instance;
    HttpHelper httpHelper;

    public synchronized static MyApplication getInstance() {
        return instance;
    }

    private RetrofitServices httpMethods;
    private Gson gson;
    private SharedPreferences preferences;

    public synchronized RetrofitServices getHttpMethods() {
        if (httpMethods == null) {
            gson = new GsonBuilder()
                    .setLenient()
                    .create();
            Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(AppConstants.BASE_URL)
                    .client(createClient())
                    .build();

            httpMethods = retrofit.create(RetrofitServices.class);
        }
        return httpMethods;
    }

    public OkHttpClient createClient() {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request originalRequest = chain.request();

                        Request.Builder builder = originalRequest
                                .newBuilder()
                                .header("Accept-Language", PreferencesUtils.getLanguage());

                        Request newRequest = builder.build();

                        Response resp=  chain.proceed(newRequest);
                        int code = resp.code();
                        Log.d("res Code","-------------"+code);
                        if(code==401){
                            startActivity();
                        }

//                        Request.Builder builder = originalRequest.newBuilder();
//                        Request newRequest = builder.build();

                        return resp;

                    }
                }).readTimeout(1, TimeUnit.MINUTES).
                connectTimeout(1, TimeUnit.MINUTES).build();


        return okHttpClient;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public synchronized HttpHelper getHttpHelper() {
        if (httpHelper == null)
            httpHelper = new HttpHelper();
        return httpHelper;
    }

    public synchronized SharedPreferences getPreferences() {
        if (preferences == null)
            preferences = PreferenceManager.getDefaultSharedPreferences(this);
        return preferences;
    }

    public synchronized Gson getGson() {
        if (gson == null)
            gson = new GsonBuilder()
                    .setLenient()
                    .create();
        return gson;
    }

    private void startActivity() {
        startActivity( new Intent(getApplicationContext(), MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME));
    }

}
