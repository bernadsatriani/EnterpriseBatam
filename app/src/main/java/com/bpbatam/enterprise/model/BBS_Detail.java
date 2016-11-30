package com.bpbatam.enterprise.model;

import java.util.List;

public class BBS_Detail {
    public String code ;
    public List<Datum> data ;


    public  String hashid;
    public  String userid;
    public String reqid;
    public String bbs_id;

    public BBS_Detail(String hashid, String userid, String reqid, String bbs_id) {
        this.hashid = hashid;
        this.userid = userid;
        this.reqid = reqid;
        this.bbs_id = bbs_id;
    }

    public class Datum
    {
        public int priority_id ;
        public int bbs_id ;
        public String create_by ;
        public String create_name ;
        public String end_period ;
        public int attc_count ;
        public String category_id ;
        public String category_name ;
        public String start_period ;
        public String content ;
        public String title ;
        public String name ;
        public int read ;
    }
}
