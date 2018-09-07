package com.android.bish.walmart.recommendations;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.bish.walmart.R;
import com.android.bish.walmart.model.ProductRecoResponse;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductRecosAdapter extends RecyclerView.Adapter<ProductRecosAdapter.ProductRecosViewHolder> {

    private List<ProductRecoResponse> productRecos;

    private Picasso picasso;

    private ProductRecosAdapter.OnItemClickListener itemClickListener;

    public interface OnItemClickListener {
        void onProductItemClicked(String itemId, String name, String imageUrl, String price);
    }

    public ProductRecosAdapter(List<ProductRecoResponse> productRecos, Picasso picasso, OnItemClickListener listener) {
        this.productRecos = productRecos;
        this.picasso = picasso;
        this.itemClickListener = listener;
    }

    @NonNull
    @Override
    public ProductRecosViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recos_row_item, viewGroup, false);

        return new ProductRecosViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductRecosViewHolder viewHolder, int i) {
        final ProductRecoResponse product = productRecos.get(i);
        viewHolder.productName.setText(product.getName());

        viewHolder.productListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemClickListener == null || product.getItemId() == null) {
                    return;
                }
                itemClickListener.onProductItemClicked(String.valueOf(product.getItemId()),
                        product.getName(), product.getLargeImage(), String.valueOf(product.getSalePrice()));
            }
        });

        if(product.getMediumImage() != null) {
            RequestCreator requestCreator = picasso.load(product.getMediumImage());
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
    }

    @Override
    public int getItemCount() {
        return productRecos.size();
    }

    public void refresh(List<ProductRecoResponse> products) {
        this.productRecos = products;
        notifyDataSetChanged();
    }

    public class ProductRecosViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.product_list_container)
        View productListView;

        @BindView(R.id.product_image)
        ImageView productImage;

        @BindView(R.id.product_name)
        TextView productName;

        @BindView(R.id.product_price)
        TextView productPrice;

        @BindView(R.id.product_customer_rating)
        TextView productRating;

        public ProductRecosViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
