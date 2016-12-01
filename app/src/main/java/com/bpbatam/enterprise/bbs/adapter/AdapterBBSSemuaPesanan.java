package com.bpbatam.enterprise.bbs.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bpbatam.AppConstant;
import com.bpbatam.AppController;
import com.bpbatam.enterprise.R;
import com.bpbatam.enterprise.bbs.BBS_edit_berita;
import com.bpbatam.enterprise.bbs.bbs_komentar_activity;
import com.bpbatam.enterprise.model.BBS_Detail;
import com.bpbatam.enterprise.model.BBS_LIST;
import com.bpbatam.enterprise.model.BBS_List_ByCategory;
import com.bpbatam.enterprise.model.BBS_Opini;
import com.bpbatam.enterprise.model.ListData;
import com.bpbatam.enterprise.model.net.NetworkManager;

import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ui.ResizableCustomView;

/**
 * Created by User on 9/19/2016.
 */
public class AdapterBBSSemuaPesanan extends  RecyclerView.Adapter<AdapterBBSSemuaPesanan.ViewHolder>{
    private BBS_LIST bbs_list;
    BBS_Opini bbsOpini;
    private Context context;
    BBS_Detail bbsDetail;

    public AdapterBBSSemuaPesanan(Context context, BBS_LIST bbs_list, OnDownloadClicked listener) {
        this.context = context;
        this.bbs_list = bbs_list;
        this.listener = listener;
        if (bbs_list == null) {
            throw new IllegalArgumentException("courses ArrayList must not be null");
        }
    }

    public interface OnDownloadClicked {
        public void OnDownloadClicked(String sUrl, boolean bStatus);
    }

