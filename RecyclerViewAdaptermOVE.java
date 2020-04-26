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
import android.content.Intent;
import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Aws on 11/03/2018.
 */

public class RecyclerViewAdaptermOVE extends RecyclerView.Adapter<RecyclerViewAdaptermOVE.MyViewHolder> {

    private Context mContext ;
    private List<StudentDetails> mData ;
    public static final String Firebase_Server_URL = "https://osmanabadonline-7c3db.firebaseio.com/";


    Firebase firebase;

    DatabaseReference databaseReference;

    // Root Database Name for Firebase Database.
    public static final String Database_Path = "MoveSelected";


    public RecyclerViewAdaptermOVE(CartList mContext, List<StudentDetails> mData) {
        this.mContext = mContext;
        this.mData = mData;




    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.cartlist,parent,false) ;
        final MyViewHolder viewHolder = new MyViewHolder(view) ;

        viewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                /*Firebase.setAndroidContext();
*/
                firebase = new Firebase(Firebase_Server_URL);

                databaseReference = FirebaseDatabase.getInstance().getReference(Database_Path);

                String cakename = mData.get(viewHolder.getAdapterPosition()).getCakename();
                String cakeweight = mData.get(viewHolder.getAdapterPosition()).getCakeweight();
                String cakeprice = mData.get(viewHolder.getAdapterPosition()).getCakeprice();

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





            }
        });




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
            button = itemView.findViewById(R.id.move);
        }

    }

}
