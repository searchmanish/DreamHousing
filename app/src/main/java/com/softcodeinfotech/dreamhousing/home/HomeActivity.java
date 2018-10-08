package com.softcodeinfotech.dreamhousing.home;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.github.javiersantos.appupdater.AppUpdaterUtils;
import com.github.javiersantos.appupdater.enums.AppUpdaterError;
import com.github.javiersantos.appupdater.objects.Update;
import com.softcodeinfotech.dreamhousing.R;
import com.softcodeinfotech.dreamhousing.beanResponse.GetbannerModel;
import com.softcodeinfotech.dreamhousing.beanResponse.PropertyDetails;
import com.softcodeinfotech.dreamhousing.beanResponse.PropertyDetailsHot;
import com.softcodeinfotech.dreamhousing.beanResponse.PropertyDetailsOwner;
import com.softcodeinfotech.dreamhousing.login.SigninActivity;
import com.softcodeinfotech.dreamhousing.myaccount.AddedProperty;
import com.softcodeinfotech.dreamhousing.profile.ProfileActivity;
import com.softcodeinfotech.dreamhousing.settings.SettingsActivity;
import com.softcodeinfotech.dreamhousing.utility.AppUtilits;
import com.softcodeinfotech.dreamhousing.utility.Constant;
import com.softcodeinfotech.dreamhousing.utility.NetworkUtility;
import com.softcodeinfotech.dreamhousing.utility.SharePreferenceUtils;
import com.softcodeinfotech.dreamhousing.webServices.ServiceWrapper;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ss.com.bannerslider.banners.Banner;
import ss.com.bannerslider.banners.RemoteBanner;
import ss.com.bannerslider.views.BannerSlider;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private BannerSlider bannerSlider;
    private List<Banner> remoteBanners = new ArrayList<>();


    private RecyclerView recycler_Propertydetailsfresh;
    private ArrayList<PropertyDetailsModelFresh> mPDetailsModelFreshList = new ArrayList<PropertyDetailsModelFresh>();
    private PropertyAdapterFresh propertyAdapterFresh;

    //
    private RecyclerView recycler_Propertydetailsowner;
    private ArrayList<PropertyDetailsModelFresh> mPDetailsModelOwnerList = new ArrayList<PropertyDetailsModelFresh>();
    private PropertyAdapterFresh propertyAdapterOwner;

    //
    private RecyclerView recycler_Propertydetailshot;
    private ArrayList<PropertyDetailsModelFresh> mPDetailsModelHotList = new ArrayList<PropertyDetailsModelFresh>();
    private PropertyAdapterFresh propertyAdapterHot;
/*//
private AHBottomNavigation bottomNavigation;
    private ArrayList<AHBottomNavigationItem> bottomNavigationItems = new ArrayList<>();*/

   // Drawer Layout
    private DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    AVLoadingIndicatorView avi;
    private Toolbar mToolbar;
    private String TAG ="HomeActivity";

    //app updater from playstore library
    AppUpdaterUtils appUpdaterUtils;

    //TextView
    TextView textView_fresh,textView_hotdeals,textView_owner,navName,navEmail;
