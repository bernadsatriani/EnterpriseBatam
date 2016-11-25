package com.bpbatam.enterprise.model;

import java.util.List;

/**
 * Created by setia.n on 10/12/2016.
 */

public class BBS_CATEGORY {
    public String code ;
    public List<Datum> data ;

    public  String hashid;
    public  String userid;
    public String reqid;

    public BBS_CATEGORY(String hashid, String userid, String reqid) {
        this.hashid = hashid;
        this.userid = userid;
        this.reqid = reqid;
    }

    public class Datum
    {
        public String code;
        public String description;
    }

}
