package com.arekar.android.ventascasacasa.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.arekar.android.ventascasacasa.R;
import com.arekar.android.ventascasacasa.activities.BaseActivity;
import com.arekar.android.ventascasacasa.model.Client;
import com.arekar.android.ventascasacasa.model.Product;
import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.util.List;

public class ProductRvAdapter extends AbstractAdapter<ProductRvAdapter.ProductViewHolder>
{
  List<Product> products;
  WeakReference<BaseActivity> act;

  public ProductRvAdapter(JsonArray paramJsonArray)
  {
    Type list = new TypeToken<List<Product>>(){}.getType();
    this.products = ((List)new Gson().fromJson(paramJsonArray, list));
  }
  public ProductRvAdapter(JsonArray array, BaseActivity act){
    this(array);
    this.act = new WeakReference<>(act);
  }

  public Product getProductById(String id){
    if(products==null || products.size()==0) return null;
    for(Product prod: products){
      if(prod.getId().equals(id)) return prod;
    }
    return null;
  }
  public int getItemCount()
  {
    return this.products.size();
  }

  public void onBindViewHolder(ProductViewHolder paramProductViewHolder, int paramInt)
  {
    final Product localProduct = (Product)this.products.get(paramInt);

    paramProductViewHolder.cardViewProd.setOnLongClickListener(new View.OnLongClickListener() {
      @Override
      public boolean onLongClick(View v) {
        setPosition(localProduct.getId());
        return false;
      }
    });

    paramProductViewHolder.productName.setText(((Product)this.products.get(paramInt)).getName());
    paramProductViewHolder.productBrand.setText(((Product)this.products.get(paramInt)).getBrand());
    paramProductViewHolder.productPrice.setText(((Product)this.products.get(paramInt)).getPrice().toString());
    paramProductViewHolder.productDescription.setText(((Product)this.products.get(paramInt)).getDescription());
    new AlphaAnimation(0.0F, 1.0F).setDuration(1000L);
//    if (Math.random() > 0.5D)
//    {
//      Glide.with(paramProductViewHolder.productImage.getContext()).load("http://lorempixel.com/128/128/").crossFade().into(paramProductViewHolder.productImage);
//      return;
//    }
    Glide.with(paramProductViewHolder.productImage.getContext()).load(localProduct.getImage()).crossFade().into(paramProductViewHolder.productImage);
  }

  public ProductViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt)
  {
    return new ProductViewHolder(LayoutInflater.from(paramViewGroup.getContext()).inflate(R.layout.products_item, paramViewGroup, false));
  }

  public void onViewRecycled(ProductViewHolder paramProductViewHolder)
  {
    super.onViewRecycled(paramProductViewHolder);
  }

  public static class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
    CardView cardViewProd;
    TextView productBrand;
    TextView productDescription;
    ImageView productImage;
    TextView productName;
    TextView productPrice;

    ProductViewHolder(View paramView)
    {
      super(paramView);
      this.cardViewProd = ((CardView)paramView.findViewById(R.id.card_view_prod));
      this.productImage = ((ImageView)paramView.findViewById(R.id.product_image));
      this.productName = ((TextView)paramView.findViewById(R.id.product_name));
      this.productBrand = ((TextView)paramView.findViewById(R.id.product_brand));
      this.productPrice = ((TextView)paramView.findViewById(R.id.product_price));
      this.productDescription = ((TextView)paramView.findViewById(R.id.product_description));
      cardViewProd.setOnCreateContextMenuListener(this);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
      menu.setHeaderTitle(R.string.action_product);
      menu.add(0, v.getId(), 0, "Edit");//groupId, itemId, order, title
      menu.add(0, v.getId(), 0, "Delete");
    }
  }
}

/* Location:           D:\Apps\Apk2Java\tools\classes-dex2jar.jar
 * Qualified Name:     com.arekar.android.ventascasaencasa.adapters.ProductRvAdapter
 * JD-Core Version:    0.6.0
 */