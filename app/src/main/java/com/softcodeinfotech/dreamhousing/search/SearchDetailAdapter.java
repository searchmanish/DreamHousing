package com.softcodeinfotech.dreamhousing.search;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.softcodeinfotech.dreamhousing.R;
import com.softcodeinfotech.dreamhousing.myaccount.AccountDetailsAdapter;
import com.softcodeinfotech.dreamhousing.myaccount.AccountDetailsModel;

import java.util.List;

public class SearchDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    String imageUrl;
    String path;
    Integer prop_id;
    int  clickedPosition;


    private Context mContext;
    private List<SearchDetailModel> mSearchDetailsList;
    private String TAG ="SearchDetailAdapter";
    private int mScrenwidth;

    public SearchDetailAdapter(Context context, List<SearchDetailModel> list, int screenwidth)
    {
        mContext = context;
        mSearchDetailsList = list;
        mScrenwidth = screenwidth;
    }



    private class SearchDetailAdapterHolder extends RecyclerView.ViewHolder{

        ImageView accDetails_image;
        ImageView accDetails_delete_image;
        TextView accDetails_details;
        TextView accDetails_address;
        TextView accDetails_phone;
        TextView accDetails_mrp;
        CardView cardView;


        public SearchDetailAdapterHolder(@NonNull View itemView) {
            super(itemView);

            accDetails_image= itemView.findViewById(R.id.acc_image);
            accDetails_delete_image = itemView.findViewById(R.id.acc_delete_image);
            accDetails_details=itemView.findViewById(R.id.acc_details);
            accDetails_address=itemView.findViewById(R.id.acc_address);
            accDetails_phone=itemView.findViewById(R.id.acc_phone);
            accDetails_mrp=itemView.findViewById(R.id.acc_mrp);
            cardView=itemView.findViewById(R.id.acc_card_view);
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item4, parent,false);
        // Log.e(TAG, "  view created ");
        return new SearchDetailAdapter.SearchDetailAdapterHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final SearchDetailModel model = mSearchDetailsList.get(position);

        ((SearchDetailAdapter.SearchDetailAdapterHolder) holder).accDetails_details.setText(model.getAcc_details());
        ((SearchDetailAdapter.SearchDetailAdapterHolder) holder).accDetails_address.setText(model.getAcc_address());
       // ((SearchDetailAdapter.SearchDetailAdapterHolder) holder).accDetails_phone.setText(model.getAcc_phone());
        ((SearchDetailAdapter.SearchDetailAdapterHolder) holder).accDetails_mrp.setText("₹ "+String.valueOf(model.getAcc_mrp()));

        Glide.with(mContext)
                .load(model.getAcc_image()).apply(new RequestOptions().placeholder(R.drawable.placeholder2).error(R.drawable.placeholder2))
                .into(((SearchDetailAdapter.SearchDetailAdapterHolder) holder).accDetails_image);



        ((SearchDetailAdapterHolder) holder).cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mContext,  com.softcodeinfotech.dreamhousing.home.DetailsActivity.class);
                intent.putExtra("property_phone", model.getAcc_phone());
                intent.putExtra("property_img_url", model.getAcc_image());
                intent.putExtra("property_details",model.getAcc_details());
                intent.putExtra("property_mrp", String.valueOf(model.getAcc_mrp()));
                intent.putExtra("property_address",model.getAcc_address());
                intent.putExtra("property_id",model.getAcc_id());
               // prop_id = model.getAcc_id();

                mContext.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mSearchDetailsList.size();    }
}
