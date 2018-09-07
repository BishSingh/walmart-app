package com.android.bish.walmart.recommendations;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.bish.walmart.R;
import com.android.bish.walmart.WalmartApplication;
import com.android.bish.walmart.model.ProductRecoResponse;
import com.android.bish.walmart.search.SearchResultsActivity;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.android.bish.walmart.search.SearchResultsActivity.IMAGE_URL_EXTRA;
import static com.android.bish.walmart.search.SearchResultsActivity.ITEM_ID_EXTRA;
import static com.android.bish.walmart.search.SearchResultsActivity.NAME_EXTRA;
import static com.android.bish.walmart.search.SearchResultsActivity.PRICE_EXTRA;

public class RecommendationsActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.product_image)
    ImageView productImage;

    @BindView(R.id.product_name)
    TextView productNameView;

    @BindView(R.id.product_price)
    TextView priceView;

    private RecoViewModel recoViewModel;

    private ProductRecosAdapter productRecosAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendations);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        String itemId = intent.getStringExtra(ITEM_ID_EXTRA);
        String name = intent.getStringExtra(NAME_EXTRA);
        String imageUrl = intent.getStringExtra(IMAGE_URL_EXTRA);
        String price = intent.getStringExtra(PRICE_EXTRA);

        Picasso picasso = WalmartApplication.get(this).getWalmartApplicationComponent().getPicasso();

        getSupportActionBar().setTitle("Item Details");

        showProductUI(imageUrl, name, price, picasso);

        configureViewModel(itemId);
        configureAdapter(picasso);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return (super.onOptionsItemSelected(item));
    }

    private void configureViewModel(String searchTerm) {
        recoViewModel = ViewModelProviders.of(this).get(RecoViewModel.class);
        recoViewModel.init(searchTerm);
        recoViewModel.getProductRecos().observe(this, new Observer<List<ProductRecoResponse>>() {
            @Override
            public void onChanged(@Nullable List<ProductRecoResponse> productRecos) {
                if (productRecos != null) {
                    updateRecoResults(productRecos);
                }
            }
        });
    }

    private void configureAdapter(Picasso picasso) {
        List<ProductRecoResponse> productRecos = new ArrayList<>();
        productRecosAdapter = new ProductRecosAdapter(productRecos, picasso, new ProductRecosAdapter.OnItemClickListener() {
            @Override
            public void onProductItemClicked(String itemId, String name, String imageUrl, String price) {
                startProductRecoActivity(itemId, name, imageUrl, price);
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(productRecosAdapter);
    }

    private void showProductUI(String imageUrl, String name, String price, Picasso picasso) {
        if(name != null) {
            productNameView.setText(name);
        }
        if (imageUrl != null) {
            RequestCreator requestCreator = picasso.load(imageUrl);
            requestCreator.tag("TagName");
            requestCreator.memoryPolicy(MemoryPolicy.NO_CACHE);
            requestCreator.networkPolicy(NetworkPolicy.NO_STORE);
            requestCreator.error(R.drawable.ic_broken_image);
            requestCreator.into(productImage);
        }
        if (price != null) {
            priceView.setText("$"+price);
        }
    }

    private void updateRecoResults(List<ProductRecoResponse> productRecos) {
        this.productRecosAdapter.refresh(productRecos);
    }

    private void startProductRecoActivity(String itemId, String name, String imageUrl, String price) {
        Intent intent = new Intent(this, RecommendationsActivity.class);
        intent.putExtra(ITEM_ID_EXTRA, itemId);
        intent.putExtra(NAME_EXTRA, name);
        intent.putExtra(IMAGE_URL_EXTRA, imageUrl);
        intent.putExtra(PRICE_EXTRA, price);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
