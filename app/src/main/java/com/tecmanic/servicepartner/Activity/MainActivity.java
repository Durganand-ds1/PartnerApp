package com.tecmanic.servicepartner.Activity;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import com.squareup.picasso.Picasso;
import com.tecmanic.servicepartner.Constants.Session_management;
import com.tecmanic.servicepartner.Fragments.ContactFragment;
import com.tecmanic.servicepartner.Fragments.CreditBalanceFragment;
import com.tecmanic.servicepartner.Fragments.GSTdetailsFragment;
import com.tecmanic.servicepartner.Fragments.HomeeFragment;
import com.tecmanic.servicepartner.Fragments.JobHistoryFragment;
import com.tecmanic.servicepartner.Fragments.ProfileFragment;
import com.tecmanic.servicepartner.Fragments.TermsFragment;
import com.tecmanic.servicepartner.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import static com.tecmanic.servicepartner.Constants.Config.IMAGE_URL;


public class MainActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{

    private AppBarConfiguration mAppBarConfiguration;
    private Menu nav_menu;
    private Session_management sessionManagement;
    ImageView slider;
    TextView txtHead;
    String bookingID,partnerName,partnerNo,partnerID,partner_image;
    ImageView imageView;
    TextView name,number;
    public static BottomNavigationView navigation;
    public static DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sessionManagement=new Session_management(this);
        partnerName=sessionManagement.userName();
        partnerNo=sessionManagement.userNo();
        partner_image=sessionManagement.userNo();

        Toolbar toolbar = findViewById(R.id.toolbar);
        navigation = findViewById(R.id.nav_view12);
        setSupportActionBar(toolbar);
        slider=findViewById(R.id.slidr);
        txtHead=findViewById(R.id.txtHead);

        NavigationView navigationView = findViewById(R.id.nav_view);
        View header = ((NavigationView) findViewById(R.id.nav_view)).getHeaderView(0);
        imageView=header.findViewById(R.id.imageView);

        name=header.findViewById(R.id.name);
        number=header.findViewById(R.id.No);
        if(partnerNo!=null){
        name.setText(partnerName);
        number.setText(partnerNo);
             Picasso.with(getApplicationContext()).load(IMAGE_URL+partner_image).error(R.drawable.logo).into(imageView);

        }


        txtHead.setText("New Leads");


        drawer = findViewById(R.id.drawer_layout);
        slider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(Gravity.LEFT);
            }
        });
        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(MainActivity.this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.setDrawerListener(toggle);
        drawer.addDrawerListener(toggle);
        toggle.syncState();



        Menu m = navigationView.getMenu();

        for (
                int i = 0; i < m.size(); i++) {
            MenuItem mi = m.getItem(i);
            SubMenu subMenu = mi.getSubMenu();
            if (subMenu != null && subMenu.size() > 0) {
                for (int j = 0; j < subMenu.size(); j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                }
            }

        }
        View headerView = navigationView.getHeaderView(0);
        navigationView.getBackground().setColorFilter(0x80000000, PorterDuff.Mode.MULTIPLY);
        navigationView.setNavigationItemSelectedListener(this);
        nav_menu = navigationView.getMenu();
      //  View header = ((NavigationView) findViewById(R.id.nav_view)).getHeaderView(0);
      /*  iv_profile = header.findViewById(R.id.iv_header_img);

        tv_name = header.findViewById(R.id.tv_header_name);*/
/*
        if (savedInstanceState == null) {
//            TodayOrder tm = new TodayOrder();
            HomeeFragment tm = new HomeeFragment();
            FragmentManager manager21 = getSupportFragmentManager();
            FragmentTransaction transaction21 = manager21.beginTransaction();
            transaction21.replace(R.id.contentPanell, tm);
            transaction21.commit();
        }
*/
        initComponent();
     //   CoinsCollectUrl();
        loadFragment(new HomeeFragment());
    }
    private void loadFragment(Fragment fragment) {
        this.getSupportFragmentManager().beginTransaction()
                .replace(R.id.contentPanell, fragment)
                .commitAllowingStateLoss();
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Fragment fm = null;
        Bundle args = new Bundle();
        if (id == R.id.nav_homed) {
            fm = new HomeeFragment();


        } else if (id == R.id.nav_profile) {
            fm  = new ProfileFragment();

        }
        else if (id == R.id.nav_credit) {
            fm  = new CreditBalanceFragment();
        }
        else if (id == R.id.nav_jobhistory) {
            fm  = new JobHistoryFragment();
        }
        else if (id == R.id.nav_gst) {
            fm  = new GSTdetailsFragment();
        }
        else if (id == R.id.nav_terms) {
            fm  = new TermsFragment();

        } else if (id == R.id.nav_contct) {
            fm = new ContactFragment();

        } else if (id == R.id.nav_log_out) {
            sessionManagement.logoutSession();
            finish();
        }
        if (fm != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.contentPanell, fm).addToBackStack(null).commit();

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }




   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }*/

    @Override
    public boolean onSupportNavigateUp() {
      /*  NavController navController = Navigation.findNavController(this, R.id.contentPanell);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();*/
      return false;
    }
    private void initComponent() {
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_leads:
                        MainActivity.this.loadFragment(new HomeeFragment());

                        return true;
                    case R.id.navigation_earn:
                        MainActivity.this.loadFragment(new CreditBalanceFragment());

                        return true;

                    case R.id.navigation_bookk:
                        MainActivity.this.loadFragment(new JobHistoryFragment());

                        return true;


                    case R.id.navigation_prof:
                        MainActivity.this.loadFragment(new ProfileFragment());

                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}