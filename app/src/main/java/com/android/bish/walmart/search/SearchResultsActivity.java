package com.android.bish.walmart.search;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.android.bish.walmart.R;
import com.android.bish.walmart.WalmartApplication;
import com.android.bish.walmart.WalmartMainActivity;
import com.android.bish.walmart.model.ProductItemDetails;
import com.android.bish.walmart.recommendations.RecommendationsActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchResultsActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.error_layout)
    LinearLayout errorLayout;

    public static final String ITEM_ID_EXTRA = "item.id.extra";
    public static final String NAME_EXTRA = "name.extra";
    public static final String IMAGE_URL_EXTRA = "image.extra";
    public static final String PRICE_EXTRA = "price.extra";

    private SearchResultsAdapter searchResultsAdapter;

    private SearchViewModel searchViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String searchTerm = intent.getStringExtra(WalmartMainActivity.EXTRA_SEARCH_TERM);

        Picasso picasso = WalmartApplication.get(this).getWalmartApplicationComponent().getPicasso();

        getSupportActionBar().setTitle(searchTerm);

        configureViewModel(searchTerm);
        configureAdapter(picasso);
    }

    private void configureViewModel(String searchTerm) {
        searchViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        searchViewModel.init(searchTerm);
        searchViewModel.getProductsList().observe(this, new Observer<List<ProductItemDetails>>() {
            @Override
            public void onChanged(@Nullable List<ProductItemDetails> productItemDetails) {
                if(productItemDetails != null) {
                    updateSearchResults(productItemDetails);
                }
            }
        });
        searchViewModel.isLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean isLoading) {
                handleLoadingState(isLoading);
            }
        });
        searchViewModel.hasLoadingFailed().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean hasLoadingFailed) {
                handleErrorState(hasLoadingFailed);
            }
        });
    }

    private void configureAdapter(final Picasso picasso) {
        List<ProductItemDetails> productItemDetailsList = new ArrayList<>();
        searchResultsAdapter = new SearchResultsAdapter(productItemDetailsList, picasso, new SearchResultsAdapter.OnItemClickListener() {
            @Override
            public void onProductItemClicked(String itemId, String name, String imageUrl, String price) {
               startProductRecoActivity(itemId, name, imageUrl, price);
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(searchResultsAdapter);
    }

    private void updateSearchResults(List<ProductItemDetails> productItemDetails) {
        this.searchResultsAdapter.refresh(productItemDetails);
    }

    private void handleLoadingState(@Nullable Boolean isLoading) {
        if(isLoading) {
            recyclerView.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
    }

    private void handleErrorState(@Nullable Boolean hasLoadingFailed) {
        if(hasLoadingFailed) {
            errorLayout.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            errorLayout.setVisibility(View.GONE);
        }
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
