package com.android.bish.walmart.dagger;

import com.android.bish.walmart.api.ProductApi;
import com.squareup.picasso.Picasso;

import dagger.Component;

@WalmartApplicationScope
@Component(modules = {ProductsApiModule.class, PicassoModule.class})
public interface WalmartComponent {

    ProductApi getProductApi();

    Picasso getPicasso();


}
