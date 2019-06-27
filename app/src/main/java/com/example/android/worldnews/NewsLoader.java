package com.example.android.worldnews;

import android.content.Context;
import android.content.AsyncTaskLoader;
import android.text.TextUtils;

import java.util.List;

public class NewsLoader extends AsyncTaskLoader<List<News>> {

    private String mUrl;

    public NewsLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<News> loadInBackground() {
        if (TextUtils.isEmpty(mUrl) || mUrl == null) {
            return null;
        }
        return NewsQueryUtils.feacthNewsData(mUrl);
    }
}
