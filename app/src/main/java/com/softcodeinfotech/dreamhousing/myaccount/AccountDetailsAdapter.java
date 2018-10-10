package com.softcodeinfotech.dreamhousing.myaccount;

import android.content.Context;

import com.softcodeinfotech.dreamhousing.home.PropertyDetailsModelFresh;

import java.util.List;

public class AccountDetailsAdapter {

    private Context mContext;
    private List<AccountDetailsModel> mAccountDetailsList;
    private String TAG ="AccountDetailsAdapter";
    private int mScrenwidth;


    public AccountDetailsAdapter(Context context, List<AccountDetailsModel> list, int screenwidth)
    {
        mContext = context;
        mAccountDetailsList = list;
        mScrenwidth = screenwidth;
    }

}
