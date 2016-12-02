package com.bpbatam.enterprise.model;

import java.util.List;

public class Persuratan_Distribusi_Detail {
    public String code ;
    public String info ;
    public List<Datum> data ;
    public String hashid;
    public String userid;
    public String reqid;


    public String doc_id;
    public String type;
    public Persuratan_Distribusi_Detail(String hashid, String userid, String reqid, String type, String doc_id) {
        this.hashid = hashid;
        this.reqid = reqid;
        this.userid = userid;
        this.type = type;
        this.doc_id = doc_id;
    }


    public class Datum
    {
        public String dist_dept ;
        public String read_time ;
        public String user_id ;
        public String user_name ;
        public String receive_as ;
        public String dist_by ;
        public String dist_name ;
        public String receive_time ;
        public String position ;
        public String department ;
    }


}
