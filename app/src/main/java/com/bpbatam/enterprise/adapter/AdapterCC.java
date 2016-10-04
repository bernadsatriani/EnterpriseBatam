package com.bpbatam.enterprise.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bpbatam.enterprise.DistribusiActivity;
import com.bpbatam.enterprise.R;
import com.bpbatam.enterprise.disposisi.disposisi_detail;
import com.bpbatam.enterprise.model.ListData;

import java.util.ArrayList;

/**
 * Created by User on 9/19/2016.
 */
public class AdapterCC extends  RecyclerView.Adapter<AdapterCC.ViewHolder>{

    private ArrayList<ListData> mCourseArrayList;
    private Context context;

    public AdapterCC(Context context, ArrayList<ListData> mCourseArrayList) {
        this.context = context;
        this.mCourseArrayList = mCourseArrayList;

        if (mCourseArrayList == null) {
            throw new IllegalArgumentException("courses ArrayList must not be null");
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate layout
        View itemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.row_cc, null);

        return new ViewHolder(itemView, context, this);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ListData listData = mCourseArrayList.get(position);
        //Set text
        holder.txtDepartemen.setText(listData.getAtr1());
        holder.txtNama.setText(listData.getAtr2());
        holder.txtTerkirim.setText(listData.getAtr3());
        holder.txtDibaca.setText(listData.getNama());

        holder.listData = listData;
    }

    @Override
    public int getItemCount() {
        return mCourseArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {

        TextView txtDepartemen,
                txtNama,
                txtTerkirim,
                txtDibaca
        ;

        LinearLayout layoutRow;
        ListData listData;
        public ViewHolder(View itemView,
                          Context context,
                          final AdapterCC mCourseAdapter) {
            super(itemView);

            txtDepartemen = (TextView)itemView.findViewById(R.id.text_departemen);
            txtNama = (TextView)itemView.findViewById(R.id.text_nama);
            txtTerkirim = (TextView)itemView.findViewById(R.id.text_terkirim);
            txtDibaca = (TextView)itemView.findViewById(R.id.text_dibaca);
            layoutRow = (LinearLayout)itemView.findViewById(R.id.layout_row);

        }

        @Override
        public void onClick(View v) {

        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }
}
