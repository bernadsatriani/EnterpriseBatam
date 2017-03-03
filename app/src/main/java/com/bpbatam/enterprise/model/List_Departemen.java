package com.bpbatam.enterprise.model;

import java.util.List;

/**
 * Created by setia.n on 10/12/2016.
 */

public class List_Departemen {
    public String code ;
    public List<Datum> data ;

    public  String hashid;
    public  String userid;
    public String reqid;

    public List_Departemen(String hashid, String userid, String reqid) {
        this.hashid = hashid;
        this.userid = userid;
        this.reqid = reqid;
    }

    public class Datum
    {
        public String parent_id ;
        public String dept_name ;
        public String dept_id ;
    }

}
