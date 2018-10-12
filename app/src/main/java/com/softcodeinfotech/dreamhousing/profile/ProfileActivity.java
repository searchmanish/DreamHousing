package com.softcodeinfotech.dreamhousing.profile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.softcodeinfotech.dreamhousing.R;
import com.softcodeinfotech.dreamhousing.utility.Constant;
import com.softcodeinfotech.dreamhousing.utility.SharePreferenceUtils;

public class ProfileActivity extends AppCompatActivity {
    TextView mUserName,mUserPhone,mUserEmail;

    String user_name,user_phone,user_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Profile");
        setSupportActionBar(toolbar);
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_previous);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setUpWidget();

        getDataFromSharedPref();

        mUserName.setText(user_name);
        mUserPhone.setText(user_phone);
        mUserEmail.setText(user_email);
    }



    private void setUpWidget() {
        mUserName = findViewById(R.id.userName);
        mUserPhone = findViewById(R.id.userPhone);
        mUserEmail = findViewById(R.id.userEmail);
    }

    private void getDataFromSharedPref() {
        user_name = SharePreferenceUtils.getInstance().getString(Constant.USER_name);
        user_phone = SharePreferenceUtils.getInstance().getString(Constant.USER_phone);
        user_email = SharePreferenceUtils.getInstance().getString(Constant.USER_email);


    }



}
