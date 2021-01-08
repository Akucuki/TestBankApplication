package com.pirkovitaliysoft.testbankapplication.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NbuService {
    @GET("NBU_Exchange/exchange?json")
    Call<List<NbuEntity>> listEntities(@Query("date") String date);
}
