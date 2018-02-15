package com.gazua.ddeokrok.coinman.network;



import com.gazua.ddeokrok.coinman.network.page.Page;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface PageService {
    @GET
    public abstract Call<Page> selectContentGetSubList(@Url String paramString);

    @FormUrlEncoded
    @POST
    public abstract Call<Page> selectContentSubList(@Url String paramString, @FieldMap Map<String, Object> paramMap);
}