package com.bpbatam.enterprise.model;

import java.util.List;

public class BBS_LIST {
    public String code ;
    public List<Datum> data ;


    public  String hashid;
    public  String userid;
    public String reqid;
    public String min;
    public String max;

    public BBS_LIST(){}

    public BBS_LIST(String hashid, String userid, String reqid, String min, String max) {
        this.hashid = hashid;
        this.userid = userid;
        this.reqid = reqid;
        this.min = min;
        this.max = max;
    }

    public class AttcData
    {
        public String file_size ;
        public int bbs_id ;
        public String attc_link ;
        public String file_type ;
    }

    public class Datum
    {
        public String content ;
        public String bbs_time ;
        public String title ;
        public String bbs_date ;
        public int bbs_id ;
        public String name ;
        public int attc_count ;
        public String category_id ;
        public String bbs_by ;
        public int total_read ;
        public String read_sts ;
        public List<AttcData> attc_data ;
    }


}
