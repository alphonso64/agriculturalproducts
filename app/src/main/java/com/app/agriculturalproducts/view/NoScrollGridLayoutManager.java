package com.app.agriculturalproducts.view;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;

/**
 * Created by ALPHONSO on 2016/1/6.
 */
public class NoScrollGridLayoutManager extends GridLayoutManager {
    public NoScrollGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    @Override
    public boolean canScrollVertically() {
        return false;
    }
}