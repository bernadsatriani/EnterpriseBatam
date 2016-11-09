package com.bpbatam.enterprise.model;

import java.util.List;

public class BBS_Attachment {
    public String code ;
    public List<Datum> data ;


    public  String hashid;
    public  String userid;
    public String reqid;
    public String bbs_id;

    public BBS_Attachment(String hashid, String userid, String reqid, String bbs_id) {
        this.hashid = hashid;
        this.userid = userid;
        this.reqid = reqid;
        this.bbs_id = bbs_id;
    }

    public class Datum
    {
        public String attc_link ;
        public int bbs_id ;
        public String file_type ;
        public String file_size ;
    }
}
