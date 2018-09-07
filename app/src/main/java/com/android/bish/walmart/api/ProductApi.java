package com.android.bish.walmart.api;

import com.android.bish.walmart.model.ProductRecoResponse;
import com.android.bish.walmart.model.ProductSearchResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

//Retrofit Api Interface
public interface ProductApi {

    @GET("v1/search")
    Call<ProductSearchResponse> getSearchResults(@Query("query") String searchTerm);

    @GET("v1/nbp")
    Call<List<ProductRecoResponse>> getProductRecommendations(@Query("itemId") String itemID);
}
