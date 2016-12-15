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
import com.bpbatam.enterprise.model.BBS_Attachment;
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
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ui.ResizableCustomView;

/**
 * Created by User on 9/19/2016.
 */
public class AdapterBBSDaftarPesanan extends  RecyclerView.Adapter<AdapterBBSDaftarPesanan.ViewHolder>{
    private BBS_List_ByCategory bbs_list;

    BBS_Opini bbsOpini;
    private Context context;
    BBS_Attachment bbsAttachment;
    BBS_Detail bbsDetail;

    public AdapterBBSDaftarPesanan(Context context, BBS_List_ByCategory bbs_list, OnDownloadClicked listener) {
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
                R.layout.row_bbs_daftar_pesanan, null);

        return new ViewHolder(itemView, context, this);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final BBS_List_ByCategory.Datum listData = bbs_list.data.get(position);
        //Set text
        //if (listData.read_sts.equals("N")){
            holder.txtName.setText(listData.name);
            holder.txtDate.setText(listData.bbs_date);
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

            switch (listData.priority_id){
                case 0:
                    holder.imgStatus.setColorFilter(context.getResources().getColor(R.color.colorRedCircle));
                    holder.txtStatus.setText("Tinggi");
                    break;
                case 1:
                    holder.imgStatus.setColorFilter(context.getResources().getColor(R.color.colorGreen));
                    holder.txtStatus.setText("Sedang");
                    break;
                case 2:
                    holder.imgStatus.setColorFilter(context.getResources().getColor(R.color.colorRendah));
                    holder.txtStatus.setText("Rendah");
                    break;
            }

            final DecimalFormat precision = new DecimalFormat("0.00");
            holder.btnDokumen.setVisibility(View.GONE);

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
            BBS_Attachment param = new BBS_Attachment( AppConstant.HASHID,
                    AppConstant.USER,
                    AppConstant.REQID,
                    Integer.toString(listData.bbs_id));
            Call<BBS_Attachment> call = NetworkManager.getNetworkService(context).getBBS_Attachment(param);
            call.enqueue(new Callback<BBS_Attachment>() {
                @Override
                public void onResponse(Call<BBS_Attachment> call, Response<BBS_Attachment> response) {
                    int code = response.code();
                    if (code == 200){
                        bbsAttachment = response.body();
                        if(bbsAttachment.code.equals("00")){
                            for(BBS_Attachment.Datum dat : bbsAttachment.data){
                                listData.attc_size = dat.file_size;
                                listData.attc_link = dat.attc_link;
                            }
                            try{
                                if (listData.attc_size != null ){
                                    String fileName = listData.attc_link.substring(listData.attc_link.lastIndexOf('/') + 1);
                                    double dFileSize = Double.parseDouble(listData.attc_size) / 1024;
                                    holder.lbl_Attach.setText(fileName);
                                    holder.lbl_Size.setText("(" + precision.format(dFileSize) + " kb)" );
                                    holder.btnDokumen.setVisibility(View.VISIBLE);
                                    holder.layoutAttachMent.setVisibility(View.VISIBLE);
                                }
                            }catch (Exception e){

                            }
                        }else{
                            holder.layoutAttachMent.setVisibility(View.GONE);
                        }
                    }
                }

                @Override
                public void onFailure(Call<BBS_Attachment> call, Throwable t) {

                }
            });
        }catch (Exception e){

        }


        try {
            AppConstant.HASHID = AppController.getInstance().getHashId(AppConstant.USER);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
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
                    AppConstant.BBS_LINK = listData.attc_link;
                    try{
                        AppConstant.BBS_LINK = listData.attc_link;
                        listener.OnDownloadClicked(listData.attc_link, true);
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
                        AppConstant.BBS_LINK = listData.attc_link;
                        if (listData.attc_link == null) AppConstant.BBS_LINK = "";
                    }catch (Exception e){
                        AppConstant.BBS_LINK = "";
                    }
                    Intent mIntent = new Intent(context, BBS_edit_berita.class);
                    mIntent.putExtra("BBS_NAME", listData.name);
                    mIntent.putExtra("BBS_JUDUL", listData.title);
                    mIntent.putExtra("BBS_SIZE", holder.lbl_Size.getText().toString());
                    mIntent.putExtra("BBS_ISI", holder.lbl_Judul.getText().toString());
                    mIntent.putExtra("BBS_DATE", listData.bbs_date);
                    mIntent.putExtra("BBS_READ", listData.read_sts);
                    mIntent.putExtra("BBS_CATEGORY", listData.category_id);
                    mIntent.putExtra("BBS_CATEGORY_NAME", holder.txtFrom.getText().toString());
                    mIntent.putExtra("BBS_PRIORITY", String.valueOf(listData.priority_id));
                    AppConstant.EMAIL_ID = listData.bbs_id;
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
       // }

    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        BBS_List_ByCategory Lisdata = null;

        Lisdata.data.clear();
        if (charText.length() == 0) {
            bbs_list.data.addAll(Lisdata.data);
        }
        else
        {
            for (BBS_List_ByCategory.Datum wp : Lisdata.data)
            {
                if (wp.title.toLowerCase(Locale.getDefault()).contains(charText))
                {
                    bbs_list.data.add(wp);
                }
            }
        }
        notifyDataSetChanged();
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
        BBS_List_ByCategory.Datum listData;
        public ViewHolder(View itemView,
                          final Context context,
                          final AdapterBBSDaftarPesanan mCourseAdapter) {
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
