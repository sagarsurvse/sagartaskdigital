package com.example.sony.sagartaskdigital;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.firebase.client.Firebase;

public class MainActivity extends AppCompatActivity {

    private String jsonURL = "http://kekizadmin.com/kekiz_api/actions.php?action=get_cakes&category=27";
    private final int jsoncode = 1;
    private RecyclerView recyclerView;
    ArrayList<RogerModel> rogerModelArrayList;
    private RecyclerViewAdapter rogerAdapter;
    private static ProgressDialog mProgressDialog;
    public static final String BASE_IMAGE_URL = "http://kekizadmin.com/uploads/catrgories/";
    public static final String Firebase_Server_URL = "https://osmanabadonline-7c3db.firebaseio.com/";

    Firebase firebase;

    DatabaseReference databaseReference;

    // Root Database Name for Firebase Database.
    public static final String Database_Path = "Student_Details_Database";
    public static final String Database_Slected = "MoveSelected";
    public static final String Database_Download = "DownSelected";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        recyclerView = findViewById(R.id.recycler);
        Firebase.setAndroidContext(MainActivity.this);

        firebase = new Firebase(Firebase_Server_URL);

        databaseReference = FirebaseDatabase.getInstance().getReference(Database_Path);

        fetchJSON();

    }
    @SuppressLint("StaticFieldLeak")
    private void fetchJSON(){

        showSimpleProgressDialog(this, "Loading...","Fetching Json",false);

        new AsyncTask<Void, Void, String>(){
            protected String doInBackground(Void[] params) {
                String response="";
                HashMap<String, String> map=new HashMap<>();
                try {
                    HttpRequest req = new HttpRequest(jsonURL);
                    response = req.prepare(HttpRequest.Method.POST).withData(map).sendAndReadString();
                } catch (Exception e) {
                    response=e.getMessage();
                }
                return response;
            }
            protected void onPostExecute(String result) {
                //do something with response
                Log.d("newwwss",result);
                onTaskCompleted(result,jsoncode);
            }
        }.execute();
    }

    public void onTaskCompleted(String response, int serviceCode) {
        Log.d("responsejson", response.toString());
        switch (serviceCode) {
            case jsoncode:

                if (isSuccess(response)) {
                    removeSimpleProgressDialog();  //will remove progress dialog
                    rogerModelArrayList = getInfo(response);
                    rogerAdapter = new RecyclerViewAdapter(this,rogerModelArrayList);
                    recyclerView.setAdapter(rogerAdapter);
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
                    recyclerView.setLayoutManager(gridLayoutManager);

                }else {
                    Toast.makeText(MainActivity.this, getErrorCode(response), Toast.LENGTH_SHORT).show();
                }
        }
    }

    public ArrayList<RogerModel> getInfo(String response) {
        ArrayList<RogerModel> tennisModelArrayList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString("status").equals("success")) {

                JSONArray dataArray = jsonObject.getJSONArray("data");

                for (int i = 0; i < dataArray.length(); i++) {

                    RogerModel playersModel = new RogerModel();
                    JSONObject dataobj = dataArray.getJSONObject(i);
                    playersModel.setCakeName(dataobj.getString("cake_name"));




                    JSONArray arr2 = dataobj.getJSONArray("w_l_p");

                    if(arr2.length()>0)
                    {
                        for(int l=0;l< arr2.length(); l++)

                        {
                            JSONObject J_OBJ2= arr2.getJSONObject(l);
                            playersModel.setCakeWeight(J_OBJ2.getString("weight"));
                            playersModel.setCakePrice(J_OBJ2.getString("price"));






                           /* JSONObject images = J_OBJ2.getJSONObject("pictures");
                            playersModel.setCakeImage(BASE_IMAGE_URL+images.getString("file_name"));
*/


                            tennisModelArrayList.add(playersModel);



                        }

                    }

                    else
                    {
                        // Array is Empty
                    }

                }


            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tennisModelArrayList;
    }


    public boolean isSuccess(String response) {

        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.optString("status").equals("success")) {
                return true;
            } else {

                return false;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getErrorCode(String response) {

        try {
            JSONObject jsonObject = new JSONObject(response);
            return jsonObject.getString("error_msg");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "No data";
    }

    public static void removeSimpleProgressDialog() {
        try {
            if (mProgressDialog != null) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                    mProgressDialog = null;
                }
            }
        } catch (IllegalArgumentException ie) {
            ie.printStackTrace();

        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void showSimpleProgressDialog(Context context, String title,
                                                String msg, boolean isCancelable) {
        try {
            if (mProgressDialog == null) {
                mProgressDialog = ProgressDialog.show(context, title, msg);
                mProgressDialog.setCancelable(isCancelable);
            }

            if (!mProgressDialog.isShowing()) {
                mProgressDialog.show();
            }

        } catch (IllegalArgumentException ie) {
            ie.printStackTrace();
        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
