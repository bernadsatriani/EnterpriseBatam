package com.bpbatam.enterprise.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bpbatam.AppConstant;
import com.bpbatam.enterprise.R;
import com.bpbatam.enterprise.disposisi.adapter.AdapterDisposisiUmum;
import com.bpbatam.enterprise.model.ListData;
import com.bpbatam.enterprise.model.ListUser;

import java.util.ArrayList;

/**
 * Created by User on 9/19/2016.
 */
public class AdapterListUser extends  RecyclerView.Adapter<AdapterListUser.ViewHolder>{
    ListUser listUser;
    private Context context;

    public AdapterListUser(Context context, ListUser listUser, OnDownloadClicked listener) {
        this.context = context;
        this.listUser = listUser;
        this.listener = listener;
        if (listUser == null) {
            throw new IllegalArgumentException("courses ArrayList must not be null");
        }
    }

    public interface OnDownloadClicked {
        public void OnDownloadClicked(boolean bFromDistri, String UserId, String UserName);

        void OnDownloadClicked();
    }

    private AdapterListUser.OnDownloadClicked listener;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate layout
        View itemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.row_list_user, null);

        return new ViewHolder(itemView, context, this);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ListUser.Datum listData = listUser.data.get(position);
        //Set text
        holder.txtIsi1.setText(listData.user_name);
        holder.txtIsi2.setText(listData.nama_jabatan);

        if ((position%2)==0){
            holder.layoutRow.setBackgroundColor(context.getResources().getColor(R.color.colorListItem));
        }

        holder.layoutRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppConstant.B_USER_DISITRI){
                    listener.OnDownloadClicked(true, listData.user_id, listData.user_name);
                }else{
                    listener.OnDownloadClicked(false, listData.user_id, listData.user_name);
                }

            }
        });

        holder.listData = listData;
    }

    @Override
    public int getItemCount() {
        return listUser.data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {

        TextView
                txtIsi1,
                txtIsi2
        ;

        ListUser.Datum listData;

        LinearLayout layoutRow;

        public ViewHolder(View itemView,
                          Context context,
                          final AdapterListUser mCourseAdapter) {
            super(itemView);

            txtIsi1 = (TextView)itemView.findViewById(R.id.text_isi1);
            txtIsi2 = (TextView)itemView.findViewById(R.id.text_isi2);
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
