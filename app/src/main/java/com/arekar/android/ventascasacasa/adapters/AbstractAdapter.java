package com.arekar.android.ventascasacasa.adapters;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;

import java.lang.ref.WeakReference;

public abstract class AbstractAdapter<T extends ViewHolder> extends RecyclerView.Adapter<T> {
    protected String id;
    protected WeakReference<View> clickedView;

    public String getPosition() {
        return id;
    }

    public void setPosition(String position) {
        this.id = position;
    }

}
