package com.bpbatam.enterprise.model;

import java.util.List;

/**
 * Created by setia.n on 10/12/2016.
 */

public class BBS_Opini {
    public String code ;
    public List<Datum> data;

    public  String hashid;
    public  String userid;
    public String reqid;
    public String bbs_id;
    public String content;

    public BBS_Opini(String hashid, String userid, String reqid, String bbs_id) {
        this.hashid = hashid;
        this.userid = userid;
        this.reqid = reqid;
        this.bbs_id = bbs_id;
    }

    public BBS_Opini(String hashid, String userid, String reqid, String bbs_id,
                     String content) {
        this.hashid = hashid;
        this.userid = userid;
        this.reqid = reqid;
        this.bbs_id = bbs_id;
        this.content = content;
    }

    public class Datum
    {
        public String content;
        public int id;
        public String user_name;
        public String create_date;
        public int bbs_id;
        public String create_time;
        public String user_id;
    }
}
