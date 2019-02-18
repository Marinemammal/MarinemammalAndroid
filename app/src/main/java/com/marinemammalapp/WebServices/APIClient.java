package com.marinemammalapp.WebServices;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {


    public static final String BASE_URL ="https://backend-adheeshk93.herokuapp.com";

    private static Retrofit retrofit = null;

    public static OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
    public static HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();




    public static Retrofit getClient() {

        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        clientBuilder.addInterceptor(loggingInterceptor);

        if (retrofit==null) {


            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(clientBuilder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}



