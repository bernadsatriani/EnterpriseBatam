package com.bpbatam.enterprise.model;

import java.util.List;

public class Persuratan_Detail_CC {
    public String code ;
    public List<Datum> data ;
    public String hashid;
    public String userid;
    public String reqid;
    public String dispo_id;


    public Persuratan_Detail_CC(String hashid, String userid, String reqid, String dispo_id) {
        this.hashid = hashid;
        this.reqid = reqid;
        this.userid = userid;
        this.dispo_id = dispo_id;

    }

    public class Datum
    {
        public String receive_date ;
        public String user_pos ;
        public String user_gol ;
        public String user_id ;
        public String user_name ;
        public String user_dept ;
        public String read_date ;
    }


}
