package com.example.zeeshan.bookdonation.Retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zeeshan on 12/25/2017.
 */

public class Retro {
    private static Retrofit retorfit = null;
    static{
        retorfit = new Retrofit.Builder().baseUrl("http://192.168.0.140/bookdonation/public/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static Retrofit getRetorfit(){
        return  retorfit;
    }
}
