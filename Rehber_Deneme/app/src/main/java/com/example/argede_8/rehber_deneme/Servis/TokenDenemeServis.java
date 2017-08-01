package com.example.argede_8.rehber_deneme.Servis;

import com.example.argede_8.rehber_deneme.Model.denememodel;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Path;

/**
 * Created by ARGEDE_8 on 5/6/2017.
 */

public interface TokenDenemeServis {
    @GET("/api/BirseyLazimmi")
    Call<denememodel> getProfil(@Header("Authorization") String autToken);
}
