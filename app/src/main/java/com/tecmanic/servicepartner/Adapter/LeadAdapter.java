package com.tecmanic.servicepartner.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.tecmanic.servicepartner.Activity.ServiceDetails;
import com.tecmanic.servicepartner.Constants.Session_management;
import com.tecmanic.servicepartner.ModelClass.LeadModelClass;
import com.tecmanic.servicepartner.R;

import java.util.List;

public class LeadAdapter extends RecyclerView.Adapter<LeadAdapter.MyViewHolder> {

    private List<LeadModelClass> moviesList;
    Context context;
    double dist;
    Session_management session_management;
    public LeadAdapter(Context context, List<LeadModelClass> list) {
        this.context=context;
        this.moviesList=list;
        session_management=new Session_management(context);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name,credits,bookingDte1,Date2,address,distance,tdate;
        CardView cardView;
        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.tname);
            credits = (TextView) view.findViewById(R.id.credits);
            bookingDte1 = (TextView) view.findViewById(R.id.timee1);
            Date2 = (TextView) view.findViewById(R.id.timee2);
            address = (TextView) view.findViewById(R.id.addresss);
            distance = (TextView) view.findViewById(R.id.distance);
            tdate= (TextView) view.findViewById(R.id.tdate);
            cardView=  view.findViewById(R.id.cadView);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_leads_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final LeadModelClass movie = moviesList.get(position);
        holder.name.setText(movie.getUser_name());
        holder.credits.setText(movie.getCoins()+" credits");
        holder.bookingDte1.setText(movie.getConfirmed_on()+","+movie.getConfirmed_on());
        holder.Date2.setText(movie.getTime_slot());
        holder.address.setText(movie.getUser_address());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, ServiceDetails.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("booking_id",moviesList.get(position).getBooking_id());
               // intent.putExtra("booking_id",moviesList.get(position).getBooking_id());
                context.startActivity(intent);
            }
        });

        try {
            String totalOff = String.valueOf(Double.parseDouble(movie.getDistance()));

            CharSequence foo = totalOff;
            String bar = foo.toString();
            final String desiredString = bar.substring(0, 6);

            holder.distance.setText(desiredString + " Kms.");
        }catch (Exception e){

        }

      /*  double theta = Double.parseDouble(session_management.Lng()) - Double.parseDouble(movie.getLng());
        dist = Math.sin(deg2rad(Double.parseDouble(session_management.Lat())))
                * Math.sin(deg2rad(Double.parseDouble(movie.getLat())))
                + Math.cos(deg2rad(Double.parseDouble(session_management.Lat())))
                * Math.cos(deg2rad(Double.parseDouble(movie.getLat())))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515* 1.852;

        holder.distance.setText(Double.toString((int) dist)+" Kms.");
*/
// Get Current Date Time
     /*   Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String formattedDate = df.format(c);

        if(movie.getConfirmed_on().equalsIgnoreCase(formattedDate)){
            holder.tdate.setText("Today");
        }*/
/*
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String todayAsString = dateFormat.format(today);

        if(movie.getConfirmed_on().equalsIgnoreCase(todayAsString)){
            holder.tdate.setText("Today");
        }
        else {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            Date tomorrow = calendar.getTime();
            SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
            String tomorrowAsString = dateFormat1.format(tomorrow);

            if(movie.getConfirmed_on().equalsIgnoreCase(tomorrowAsString)){
                holder.tdate.setText("Tomorrow");
            }
            else{
                holder.tdate.setText(movie.getConfirmed_on());
            }
        }*/
     /*   int drange= Integer.parseInt(session_management.Deli_range());
     //   double number = Double.valueOf(String.valueOf(dist));
        if (drange<=dist){
           // Log.d("sdf", String.valueOf(drange+number));
            holder.cardView.setVisibility(View.GONE);
        }else {
            holder.cardView.setVisibility(View.VISIBLE);

        }*/

    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}