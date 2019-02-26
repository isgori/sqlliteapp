package com.example.sqlliteapp.utils;



import com.example.sqlliteapp.api.RandomAPI;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class NetworkHelper {

    public static final String BASE_URL = "https://randomuser.me/api/";

    private NetworkHelper() {

    }

    public static RandomAPI createRandomAPI() {
        return prepareRetrofitClient().create(RandomAPI.class);
    }

    private static Retrofit prepareRetrofitClient() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(prepareOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private static OkHttpClient prepareOkHttpClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(getHttpLoggingInterceptor())
                .build();
    }

    private static HttpLoggingInterceptor getHttpLoggingInterceptor() {
        return new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    }
}
