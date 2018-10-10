package com.softcodeinfotech.dreamhousing.myaccount;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.softcodeinfotech.dreamhousing.R;


import java.util.List;

public class AccountDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

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

    

    private class AccountAdapterDetailsHolder extends RecyclerView.ViewHolder{

        ImageView accDetails_image;
        ImageView accDetails_delete_image;
        TextView accDetails_details;
        TextView accDetails_address;
        TextView accDetails_phone;
        TextView accDetails_mrp;
        CardView cardView;


        public AccountAdapterDetailsHolder(@NonNull View itemView) {
            super(itemView);

            accDetails_image= itemView.findViewById(R.id.acc_image);
            accDetails_delete_image = itemView.findViewById(R.id.acc_delete_image);
            accDetails_details=itemView.findViewById(R.id.acc_details);
            accDetails_address=itemView.findViewById(R.id.acc_address);
            accDetails_phone=itemView.findViewById(R.id.acc_phone);
            accDetails_mrp=itemView.findViewById(R.id.acc_mrp);
            cardView=itemView.findViewById(R.id.acc_card_view);


           /* LinearLayout.LayoutParams params = new LinearLayout.LayoutParams( mScrenwidth - (mScrenwidth/100*65), LinearLayout.LayoutParams.MATCH_PARENT);
            params.setMargins(10,10,10,10);
            cardView.setLayoutParams(params);
            cardView.setPadding(5,5,5,5);*/
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list2, parent,false);
        // Log.e(TAG, "  view created ");
        return new AccountAdapterDetailsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final AccountDetailsModel model = mAccountDetailsList.get(position);
        // Log.e(TAG, " assign value ");
        ((AccountAdapterDetailsHolder) holder).accDetails_details.setText(model.getAcc_details());
        ((AccountAdapterDetailsHolder) holder).accDetails_address.setText(model.getAcc_address());
        ((AccountAdapterDetailsHolder) holder).accDetails_phone.setText(model.getAcc_phone());
        ((AccountAdapterDetailsHolder) holder).accDetails_mrp.setText(String.valueOf(model.getAcc_mrp()));

        Glide.with(mContext)
                .load(model.getAcc_image())
                .into(((AccountAdapterDetailsHolder) holder).accDetails_image);



    }

    @Override
    public int getItemCount() {

        return mAccountDetailsList.size();
    }

}
