package com.example.mstarttask_hussam.Controller.Network;

import android.content.Context;
import android.util.Log;

import com.example.mstarttask_hussam.Model.basic.MyApplication;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HttpHelper {

    private CallBack callback;

    public void get(final Context context, String url, final int tag, final Class clazz, HashMap<String, Object> map) {

        Call<ResponseBody> call = MyApplication.getInstance().getHttpMethods().get(url, map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    if (response.isSuccessful()){
                        result(clazz, response.body().string(), tag, response.isSuccessful(), response.isSuccessful());
                    } else {
                        result(clazz, response.errorBody().source().readUtf8().toString(), tag, response.isSuccessful(), response.isSuccessful());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                if (callback != null)
                    callback.onFailure(tag,call);

                t.printStackTrace();
            }
        });
    }

    public void setCallback(CallBack callback) {
        this.callback = callback;
    }

    private void result(Class clazz, String str, int tag, boolean isSuccess,boolean flag) {
        if (flag) {
            if (callback != null) {
                Log.d("Result API", str);
                callback.onSuccess(tag, isSuccess, MyApplication.getInstance().getGson().fromJson(str, clazz));
            }
        }else {
            if (callback != null) {
                Log.d("Result API", str);
                callback.onFailure(tag, MyApplication.getInstance().getGson().fromJson(str, clazz));
            }
        }
    }
}