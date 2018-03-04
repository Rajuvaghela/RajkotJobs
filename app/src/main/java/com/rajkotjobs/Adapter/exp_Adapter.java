package com.rajkotjobs.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.rajkotjobs.Global;
import com.rajkotjobs.Model.exp_info;
import com.rajkotjobs.R;

import java.util.ArrayList;


public class exp_Adapter extends RecyclerView.Adapter<exp_Adapter.myholder> {
    Context ctx;
    ArrayList<exp_info> exp_list;
    public exp_Adapter(Context ctx, ArrayList<exp_info> exp_list) {
        this.ctx = ctx;
        this.exp_list = exp_list;

    }

    public exp_Adapter.myholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.rv_item, parent, false);
        return new myholder(v);
    }

    public void onBindViewHolder(exp_Adapter.myholder holder, final int position)
    {
        try {
        Global.set();
            ArrayAdapter<String> month = new ArrayAdapter<String>(ctx, R.layout.sp_single, Global.month);
            ArrayAdapter<String> year = new ArrayAdapter<String>(ctx, R.layout.sp_single, Global.year);
            holder.sp_fmonth.setAdapter(month);
            holder.sp_tmonth.setAdapter(month);
            holder.sp_fyear.setAdapter(year);
            holder.sp_tyear.setAdapter(year);
        holder.et_title.setText(exp_list.get(position).getTitle());
        holder.et_cmp.setText(exp_list.get(position).getCmp());
        holder.et_city.setText(exp_list.get(position).getCity());

        holder.sp_fmonth.setSelection(exp_list.get(position).getFrom_month());
        holder.sp_fyear.setSelection(exp_list.get(position).getFrom_year());
        holder.sp_tmonth.setSelection(exp_list.get(position).getTo_month());
        holder.sp_tyear.setSelection(exp_list.get(position).getTo_year());
        holder.et_jobdesc.setText(exp_list.get(position).getDesc());

            holder.iv_close.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View view)
                {

                    exp_list.remove(position);
                    notifyDataSetChanged();
                    notifyItemRemoved(position);

                }
            });



        }catch (Exception e)
        {
            Log.d("arrlogs",e.getMessage());}
    }

    public int getItemCount() {
        return exp_list.size();
    }

    class myholder extends RecyclerView.ViewHolder {
        EditText et_title, et_cmp, et_city, et_jobdesc;
        ImageView iv_close;
        Spinner sp_fmonth,sp_fyear,sp_tmonth,sp_tyear;
        public myholder(View itemView) {
            super(itemView);
            et_title = (EditText) itemView.findViewById(R.id.et_title);
            et_cmp = (EditText) itemView.findViewById(R.id.et_cmp);
            et_city = (EditText) itemView.findViewById(R.id.et_city);
            sp_fmonth = (Spinner) itemView.findViewById(R.id.sp_fmonth);
            sp_fyear = (Spinner) itemView.findViewById(R.id.sp_fyear);
            sp_tmonth = (Spinner) itemView.findViewById(R.id.sp_tmonth);
            sp_tyear = (Spinner) itemView.findViewById(R.id.sp_tyear);
            et_jobdesc = (EditText) itemView.findViewById(R.id.et_jobdesc);
            iv_close = (ImageView) itemView.findViewById(R.id.iv_close);


        }
    }
}
