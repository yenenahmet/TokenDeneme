package com.example.argede_8.rehber_deneme.Servis;

import com.example.argede_8.rehber_deneme.Model.TokenCb;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Header;
import retrofit.http.POST;

/**
 * Created by ARGEDE_8 on 5/6/2017.
 */

public interface TokenAl {
    @FormUrlEncoded
    @POST("/token")
    Call<TokenCb> konumyollaServis(@Field("grant_type")String grat_type,@Field("username") String username, @Field("password") String password);
}
