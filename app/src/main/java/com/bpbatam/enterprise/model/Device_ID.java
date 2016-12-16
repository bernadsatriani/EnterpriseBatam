package com.bpbatam.enterprise.model;

import java.util.List;

/**
 * Created by setia.n on 10/12/2016.
 */

public class Device_ID {
    public String code ;
    public List<Datum> data ;

    public  String hashid;
    public  String userid;
    public String reqid;
        public Device_ID(String hashid, String userid, String reqid) {
        this.hashid = hashid;
        this.userid = userid;
        this.reqid = reqid;
    }

    public class Datum
    {
        public String device_id  ;
    }
}
