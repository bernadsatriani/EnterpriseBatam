package com.bpbatam.enterprise.model;

import java.util.List;

public class Disposisi_Detail {
    public String code ;
    public List<Datum> data ;
    public String hashid;
    public String userid;
    public String reqid;
    public String dispo_id;


    public Disposisi_Detail(String hashid, String userid, String reqid, String dispo_id) {
        this.hashid = hashid;
        this.reqid = reqid;
        this.userid = userid;
        this.dispo_id = dispo_id;

    }

    public class Datum
    {
        public int dispo_parent ;
        public String receiver ;
        public String about ;
        public int priority ;
        public String related_dispo ;
        public int dispo_origin ;
        public String mail_no ;
        public String content;
        public String mail_date ;
        public String dead_line ;
        public String receive_date ;
        public String create_by ;
        public int dispo_id ;
        public String retensi ;
        public String sender ;
        public String dispo_date ;
        public String related_mail ;
        public String create_date ;
        public String dispositior ;
        public String dispo_num ;

    }


}
