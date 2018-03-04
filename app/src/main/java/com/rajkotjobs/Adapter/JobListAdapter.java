package com.rajkotjobs.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by raju on 05-10-2017.
 */

import com.rajkotjobs.Model.ListOfJobModel;
import com.rajkotjobs.R;


public class JobListAdapter extends RecyclerView.Adapter<JobListAdapter.MyViewHolder> {
    Context context;
    List<ListOfJobModel> rv_list = new ArrayList<>();

    public JobListAdapter(Context context, List<ListOfJobModel> list) {
        this.context = context;
        this.rv_list = list;


    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_job_list, parent, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        v.setLayoutParams(lp);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.tv_job_title.setText(rv_list.get(position).getJob_title());
        holder.tv_post_days.setText(rv_list.get(position).getJob_post_days());
        holder.tv_job_des.setText(rv_list.get(position).getJob_des());
        holder.tv_sallery.setText(rv_list.get(position).getJob_sellery());
       /* holder.tv_product_price.setText(rv_list.get(position).getLastpage_recyclerview_product_price());
        holder.tv_company_name.setText(rv_list.get(position).getLastpage_recyclerview_product_name());
        Glide.with(context).load(rv_list.get(position).getLastpage_recyclerview_product_image()).into(holder.iv_profile_image);
        // Constants.delete_p_id = rv_list.get(position).getP_id();
        holder.tv_delete_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(context.getApplicationContext(), DeleteGalleryPhotoActivity.class);
                i.putExtra("p_id", rv_list.get(position).getP_id());
                rv_list.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, rv_list.size());
                context.startActivity(i);

                // context.startActivity(new Intent(context.getApplicationContext(), DeleteGalleryPhotoActivity.class));
            }
        });

      *//*  holder.tv_product_list_price.setText(rv_list.get(position).getLastpage_recyclerview_product_price());
        holder.tv_product_list_name.setText(rv_list.get(position).getLastpage_recyclerview_product_name());
        Glide.with(context).load(rv_list.get(position).getLastpage_recyclerview_product_image()).into(holder.iv_product_list_image);
   */
    }


    @Override
    public int getItemCount() {
        return rv_list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_job_title, tv_job_des, tv_sallery, tv_post_days;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_job_title = (TextView) itemView.findViewById(R.id.tv_job_title);
            tv_job_des = (TextView) itemView.findViewById(R.id.tv_job_des);
            tv_sallery = (TextView) itemView.findViewById(R.id.tv_sallery);
            tv_post_days = (TextView) itemView.findViewById(R.id.tv_post_days);


        }
    }
}