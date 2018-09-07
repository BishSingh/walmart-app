package com.android.bish.walmart.recommendations;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.android.bish.walmart.WalmartApplication;
import com.android.bish.walmart.api.ProductApi;
import com.android.bish.walmart.model.ProductRecoResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecoViewModel extends AndroidViewModel{

    private  String itemId;

    private MutableLiveData<List<ProductRecoResponse>> productsRecoList = new MutableLiveData<>();

    private ProductApi productApi;

    public RecoViewModel(Application application) {
        super(application);
        this.productApi = ((WalmartApplication) application).getWalmartApplicationComponent().getProductApi();
    }

    public void init(String itemId) {
        if(itemId == null || itemId.isEmpty()) {
            return;
        }
        fetchProductRecos(itemId);
    }

    public LiveData<List<ProductRecoResponse>> getProductRecos() {
        return productsRecoList;
    }

    private void fetchProductRecos(String itemId) {

        Call<List<ProductRecoResponse>> call = productApi.getProductRecommendations(itemId);

        call.enqueue(new Callback<List<ProductRecoResponse>>() {
            @Override
            public void onResponse(Call<List<ProductRecoResponse>> call, Response<List<ProductRecoResponse>> response) {
                if(response.body() != null) {
                    productsRecoList.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<ProductRecoResponse>> call, Throwable t) {

            }
        });
    }
}
