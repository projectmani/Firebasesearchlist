package com.example.kutti.firebasesearchlist;
import android.content.Context;
import android.media.Image;
import android.net.sip.SipAudioCall;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by Dushyant Mainwal on 29-Oct-17.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    Context context;
    ArrayList<String> modelnamelist;
    ArrayList<String> pricelist;
    ArrayList<String> shopnamelist;
    ArrayList<String> speclist;

    Listener mListener;
    public Object spec_name;


    public static interface Listener{

        public void onclick(int position);
    }

    public void setListener(Listener listener){
        mListener = listener;
    }

    class SearchViewHolder extends RecyclerView.ViewHolder  {

        TextView model_name, price_name,shop_name,spec_name;

        public SearchViewHolder(View itemView) {
            super(itemView);
            model_name = (TextView) itemView.findViewById(R.id.model_name);
            price_name = (TextView) itemView.findViewById(R.id.price_name);
            shop_name = (TextView) itemView.findViewById(R.id.shop_name);
            spec_name = (TextView) itemView.findViewById(R.id.spec_name);


        }

    }

    public SearchAdapter(Context context, ArrayList<String> modelnamelist, ArrayList<String> pricelist, ArrayList<String> shopnamelist,ArrayList<String> speclist) {
        this.context = context;
        this.modelnamelist = modelnamelist;
        this.pricelist = pricelist;
        this.shopnamelist = shopnamelist;
        this.speclist = speclist;
    }

    @Override
    public SearchAdapter.SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_list_items, parent, false);
        return new SearchAdapter.SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchViewHolder holder, final int position) {
        holder.model_name.setText(modelnamelist.get(position));
        holder.price_name.setText(pricelist.get(position));
        holder.shop_name.setText(shopnamelist.get(position));
        holder.spec_name.setText(speclist.get(position));
        holder.model_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mListener != null) {
                    mListener.onclick(position);
                }
            }

        });
            }

    @Override
    public int getItemCount() {
        return modelnamelist.size();
    }
}