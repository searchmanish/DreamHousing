package com.softcodeinfotech.dreamhousing.home;

import android.content.Context;
import android.content.Intent;
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

public class PropertyAdapterFresh extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<PropertyDetailsModelFresh> mPDetailsModelFreshList;
    private String TAG ="PropertyAdapterFresh";
    private int mScrenwith;


    public PropertyAdapterFresh (Context context, List<PropertyDetailsModelFresh> list, int screenwidth ){
        mContext = context;
        mPDetailsModelFreshList = list;
        mScrenwith =screenwidth;
// response.body.getString("information")
    }

    private class PropertyAdapterFreshHolder extends RecyclerView.ViewHolder {
     /*this.property_details = property_details;
        this.property_address = property_address;
        this.property_phone = property_phone;
        this.property_image = property_image;
        this.property_mrp = property_mrp;*/

        ImageView property_image;
        TextView property_details;
        TextView property_address;
        TextView property_phone;
        TextView property_mrp;
        CardView cardView;

        PropertyAdapterFreshHolder(View itemView) {
            super(itemView);
            /*prod_img = (ImageView) itemView.findViewById(R.id.prod_img);
            prod_name = (TextView) itemView.findViewById(R.id.prod_name);
            cardView = (CardView) itemView.findViewById(R.id.card_view);*/
            property_image= itemView.findViewById(R.id.list_image);
            property_details=itemView.findViewById(R.id.list_details);
            property_address=itemView.findViewById(R.id.list_address);
            property_phone=itemView.findViewById(R.id.list_phone);
            property_mrp=itemView.findViewById(R.id.list_mrp);
            cardView=itemView.findViewById(R.id.cardView);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams( mScrenwith - (mScrenwith/100*65), LinearLayout.LayoutParams.MATCH_PARENT);
            params.setMargins(12, 10, 15, 10);
            cardView.setLayoutParams(params);
            cardView.setPadding(5,5,5,5);

        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_list_item, parent,false);
        // Log.e(TAG, "  view created ");
        return new PropertyAdapterFreshHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final PropertyDetailsModelFresh model = mPDetailsModelFreshList.get(position);
        // Log.e(TAG, " assign value ");
        ((PropertyAdapterFreshHolder) holder).property_details.setText(model.getProperty_details());
        ((PropertyAdapterFreshHolder) holder).property_address.setText(model.getProperty_address());
       //((PropertyAdapterFreshHolder) holder).property_phone.setText(model.getProperty_phone());
        ((PropertyAdapterFreshHolder) holder).property_mrp.setText("₹ "+ String.valueOf(model.getProperty_mrp()));

        Glide.with(mContext)
                .load(model.getProperty_image())
                .into(((PropertyAdapterFreshHolder) holder).property_image);





        ((PropertyAdapterFreshHolder) holder).cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mContext,  com.softcodeinfotech.dreamhousing.home.DetailsActivity.class);
                intent.putExtra("property_phone", model.getProperty_phone());
                intent.putExtra("property_img_url", model.getProperty_image());
                intent.putExtra("property_details",model.getProperty_details());
                intent.putExtra("property_mrp", String.valueOf(model.getProperty_mrp()));
                intent.putExtra("property_address",model.getProperty_address());
                mContext.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return mPDetailsModelFreshList.size();
    }
}
