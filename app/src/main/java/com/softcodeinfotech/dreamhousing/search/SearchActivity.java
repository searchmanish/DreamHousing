package com.softcodeinfotech.dreamhousing.search;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.softcodeinfotech.dreamhousing.R;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Toolbar
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Search");
        mToolbar.setLogo(R.drawable.search);
       // mToolbar.setSubtitle("    by Abc ");
    }
}
