package com.tecmanic.servicepartner.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.tecmanic.servicepartner.Adapter.JobAdapter;
import com.tecmanic.servicepartner.Constants.Config;
import com.tecmanic.servicepartner.Constants.Session_management;
import com.tecmanic.servicepartner.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.tecmanic.servicepartner.Activity.MainActivity.drawer;


public class JobHistoryFragment extends Fragment {

    TabLayout tabLayout;
    ViewPager viewPager;
    JobAdapter pageAdapter;
    TabItem tab1, tab2,tab3;
    ImageView slider;
    TextView txtHead,notification,credits,crdts;
    Session_management sessionManagement;
    String coinsss,user_id;
    public JobHistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_booking, container, false);
        sessionManagement=new Session_management(getContext());
        user_id=sessionManagement.userId();
        slider=view.findViewById(R.id.slidr);
        txtHead=view.findViewById(R.id.txtHead);
        slider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(Gravity.LEFT);
            }
        });
        txtHead.setText("Job Details");

        notification=view.findViewById(R.id.notification);
        credits=view.findViewById(R.id.credits);


        crdts=view.findViewById(R.id.crdts);
        credits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreditBalanceFragment tm = new CreditBalanceFragment();
                FragmentManager manager21 = getFragmentManager();
                FragmentTransaction transaction21 = manager21.beginTransaction();
                transaction21.replace(R.id.contentPanell, tm);
                transaction21.commit();

            }
        });
        crdts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreditBalanceFragment tm = new CreditBalanceFragment();
                FragmentManager manager21 = getFragmentManager();
                FragmentTransaction transaction21 = manager21.beginTransaction();
                transaction21.replace(R.id.contentPanell, tm);
                transaction21.commit();

            }
        });
        tabLayout = view.findViewById(R.id.tablayout1);
        viewPager = view.findViewById(R.id.viewOager);
        tab1 = view.findViewById(R.id.ongoing);
        tab2 = view.findViewById(R.id.historyy);
        tab3 = view.findViewById(R.id.withdral);

        pageAdapter = new JobAdapter(getFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pageAdapter);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
//
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        CoinsCollectUrl();
        return view;
    }
    private void CoinsCollectUrl() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.COINS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Login",response);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    String msg = jsonObject.getString("message");
                    if (status.equalsIgnoreCase("1")){
                        JSONObject resultObj = jsonObject.getJSONObject("data");
                       String coinss = resultObj.getString("coins");
                        credits.setText(coinss);
                      /*  Session_management sessionManagement = new Session_management(getActivity());
                        sessionManagement.Coins(coins);*/
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<>();
                param.put("partner_id",user_id);
                return param;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);

    }

}
