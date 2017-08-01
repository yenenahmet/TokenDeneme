package com.example.argede_8.rehber_deneme.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ARGEDE_8 on 5/8/2017.
 */

public class KayitOlusturModelCb {

    @SerializedName("UserId")
    @Expose
    private String UserId;
    public void setUserId(String UserId){

        this.UserId = UserId;
    }
    public String getUserId(){

        return this.UserId;
    }
}
