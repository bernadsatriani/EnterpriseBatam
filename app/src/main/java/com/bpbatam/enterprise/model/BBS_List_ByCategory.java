package com.bpbatam.enterprise.model;

import java.util.List;

public class BBS_List_ByCategory {
    public String code ;
    public List<Datum> data ;


    public  String hashid;
    public  String userid;
    public String reqid;
    public String category_id;
    public String min;
    public String max;

    public BBS_List_ByCategory(String hashid, String userid, String reqid, String category_id, String min, String max) {
        this.hashid = hashid;
        this.userid = userid;
        this.reqid = reqid;
        this.category_id = category_id;
        this.min = min;
        this.max = max;
    }

    public String type;
    public BBS_List_ByCategory(String type){
        this.type = type;
    }

    public class Datum
    {
        public String attc_exist ;
        public String attc_link ;
        public int bbs_id ;
        public String category_id ;
        public String bbs_date ;
        public String attc_size ;
        public String name ;
        public String read_sts;
        public int attc_count ;
        public String title ;
        public String attc_type ;
    }


}
