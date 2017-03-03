package com.bpbatam.enterprise.model;

import java.util.List;

public class Disposisi_Riwayat_Detail {
    public String code ;
    public List<Datum> data ;
    public String hashid;
    public String userid;
    public String reqid;
    public String dispo_id;


    public Disposisi_Riwayat_Detail(){}

    public Disposisi_Riwayat_Detail(String hashid, String userid, String reqid, String dispo_id) {
        this.hashid = hashid;
        this.reqid = reqid;
        this.userid = userid;
        this.dispo_id = dispo_id;
    }

    public class Datum
    {
        public String sender ;
        public String dispo_num ;
        public String create_date ;
        public String receiver ;
        public String create_by ;
        public String isi_dispo ;
        public String create_name ;
        public String dispo_date ;
        public String mail_no ;
        public int dispo_id ;
    }


}
