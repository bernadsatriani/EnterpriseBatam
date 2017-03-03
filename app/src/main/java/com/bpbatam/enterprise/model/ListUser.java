package com.bpbatam.enterprise.model;

import java.util.List;

/**
 * Created by User on 20/07/2016.
 */
public class ListUser {

    public String code ;
    public List<Datum> data ;

    public String hashid;
    public String userid;
    public String reqid;
    public String keyword;
    public String isfire;

    public ListUser(String hashid, String userid, String reqid, String keyword) {
        this.hashid = hashid;
        this.reqid = reqid;
        this.userid = userid;
        this.keyword = keyword;
    }

    public ListUser(String hashid, String userid, String reqid, String keyword, String isfire) {
        this.hashid = hashid;
        this.reqid = reqid;
        this.userid = userid;
        this.keyword = keyword;
        this.isfire = isfire;
    }


    public class Datum
    {
        public String id_jabatan ;
        public String user_id ;
        public String user_name ;
        public String nama_jabatan ;
    }

}
