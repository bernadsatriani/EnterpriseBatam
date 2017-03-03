package com.bpbatam.enterprise.model;

import java.util.List;

public class Disposisi_Riwayat {
    public String code ;
    public List<Datum> data ;
    public String hashid;
    public String userid;
    public String reqid;


    public Disposisi_Riwayat(){}

    public Disposisi_Riwayat(String hashid, String userid, String reqid) {
        this.hashid = hashid;
        this.reqid = reqid;
        this.userid = userid;
    }

    public class Datum
    {
        public String dispo_origin_name ;
        public String dispo_num ;
        public int dispo_origin ;
        public String dispo_by ;
        public String dead_line ;
        public String dispo_loc ;
        public String read_time ;
        public String dispo_cc ;
        public String title ;
        public String read_date ;
        public String category ;
        public String priority ;
        public String dispo_date ;
        public String name ;
        public int dispo_id ;
        public String attc_exist ;
        public String attach_link;
        public String file_size;
        public String file_type;
        public String create_name;
    }


}
