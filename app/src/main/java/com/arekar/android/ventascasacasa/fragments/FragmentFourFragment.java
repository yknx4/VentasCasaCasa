package com.arekar.android.ventascasacasa.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.arekar.android.ventascasacasa.R;
import com.bumptech.glide.Glide;

public class FragmentFourFragment extends Fragment
{
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(R.layout.fragment_four, paramViewGroup, false);
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);

    Glide.with(this).load("http://lorempixel.com/256/256/people/").crossFade().into((ImageView) paramView.findViewById(R.id.client_image));
  }
}

/* Location:           D:\Apps\Apk2Java\tools\classes-dex2jar.jar
 * Qualified Name:     com.arekar.android.ventascasaencasa.fragments.FragmentFourFragment
 * JD-Core Version:    0.6.0
 */