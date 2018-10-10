package com.softcodeinfotech.dreamhousing.myaccount;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import com.softcodeinfotech.dreamhousing.R;
import com.softcodeinfotech.dreamhousing.beanResponse.MyAccountItemsDetails;
import com.softcodeinfotech.dreamhousing.beanResponse.PropertyDetails;
import com.softcodeinfotech.dreamhousing.beanResponse.PropertyDetailsOwner;
import com.softcodeinfotech.dreamhousing.home.HomeActivity;
import com.softcodeinfotech.dreamhousing.home.PropertyDetailsModelFresh;
import com.softcodeinfotech.dreamhousing.utility.AppUtilits;
import com.softcodeinfotech.dreamhousing.utility.Constant;
import com.softcodeinfotech.dreamhousing.utility.NetworkUtility;
import com.softcodeinfotech.dreamhousing.utility.SharePreferenceUtils;
import com.softcodeinfotech.dreamhousing.webServices.ServiceWrapper;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddedProperty extends AppCompatActivity {

    String TAG = "AddedProperty";
    private RecyclerView recycler_accDetails;
    private ArrayList<AccountDetailsModel> mPAccountDetailsList = new ArrayList<AccountDetailsModel>();
    private AccountDetailsAdapter accountDetailsAdapter;

    String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_added_property);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(" ");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_button);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //get user id from Sharedpreferences

        user_id = SharePreferenceUtils.getInstance().getString(Constant.USER_id);
        Log.v(TAG, user_id + user_id);

        // for propertyDetailsFresh
        recycler_accDetails = (RecyclerView) findViewById(R.id.accDetailsRecyler);
        LinearLayoutManager mLayoutManger = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recycler_accDetails.setLayoutManager(mLayoutManger);
        recycler_accDetails.setItemAnimator(new DefaultItemAnimator());

        accountDetailsAdapter = new AccountDetailsAdapter(this, mPAccountDetailsList, GetScreenWidth());
        recycler_accDetails.setAdapter(accountDetailsAdapter);


        getAccountItemDetails();

    }

    private int GetScreenWidth() {
        int  width=100;

        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager =  (WindowManager) getApplicationContext().getSystemService(WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        width = displayMetrics.widthPixels;

        return width;
    }

    private void getAccountItemDetails() {

        if (!NetworkUtility.isNetworkConnected(AddedProperty.this)) {
            AppUtilits.displayMessage(AddedProperty.this, getString(R.string.network_not_connected));

        } else
        {
            ServiceWrapper service =new ServiceWrapper(null);
            Call<MyAccountItemsDetails> call = service.getUserAccountItemsdetails("1234",user_id);
            call.enqueue(new Callback<MyAccountItemsDetails>() {
                @Override
                public void onResponse(Call<MyAccountItemsDetails> call, Response<MyAccountItemsDetails> response) {
                    if (response.body()!=null && response.isSuccessful())
                    {
                        if (response.body().getStatus()==1)
                        {
                            if (response.body().getInformation().size()>0) {
                                mPAccountDetailsList.clear();
                                for (int i = 0; i < response.body().getInformation().size(); i++) {
                                    mPAccountDetailsList.add(new AccountDetailsModel(response.body().getInformation().get(i).getPropertyDetails(),
                                            response.body().getInformation().get(i).getPropertyAddress(), response.body().getInformation().get(i).getPropertyPhone(),
                                            response.body().getInformation().get(i).getPropertyImage(),
                                            response.body().getInformation().get(i).getPropertyMrp(), response.body().getInformation().get(i).getPropertyId()));

                                    String Url = response.body().getInformation().get(i).getPropertyImage();

                                    Log.i("url",Url.substring(67));
                                    Log.i("property_id",String.valueOf(response.body().getInformation().get(i).getPropertyId()));
                                }

                                accountDetailsAdapter.notifyDataSetChanged();
                            }
                        }else

                        {
                            Log.e(TAG, "failed to get rnew prod "+ response.body().getMsg());
                        }
                    }else
                    {
                        Log.e(TAG, "fail"+response.body().getMsg());
                    }

                }

                @Override
                public void onFailure(Call<MyAccountItemsDetails> call, Throwable t) {
                    Log.e(TAG, "fail to load data "+ t.toString());

                }
            });
        }

    }
}