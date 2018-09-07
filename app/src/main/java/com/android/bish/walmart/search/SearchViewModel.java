package com.android.bish.walmart.search;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.android.bish.walmart.WalmartApplication;
import com.android.bish.walmart.api.ProductApi;
import com.android.bish.walmart.model.ProductItemDetails;
import com.android.bish.walmart.model.ProductSearchResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchViewModel extends AndroidViewModel {

   private MutableLiveData<List<ProductItemDetails>> productsList = new MutableLiveData<>();

   private MutableLiveData<Boolean> isLoadingLiveData = new MutableLiveData<>();

   private MutableLiveData<Boolean> hasLoadFailed = new MutableLiveData<>();

   private ProductApi productApi;

   public SearchViewModel(Application application) {
       super(application);
       this.productApi = ((WalmartApplication) application).getWalmartApplicationComponent().getProductApi();
   }

   public void init(String searchTerm) {
       if(searchTerm == null || searchTerm.isEmpty()) {
           return;
       }
       hasLoadFailed.setValue(false);
       isLoadingLiveData.setValue(true);
       fetchProductResults(searchTerm);
   }

   public LiveData<Boolean> isLoading() {
       return isLoadingLiveData;
   }

   public LiveData<Boolean> hasLoadingFailed() {
       return hasLoadFailed;
   }

   public LiveData<List<ProductItemDetails>> getProductsList() {
       return productsList;
   }

    private void fetchProductResults(String searchTerm) {

        Call<ProductSearchResponse> call = productApi.getSearchResults(searchTerm);

        call.enqueue(new Callback<ProductSearchResponse>() {
            @Override
            public void onResponse(Call<ProductSearchResponse> call, Response<ProductSearchResponse> response) {
                isLoadingLiveData.setValue(false);
                hasLoadFailed.setValue(false);
                if(response.body() != null && response.body().getTotalResults() != 0) {
                   productsList.setValue(response.body().getProductItemDetailsList());
                } else {
                    hasLoadFailed.setValue(true);
                }
            }

            @Override
            public void onFailure(Call<ProductSearchResponse> call, Throwable t) {
                isLoadingLiveData.setValue(false);
                hasLoadFailed.setValue(true);
            }
        });
    }
}
