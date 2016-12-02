package com.bpbatam.enterprise.model.net;

import com.bpbatam.enterprise.model.ApiGithub;
import com.bpbatam.enterprise.model.AuthUser;
import com.bpbatam.enterprise.model.BBS_Attachment;
import com.bpbatam.enterprise.model.BBS_CATEGORY;
import com.bpbatam.enterprise.model.BBS_Detail;
import com.bpbatam.enterprise.model.BBS_Insert;
import com.bpbatam.enterprise.model.BBS_LIST;
import com.bpbatam.enterprise.model.BBS_List_ByCategory;
import com.bpbatam.enterprise.model.BBS_Opini;
import com.bpbatam.enterprise.model.DISPOSISI_Category;
import com.bpbatam.enterprise.model.DataAdmin;
import com.bpbatam.enterprise.model.Diposisi_List_Folder;
import com.bpbatam.enterprise.model.Disposisi_Attachment;
import com.bpbatam.enterprise.model.Disposisi_Detail;
import com.bpbatam.enterprise.model.Disposisi_Distribusi;
import com.bpbatam.enterprise.model.Disposisi_Folder;
import com.bpbatam.enterprise.model.Disposisi_Notifikasi;
import com.bpbatam.enterprise.model.Disposisi_Riwayat;
import com.bpbatam.enterprise.model.GitHubUser;
import com.bpbatam.enterprise.model.ListUser;
import com.bpbatam.enterprise.model.LocationList;
import com.bpbatam.enterprise.model.Persuratan_Attachment;
import com.bpbatam.enterprise.model.Persuratan_Detail;
import com.bpbatam.enterprise.model.Persuratan_Distribusi;
import com.bpbatam.enterprise.model.Persuratan_Distribusi_Detail;
import com.bpbatam.enterprise.model.Persuratan_Folder;
import com.bpbatam.enterprise.model.Persuratan_List_Folder;
import com.bpbatam.enterprise.model.Persuratan_proses;
import com.bpbatam.enterprise.model.USER_Info;
import com.bpbatam.enterprise.model.UpdateDeviceId;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by User on 20/07/2016.
 */
public interface NetworkService {
    @GET("group/{id}/users")
    Call<List<ApiGithub>> getGroupList(@Path("id") int groupId) ;


    @GET("users/{user}")
    Call<GitHubUser> getUser(@Path("user") String user);

    @GET("test/location")
    Call<LocationList> getLocationList() ;

    @GET("users")
    Call<List<LocationList>> getUserListNew() ;

    @GET("contohjson")
    Call<DataAdmin> getAdmin() ;

    @POST("ep_api_services/ep_user/login")
    Call<AuthUser> loginUser(@Body AuthUser params);

    @POST("ep_api_services/ep_bbs/get_list")
    Call<BBS_LIST> getBBS_List(@Body BBS_LIST params);

    @POST("ep_api_services/ep_bbs/get_detail")
    Call<BBS_Detail> getBBS_Detail(@Body BBS_Detail params);


    @POST("ep_api_services/ep_bbs/get_list_bycat")
    Call<BBS_List_ByCategory> getBBS_List_ByCat(@Body BBS_List_ByCategory params);

    @POST("ep_api_services/ep_bbs/get_bbs_attc")
    Call<BBS_Attachment> getBBS_Attachment(@Body BBS_Attachment params);

    @POST("ep_api_services/ep_bbs/insert")
    Call<BBS_Insert> postBBSInsert(@Body BBS_Insert params);

    @POST("ep_api_services/ep_bbs/update_bbs")
    Call<BBS_Insert> postBBSUpdate(@Body BBS_Insert params);

    @POST("ep_api_services/ep_bbs/get_bbs_opini")
    Call<BBS_Opini> getBBS_Opini(@Body BBS_Opini params);

    @POST("ep_api_services/ep_bbs/insert_bbs_opini")
    Call<BBS_Opini> postBBS_Opini(@Body BBS_Opini params);

    @Multipart
    @POST("upload.php")
    Call<BBS_Insert> postBBSInsertAttachmentOnly(@Part("user_id") RequestBody user_id,
                                                 @Part("id") RequestBody id,
                                                 @Part("fileKey") RequestBody file_key,
                                                 @Part MultipartBody.Part file);

