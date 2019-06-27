package com.example.android.worldnews;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NewsAdapter extends ArrayAdapter<News> {

    public NewsAdapter(Context context, List<News> listNews) {
        super(context, 0, listNews);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.news_list_item, parent, false);
        }

        final News currentNews = getItem(position);

        TextView publicationSectionNameView = listItemView.findViewById(R.id.publication_section_name);
        publicationSectionNameView.setText(currentNews.getSectionName());

        String[] arrayDateTime = currentNews.getWebPublicationDate().split("T");

        String date = arrayDateTime[0];
        date = formatDate(new Date(), "dd/MM/yyyy");

        TextView publicationDateView = listItemView.findViewById(R.id.publication_date);
        publicationDateView.setText(date);

        String time = arrayDateTime[1].substring(0, arrayDateTime[1].indexOf("Z"));

        TextView publicationTimeView = listItemView.findViewById(R.id.publication_time);
        publicationTimeView.setText(time);

        TextView publicationTitleView = listItemView.findViewById(R.id.publication_title);
        publicationTitleView.setText(currentNews.getWebTitle());

        listItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openWebPage(currentNews.getWebUrl());
            }
        });
        return listItemView;
    }

    private void openWebPage(String url) {
        Uri webpage = Uri.parse(url);

        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);

        if (intent.resolveActivity(getContext().getPackageManager()) != null) {
            getContext().startActivity(intent);
        }
    }

    private String formatDate(Date dateObject, String pattern) {
        return new SimpleDateFormat(pattern).format(dateObject);
    }
}
