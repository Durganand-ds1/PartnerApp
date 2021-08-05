package com.tecmanic.servicepartner.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.tecmanic.servicepartner.Activity.Razorpay;
import com.tecmanic.servicepartner.ModelClass.rechargePlanModelClass;
import com.tecmanic.servicepartner.R;

import java.util.List;

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.MyViewHolder> {

    private List<rechargePlanModelClass> moviesList;
    Context context;
    private int lastSelectedPosition = -1;
    int myPos = 0;
    public PlanAdapter(Context context, List<rechargePlanModelClass> list) {
        this.context=context;
        this.moviesList=list;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name,credits,Date2,address;
        LinearLayout ll;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.price);
            credits = (TextView) view.findViewById(R.id.credits);
            ll =  view.findViewById(R.id.ll);
        }
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_planlist, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final rechargePlanModelClass movie = moviesList.get(position);

        holder.name.setText("Rs."+movie.getCoin_price());
        holder.credits.setText(movie.getCoins()+" CREDITS");
        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // lastSelectedPosition = position;

            }
        });

        if (myPos == position) {
            holder.name.setTextColor(Color.parseColor("#2bab1c"));//38393f
            holder.ll.setBackgroundResource(R.drawable.bordergreen);
        }
        else {

            holder.name.setTextColor(Color.parseColor("#000000"));//acacac
            holder.ll.setBackgroundResource(R.drawable.border);

        }
        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myPos=position;
                notifyDataSetChanged();


                Intent intent=new Intent(context, Razorpay.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("dId",position);
                intent.putExtra("planId",moviesList.get(position).getPlan_id());
               // Log.d("hjk",moviesList.get(position).getPlan_id());
                intent.putExtra("Coins",moviesList.get(position).getCoins());
                intent.putExtra("price",moviesList.get(position).getCoin_price());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}