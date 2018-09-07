package com.android.bish.walmart.search;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.bish.walmart.R;
import com.android.bish.walmart.model.ProductItemDetails;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsAdapter.SearchViewHolder> {

    private List<ProductItemDetails> products;

    private Picasso picasso;

    private OnItemClickListener itemClickListener;

    public interface OnItemClickListener {
        void onProductItemClicked(String itemId, String name, String imageUrl, String price);
    }

    public SearchResultsAdapter(List<ProductItemDetails> products, Picasso picasso, OnItemClickListener listener) {
        this.products = products;
        this.picasso = picasso;
        this.itemClickListener = listener;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.product_row_item, viewGroup, false);

        return new SearchViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder viewHolder, int i) {
        final ProductItemDetails product = products.get(i);
        viewHolder.productName.setText(product.getName());

        viewHolder.productListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemClickListener == null || product.getItemId() == null) {
                    return;
                }
                itemClickListener.onProductItemClicked(String.valueOf(product.getItemId()), product.getName(), product.getLargeImage(), String.valueOf(product.getSalePrice()));
            }
        });

        if(product.getThumbnailImage() != null) {
            RequestCreator requestCreator = picasso.load(product.getThumbnailImage());
            requestCreator.tag("TagName");
            requestCreator.memoryPolicy(MemoryPolicy.NO_CACHE);
            requestCreator.networkPolicy(NetworkPolicy.NO_STORE);
            requestCreator.error(R.drawable.ic_broken_image);
            requestCreator.into(viewHolder.productImage);
        }

        if(product.getSalePrice() != null) {
            //Rounding off to 2 places after decimal
            double roundOff = (double) Math.round(Double.valueOf(product.getSalePrice()) * 100) / 100;
            viewHolder.productPrice.setText("$"+String.valueOf(roundOff));
        }

        if(product.getCustomerRating() != null) {
            //Rounding off to 2 places after decimal
            double roundOff = (double) Math.round(Double.valueOf(product.getCustomerRating()) * 100) / 100;
            viewHolder.productRating.setText("Rating: "+ roundOff);
        }

        if(product.getStock() !=  null) {
            viewHolder.productStock.setText("Stock: "+product.getStock());
        }
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void refresh(List<ProductItemDetails> products) {
        this.products = products;
        notifyDataSetChanged();
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.product_list_container)
        View productListView;

        @BindView(R.id.product_image)
        ImageView productImage;

        @BindView(R.id.product_name)
        TextView productName;

        @BindView(R.id.product_price)
        TextView productPrice;

        @BindView(R.id.product_stock)
        TextView productStock;

        @BindView(R.id.product_customer_rating)
        TextView productRating;

        public SearchViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
