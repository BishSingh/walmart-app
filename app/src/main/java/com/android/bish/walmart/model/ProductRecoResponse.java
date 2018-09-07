package com.android.bish.walmart.model;

import android.support.annotation.Nullable;

public class ProductRecoResponse {

    @Nullable
    private final Long itemId;

    @Nullable
    private final String name;

    @Nullable
    private final Double msrp;

    @Nullable
    private final Double salePrice;

    @Nullable
    private final String thumbnailImage;

    @Nullable
    private final String mediumImage;

    @Nullable
    private final String largeImage;

    @Nullable
    private final String customerRating;

    @Nullable
    private final String stock;

    @Nullable
    private final String shortDescription;

    public ProductRecoResponse(
            @Nullable Long itemId,
            @Nullable String name,
            @Nullable Double msrp,
            @Nullable Double salePrice,
            @Nullable String thumbnailImage,
            @Nullable String mediumImage,
            @Nullable String largeImage,
            @Nullable String shortDescription,
            @Nullable String customerRating,
            @Nullable String stock) {
        this.itemId = itemId;
        this.name = name;
        this.msrp = msrp;
        this.salePrice = salePrice;
        this.thumbnailImage = thumbnailImage;
        this.mediumImage = mediumImage;
        this.stock = stock;
        this.customerRating = customerRating;
        this.largeImage = largeImage;
        this.shortDescription = shortDescription;
    }

    @Nullable
    public  Long getItemId() {
        return itemId;
    }

    @Nullable
    public  String getName() {
        return name;
    }

    @Nullable
    public  Double getMsrp() {
        return msrp;
    }
    @Nullable
    public  Double getSalePrice() {
        return salePrice;
    }

    @Nullable
    public  String getThumbnailImage() {
        return thumbnailImage;
    }

    @Nullable
    public  String getMediumImage() {
        return mediumImage;
    }

    @Nullable
    public  String  getLargeImage() {
        return largeImage;
    }

    @Nullable
    public  String getShortDescription() {
        return shortDescription;
    }

    @Nullable
    public  String getStock() {
        return stock;
    }

    @Nullable
    public String getCustomerRating() {
        return customerRating;
    }
}
