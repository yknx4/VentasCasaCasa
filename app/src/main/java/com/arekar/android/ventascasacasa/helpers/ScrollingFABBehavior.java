package com.arekar.android.ventascasaencasa.helpers;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.CoordinatorLayout.Behavior;
import android.support.design.widget.CoordinatorLayout.LayoutParams;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.arekar.android.ventascasacasa.R;

public class ScrollingFABBehavior extends CoordinatorLayout.Behavior<FloatingActionButton>
{
    private float toolbarHeight;
    private Context paramContext;

    public ScrollingFABBehavior(Context paramContext, AttributeSet paramAttributeSet)
    {
        super(paramContext, paramAttributeSet);

        toolbarHeight = getToolbarHeight(paramContext);
    }

    private int getToolbarHeight(Context paramContext)
    {

        TypedValue localTypedValue = new TypedValue();
        if (paramContext.getTheme().resolveAttribute(R.id.toolbar, localTypedValue, true))
            return TypedValue.complexToDimensionPixelSize(localTypedValue.data, paramContext.getResources().getDisplayMetrics());
        return 0;
    }

    public boolean layoutDependsOn(CoordinatorLayout paramCoordinatorLayout, FloatingActionButton paramFloatingActionButton, View paramView)
    {
        return paramView instanceof AppBarLayout;
    }

    public boolean onDependentViewChanged(CoordinatorLayout paramCoordinatorLayout, FloatingActionButton paramFloatingActionButton, View paramView)
    {
        if ((paramView instanceof AppBarLayout))
        {
            int i = ((CoordinatorLayout.LayoutParams)paramFloatingActionButton.getLayoutParams()).bottomMargin;
            int j = paramFloatingActionButton.getHeight();
            float f = paramView.getY() / this.toolbarHeight;
            paramFloatingActionButton.setTranslationY(-(j + i) * f);
        }
        return true;
    }
}

/* Location:           D:\Apps\Apk2Java\tools\classes-dex2jar.jar
 * Qualified Name:     com.arekar.android.ventascasaencasa.helpers.ScrollingFABBehavior
 * JD-Core Version:    0.6.0
 */