    private OnDownloadClicked listener;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate layout
        View itemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.row_bbs_semua_pesanan, null);

        return new ViewHolder(itemView, context, this);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final BBS_LIST.Datum listData = bbs_list.data.get(position);
        //Set text
        holder.txtName.setText(listData.name);
        holder.txtDate.setText(listData.bbs_date);
        holder.lbl_Attach.setText((listData.title == null) ? "" : listData.title + " " + ((listData.content == null) ? "" : listData.content));
        holder.lbl_Judul.setText((listData.title == null) ? "" : listData.title + " " + ((listData.content == null) ? "" : listData.content));
        //holder.lbl_Isi.setText(listData.content);

        holder.txtFrom.setText(listData.category_id);
        holder.lbl_Attach.setText(listData.title);
        holder.lbl_Judul.setText(listData.title);
        holder.txtDivisi.setText(listData.title);

        holder.lbl_Size.setText("");
        holder.txtOpini.setText("");

        holder.btnDownload.setVisibility(View.GONE);
        if (listData.bbs_by != null){
            if (AppConstant.USER.equals(listData.bbs_by)){
                holder.btnDownload.setVisibility(View.VISIBLE);
            }
        }
        DecimalFormat precision = new DecimalFormat("0.00");
        holder.btnDokumen.setVisibility(View.GONE);
        try{
            if (listData.attc_data.size() > 0){
                for(BBS_LIST.AttcData dat : listData.attc_data){
                    String fileName = dat.attc_link.substring(dat.attc_link.lastIndexOf('/') + 1);
                    double dFileSize = Double.parseDouble(dat.file_size) / 1024;
                    holder.lbl_Attach.setText(fileName);
                    holder.lbl_Size.setText("(" + precision.format(dFileSize) + " kb)" );
                    holder.btnDokumen.setVisibility(View.VISIBLE);
                    holder.layoutAttachMent.setVisibility(View.VISIBLE);
                }
            }else{
                holder.layoutAttachMent.setVisibility(View.GONE);
            }
        }catch (Exception e){
            holder.layoutAttachMent.setVisibility(View.GONE);
        }

        try {
            AppConstant.HASHID = AppController.getInstance().getHashId(AppConstant.USER);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


        try {
            AppConstant.HASHID = AppController.getInstance().getHashId(AppConstant.USER);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        try{
            BBS_Detail param = new BBS_Detail( AppConstant.HASHID,
                    AppConstant.USER,
                    AppConstant.REQID,
                    Integer.toString(listData.bbs_id));

            Call<BBS_Detail>call = NetworkManager.getNetworkService(context).getBBS_Detail(param);
            call.enqueue(new Callback<BBS_Detail>() {
                @Override
                public void onResponse(Call<BBS_Detail> call, Response<BBS_Detail> response) {
                    int code = response.code();
                    if (code == 200){
                        bbsDetail = response.body();
                        if (bbsDetail.code.equals("00")){
                            for(BBS_Detail.Datum dat : bbsDetail.data){
                                holder.lbl_Judul.setText(Html.fromHtml(dat.content));

                                if (holder.lbl_Judul.getLineCount() > 3){
                                    ResizableCustomView.doResizeTextView(holder.lbl_Judul, 3, "View More", true);
                                }

                              /* String text = holder.lbl_Judul.getText().toString();
                                if (holder.lbl_Judul.length()>20) {
                                    text=text.substring(0,20)+"...";
                                    holder.lbl_Judul.setText(Html.fromHtml(text+"<font color='red'> <u>View More</u></font>"));

                                }*/
                                holder.txtFrom.setText(dat.category_name);


                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<BBS_Detail> call, Throwable t) {

                }
            });
        }catch (Exception e){

        }

        try{
            BBS_Opini param = new BBS_Opini(AppConstant.HASHID,
                    AppConstant.USER,
                    AppConstant.REQID,
                    String.valueOf(listData.bbs_id));

            Call<BBS_Opini> call = NetworkManager.getNetworkService(context).getBBS_Opini(param);
            call.enqueue(new Callback<BBS_Opini>() {
                @Override
                public void onResponse(Call<BBS_Opini> call, Response<BBS_Opini> response) {
                    int code = response.code();
                    if (code == 200){
                        bbsOpini = response.body();
                        if (bbsOpini.code.equals("00")){
                            holder.txtOpini.setText(bbsOpini.data.size() + " Opini");
                        }
                    }
                }

                @Override
                public void onFailure(Call<BBS_Opini> call, Throwable t) {

                }
            });
        }catch (Exception e){

        }
        //holder.txtStatus.setText(listData.getAtr2());

        //AppController.getInstance().displayImage(context,listData.getAtr3(), holder.imgCover);

        holder.btnDokumen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppConstant.EMAIL_ID = listData.bbs_id;
                try{
                    AppConstant.BBS_LINK = listData.attc_data.get(0).attc_link;
                    listener.OnDownloadClicked(listData.attc_data.get(0).attc_link, true);
                }catch (Exception e){
                    AppConstant.BBS_LINK = "";
                    listener.OnDownloadClicked("", false);
                }

            }
        });

        holder.btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppConstant.EMAIL_ID = listData.bbs_id;
                try{
                    AppConstant.BBS_LINK = listData.attc_data.get(0).attc_link;
                }catch (Exception e){
                    AppConstant.BBS_LINK = "";
                }
                Intent mIntent = new Intent(context, BBS_edit_berita.class);
                mIntent.putExtra("BBS_NAME", listData.name);
                mIntent.putExtra("BBS_SIZE", holder.lbl_Size.getText().toString());
                mIntent.putExtra("BBS_JUDUL", listData.title);
                mIntent.putExtra("BBS_ISI", listData.content);
                mIntent.putExtra("BBS_DATE", listData.bbs_date);
                mIntent.putExtra("BBS_READ", listData.read_sts);
                context.startActivity(mIntent);
            }
        });

        holder.btnOpini.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppConstant.EMAIL_ID = listData.bbs_id;
                Intent mIntent = new Intent(context, bbs_komentar_activity.class);
                context.startActivity(mIntent);
            }
        });
        holder.listData = listData;
    }

    @Override
    public int getItemCount() {
        return bbs_list.data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {

        TextView txtDate,
                txtName,
                txtFrom,
                txtDivisi,
                txtStatus,
                lbl_Attach,
                lbl_Size,
                lbl_Judul,
                btnOpini,
                txtOpini
                        ;
        RelativeLayout btnDownload, btnDokumen, layoutAttachMent;
        ImageView imgCover, imgStatus;
        BBS_LIST.Datum listData;
        public ViewHolder(View itemView,
                          final Context context,
                          final AdapterBBSSemuaPesanan mCourseAdapter) {
            super(itemView);
            imgCover = (ImageView)itemView.findViewById(R.id.imageView7);
            imgStatus = (ImageView)itemView.findViewById(R.id.img_status);
            btnOpini = (TextView)itemView.findViewById(R.id.btnOpini);
            txtOpini = (TextView)itemView.findViewById(R.id.text_opini);
            txtDate = (TextView)itemView.findViewById(R.id.text_Date);
            txtName = (TextView)itemView.findViewById(R.id.text_Name);
            txtFrom = (TextView)itemView.findViewById(R.id.text_from);
            txtDivisi = (TextView)itemView.findViewById(R.id.text_Division);
            txtStatus = (TextView)itemView.findViewById(R.id.text_status);
            lbl_Judul = (TextView)itemView.findViewById(R.id.lbl_Judul);
            lbl_Attach = (TextView)itemView.findViewById(R.id.lbl_attach);
            lbl_Size = (TextView)itemView.findViewById(R.id.lbl_size);
            btnDownload = (RelativeLayout) itemView.findViewById(R.id.btnDownload);
            btnDokumen = (RelativeLayout) itemView.findViewById(R.id.layout_dokumen);
            layoutAttachMent = (RelativeLayout)itemView.findViewById(R.id.layout_attachment);
            /*btnDownload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //https://www.dropbox.com/s/jadu92w71vnku3o/Wireframe.pdf?dl=0
                    listener.OnDownloadClicked("http://unec.edu.az/application/uploads/2014/12/pdf-sample.pdf", true);
                }
            });*/


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
