package com.tecmanic.servicepartner.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import com.tecmanic.servicepartner.Constants.CustomVolleyJsonRequest;
import com.tecmanic.servicepartner.Constants.Session_management;
import com.tecmanic.servicepartner.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.tecmanic.servicepartner.Constants.Config.IMAGE_URL;
import static com.tecmanic.servicepartner.Constants.Config.PROFILEUPDATE;
import static com.tecmanic.servicepartner.Constants.Config.Profile;

public class ProfileEdit extends AppCompatActivity {


    EditText eTname,profession,range;
    TextView Button;
    ImageView back;
    RelativeLayout ic_edit;
    CircleImageView image;
    Session_management session_management;
    ProgressDialog progressDialog;
    private static final int  RESULT_LOAD_IMAGE = 1;
    Bitmap bitmap;
    Uri filePath;
    String encodedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);
        niit();
    }

    private void niit() {
        session_management=new Session_management(this);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading..");
        progressDialog.setCancelable(true);
        back=findViewById(R.id.back);
        ic_edit=findViewById(R.id.rlImage);
        Button=findViewById(R.id.btnSvae);
        image=findViewById(R.id.pImage);
        eTname=findViewById(R.id.eTname);
        profession=findViewById(R.id.etProfression);
        range=findViewById(R.id.etrange);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
            }
        });
        Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(eTname.getText().toString().trim().equalsIgnoreCase("")){
                    Toast.makeText(getApplicationContext(),"Enter your Name",Toast.LENGTH_SHORT).show();

                }else if(profession.getText().toString().trim().equalsIgnoreCase("")){
                    Toast.makeText(getApplicationContext(),"Enter your Profession",Toast.LENGTH_SHORT).show();

                }else if(range.getText().toString().trim().equalsIgnoreCase("")){
                    Toast.makeText(getApplicationContext(),"Enter delivery range",Toast.LENGTH_SHORT).show();

                } else {
                   /* if (encodedImage.equalsIgnoreCase("") && encodedImage==null) {
                        saveEditprofileUrl();
                    } else {
                       */ if (isOnline()) {

                            save_EditprofileUrl(encodedImage);
                        } else {
                            Toast.makeText(getApplicationContext(), "Please check your Internet Connection!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        });

        profileDataUrl();

        ic_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser1();
            }
        });
    }

    private void profileDataUrl() {
        progressDialog.show();
        String tag_json_obj = "json_category_req";
        Map<String, String> params = new HashMap<String, String>();
        params.put("partner_id", session_management.userId());
        CustomVolleyJsonRequest jsonObjReq = new CustomVolleyJsonRequest(Request.Method.POST,
                Profile, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.d("TAG", response.toString());
                progressDialog.dismiss();
                try {

                    String status = response.getString("status");
                    String message = response.getString("message");

                    if (status.contains("1")) {

                        JSONObject obj = response.getJSONObject("data");
                        String partner_id = obj.getString("partner_id");
                        String user_fullname = obj.getString("partner_name");
                        String user_email = obj.getString("partner_email");
                        String user_phone = obj.getString("partner_phone");
                        String partner_profesion = obj.getString("partner_profesion");
                        String upi = obj.getString("upi");
                        String coins = obj.getString("coins");
                        String partner_image = obj.getString("partner_image");
                        String deliveryrange = obj.getString("range");

                        profession.setText(partner_profesion);
                        eTname.setText(user_fullname);
                        range.setText(deliveryrange);


                        Picasso.with(getApplicationContext()).load(IMAGE_URL+partner_image).error(R.drawable.logo).into(image);
                    }}
                catch (Exception e) {
                    e.printStackTrace();
                }
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("TAG", "Error: " + error.getMessage());
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    //Toast.makeText(getContext(), getResources().getString(R.string.connection_time_out), Toast.LENGTH_SHORT).show();
                }
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.getCache().clear();
        requestQueue.add(jsonObjReq);

    }

    private void showFileChooser1() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, RESULT_LOAD_IMAGE);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE) {
            if (resultCode == RESULT_OK) {
                filePath = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                    image.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] imageBytes = baos.toByteArray();
                encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);

            } else if (resultCode == RESULT_CANCELED) {

                Toast.makeText(this, " Picture was not taken ", Toast.LENGTH_SHORT).show();
            } else {

                Toast.makeText(this, " Picture was not taken ", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void saveEditprofileUrl() {
        progressDialog.show();
        // Tag used to cancel the request
        String tag_json_obj = "json_add_address_req";

        Map<String, String> params = new HashMap<String, String>();
        params.put("partner_id",session_management.userId());
        params.put("delivery_range",range.getText().toString().trim());
        params.put("partner_name",eTname.getText().toString().trim());
        params.put("partner_profesion",profession.getText().toString().trim());
       // params.put("user_profile",encodedImage);

        CustomVolleyJsonRequest jsonObjReq = new CustomVolleyJsonRequest(Request.Method.POST,
                PROFILEUPDATE, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("TAG", response.toString());

                progressDialog.dismiss();
                try {
                    String status = response.getString("status");
                    String msg = response.getString("message");
                    if (status.contains("1")) {
                        Button.setEnabled(false);
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), msg+"", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), msg+"", Toast.LENGTH_SHORT).show();
                        Button.setEnabled(true);
                        progressDialog.dismiss();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("TAG", "Error: " + error.getMessage());
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    // Toast.makeText(getApplicationContext(), "Please select image to continue", Toast.LENGTH_SHORT).show();
                }
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.getCache().clear();
        requestQueue.add(jsonObjReq);

    }

    private void save_EditprofileUrl(String encodedImage) {
        progressDialog.show();
        // Tag used to cancel the request
        String tag_json_obj = "json_add_address_req";

        Map<String, String> params = new HashMap<String, String>();
        params.put("partner_id",session_management.userId());
        params.put("delivery_range",range.getText().toString().trim());
        params.put("partner_name",eTname.getText().toString().trim());
        params.put("partner_profesion",profession.getText().toString().trim());
        params.put("user_profile",encodedImage);

        CustomVolleyJsonRequest jsonObjReq = new CustomVolleyJsonRequest(Request.Method.POST,
                PROFILEUPDATE, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("TAG", response.toString());

                progressDialog.dismiss();
                try {
                    String status = response.getString("status");
                    String msg = response.getString("message");
                    if (status.contains("1")) {
                        Button.setEnabled(false);
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), msg+"", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), msg+"", Toast.LENGTH_SHORT).show();
                        Button.setEnabled(true);
                        progressDialog.dismiss();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("TAG", "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), "Please select image to continue", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.getCache().clear();
        requestQueue.add(jsonObjReq);

    }


    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

}