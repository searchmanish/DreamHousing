package com.softcodeinfotech.dreamhousing.home;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.softcodeinfotech.dreamhousing.EnquireActivity;
import com.softcodeinfotech.dreamhousing.R;
import com.softcodeinfotech.dreamhousing.beanResponse.GetbannerModel;
import com.softcodeinfotech.dreamhousing.login.SigninActivity;
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
import ss.com.bannerslider.banners.Banner;
import ss.com.bannerslider.banners.RemoteBanner;
import ss.com.bannerslider.views.BannerSlider;

public class DetailsActivity extends AppCompatActivity {
     String TAG = "DetailsActivity";
    FloatingActionButton floatingActionButton;
    ImageView imageView;

    TextView prop_details, prop_mrp, prop_location, call_agent, enquire_now;

   /* private BannerSlider bannerSlider;
    private List<Banner> remoteBanners = new ArrayList<>();
*/
    String userName;
    String userMobile;

    String image_url;
    String details;
    String mrp;
    String phone;
    String address;
    String property_id;
    String user_id;

    Toolbar toolbar;



     /* intent.putExtra("property_phone", model.getProperty_phone());
                intent.putExtra("property_img_url", model.getProperty_image());
                intent.putExtra("property_details",model.getProperty_details());
                intent.putExtra("property_mrp",model.getProperty_mrp());
                intent.putExtra("property_address",model.getProperty_address());*/

    CoordinatorLayout rootlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        rootlayout = findViewById(R.id.main_content);

     /*   //Banner slider
        bannerSlider = (BannerSlider) findViewById(R.id.image_slider);
*/
        setUpWidget();

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

                finish();

            }
        });
      /*  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(" ");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_button);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/

        // getting Data from preferences
        userName = SharePreferenceUtils.getInstance().getString(Constant.USER_name);
        userMobile = SharePreferenceUtils.getInstance().getString(Constant.USER_phone);
        user_id = SharePreferenceUtils.getInstance().getString(Constant.USER_id);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar);
       // collapsingToolbar.setTitle("Dream Housing");


        final Intent intent = getIntent();
       final String property_call = intent.getExtras().getString("property_phone");

      //  Toast.makeText(DetailsActivity.this, "" + property_call, Toast.LENGTH_SHORT).show();

        image_url = intent.getExtras().getString("property_img_url");
        details = intent.getExtras().getString("property_details");
        mrp = intent.getExtras().getString("property_mrp");
        address = intent.getExtras().getString("property_address");
        phone = intent.getExtras().getString("property_phone");
        property_id = intent.getExtras().getString("property_id");

        setValueToWidget();

        Log.i(TAG,property_id);
      /*  //Banner slider
        bannerSlider = (BannerSlider) findViewById(R.id.image_slider);

        loadMultipleImages();
*/
       // Toast.makeText(DetailsActivity.this, ""+mrp, Toast.LENGTH_SHORT).show();

       // Toast.makeText(DetailsActivity.this, ""+details +""+mrp +""+address +""+phone, Toast.LENGTH_LONG).show();


        imageView = findViewById(R.id.col_imageview);
        Glide.with(DetailsActivity.this)
                .load(image_url)
                .into(imageView);
        floatingActionButton = findViewById(R.id.floatingActionButton);

      /*  if(userMobile.isEmpty())
        {
            {

                Snackbar snackbar = Snackbar.make(rootlayout, "Your are not login ", Snackbar.LENGTH_LONG);
                snackbar.show();
                snackbar.setAction("Login", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent detailsIntent = new Intent(DetailsActivity.this, SigninActivity.class);
                        startActivity(detailsIntent);
                        finish();
                    }
                });
            }
        } */


       floatingActionButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent multiImageIntent = new Intent(DetailsActivity.this,MultipleImageActivity.class);
               multiImageIntent.putExtra("Property_id",property_id);
               multiImageIntent.putExtra("url",image_url);
              // multiImageIntent.putExtra("user_id",user_id);
               startActivity(multiImageIntent);

           }
       });

// call agent button
        call_agent = findViewById(R.id.callagent);
        enquire_now = findViewById(R.id.enquirenow);

      /*  if(userMobile.isEmpty())
        {
            {

                Snackbar snackbar = Snackbar.make(rootlayout, "Your are not login ", Snackbar.LENGTH_LONG);
                snackbar.show();
                snackbar.setAction("Login", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent detailsIntent = new Intent(DetailsActivity.this, SigninActivity.class);
                        startActivity(detailsIntent);
                        finish();
                    }
                });
            }
        } */


        call_agent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userMobile.isEmpty()) {
                    {

                        Snackbar snackbar = Snackbar.make(rootlayout, "Login/Register needed to access contacts ", Snackbar.LENGTH_LONG);
                        View snackBarView = snackbar.getView();
                        snackBarView.setBackgroundColor(getResources().getColor(R.color.green));
                        snackbar.show();
                        snackbar.setAction("Login", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent detailsIntent = new Intent(DetailsActivity.this, SigninActivity.class);
                                startActivity(detailsIntent);
                                finish();
                            }
                        });
                    }
                } else {


                    AlertDialog.Builder alerDialog = new AlertDialog.Builder(DetailsActivity.this, R.style.CallAlertDialogStyle);
                    alerDialog.setIcon(R.drawable.ic_displayicon);
                    alerDialog.setTitle("Call Owner/ Agent");
                    // alerDialog.setMessage("Sure would you like to call ?");
                    alerDialog.setPositiveButton("call", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                            callIntent.setData(Uri.parse("tel:" + property_call));
                            startActivity(callIntent);
                            if (ActivityCompat.checkSelfPermission(DetailsActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                // TODO: Consider calling
                                //    ActivityCompat#requestPermissions
                                // here to request the missing permissions, and then overriding
                                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                //                                          int[] grantResults)
                                // to handle the case where the user grants the permission. See the documentation
                                // for ActivityCompat#requestPermissions for more details.
                                return;
                            }
                            startActivity(callIntent);
                        }
                    });

                    alerDialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });

                    AlertDialog alertDialog = alerDialog.create();
                    alertDialog.show();

                       /* Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:" + property_call));
                        startActivity(callIntent);
                        if (ActivityCompat.checkSelfPermission(DetailsActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        startActivity(callIntent);*/

                }
            }
        });
        enquire_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent enquireIntent = new Intent(DetailsActivity.this, EnquireActivity.class);
                startActivity(enquireIntent);
            }
        });

    }

   /* private void loadMultipleImages() {

        if (!NetworkUtility.isNetworkConnected(DetailsActivity.this)) {
            AppUtilits.displayMessage(DetailsActivity.this, getString(R.string.network_not_connected));

        } else {
            ServiceWrapper service = new ServiceWrapper(null);
            Call<GetbannerModel> call = service.getMultipleImage("1234",property_id);
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

                                remoteBanners.add(new RemoteBanner(image_url
                                ));
                                remoteBanners.add(new RemoteBanner(image_url
                                ));
                            }

                            bannerSlider.setBanners(remoteBanners);
                        } else {
                            remoteBanners.add(new RemoteBanner(image_url
                            ));
                            remoteBanners.add(new RemoteBanner(image_url
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
    }*/

    private void setValueToWidget() {
        prop_details.setText(details);
        prop_mrp.setText("₹ "+mrp);
        prop_location.setText(address);
    }

    private void setUpWidget() {
        prop_details = findViewById(R.id.prop_details);
        prop_mrp = findViewById(R.id.prop_mrp);
        prop_location = findViewById(R.id.prop_location);

    }
}



//
/*
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
        }*/
