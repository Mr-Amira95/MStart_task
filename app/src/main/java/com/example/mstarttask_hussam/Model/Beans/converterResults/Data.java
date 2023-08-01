package com.example.mstarttask_hussam.Model.Beans.converterResults;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("hijri")
    @Expose
    private Hijri hijri;
    @SerializedName("gregorian")
    @Expose
    private Hijri gregorian;

    public Hijri getHijri() {
        return hijri;
    }

    public void setHijri(Hijri hijri) {
        this.hijri = hijri;
    }

    public Hijri getGregorian() {
        return gregorian;
    }

    public void setGregorian(Hijri gregorian) {
        this.gregorian = gregorian;
    }

}
