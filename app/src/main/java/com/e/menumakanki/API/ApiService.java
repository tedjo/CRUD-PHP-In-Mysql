package com.e.menumakanki.API;

import com.e.menumakanki.Model.DataModel;
import com.e.menumakanki.Model.Value;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    public static final String ROOT_URL = "http://192.168.0.100/restapi/";
    @GET("lihatData.php")
    Call<List<DataModel>> getAll();

    @FormUrlEncoded
    @POST("tambahdata.php")
    Call<Value> getAdd(@Field("name") String name,
                      @Field("price") String price,
                      @Field("stock") String stock,
                      @Field("description") String description);

    @FormUrlEncoded
    @POST("updateData.php")
    Call<Value> getUpdate(@Field("id") String id,
                          @Field("name") String name,
                          @Field("price") String price,
                          @Field("stock") String stock,
                          @Field("description") String description);

    @FormUrlEncoded
    @POST("deleteData.php")
    Call<Value> getDelete(@Field("id") String id);
}