//bottom navigation
BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

      //app updater from playstore library



        appUpdaterUtils = new AppUpdaterUtils(this)
                //.setUpdateFrom(UpdateFrom.AMAZON)
                //.setUpdateFrom(UpdateFrom.GITHUB)
                //.setGitHubUserAndRepo("javiersantos", "AppUpdater")
                //...
                .withListener(new AppUpdaterUtils.UpdateListener() {
                    @Override
                    public void onSuccess(Update update, Boolean isUpdateAvailable) {
                        final String nv = update.getLatestVersion();
                        final String url = update.getUrlToDownload().toString();
                        if (isUpdateAvailable) {
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(HomeActivity.this);
                            alertDialogBuilder.setTitle("Update Available");
                            alertDialogBuilder.setMessage("Version " + nv + " is available to download." + "\n" + "Downloading the latest update you will get the latest features and improvements in Dreamhousing App.");
                            alertDialogBuilder.setPositiveButton("Update",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface arg0, int arg1) {
                                            Intent i = new Intent(Intent.ACTION_VIEW);
                                            i.setData(Uri.parse(url));
                                            startActivity(i);
                                        }
                                    });

                            alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    dialog.dismiss();

                                }
                            });

                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();
                        }

                        //    Log.d("Latest Version", update.getLatestVersion());
                        //    Log.d("Latest Version Code", update.getLatestVersionCode());
                        //     Log.d("Release notes", update.getReleaseNotes());
                        //    Log.d("URL", update.getUrlToDownload());
                        //   Log.d("Is update available?", Boolean.toString(isUpdateAvailable));
                    }

                    @Override
                    public void onFailed(AppUpdaterError error) {
                        Log.d("AppUpdater Error", "Something went wrong");
                    }
                });
        appUpdaterUtils.start();


        //

        avi= findViewById(R.id.avi);

        //Force crashing by firebase

       /* Button crashButton = new Button(this);
        crashButton.setText("Crash!");
        crashButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Crashlytics.getInstance().crash(); // Force a crash
            }
        });

        addContentView(crashButton, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));*/

    //

        String sName= SharePreferenceUtils.getInstance().getString(Constant.USER_name);
        String sEmail= SharePreferenceUtils.getInstance().getString(Constant.USER_email);

        //Bottom navigation
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //Banner slider
        bannerSlider = (BannerSlider) findViewById(R.id.banner_slider1);

        // casting text view
        textView_fresh =findViewById(R.id.textView_fresh);
        textView_owner = findViewById(R.id.textView_owner);
        textView_hotdeals = findViewById(R.id.textView_hotdeals);

        //navigationDrawer textView

        NavigationView navView =findViewById(R.id.navigationView);
        View headView = navView.getHeaderView(0);
        navName = headView.findViewById(R.id.navName);
        navEmail =headView.findViewById(R.id.navEmail);
        navName.setText(sName);
        navEmail.setText(sEmail);

        //
        textView_fresh.setVisibility(View.INVISIBLE);
        textView_owner.setVisibility(View.INVISIBLE);
        textView_hotdeals.setVisibility(View.INVISIBLE);


        // Toolbar
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(" DreamHousing");
       // mToolbar.setSubtitle("  by Abc ");
         
        // //setUpNavigationDrawer menu
        @SuppressLint("CutPasteId")
        NavigationView navigationView = findViewById(R.id.navigationView);
        mDrawerLayout=findViewById(R.id.drawerLayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(HomeActivity.this,mDrawerLayout,mToolbar,
                R.string.drawer_open,R.string.drawer_close);
        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        //setUpNavigationDrawer();

    // Bottom navigation
      /*  bottomNavigation = findViewById(R.id.bottom_navigation);
*/
        mToolbar.inflateMenu(R.menu.menu_main);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                String title = (String) item.getTitle();
               // Toast.makeText(HomeActivity.this, title + " Selected !", Toast.LENGTH_SHORT).show();

               /* switch (item.getItemId()) {

                    case R.id.search:
                        Intent intent =new Intent(HomeActivity.this, SearchActivity.class);
                        startActivity(intent);
                        finish();
                        // Perform the individual Menu Actions.
                        break;

                    case R.id.mail:
                        // Perform some Actions.
                        break;
                    case R.id.logout:
                        SharePreferenceUtils.getInstance().deletePref();
                        Intent signIntent = new Intent(HomeActivity.this, SigninActivity.class);
                        startActivity(signIntent);

                        break;

                    case R.id.settings:
                        Intent settingsIntent = new Intent(HomeActivity.this, SettingsActivity.class);
                        startActivity(settingsIntent);

                }*/

                // Similarly you can write CASES for other menu items as well.


                return true;
            }
        });


        // for propertyDetailsFresh
        recycler_Propertydetailsfresh = (RecyclerView) findViewById(R.id.recycler_Propertydetailsfresh);
        LinearLayoutManager mLayoutManger3 = new LinearLayoutManager( this, LinearLayoutManager.HORIZONTAL, false);
        recycler_Propertydetailsfresh.setLayoutManager(mLayoutManger3);
        recycler_Propertydetailsfresh.setItemAnimator(new DefaultItemAnimator());

        propertyAdapterFresh = new PropertyAdapterFresh(this, mPDetailsModelFreshList, GetScreenWidth());
        recycler_Propertydetailsfresh.setAdapter(propertyAdapterFresh);


        // for propertyDetailsOwner
        recycler_Propertydetailsowner = (RecyclerView) findViewById(R.id.recycler_Propertydetailsowner);
        LinearLayoutManager mLayoutManger4 = new LinearLayoutManager( this, LinearLayoutManager.HORIZONTAL, false);
        recycler_Propertydetailsowner.setLayoutManager(mLayoutManger4);
        recycler_Propertydetailsowner.setItemAnimator(new DefaultItemAnimator());

        propertyAdapterOwner = new PropertyAdapterFresh(this, mPDetailsModelOwnerList, GetScreenWidth());
        recycler_Propertydetailsowner.setAdapter(propertyAdapterOwner);

     // for propertyDetailsHot
        recycler_Propertydetailshot = (RecyclerView) findViewById(R.id.recycler_Propertydetailshotdeal);
        LinearLayoutManager mLayoutManger5 = new LinearLayoutManager( this, LinearLayoutManager.HORIZONTAL, false);
        recycler_Propertydetailshot.setLayoutManager(mLayoutManger5);
        recycler_Propertydetailshot.setItemAnimator(new DefaultItemAnimator());

        propertyAdapterHot = new PropertyAdapterFresh(this, mPDetailsModelHotList, GetScreenWidth());
        recycler_Propertydetailshot.setAdapter(propertyAdapterHot);

        //banner
        getbannerimg();

        propertySellRes();
        propertyOwnerRes();
        propertyHotRes();



        //

       /* AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.tab_1, R.drawable.save, R.color.color_tab_1);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.tab_2, R.drawable.search, R.color.color_tab_2);
       // AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.tab_3, R.drawable.ic_maps_local_restaurant, R.color.color_tab_3);

        bottomNavigationItems.add(item1);
        bottomNavigationItems.add(item2);
       // bottomNavigationItems.add(item3);

        bottomNavigation.addItems(bottomNavigationItems);*/
    }

   /* private void setUpNavigationDrawer() {
        NavigationView navigationView = findViewById(R.id.navigationView);
        mDrawerLayout=findViewById(R.id.drawerLayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(HomeActivity.this,mDrawerLayout,mToolbar,
                R.string.drawer_open,R.string.drawer_close);
        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
       actionBarDrawerToggle.syncState();


    }*/


    //To get ScreenWidth
    public int GetScreenWidth(){
        int  width=100;

        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager =  (WindowManager) getApplicationContext().getSystemService(WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        width = displayMetrics.widthPixels;

        return width;

    }


    private void propertySellRes() {
        //avi.show();
        avi.smoothToShow();
        if (!NetworkUtility.isNetworkConnected(HomeActivity.this)){
            AppUtilits.displayMessage(HomeActivity.this,  getString(R.string.network_not_connected));

        }else {
            ServiceWrapper service = new ServiceWrapper(null);
            Call<PropertyDetails> call = service.PorductDetailsFreshRes("1234");
            call.enqueue(new Callback<PropertyDetails>() {
                @Override
                public void onResponse(Call<PropertyDetails> call, Response<PropertyDetails> response) {
                    Log.e(TAG, " response is "+ response.body().getInformation().toString());
                    if (response.body()!= null && response.isSuccessful()){
                        if (response.body().getStatus() ==1){
                            // avi.hide();
                            avi.smoothToHide();
                            enabledTitleTextViews();
                            if (response.body().getInformation().size()>0){

                                mPDetailsModelFreshList.clear();
                                for (int i=0; i< response.body().getInformation().size(); i++){

                                    mPDetailsModelFreshList.add(new PropertyDetailsModelFresh(response.body().getInformation().get(i).getPropertyDetails(), response.body().getInformation().get(i).getPropertyAddress(),
                                            response.body().getInformation().get(i).getPropertyPhone(),response.body().getInformation().get(i).getPropertyImage(),
                                            response.body().getInformation().get(i).getPropertyMrp()));
                                   /* mPDetailsModelFreshList.add(new PropertyDetailsModelFresh(response.body().getInformation().get(i).getPropertyDetails(), response.body().getInformation().get(i).getPropertyAddress(),
                                            response.body().getInformation().get(i).getPropertyMrp(), response.body().getInformation().get(i).getPropertyPhone(),response.body().getInformation().get(i).getPropertyImage()
                                           ));
*/
                                }

                                propertyAdapterFresh.notifyDataSetChanged();
                            }

                        }else {
                            // avi.hide();
                            avi.smoothToHide();
                            Log.e(TAG, "failed to get rnew prod "+ response.body().getMsg());
                            // AppUtilits.displayMessage(HomeActivity.this,  response.body().getMsg());
                        }
                    }else {
                        // AppUtilits.displayMessage(HomeActivity.this,  getString(R.string.failed_request));
                        // avi.hide();
                        avi.smoothToHide();
                        //  getNewProdRes();
                    }
                }

                @Override
                public void onFailure(Call<PropertyDetails> call, Throwable t) {
                    Log.e(TAG, "fail new prod "+ t.toString());
                    // avi.hide();
                    avi.smoothToHide();
                }
            });

        }
    }


    private void propertyOwnerRes() {
        avi.smoothToShow();
        if (!NetworkUtility.isNetworkConnected(HomeActivity.this)){
            AppUtilits.displayMessage(HomeActivity.this,  getString(R.string.network_not_connected));

        }else {
            ServiceWrapper service = new ServiceWrapper(null);
            Call<PropertyDetailsOwner> call = service.PorductDetailsOwner("1234");
            call.enqueue(new Callback<PropertyDetailsOwner>() {
                @Override
                public void onResponse(Call<PropertyDetailsOwner> call, Response<PropertyDetailsOwner> response) {
                    Log.e(TAG, " response is "+ response.body().getInformation().toString());
                    if (response.body()!= null && response.isSuccessful()){
                        if (response.body().getStatus() ==1){
                            // avi.hide();
                            avi.smoothToHide();
                            enabledTitleTextViews();
                            if (response.body().getInformation().size()>0){

                                mPDetailsModelOwnerList.clear();
                                for (int i=0; i< response.body().getInformation().size(); i++){

                                    mPDetailsModelOwnerList.add(new PropertyDetailsModelFresh(response.body().getInformation().get(i).getPropertyDetails(), response.body().getInformation().get(i).getPropertyAddress(),
                                            response.body().getInformation().get(i).getPropertyPhone(),response.body().getInformation().get(i).getPropertyImage(),
                                            response.body().getInformation().get(i).getPropertyMrp()));
                                   /* mPDetailsModelFreshList.add(new PropertyDetailsModelFresh(response.body().getInformation().get(i).getPropertyDetails(), response.body().getInformation().get(i).getPropertyAddress(),
                                            response.body().getInformation().get(i).getPropertyMrp(), response.body().getInformation().get(i).getPropertyPhone(),response.body().getInformation().get(i).getPropertyImage()
                                           ));
*/
                                }

                                propertyAdapterOwner.notifyDataSetChanged();
                            }

                        }else {
                            // avi.hide();
                            avi.smoothToHide();
                            Log.e(TAG, "failed to get rnew prod "+ response.body().getMsg());
                            // AppUtilits.displayMessage(HomeActivity.this,  response.body().getMsg());
                        }
                    }else {
                        // avi.hide();
                        avi.smoothToHide();
                        // AppUtilits.displayMessage(HomeActivity.this,  getString(R.string.failed_request));

                        //  getNewProdRes();
                    }
                }

                @Override
                public void onFailure(Call<PropertyDetailsOwner> call, Throwable t) {
                    Log.e(TAG, "fail new prod "+ t.toString());
                    // avi.hide();
                    avi.smoothToHide();
                }
            });

        }

    }


    private void propertyHotRes() {
        avi.smoothToShow();
        if (!NetworkUtility.isNetworkConnected(HomeActivity.this)){
            AppUtilits.displayMessage(HomeActivity.this,  getString(R.string.network_not_connected));

        }else {
            ServiceWrapper service = new ServiceWrapper(null);
            Call<PropertyDetailsHot> call = service.PorductDetailsHot("1234");
            call.enqueue(new Callback<PropertyDetailsHot>() {
                @Override
                public void onResponse(Call<PropertyDetailsHot> call, Response<PropertyDetailsHot> response) {
                    Log.e(TAG, " response is "+ response.body().getInformation().toString());
                    if (response.body()!= null && response.isSuccessful()){
                        if (response.body().getStatus() ==1){
                            // avi.hide();
                            avi.smoothToHide();
                            enabledTitleTextViews();
                            if (response.body().getInformation().size()>0){

                                mPDetailsModelHotList.clear();
                                for (int i=0; i< response.body().getInformation().size(); i++){

                                    mPDetailsModelHotList.add(new PropertyDetailsModelFresh(response.body().getInformation().get(i).getPropertyDetails(), response.body().getInformation().get(i).getPropertyAddress(),
                                            response.body().getInformation().get(i).getPropertyPhone(),response.body().getInformation().get(i).getPropertyImage(),
                                            response.body().getInformation().get(i).getPropertyMrp()));
                                   /* mPDetailsModelFreshList.add(new PropertyDetailsModelFresh(response.body().getInformation().get(i).getPropertyDetails(), response.body().getInformation().get(i).getPropertyAddress(),
                                            response.body().getInformation().get(i).getPropertyMrp(), response.body().getInformation().get(i).getPropertyPhone(),response.body().getInformation().get(i).getPropertyImage()
                                           ));
*/
                                }

                                propertyAdapterHot.notifyDataSetChanged();
                            }

                        }else {
                            // avi.hide();
                            avi.smoothToHide();
                            Log.e(TAG, "failed to get rnew prod "+ response.body().getMsg());
                            // AppUtilits.displayMessage(HomeActivity.this,  response.body().getMsg());
                        }
                    }else {
                        // avi.hide();
                        avi.smoothToHide();
                        // AppUtilits.displayMessage(HomeActivity.this,  getString(R.string.failed_request));

                        //  getNewProdRes();
                    }
                }

                @Override
                public void onFailure(Call<PropertyDetailsHot> call, Throwable t) {
                    Log.e(TAG, "fail new prod "+ t.toString());
                    // avi.hide();
                    avi.smoothToHide();
                }
            });

        }


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        String itemName = (String) item.getTitle();
       // Toast.makeText(HomeActivity.this, ""+itemName, Toast.LENGTH_SHORT).show();
        closeDrawer();
        switch ( item.getItemId())
        {
           /* case R.id.save:
                break;*/
            case R.id.profile:
                Intent profileIntent = new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(profileIntent);
                break;
            case R.id.setting:
                break;
            case R.id.user_logout:
                SharePreferenceUtils.getInstance().deletePref();
                Intent signIntent = new Intent(HomeActivity.this, SigninActivity.class);
                startActivity(signIntent);
                finish();
                break;
        }
        return false;
    }

    private void closeDrawer() {
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }
    // Open the Drawer
    private void showDrawer() {
        mDrawerLayout.openDrawer(GravityCompat.START);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START))
            closeDrawer();
        else
            super.onBackPressed();
    }

    public void enabledTitleTextViews(){
        textView_hotdeals.setVisibility(View.VISIBLE);
        textView_owner.setVisibility(View.VISIBLE);
        textView_fresh.setVisibility(View.VISIBLE);
    }


    //Bottom navigation
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            //  Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                   // Toast.makeText(HomeActivity.this, "Clicked on Home", Toast.LENGTH_SHORT).show();

                    //
                    Intent homeIntent = new Intent(HomeActivity.this,HomeActivity.class);
                    startActivity(homeIntent);
                    return true;
              /*  case R.id.navigation_profile:
                   // Toast.makeText(HomeActivity.this, "Clicked on profile", Toast.LENGTH_SHORT).show();
                    // toolbar.setTitle("Profile");
                   // navigation.setItemBackgroundResource(R.color.green);
                    Intent profileIntent = new Intent(HomeActivity.this, ProfileActivity.class);
                    startActivity(profileIntent);

                    return true;*/

                case R.id.my_account:
                    Intent accountIntent = new Intent(HomeActivity.this, AddedProperty.class);
                    startActivity(accountIntent);
                    return true;

                case R.id.addProperty:
                   // Toast.makeText(HomeActivity.this, "Clicked on Share", Toast.LENGTH_SHORT).show();
                    //toolbar.setTitle("Share");
                    Intent settingsIntent = new Intent(HomeActivity.this,SettingsActivity.class);
                    startActivity(settingsIntent);
                    return true;

            }
            return false;
        }
    };

    public void getbannerimg() {
        if (!NetworkUtility.isNetworkConnected(HomeActivity.this)) {
            AppUtilits.displayMessage(HomeActivity.this, getString(R.string.network_not_connected));

        } else {
            ServiceWrapper service = new ServiceWrapper(null);
            Call<GetbannerModel> call = service.getbannerModelCall("1234");
            call.enqueue(new Callback<GetbannerModel>() {
                @Override
                public void onResponse(Call<GetbannerModel> call, Response<GetbannerModel> response) {
                    Log.e(TAG, " banner response is " + response.body().getInformation().toString());
                    if (response.body() != null && response.isSuccessful()) {
                        if (response.body().getStatus() == 1) {
                            if (response.body().getInformation().size() > 0) {

                                for (int i = 0; i < response.body().getInformation().size(); i++) {
                                    remoteBanners.add(new RemoteBanner(response.body().getInformation().get(i).getImgurl()));

                                }


                            } else {

                                remoteBanners.add(new RemoteBanner("http://dreamhousing.in/property/banner/preview3.png"
                                ));
                                remoteBanners.add(new RemoteBanner("http://dreamhousing.in/property/banner/preview3.png"
                                ));
                            }

                            bannerSlider.setBanners(remoteBanners);
                        } else {
                            remoteBanners.add(new RemoteBanner("http://dreamhousing.in/property/banner/preview3.png"
                            ));
                            remoteBanners.add(new RemoteBanner("http://dreamhousing.in/property/banner/preview3.png"
                            ));
                            bannerSlider.setBanners(remoteBanners);
                        }
                    }
                }

                @Override
                public void onFailure(Call<GetbannerModel> call, Throwable t) {
                    Log.e(TAG, "fail banner ads " + t.toString());

                }
            });


        }

    }
}