    @POST("ep_api_services/ep_bbs/delete")
    Call<BBS_Insert> postBBSDelete(@Body BBS_Insert params);

    @POST("ep_api_services/ep_bbs/delete_attachment")
    Call<BBS_Insert> postBBSDeleteAttachment(@Body BBS_Insert params);

    @POST("ep_api_services/ep_bbs/get_category")
    Call<BBS_CATEGORY> getBBS_Category(@Body BBS_CATEGORY params);

    @POST("ep_api_services/ep_user/update_deviceid")
    Call<UpdateDeviceId> updateDeviceID(@Body UpdateDeviceId params);


    @POST("ep_api_services/ep_user/search_user")
    Call<ListUser> getListUser(@Body ListUser params);

    //PERSURATAN-------------------------------------------------------------------
    @POST("ep_api_services/ep_mail/get_folder")
    Call<Persuratan_Folder> getMailFolder(@Body Persuratan_Folder params);

    @POST("ep_api_services/ep_mail/get_mail_list_by_folder")
    Call<Persuratan_List_Folder> getMailFolder(@Body Persuratan_List_Folder params);

    @POST("ep_api_services/ep_mail/get_mail_detail")
    Call<Persuratan_Detail> getMailDetail(@Body Persuratan_Detail params);

    @POST("ep_api_services/ep_mail/get_mail_attc")
    Call<Persuratan_Attachment> getMailAttachment(@Body Persuratan_Attachment params);

    @POST("ep_api_services/ep_mail/approve_mail")
    Call<Persuratan_proses> postDisetejui(@Body Persuratan_proses params);

    @POST("ep_api_services/ep_mail/reject_mail")
    Call<Persuratan_proses> postDitolak(@Body Persuratan_proses params);

    @POST("ep_api_services/ep_mail/recall_mail")
    Call<Persuratan_proses> postRecall(@Body Persuratan_proses params);

    @POST("ep_api_services/ep_mail/save_mail_to_draft")
    Call<Persuratan_proses> postSave(@Body Persuratan_proses params);

    @POST("ep_api_services/ep_mail/delete_mail")
    Call<Persuratan_List_Folder> postDelete(@Body Persuratan_List_Folder params);


    @POST("ep_api_services/ep_mail/distribution_mail")
    Call<Persuratan_Distribusi> postSendDistribusiPersuratan(@Body Persuratan_Distribusi params);

    @POST("ep_api_services/ep_mail/get_distribution_detail")
    Call<Persuratan_Distribusi_Detail> getDistribusiDetail(@Body Persuratan_Distribusi_Detail params);

    //DISPOSISI-------------------------------------------------------------------------
    @POST("ep_api_services/ep_dispo/get_category")
    Call<DISPOSISI_Category> getCategory(@Body DISPOSISI_Category params);

    @POST("ep_api_services/ep_dispo/get_folder")
    Call<Disposisi_Folder> getDisposisiFolder(@Body Disposisi_Folder params);

    @POST("ep_api_services/ep_dispo/get_dispo_list_by_folder")
    Call<Diposisi_List_Folder> getDisposisiFolder(@Body Diposisi_List_Folder params);

    @POST("ep_api_services/ep_dispo/get_dispo_detail")
    Call<Disposisi_Detail> getDisposisiDetail(@Body Disposisi_Detail params);


    @POST("ep_api_services/ep_dispo/send_dispo_new")
    Call<Diposisi_List_Folder> postSendDisposisi(@Body Diposisi_List_Folder params);

    @POST("ep_api_services/ep_dispo/distribution_dispo")
    Call<Disposisi_Distribusi> postSendDistribusiDispos(@Body Disposisi_Distribusi params);

    @POST("ep_api_services/ep_dispo/get_dispo_attc")
    Call<Disposisi_Attachment> getDisposisiAttachment(@Body Disposisi_Attachment params);

    @POST("ep_api_services/ep_dispo/get_dispo_origin")
    Call<Disposisi_Riwayat> getDisposisiRiwayat(@Body Disposisi_Riwayat params);


    @POST("ep_api_services/ep_dispo/get_side_notif")
    Call<Disposisi_Notifikasi> getNotifikasi(@Body Disposisi_Notifikasi params);

    @POST("ep_api_services/ep_user/get_user_info")
    Call<USER_Info> getUserInfo(@Body USER_Info params);

}
