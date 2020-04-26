package com.example.sony.sagartaskdigital;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CartList extends AppCompatActivity {
    DatabaseReference databaseReference,databaseReference2,databaseReference3;

    ProgressDialog progressDialog;

    List<StudentDetails> list = new ArrayList<>();
    List<MoveModel> list2 = new ArrayList<>();
    List<DownModel> list3 = new ArrayList<>();

    RecyclerView recyclerView,recyclerViewselected,recyclerViewdown;

    RecyclerView.Adapter adapter;
    RecyclerView.Adapter adapter2;
    RecyclerView.Adapter adapter3;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_list);



        recyclerView = (RecyclerView) findViewById(R.id.movelistrecycview);
        recyclerViewselected = (RecyclerView) findViewById(R.id.selected);
        recyclerViewdown = (RecyclerView) findViewById(R.id.selecteddown);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(CartList.this));

        recyclerViewselected.setHasFixedSize(true);
        recyclerViewselected.setLayoutManager(new LinearLayoutManager(CartList.this));

        recyclerViewdown.setHasFixedSize(true);
        recyclerViewdown.setLayoutManager(new LinearLayoutManager(CartList.this));

        progressDialog = new ProgressDialog(CartList.this);

        progressDialog.setMessage("Loading Data...");

        progressDialog.show();

        databaseReference = FirebaseDatabase.getInstance().getReference(MainActivity.Database_Path);
        databaseReference2 = FirebaseDatabase.getInstance().getReference(MainActivity.Database_Slected);
                databaseReference3 = FirebaseDatabase.getInstance().getReference(MainActivity.Database_Download);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    StudentDetails studentDetails = dataSnapshot.getValue(StudentDetails.class);
                    list.add(studentDetails);



                   


                }

                adapter = new RecyclerViewAdaptermOVE(CartList.this, list);
                recyclerView.setAdapter(adapter);
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                progressDialog.dismiss();

            }
        });


        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    MoveModel moveModel = dataSnapshot.getValue(MoveModel.class);
                    list2.add(moveModel);
                }


                adapter2 = new RecyclerViewAdapterSelected(CartList.this, list2);
                recyclerViewselected.setAdapter(adapter2);
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                progressDialog.dismiss();

            }
        });

        databaseReference3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    DownModel downModel = dataSnapshot.getValue(DownModel.class);
                    list3.add(downModel);
                }


                adapter3 = new RecyclerViewAdapterDown(CartList.this, list3);
                recyclerViewdown.setAdapter(adapter3);
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                progressDialog.dismiss();

            }
        });


    }
}
