package com.pirkovitaliysoft.testbankapplication.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PbService {
    @GET("p24api/exchange_rates?json")
    Call<PbEntityContainer> listEntities(@Query("date") String date);
}
