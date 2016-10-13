package com.bpbatam.enterprise.model;

/**
 * Created by setia.n on 10/12/2016.
 */

public class BBS_CATEGORY {
    public String code ;
    public Data data ;

    public  String hashid;
    public  String userid;
    public String reqid;

    public BBS_CATEGORY(String hashid, String userid, String reqid) {
        this.hashid = hashid;
        this.userid = userid;
        this.reqid = reqid;
    }

    public class Data
    {
        public String QNQ ;
        public String PDK ;
        public String FRU ;
        public String RUL ;
        public String KDS ;
        public String KSU ;
        public String INB ;
        public String PGU ;
        public String PDB ;
    }
}
