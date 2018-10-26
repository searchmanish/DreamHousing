package com.softcodeinfotech.dreamhousing.search;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.internal.Utility;
import com.softcodeinfotech.dreamhousing.R;
import com.softcodeinfotech.dreamhousing.beanResponse.LocationResponse;
import com.softcodeinfotech.dreamhousing.beanResponse.PropertyDetails;
import com.softcodeinfotech.dreamhousing.beanResponse.PropertyDetailsHot;
import com.softcodeinfotech.dreamhousing.beanResponse.PropertyDetailsOwner;
import com.softcodeinfotech.dreamhousing.beanResponse.PropertyResponseByLocation;
import com.softcodeinfotech.dreamhousing.utility.AppUtilits;
import com.softcodeinfotech.dreamhousing.utility.Constant;
import com.softcodeinfotech.dreamhousing.utility.NetworkUtility;
import com.softcodeinfotech.dreamhousing.utility.SharePreferenceUtils;
import com.softcodeinfotech.dreamhousing.webServices.ServiceWrapper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {


    ArrayList<String> location = new ArrayList<String>();
    String selectedItem;
    String property_type;
    String clickedView;

    ImageView imageSearch;

    String TAG = "SearchActivity";
    private RecyclerView recycler_view;
    private ArrayList<SearchDetailModel> mSearchDetailsList = new ArrayList<SearchDetailModel>();
    private SearchDetailAdapter searchDetailAdapter;

    AutoCompleteTextView autoCompleteSearch;


    String user_id;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        setupWidget();
     /*   for(int i=0;i<100 ;i++)
        {

            ar.add(String.valueOf(i));
        }*/


        //back button
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("Detail List");
        toolbar.setNavigationIcon(R.drawable.back_button);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // b.mylist.remove(b.mylist.size() - 1);

                finish();

            }
        });

        //get Location for search

       /* stringBuffer.append("mohali");
        stringBuffer.append("goa");
        stringBuffer.append("gwalior");
        stringBuffer.toString();

*/

        getLocationDataReq();

        imageSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                autoCompleteSearch.setVisibility(View.VISIBLE);
                imageSearch.setVisibility(View.GONE);
            }
        });




        //get user id from Sharedpreferences

        user_id = SharePreferenceUtils.getInstance().getString(Constant.USER_id);
        Log.v(TAG, user_id + user_id);

        // for propertyDetails
        recycler_view = (RecyclerView) findViewById(R.id.accDetailsRecyler);
        LinearLayoutManager mLayoutManger = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recycler_view.setLayoutManager(mLayoutManger);
        recycler_view.setItemAnimator(new DefaultItemAnimator());

        searchDetailAdapter = new SearchDetailAdapter(this, mSearchDetailsList, GetScreenWidth());
        recycler_view.setAdapter(searchDetailAdapter);



        //get property by searching with location

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1 ,location)
        {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setTextColor(Color.BLACK);
                text.setBackgroundColor(Color.WHITE);
                //drawView = new DrawView(SearchActivity.this);

                //text.animate();
                return view;
            }
        };
        autoCompleteSearch.setAdapter(arrayAdapter);
        autoCompleteSearch.setThreshold(1);

       /* Editable inputText = autoCompleteSearch.getText();
        Toast.makeText(SearchActivity.this, ""+inputText.toString(), Toast.LENGTH_SHORT).show();*/

        autoCompleteSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                recycler_view.setVisibility(View.GONE);
                //To Hide keyboard
                InputMethodManager imm = (InputMethodManager) getSystemService(SearchActivity.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                //
                Object item = adapterView.getItemAtPosition(i);
                selectedItem ="%"+item.toString()+"%";
                getPropertyBylocationSearch();
                // Toast.makeText(SearchActivity.this, ""+selectedItem, Toast.LENGTH_SHORT).show();

            }
        });


        final Intent intent = getIntent();
          clickedView = intent.getExtras().getString("clickedView");

        if (clickedView.equals("sell"))

        {
            property_type = "Property sell";
            propertySellRes();
        }
        if (clickedView.equals("purchase")) {
            property_type = "Property purchase";
            propertyOwnerRes();


        }
        if (clickedView.equals("rent")) {
            property_type = "Property rent";
            propertyHotRes();

        }

    }



    private void getLocationDataReq() {

        if (!NetworkUtility.isNetworkConnected(SearchActivity.this)) {
            AppUtilits.displayMessage(SearchActivity.this, getString(R.string.network_not_connected));

        } else {
            ServiceWrapper service = new ServiceWrapper(null);
            Call<LocationResponse> call = service.getLocality("1234");
            call.enqueue(new Callback<LocationResponse>() {
                @Override
                public void onResponse(Call<LocationResponse> call, Response<LocationResponse> response) {
                    Log.e(TAG, " response is " + response.body().toString());
                    if (response.body() != null && response.isSuccessful()) {
                        if (response.body().getStatus() == 1) {
                            // avi.hide();
                            // avi.smoothToHide();
                            // sellTextView.setVisibility(View.VISIBLE);

                            // enabledTitleTextViews();
                            if (response.body().getInformation().size() > 0) {

                                for (int i = 0; i < response.body().getInformation().size(); i++) {
                                    location.add(response.body().getInformation().get(i).getLocality());
                                    location.add(String.valueOf(response.body().getInformation().get(i).getPin()));


                                }

                                //searchDetailAdapter.notifyDataSetChanged();
                            }

                        } else {
                            // avi.hide();
                            //  avi.smoothToHide();
                            Log.e(TAG, "failed to get rnew prod " + response.body().getMsg());
                            // AppUtilits.displayMessage(HomeActivity.this,  response.body().getMsg());
                        }
                    } else {
                        // AppUtilits.displayMessage(HomeActivity.this,  getString(R.string.failed_request));
                        // avi.hide();
                        //avi.smoothToHide();
                        //  getNewProdRes();
                    }
                }

                @Override
                public void onFailure(Call<LocationResponse> call, Throwable t) {
                    Log.e(TAG, "fail new prod " + t.toString());
                    // avi.hide();
                    // avi.smoothToHide();
                }
            });

        }


    }


    private void propertySellRes() {

        //avi.show();
        //avi.smoothToShow();
        if (!NetworkUtility.isNetworkConnected(SearchActivity.this)) {
            AppUtilits.displayMessage(SearchActivity.this, getString(R.string.network_not_connected));

        } else {
            ServiceWrapper service = new ServiceWrapper(null);
            Call<PropertyDetails> call = service.PorductDetailsFreshRes("1234");
            call.enqueue(new Callback<PropertyDetails>() {
                @Override
                public void onResponse(Call<PropertyDetails> call, Response<PropertyDetails> response) {
                    Log.e(TAG, " response is " + response.body().getInformation().toString());
                    if (response.body() != null && response.isSuccessful()) {
                        if (response.body().getStatus() == 1) {
                            // avi.hide();
                            // avi.smoothToHide();
                            // sellTextView.setVisibility(View.VISIBLE);

                            // enabledTitleTextViews();
                            if (response.body().getInformation().size() > 0) {

                                mSearchDetailsList.clear();
                                for (int i = 0; i < response.body().getInformation().size(); i++) {

                                    mSearchDetailsList.add(new SearchDetailModel(response.body().getInformation().get(i).getPropertyDetails(),
                                            response.body().getInformation().get(i).getPropertyAddress(),
                                            response.body().getInformation().get(i).getPropertyPhone(),
                                            response.body().getInformation().get(i).getPropertyImage(),
                                            response.body().getInformation().get(i).getPropertyMrp(),
                                            String.valueOf(response.body().getInformation().get(i).getPropertyId())));
                                   /* mPDetailsModelFreshList.add(new PropertyDetailsModelFresh(response.body().getInformation().get(i).getPropertyDetails(), response.body().getInformation().get(i).getPropertyAddress(),
                                            response.body().getInformation().get(i).getPropertyMrp(), response.body().getInformation().get(i).getPropertyPhone(),response.body().getInformation().get(i).getPropertyImage()
                                           ));
*/
                                }

                                searchDetailAdapter.notifyDataSetChanged();
                            }

                        } else {
                            // avi.hide();
                            //  avi.smoothToHide();
                            Log.e(TAG, "failed to get rnew prod " + response.body().getMsg());
                            // AppUtilits.displayMessage(HomeActivity.this,  response.body().getMsg());
                        }
                    } else {
                        // AppUtilits.displayMessage(HomeActivity.this,  getString(R.string.failed_request));
                        // avi.hide();
                        //avi.smoothToHide();
                        //  getNewProdRes();
                    }
                }

                @Override
                public void onFailure(Call<PropertyDetails> call, Throwable t) {
                    Log.e(TAG, "fail new prod " + t.toString());
                    // avi.hide();
                    // avi.smoothToHide();
                }
            });

        }
    }


    private void propertyHotRes() {

        // avi.smoothToShow();
        if (!NetworkUtility.isNetworkConnected(SearchActivity.this)) {
            AppUtilits.displayMessage(SearchActivity.this, getString(R.string.network_not_connected));

        } else {
            ServiceWrapper service = new ServiceWrapper(null);
            Call<PropertyDetailsHot> call = service.PorductDetailsHot("1234");
            call.enqueue(new Callback<PropertyDetailsHot>() {
                @Override
                public void onResponse(Call<PropertyDetailsHot> call, Response<PropertyDetailsHot> response) {
                    Log.e(TAG, " response is " + response.body().getInformation().toString());
                    if (response.body() != null && response.isSuccessful()) {
                        if (response.body().getStatus() == 1) {
                            // avi.hide();
                            //avi.smoothToHide();
                            // rentTextView.setVisibility(View.VISIBLE);
                            // enabledTitleTextViews();
                            if (response.body().getInformation().size() > 0) {

                                mSearchDetailsList.clear();
                                for (int i = 0; i < response.body().getInformation().size(); i++) {

                                    mSearchDetailsList.add(new SearchDetailModel(response.body().getInformation().get(i).getPropertyDetails(),
                                            response.body().getInformation().get(i).getPropertyAddress(),
                                            response.body().getInformation().get(i).getPropertyPhone(),
                                            response.body().getInformation().get(i).getPropertyImage(),
                                            response.body().getInformation().get(i).getPropertyMrp(),
                                            String.valueOf(response.body().getInformation().get(i).getPropertyId())));
                                   /* mPDetailsModelFreshList.add(new PropertyDetailsModelFresh(response.body().getInformation().get(i).getPropertyDetails(), response.body().getInformation().get(i).getPropertyAddress(),
                                            response.body().getInformation().get(i).getPropertyMrp(), response.body().getInformation().get(i).getPropertyPhone(),response.body().getInformation().get(i).getPropertyImage()
                                           ));
*/
                                }

                                searchDetailAdapter.notifyDataSetChanged();
                            }

                        } else {
                            // avi.hide();
                            //avi.smoothToHide();
                            Log.e(TAG, "failed to get rnew prod " + response.body().getMsg());
                            // AppUtilits.displayMessage(HomeActivity.this,  response.body().getMsg());
                        }
                    } else {
                        // avi.hide();
                        //  avi.smoothToHide();
                        // AppUtilits.displayMessage(HomeActivity.this,  getString(R.string.failed_request));

                        //  getNewProdRes();
                    }
                }

                @Override
                public void onFailure(Call<PropertyDetailsHot> call, Throwable t) {
                    Log.e(TAG, "fail new prod " + t.toString());
                    // avi.hide();
                    // avi.smoothToHide();
                }
            });

        }


    }


    private void propertyOwnerRes() {

        // avi.smoothToShow();
        if (!NetworkUtility.isNetworkConnected(SearchActivity.this)) {
            AppUtilits.displayMessage(SearchActivity.this, getString(R.string.network_not_connected));

        } else {
            ServiceWrapper service = new ServiceWrapper(null);
            Call<PropertyDetailsOwner> call = service.PorductDetailsOwner("1234");
            call.enqueue(new Callback<PropertyDetailsOwner>() {
                @Override
                public void onResponse(Call<PropertyDetailsOwner> call, Response<PropertyDetailsOwner> response) {
                    Log.e(TAG, " response is " + response.body().getInformation().toString());
                    if (response.body() != null && response.isSuccessful()) {
                        if (response.body().getStatus() == 1) {
                            // avi.hide();
                            // avi.smoothToHide();
                            //purchaseTextView.setVisibility(View.VISIBLE);

                            //enabledTitleTextViews();
                            if (response.body().getInformation().size() > 0) {

                                mSearchDetailsList.clear();
                                for (int i = 0; i < response.body().getInformation().size(); i++) {

                                    mSearchDetailsList.add(new SearchDetailModel(response.body().getInformation().get(i).getPropertyDetails(),
                                            response.body().getInformation().get(i).getPropertyAddress(),
                                            response.body().getInformation().get(i).getPropertyPhone(),
                                            response.body().getInformation().get(i).getPropertyImage(),
                                            response.body().getInformation().get(i).getPropertyMrp(),
                                            String.valueOf(response.body().getInformation().get(i).getPropertyId())));
                                   /* mPDetailsModelFreshList.add(new PropertyDetailsModelFresh(response.body().getInformation().get(i).getPropertyDetails(), response.body().getInformation().get(i).getPropertyAddress(),
                                            response.body().getInformation().get(i).getPropertyMrp(), response.body().getInformation().get(i).getPropertyPhone(),response.body().getInformation().get(i).getPropertyImage()
                                           ));
*/
                                }

                                searchDetailAdapter.notifyDataSetChanged();
                            }

                        } else {
                            // avi.hide();
                            //  avi.smoothToHide();
                            Log.e(TAG, "failed to get rnew prod " + response.body().getMsg());
                            // AppUtilits.displayMessage(HomeActivity.this,  response.body().getMsg());
                        }
                    } else {
                        // avi.hide();
                        //  avi.smoothToHide();
                        // AppUtilits.displayMessage(HomeActivity.this,  getString(R.string.failed_request));

                        //  getNewProdRes();
                    }
                }

                @Override
                public void onFailure(Call<PropertyDetailsOwner> call, Throwable t) {
                    Log.e(TAG, "fail new prod " + t.toString());
                    // avi.hide();
                    //   avi.smoothToHide();
                }
            });

        }


    }


    private void getPropertyBylocationSearch() {

        if (!NetworkUtility.isNetworkConnected(SearchActivity.this)) {
            AppUtilits.displayMessage(SearchActivity.this, getString(R.string.network_not_connected));

        } else {
            ServiceWrapper service = new ServiceWrapper(null);
            Call<PropertyResponseByLocation> call = service.getPropertyByLocationSearch("1234",selectedItem,property_type);
            call.enqueue(new Callback<PropertyResponseByLocation>() {
                @Override
                public void onResponse(Call<PropertyResponseByLocation> call, Response<PropertyResponseByLocation> response) {
                    Log.e(TAG, " response is " + response.body().getInformation().toString());
                    if (response.body() != null && response.isSuccessful()) {
                        recycler_view.setVisibility(View.VISIBLE);
                        imageSearch.setVisibility(View.VISIBLE);
                        autoCompleteSearch.setVisibility(View.GONE);
                        mSearchDetailsList.clear();
                        if (response.body().getStatus() == 1) {
                            // avi.hide();
                            //avi.smoothToHide();
                            // rentTextView.setVisibility(View.VISIBLE);
                            // enabledTitleTextViews();
                            mSearchDetailsList.clear();
                            if (response.body().getInformation().size() > 0) {

                                mSearchDetailsList.clear();
                                for (int i = 0; i < response.body().getInformation().size(); i++) {

                                    mSearchDetailsList.add(new SearchDetailModel(response.body().getInformation().get(i).getPropertyDetails(),
                                            response.body().getInformation().get(i).getPropertyAddress(),
                                            response.body().getInformation().get(i).getPropertyPhone(),
                                            response.body().getInformation().get(i).getPropertyImage(),
                                            Double.valueOf (response.body().getInformation().get(i).getPropertyMrp()),
                                            String.valueOf(response.body().getInformation().get(i).getPropertyId())));
                                   /* mPDetailsModelFreshList.add(new PropertyDetailsModelFresh(response.body().getInformation().get(i).getPropertyDetails(), response.body().getInformation().get(i).getPropertyAddress(),
                                            response.body().getInformation().get(i).getPropertyMrp(), response.body().getInformation().get(i).getPropertyPhone(),response.body().getInformation().get(i).getPropertyImage()
                                           ));
*/
                                }

                                searchDetailAdapter.notifyDataSetChanged();
                            }

                        } else {
                            // avi.hide();
                            //avi.smoothToHide();
                            Log.e(TAG, "failed to get rnew prod " + response.body().getMsg());
                            // AppUtilits.displayMessage(HomeActivity.this,  response.body().getMsg());
                        }
                    } else {
                        // avi.hide();
                        //  avi.smoothToHide();
                        // AppUtilits.displayMessage(HomeActivity.this,  getString(R.string.failed_request));

                        //  getNewProdRes();
                    }
                }

                @Override
                public void onFailure(Call<PropertyResponseByLocation> call, Throwable t) {
                    Log.e(TAG, "fail new prod " + t.toString());
                    // avi.hide();
                    // avi.smoothToHide();
                }
            });

        }


    }

    private void setupWidget() {

        autoCompleteSearch = findViewById(R.id.autoCompleteSearch);
        imageSearch= findViewById(R.id.imageSearch);

    }

    private int GetScreenWidth() {
        int width = 100;

        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) getApplicationContext().getSystemService(WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        width = displayMetrics.widthPixels;

        return width;
    }


}
