package com.softcodeinfotech.dreamhousing.home;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.softcodeinfotech.dreamhousing.R;
import com.softcodeinfotech.dreamhousing.beanResponse.GetbannerModel;
import com.softcodeinfotech.dreamhousing.utility.AppUtilits;
import com.softcodeinfotech.dreamhousing.utility.Constant;
import com.softcodeinfotech.dreamhousing.utility.NetworkUtility;
import com.softcodeinfotech.dreamhousing.utility.SharePreferenceUtils;
import com.softcodeinfotech.dreamhousing.webServices.ServiceWrapper;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.WINDOW_SERVICE;

public class SlideFragment extends BottomSheetDialogFragment {
    String TAG="MultipleImageActivity";
    String property_id,user_id,url;
    Toolbar toolbar;

    Constant.TransitionType type;

    private RecyclerView recycler_multipleImage;
    private ArrayList<MultipleImageModel> mPMultipleImageModelList = new ArrayList<MultipleImageModel>();
    private MultipleImageAdapter multipleImageAdapter;


    public void setData(String property_id , String url)
    {
        this.property_id = property_id;
        this.url = url;
    }

 ImageView backButton;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_multiple_image , container , false);

        backButton = view.findViewById(R.id.backButton);
        getDialog().setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                BottomSheetDialog d = (BottomSheetDialog) dialog;
                FrameLayout bottomSheet = (FrameLayout) d.findViewById(R.id.design_bottom_sheet);
                CoordinatorLayout coordinatorLayout = (CoordinatorLayout) bottomSheet.getParent();
                BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
                bottomSheetBehavior.setPeekHeight(bottomSheet.getHeight());
                coordinatorLayout.getParent().requestLayout();
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("abc","clcked");
                dismiss();

            }
        });
        recycler_multipleImage = (RecyclerView) view.findViewById(R.id.rec_multiple_image);
        LinearLayoutManager mLayoutManger = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recycler_multipleImage.setLayoutManager(mLayoutManger);
        // recycler_multipleImage.setItemAnimator(new DefaultItemAnimator());

        multipleImageAdapter = new MultipleImageAdapter(getContext(), mPMultipleImageModelList,GetScreenHeight());
        recycler_multipleImage.setAdapter(multipleImageAdapter);



        user_id =  SharePreferenceUtils.getInstance().getString(Constant.USER_id);

        // Intent intent = new Intent();

        // mPMultipleImageModelList.add()
        mPMultipleImageModelList.add(new MultipleImageModel(url));

        //get images method
        getMultipleImages();

        return view;
    }

    private void getMultipleImages() {
        if (!NetworkUtility.isNetworkConnected(getContext())) {
            AppUtilits.displayMessage(getContext(), getString(R.string.network_not_connected));

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
        WindowManager windowManager = (WindowManager) getContext().getApplicationContext().getSystemService(WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;

        return height;

    }

}
