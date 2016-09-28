package com.bpbatam.enterprise.model.net;

import com.bpbatam.enterprise.model.ApiGithub;
import com.bpbatam.enterprise.model.GitHubUser;
import com.bpbatam.enterprise.model.ListUser;
import com.bpbatam.enterprise.model.LocationList;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
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

}
