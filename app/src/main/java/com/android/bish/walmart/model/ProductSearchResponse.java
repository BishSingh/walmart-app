package com.android.bish.walmart.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductSearchResponse {

    private final int totalResults;

    private final String query;

    private final String sort;

    @SerializedName("items")
    private final List<ProductItemDetails> productItemDetailsList;

    public ProductSearchResponse(int totalResults, String query, String sort, List<ProductItemDetails> productItemDetailsList) {
        this.productItemDetailsList = productItemDetailsList;
        this.query = query;
        this.sort = sort;
        this.totalResults = totalResults;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public String getSearchTerm() {
        return query;
    }

    public List<ProductItemDetails> getProductItemDetailsList() {
        return productItemDetailsList;
    }
}
