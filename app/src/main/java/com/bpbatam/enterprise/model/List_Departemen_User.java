package com.bpbatam.enterprise.model;

import java.util.List;

/**
 * Created by setia.n on 10/12/2016.
 */

public class List_Departemen_User {
    public String code ;
    public List<Datum> data ;

    public  String hashid;
    public  String userid;
    public String reqid;
    public String dept_id;

    public List_Departemen_User(String hashid, String userid, String reqid,
                                String dept_id) {
        this.hashid = hashid;
        this.userid = userid;
        this.reqid = reqid;
        this.dept_id = dept_id;
    }

    public class Datum
    {
        public String user_pos ;
        public String user_gol ;
        public String mobile_tel ;
        public String user_id ;
        public String user_name ;
        public String office_tel ;
        public String personno ;
    }

}
