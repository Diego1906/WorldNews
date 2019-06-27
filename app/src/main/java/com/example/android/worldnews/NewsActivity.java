package com.example.android.worldnews;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import android.app.LoaderManager.LoaderCallbacks;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity implements LoaderCallbacks<List<News>> {

    private static final int NEWS_LOADER_ID = 1;

    private String mRequestUrl;
    private NewsAdapter mAdapter;
    private TextView mEmptyStateTextView;
    private View mLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        Bundle bundle = getIntent().getExtras();

        setTitle(bundle.getString(getString(R.string.keyTitleActivity)));

        String mSectionName = bundle.getString(getString(R.string.keySectionName));

        mRequestUrl = "https://content.guardianapis.com/search?section=" + mSectionName + "&api-key=test";

        createNewsAdapter();
    }

    private void createNewsAdapter() {
        mLoadingIndicator = findViewById(R.id.loading_indicator);

        ListView newsListView = findViewById(R.id.list);
        mEmptyStateTextView = findViewById(R.id.empty_view);
        newsListView.setEmptyView(mEmptyStateTextView);

        mAdapter = new NewsAdapter(this, new ArrayList<News>());

        newsListView.setAdapter(mAdapter);

        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                News currentNews = mAdapter.getItem(position);

                Uri webPage = Uri.parse(currentNews.getWebUrl());

                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, webPage);

                if (websiteIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(websiteIntent);
                }
            }
        });

        if (internetConnectivity()) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);
        } else {
            mLoadingIndicator.setVisibility(View.GONE);

            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }
    }

    private boolean internetConnectivity() {
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return (activeNetwork != null && activeNetwork.isConnected());
    }

    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {
        return new NewsLoader(NewsActivity.this, mRequestUrl);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> news) {
        mLoadingIndicator.setVisibility(View.GONE);

        mEmptyStateTextView.setText(R.string.no_news_found);

        mAdapter.clear();

        if (news != null && !news.isEmpty()) {
            mAdapter.addAll(news);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        mAdapter.clear();
    }
}
