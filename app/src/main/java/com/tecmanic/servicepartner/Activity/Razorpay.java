package com.tecmanic.servicepartner.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.Volley;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.tecmanic.servicepartner.Constants.CustomVolleyJsonRequest;
import com.tecmanic.servicepartner.Constants.Session_management;
import com.tecmanic.servicepartner.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


import static com.android.volley.VolleyLog.TAG;
import static com.tecmanic.servicepartner.Constants.Config.ADD_ORDER_URL;
import static com.tecmanic.servicepartner.Constants.Config.KEY_EMAIL;
import static com.tecmanic.servicepartner.Constants.Config.KEY_ID;
import static com.tecmanic.servicepartner.Constants.Config.KEY_MOBILE;
import static com.tecmanic.servicepartner.Constants.Config.KEY_NAME;

public class Razorpay extends AppCompatActivity implements PaymentResultListener {

    Session_management session_management;
    String name,email,phone;
    String amount;
    String getuser_id,coins,price,plan_id;

    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_razorpay);
        progressDialog = new ProgressDialog(Razorpay.this);
        progressDialog.setMessage("Payment is in processing...");
        progressDialog.setCancelable(false);
        session_management=new Session_management(Razorpay.this);
        getuser_id=session_management.getUserDetails().get(KEY_ID);
        name=session_management.getUserDetails().get(KEY_NAME);
        email=session_management.getUserDetails().get(KEY_EMAIL);
        phone=session_management.getUserDetails().get(KEY_MOBILE);
        coins=getIntent().getStringExtra("Coins");
        price=getIntent().getStringExtra("price");
        plan_id=getIntent().getStringExtra("planId");
        startPayment(name,price,email,phone);

    }
    public void startPayment(String name, String amount, String email, String phone) {
        /*
          You need to pass current activity in order to let Razorpay create CheckoutActivity
         */

        final Activity activity = this;
        final Checkout co = new Checkout();
        try {

            JSONObject options = new JSONObject();

            options.put("name", name);
            options.put("description", "Shopping Charges");
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("currency", "INR");
            options.put("amount", Double.parseDouble(amount) * 100);

            JSONObject preFill = new JSONObject();
            preFill.put("email", email);
            preFill.put("contact", phone);

            options.put("prefill", preFill);

            co.open(activity, options);

        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
    }

    /**
     * The name of the function has to be
     * onPaymentSuccess
     * Wrap your code in try catch, as shown, to ensure that this method runs correctly
     */
    @SuppressLint("JavascriptInterface")
    @SuppressWarnings("unused")
 /*   @Override
    public void onPaymentSuccess(String razorpayPaymentID) {

      *//*  Intent intent=new Intent(Razorpay.this,OrderSuccessfull.class);
        intent.putExtra("time",gettime);
        intent.putExtra("date",getdate);
        intent.putExtra("amount",amount);
        intent.putExtra("addressid",addressid);
        intent.putExtra("mode","card");
        startActivity(intent);*//*
        if (isOnline()) {
            makeAddOrderRequest(getuser_id);
        }else{
            Toast.makeText(getApplicationContext(),"Please check your Internet Connection!", Toast.LENGTH_SHORT).show();
        }
        try {
            Toast.makeText(this, "Payment Successful: " + razorpayPaymentID, Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Log.e("tag", "Exception in onPaymentSuccess", e);

        }


    }

    @Override
    public void onPaymentError(int i, String s) {
//        Intent intent = new Intent(RazorPay.this, OrderFail.class);
//        startActivity(intent);
//        finish();

    }*/

    @Override
    public void onPaymentSuccess(String s) {
        makeAddOrderRequest(plan_id,"success",coins,price,getuser_id);
    }

    @Override
    public void onPaymentError(int i, String s) {
        progressDialog.dismiss();
      //  makeAddOrderRequest(plan_id,"failed",coins,price,getuser_id);
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
        finish();

    }
/*
    private void attemptOrder() {


        ArrayList<HashMap<String, String>> items = databaseHandler.getCartAll();
        if (items.size() > 0) {
            JSONArray passArray = new JSONArray();
            for (int i = 0; i < items.size(); i++) {
                HashMap<String, String> map = items.get(i);
                JSONObject jObjP = new JSONObject();
                try {
                    jObjP.put("service_id", map.get("service_id"));
                    jObjP.put("service_qty", map.get("qty"));
                    passArray.put(jObjP);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }



        }
    }
*/

    private void makeAddOrderRequest(String plan_id,String paymentStatus,String coins,String price,String getuser_id) {
        String tag_json_obj = "json_add_order_req";
        Map<String, String> params = new HashMap<String, String>();
        params.put("plan_id", plan_id);
        params.put("status", paymentStatus);
        params.put("coins", coins);
        params.put("price", price);
        params.put("partner_id", getuser_id);

        CustomVolleyJsonRequest jsonObjReq = new CustomVolleyJsonRequest(Request.Method.POST,
                ADD_ORDER_URL, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                try {

                    String status=response.getString("status");
                    String message=response.getString("message");
                    if(status.equalsIgnoreCase("1")){
                       // databaseHandler.clearCart();
                        Intent intent=new Intent(Razorpay.this,OrderSuccessfull.class); //HomePageActivity
                        startActivity(intent);
                        finish();
                    }
                    else {
                      /*  Intent intent=new Intent(getApplicationContext(),RechargePlan.class);
                        startActivity(intent);*/
                        finish();
                        Toast.makeText(Razorpay.this, message, Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                  //  Toast.makeText(Razorpay.this, getResources().getString(R.string.connection_time_out), Toast.LENGTH_SHORT).show();
                }
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