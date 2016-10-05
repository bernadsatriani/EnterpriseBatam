package com.bpbatam.enterprise.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bpbatam.enterprise.R;
import com.bpbatam.enterprise.model.ListData;

import java.util.ArrayList;

/**
 * Created by User on 9/19/2016.
 */
public class AdapterNotification extends  RecyclerView.Adapter<AdapterNotification.ViewHolder>{

    private ArrayList<ListData> mCourseArrayList;
    private Context context;

    public AdapterNotification(Context context, ArrayList<ListData> mCourseArrayList) {
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
                R.layout.row_notification, null);

        return new ViewHolder(itemView, context, this);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ListData listData = mCourseArrayList.get(position);
        //Set text
        holder.txtTime.setText("12:37 PM");
        holder.lbl_Attach.setText(listData.getAtr1());
        holder.lbl_Size.setText(listData.getAtr2());

        holder.txtStatus.setBackgroundResource(R.drawable.btn_shape_all_red);
        if ((position%2)==0){
            holder.txtStatus.setText("Diterima");
            holder.txtStatus.setBackgroundResource(R.drawable.btn_shape_all_green);
            holder.viewLine.setBackgroundColor(context.getResources().getColor(R.color.colorAccept));
        }else if ((position%3)==0) {
            holder.viewLine.setBackgroundColor(context.getResources().getColor(R.color.colorReject));
        }else{
            holder.viewLine.setBackgroundColor(context.getResources().getColor(R.color.colorSearch));
        }


        holder.listData = listData;
    }

    @Override
    public int getItemCount() {
        return mCourseArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {

        TextView txtTime,
                lbl_Attach,
                lbl_Size,
                txtStatus,
                txtDate,
                viewLine
        ;

        ListData listData;
        public ViewHolder(View itemView,
                          Context context,
                          final AdapterNotification mCourseAdapter) {
            super(itemView);

            txtStatus = (TextView)itemView.findViewById(R.id.btn_pay);
            txtTime = (TextView)itemView.findViewById(R.id.text_time);
            txtDate = (TextView)itemView.findViewById(R.id.text_Date);
            lbl_Attach = (TextView)itemView.findViewById(R.id.lbl_attach);
            lbl_Size = (TextView)itemView.findViewById(R.id.lbl_size);
            viewLine = (TextView)itemView.findViewById(R.id.view3);

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
