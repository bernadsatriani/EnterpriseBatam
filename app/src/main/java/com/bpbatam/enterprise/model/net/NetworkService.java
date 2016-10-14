package com.bpbatam.enterprise.model.net;

import com.bpbatam.enterprise.model.ApiGithub;
import com.bpbatam.enterprise.model.AuthUser;
import com.bpbatam.enterprise.model.BBS_CATEGORY;
import com.bpbatam.enterprise.model.BBS_LIST;
import com.bpbatam.enterprise.model.DataAdmin;
import com.bpbatam.enterprise.model.GitHubUser;
import com.bpbatam.enterprise.model.ListUser;
import com.bpbatam.enterprise.model.LocationList;
import com.bpbatam.enterprise.model.UpdateDeviceId;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by User on 20/07/2016.
 */
public interface NetworkService {
    @GET("group/{id}/users")
    Call<List<ApiGithub>> getGroupList(@Path("id") int groupId) ;

    @GET("users/{user}")
    Call<ListUser> getUserList(@Path("user") String user) ;

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

    @POST("ep_api_services/ep_bbs/get_category")
    Call<BBS_CATEGORY> getBBS_Category(@Body BBS_CATEGORY params);

    @POST("ep_api_services/ep_user/update_deviceid")
    Call<UpdateDeviceId> updateDeviceID(@Body UpdateDeviceId params);
}
