package com.bpbatam.enterprise.model;

import java.util.List;

/**
 * Created by setia.n on 10/27/2016.
 */

public class DISPOSISI_Category {
    public String code ;
    public List<Datum> data ;

    public  String hashid;
    public  String userid;
    public String reqid;

    public DISPOSISI_Category(String hashid, String userid, String reqid) {
        this.hashid = hashid;
        this.userid = userid;
        this.reqid = reqid;
    }

    public class Datum
    {
        public String id ;
        public String shortName ;
        public String description ;
        public String createBy ;
        public String createTime ;
    }
}
