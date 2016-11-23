package com.bpbatam.enterprise.model;

import java.util.List;

/**
 * Created by setia.n on 10/12/2016.
 */

public class USER_Info {
    public String code ;
    public List<Datum> data ;
    
    public  String hashid;
    public  String userid;
    public String reqid;
    public  String user_id;
    public USER_Info(String hashid, String userid, String reqid, String user_id) {
        this.hashid = hashid;
        this.userid = userid;
        this.reqid = reqid;
        this.user_id = user_id;
    }

    public class Datum
    {
        public String user_id ;
        public String user_name ;
        public String dept_name ;
        public String job_level ;
    }
}
