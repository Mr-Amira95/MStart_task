package com.example.mstarttask_hussam.Controller.Network;

public interface CallBack {

    void onSuccess(int tag, boolean isSuccess, Object result);

    void onFailure(int tag, Object result);
}
