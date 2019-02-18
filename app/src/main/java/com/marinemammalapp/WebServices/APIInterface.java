package com.marinemammalapp.WebServices;

import com.marinemammalapp.dataObjects.SaveDataResponse;

import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface APIInterface {


    //create booking
    @Headers({
            "X-Parse-Application-Id: phVoEWlREKlQ0pXo"
    })
    @POST("api/classes/Android")
    Call<SaveDataResponse>
    saveDetails(@Body HashMap<String, Object> mammalDetails);

    @Headers({
            "X-Parse-Application-Id: phVoEWlREKlQ0pXo"
    })
    @Multipart
    @POST("api/files/upload.data")
    Call<ResponseBody> uploadFile(@Part MultipartBody.Part file);

//    @POST("api/classes/Android")
//    Call<SaveDataResponse>
//    saveDetails(@PartMap HashMap<String, Object> mammalDetails,
//                @Part MultipartBody.Part file, @Header("userId") String userId);
//

}
