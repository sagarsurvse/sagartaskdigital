package com.example.sony.sagartaskdigital;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;

import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

import info.hoang8f.widget.FButton;

/**
 * Created by Aws on 11/03/2018.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context mContext ;
    private List<RogerModel> mData ;
    public static final String Firebase_Server_URL = "https://osmanabadonline-7c3db.firebaseio.com/";

    Firebase firebase;

    DatabaseReference databaseReference;

    // Root Database Name for Firebase Database.
    public static final String Database_Path = "Student_Details_Database";


    public RecyclerViewAdapter(MainActivity mContext, List<RogerModel> mData) {
        this.mContext = mContext;
        this.mData = mData;




    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        final View view ;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.rv_item,parent,false) ;
        final MyViewHolder viewHolder = new MyViewHolder(view) ;

        viewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                /*Firebase.setAndroidContext();
*/
                firebase = new Firebase(Firebase_Server_URL);

                databaseReference = FirebaseDatabase.getInstance().getReference(Database_Path);

                String cakename = mData.get(viewHolder.getAdapterPosition()).getCakeName();
                String cakeweight = mData.get(viewHolder.getAdapterPosition()).getCakeWeight();
                String cakeprice = mData.get(viewHolder.getAdapterPosition()).getCakePrice();

                StudentDetails studentDetails = new StudentDetails();


                // Adding name into class function object.
                studentDetails.setCakename(cakename);

                // Adding phone number into class function object.
                studentDetails.setCakeweight(cakeweight);
                studentDetails.setCakeprice(cakeprice);

                // Getting the ID from firebase database.
                String StudentRecordIDFromServer = databaseReference.push().getKey();

                // Adding the both name and number values using student details class object using ID.
                databaseReference.child(StudentRecordIDFromServer).setValue(studentDetails);



                Intent intent=new Intent(view.getContext(),CartList.class);
                view.getContext().startActivity(intent);



            }
        });





        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.name.setText(mData.get(position).getCakeName());
        holder.city.setText(mData.get(position).getCakeWeight());
        holder.country.setText(mData.get(position).getCakePrice());
        Picasso.get().load(mData.get(position).getCakeImage()).into(holder.iv);



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
            button = itemView.findViewById(R.id.Addtocart);
        }

    }

}
