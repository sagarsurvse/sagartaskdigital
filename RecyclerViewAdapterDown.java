package com.example.sony.sagartaskdigital;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Created by Aws on 11/03/2018.
 */

public class RecyclerViewAdapterDown extends RecyclerView.Adapter<RecyclerViewAdapterDown.MyViewHolder> {

    private Context mContext ;
    private List<DownModel> mData ;






    public RecyclerViewAdapterDown(CartList mContext, List<DownModel> mData) {
        this.mContext = mContext;
        this.mData = mData;




    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.cartlistselected,parent,false) ;
        final MyViewHolder viewHolder = new MyViewHolder(view) ;






        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.name.setText(mData.get(position).getCakename());
        holder.city.setText(mData.get(position).getCakeweight());
        holder.country.setText(mData.get(position).getCakeprice());



    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView country, name, city;
        ImageView iv;
        LinearLayout view_container;
        Button button ;







        public MyViewHolder(View itemView) {
            super(itemView);

            view_container = itemView.findViewById(R.id.container);

            name = itemView.findViewById(R.id.name);
            city = itemView.findViewById(R.id.city);
            country = itemView.findViewById(R.id.country);
            iv = (ImageView) itemView.findViewById(R.id.iv);
            button = itemView.findViewById(R.id.delete);
        }

    }

}
