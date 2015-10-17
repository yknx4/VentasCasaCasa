package com.arekar.android.ventascasacasa.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter
{
    private final List<Fragment> mFragmentList = new ArrayList();
    private final List<String> mFragmentTitleList = new ArrayList();

    public ViewPagerAdapter(FragmentManager arg2)
    {
        super(arg2);
    }

    public void addFragment(Fragment paramFragment, String paramString)
    {
        this.mFragmentList.add(paramFragment);
        this.mFragmentTitleList.add(paramString);
    }

    public int getCount()
    {
        return this.mFragmentList.size();
    }

    public Fragment getItem(int paramInt)
    {
        return (Fragment)this.mFragmentList.get(paramInt);
    }

    public CharSequence getPageTitle(int paramInt)
    {
        return null;
    }
}