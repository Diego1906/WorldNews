package com.example.android.worldnews;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button technologyBtn = findViewById(R.id.technology_text_view);
        callActivity(technologyBtn, NewsActivity.class,
                getString(R.string.technologyTitle),
                getString(R.string.technologySection));

        Button politicsBtn = findViewById(R.id.politics_text_view);
        callActivity(politicsBtn, NewsActivity.class,
                getString(R.string.politicsTitle),
                getString(R.string.politicsSection));

        Button businessBtn = findViewById(R.id.business_text_view);
        callActivity(businessBtn, NewsActivity.class,
                getString(R.string.businessTitle),
                getString(R.string.businessSection));

        Button worldBtn = findViewById(R.id.world_text_view);
        callActivity(worldBtn, NewsActivity.class,
                getString(R.string.worldTitle),
                getString(R.string.worldSection));

        Button scienceBtn = findViewById(R.id.science_text_view);
        callActivity(scienceBtn, NewsActivity.class,
                getString(R.string.scienceTitle),
                getString(R.string.scienceSection));
    }

    private void callActivity(Button button, final Class aClass, final String titleActivity, final String sectionName) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(getString(R.string.keyTitleActivity), titleActivity);
                bundle.putString(getString(R.string.keySectionName), sectionName);

                Intent intent = new Intent(MainActivity.this, aClass);
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });
    }
}
