package com.softcodeinfotech.dreamhousing.home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.softcodeinfotech.dreamhousing.R;
import com.softcodeinfotech.dreamhousing.beanResponse.GetbannerModel;
import com.softcodeinfotech.dreamhousing.beanResponse.PropertyDetails;
import com.softcodeinfotech.dreamhousing.utility.AppUtilits;
import com.softcodeinfotech.dreamhousing.utility.Constant;
import com.softcodeinfotech.dreamhousing.utility.NetworkUtility;
import com.softcodeinfotech.dreamhousing.utility.SharePreferenceUtils;
import com.softcodeinfotech.dreamhousing.webServices.ServiceWrapper;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MultipleImageActivity extends AppCompatActivity {

    String TAG="MultipleImageActivity";
    String property_id,user_id,url;
    Toolbar  toolbar;

    Constant.TransitionType type;

    private RecyclerView recycler_multipleImage;
    private ArrayList<MultipleImageModel> mPMultipleImageModelList = new ArrayList<MultipleImageModel>();
    private MultipleImageAdapter multipleImageAdapter;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_image);

        initPage();

        initAnimation();

        // For overlap between Exiting  MainActivity.java and Entering TransitionActivity.java
        getWindow().setAllowEnterTransitionOverlap(false);


        //back button
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.back_button);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // b.mylist.remove(b.mylist.size() - 1);
                finishAfterTransition();

                //finish();

            }
        });


        // for propertyDetailsFresh
        recycler_multipleImage = (RecyclerView) findViewById(R.id.rec_multiple_image);
        LinearLayoutManager mLayoutManger = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recycler_multipleImage.setLayoutManager(mLayoutManger);
       // recycler_multipleImage.setItemAnimator(new DefaultItemAnimator());

        multipleImageAdapter = new MultipleImageAdapter(this, mPMultipleImageModelList,GetScreenHeight());
        recycler_multipleImage.setAdapter(multipleImageAdapter);



        user_id =  SharePreferenceUtils.getInstance().getString(Constant.USER_id);

       // Intent intent = new Intent();
        final Intent intent = getIntent();
        property_id = intent.getStringExtra("Property_id");
        url = intent.getStringExtra("url");

        Log.i("userid",user_id);
       Log.i("propertyId",property_id);
     // mPMultipleImageModelList.add()
        mPMultipleImageModelList.add(new MultipleImageModel(url));

        //get images method
        getMultipleImages();
    }

    private void getMultipleImages() {
        if (!NetworkUtility.isNetworkConnected(MultipleImageActivity.this)) {
            AppUtilits.displayMessage(MultipleImageActivity.this, getString(R.string.network_not_connected));

        } else {
            ServiceWrapper service = new ServiceWrapper(null);
            Call<GetbannerModel> call = service.getMultipleImage("1234",property_id);
            call.enqueue(new Callback<GetbannerModel>() {
                @Override
                public void onResponse(Call<GetbannerModel> call, Response<GetbannerModel> response) {
                    Log.e(TAG, " response is " + response.body().getInformation().toString());
                    if (response.body() != null && response.isSuccessful()) {
                        if (response.body().getStatus() == 1) {

                            if (response.body().getInformation().size() > 0) {

                                mPMultipleImageModelList.clear();
                                for (int i = 0; i < response.body().getInformation().size(); i++) {

                                    mPMultipleImageModelList.add(new MultipleImageModel(response.body().getInformation().get(i).getImgurl()));
                                   /* mPDetailsModelFreshList.add(new PropertyDetailsModelFresh(response.body().getInformation().get(i).getPropertyDetails(), response.body().getInformation().get(i).getPropertyAddress(),
                                            response.body().getInformation().get(i).getPropertyMrp(), response.body().getInformation().get(i).getPropertyPhone(),response.body().getInformation().get(i).getPropertyImage()
                                           ));
*/
                                }

                                multipleImageAdapter.notifyDataSetChanged();
                            }

                        } else {

                            Log.e(TAG, "failed to get rnew prod " + response.body().getMsg());
                            // AppUtilits.displayMessage(HomeActivity.this,  response.body().getMsg());
                        }
                    } else {
                        // AppUtilits.displayMessage(HomeActivity.this,  getString(R.string.failed_request));

                        //  getNewProdRes();
                    }
                }

                @Override
                public void onFailure(Call<GetbannerModel> call, Throwable t) {
                    Log.e(TAG, "fail new prod " + t.toString());
                    // avi.hide();

                }
            });

        }
    }


   /* //To get ScreenWidth
    public int GetScreenWidth() {
        int width = 100;

        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) getApplicationContext().getSystemService(WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        width = displayMetrics.widthPixels;

        return width;

    }*/
   //To get ScreenWidth
   public int GetScreenHeight() {
       int height = 100;

       DisplayMetrics displayMetrics = new DisplayMetrics();
       WindowManager windowManager = (WindowManager) getApplicationContext().getSystemService(WINDOW_SERVICE);
       windowManager.getDefaultDisplay().getMetrics(displayMetrics);
       height = displayMetrics.heightPixels;

       return height;

   }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onSupportNavigateUp() {
        finishAfterTransition();
        return true;
    }

    private void initPage() {

        type = (Constant.TransitionType) getIntent().getSerializableExtra(Constant.KEY_ANIM_TYPE);
       // toolbarTitle = getIntent().getExtras().getString(Constant.KEY_TITLE);


      //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initAnimation() {

        switch (type)
        {
            case SlideXML: { // For Slide by XML
                Transition enterTansition = TransitionInflater.from(this).inflateTransition(R.transition.slide);
                getWindow().setEnterTransition(enterTansition);
                break;
            }
        }


    }
}
