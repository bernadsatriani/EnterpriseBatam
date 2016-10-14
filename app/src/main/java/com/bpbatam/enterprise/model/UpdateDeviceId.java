package com.bpbatam.enterprise.model;

/**
 * Created by setia.n on 10/14/2016.
 */

public class UpdateDeviceId {

    public String code ;
    public String data ;

    public  String hashid;
    public  String userid;
    public String reqid;
    public String deviceid;

    public UpdateDeviceId(String hashid, String userid, String reqid, String deviceid) {
        this.hashid = hashid;
        this.userid = userid;
        this.reqid = reqid;
        this.deviceid = deviceid;
    }
}
