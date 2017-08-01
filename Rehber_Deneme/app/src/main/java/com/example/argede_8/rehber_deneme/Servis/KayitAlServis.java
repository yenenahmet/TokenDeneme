package com.example.argede_8.rehber_deneme.Servis;

import com.example.argede_8.rehber_deneme.Model.KayitOlusturModel;
import com.example.argede_8.rehber_deneme.Model.KayitOlusturModelCb;


import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.Header;
import retrofit.http.POST;

/**
 * Created by ARGEDE_8 on 5/8/2017.
 */

public interface KayitAlServis {
    @POST("/BirseyLazimmi/KullaniciKaydet/api/json")
    Call<KayitOlusturModelCb> Kayital(@Body KayitOlusturModel kayitOlusturModel,@Header("Authorization")String autToken);
}
