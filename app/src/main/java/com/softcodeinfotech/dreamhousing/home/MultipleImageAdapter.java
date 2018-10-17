package com.softcodeinfotech.dreamhousing.home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.softcodeinfotech.dreamhousing.R;

import java.util.List;

public class MultipleImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<MultipleImageModel> mPMultipleImageModelList;
    private String TAG ="MultipleImageAdapter";
    private int mScreenheight;
    CardView cardView;



    public MultipleImageAdapter (Context context, List<MultipleImageModel> list, int screenheight ){
        mContext = context;
        mPMultipleImageModelList = list;
        mScreenheight=screenheight;
// response.body.getString("information")
    }


    private class MultipleImageHolder extends RecyclerView.ViewHolder
    {

        ImageView multipleImageView;



        public MultipleImageHolder(@NonNull View itemView) {
            super(itemView);

            multipleImageView = itemView.findViewById(R.id.multi_image);
            cardView = itemView.findViewById(R.id.mCard_view);
     /*       if(mPMultipleImageModelList.size()==1) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                cardView.setLayoutParams(params);

            }
            else
            {

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, mScreenheight - (mScreenheight / 100 * 25));
                cardView.setLayoutParams(params);
            }*/
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.multiple_image, parent, false);
        // Log.e(TAG, "  view created ");

        return new MultipleImageAdapter.MultipleImageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MultipleImageModel model = mPMultipleImageModelList.get(position);
        if(mPMultipleImageModelList.size()==1) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            cardView.setLayoutParams(params);

        }
        else
        {

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, mScreenheight - (mScreenheight / 100 * 25));
            cardView.setLayoutParams(params);
        }

        Glide.with(mContext).load(model.getImageUrl())
                .into(((MultipleImageHolder)holder).multipleImageView);

    }

    @Override
    public int getItemCount() {
        return mPMultipleImageModelList.size();
    }

}
