package com.softcodeinfotech.dreamhousing.home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.softcodeinfotech.dreamhousing.R;

import java.util.List;

public class PropertySellAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<PropertySell_Model> mPropertySellModelList;
    private String TAG ="NewProd_adapter";
    private int mScrenwith;


    public PropertySellAdapter (Context context, List<PropertySell_Model> list, int screenwidth ){
        mContext = context;
        mPropertySellModelList = list;
        mScrenwith =screenwidth;
// response.body.getString("information")
    }

    private class PropertySellHolder extends RecyclerView.ViewHolder {
        //price
//	specification
//	address
//	phone
//	image_url
        ImageView propertySell_image_url;
        TextView propertySell_specification;
        TextView propertySell_address;
        TextView propertySell_phone;
        TextView propertySell_price;
        CardView cardView;

        public PropertySellHolder(View itemView) {
            super(itemView);
            /*prod_img = (ImageView) itemView.findViewById(R.id.prod_img);
            prod_name = (TextView) itemView.findViewById(R.id.prod_name);
            cardView = (CardView) itemView.findViewById(R.id.card_view);*/
             propertySell_image_url= itemView.findViewById(R.id.list_image);
             propertySell_specification=itemView.findViewById(R.id.list_specification);
             propertySell_address=itemView.findViewById(R.id.list_address);
             propertySell_phone=itemView.findViewById(R.id.list_call);
             propertySell_price=itemView.findViewById(R.id.list_price);
            cardView=itemView.findViewById(R.id.cardView);

           /* LinearLayout.LayoutParams params = new LinearLayout.LayoutParams( mScrenwith - (mScrenwith/100*65), LinearLayout.LayoutParams.MATCH_PARENT);
            params.setMargins(10,10,10,10);
            cardView.setLayoutParams(params);
            cardView.setPadding(5,5,5,5);*/

        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent,false);
        // Log.e(TAG, "  view created ");
        return new PropertySellHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final PropertySell_Model model = mPropertySellModelList.get(position);
        // Log.e(TAG, " assign value ");
        ((PropertySellHolder) holder).propertySell_specification.setText(model.getSpecification());
        ((PropertySellHolder) holder).propertySell_address.setText(model.getAddress());
        ((PropertySellHolder) holder).propertySell_price.setText(model.getPrice());
        ((PropertySellHolder) holder).propertySell_phone.setText(model.getPhone());

        Glide.with(mContext)
                .load(model.getImage_url())
                .into(((PropertySellHolder) holder).propertySell_image_url);

    }

    @Override
    public int getItemCount() {
        return mPropertySellModelList.size();
    }
}
