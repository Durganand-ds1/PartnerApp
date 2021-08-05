package com.tecmanic.servicepartner.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tecmanic.servicepartner.R;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.tecmanic.servicepartner.Constants.Config.UpdatePaass;

public class PasswordResetActivity extends AppCompatActivity {

    EditText eNewPass,eConPass;
    ImageView back_img;
    Button button;
    ProgressDialog progressDialog;
    String mobNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);
        init();
    }

    private void init() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait!");
        progressDialog.setCancelable(false);
        button= findViewById(R.id.button);
        mobNo=getIntent().getStringExtra("MobNo");
        back_img = findViewById(R.id.back_img);
        eNewPass = findViewById(R.id.etPassword);
        eConPass = findViewById(R.id.etConPassword);
        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (eNewPass.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getApplicationContext(), "Password required!", Toast.LENGTH_SHORT).show();
                } else if (eConPass.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getApplicationContext(), "Confirm Password required!", Toast.LENGTH_SHORT).show();
                } else if (!eConPass.getText().toString().trim().matches(eNewPass.getText().toString().trim())) {
                    Toast.makeText(getApplicationContext(), "Password mismatch!", Toast.LENGTH_SHORT).show();
                } else if (!isOnline()) {
                    Toast.makeText(getApplicationContext(), "Please check your Internet Connection!", Toast.LENGTH_SHORT).show();
                } else {
                    updatePAsswordUrl();
                }

            }
        });
    }

    private void updatePAsswordUrl() {
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UpdatePaass, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("pass chnge",response);
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    String msg = jsonObject.getString("message");
                    if (status.equalsIgnoreCase("1")){
                        // JSONObject resultObj = jsonObject.getJSONObject("data");
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                        Intent in=new Intent(getApplicationContext(),LoginSignupActivity.class);
                        startActivity(in);
                        finish();

                    }else {
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<>();

                param.put("partner_phone",mobNo);
                param.put("password",eConPass.getText().toString());
                return param;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(PasswordResetActivity.this);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);

    }

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }


}