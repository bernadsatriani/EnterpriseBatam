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
        //public List<object> attc_data ;
        public String about ;
        public String pdf_link ;
        public String dispo_category ;
        public int dispo_origin ;
        public String mail_no ;
        public String content ;
        public String create_by ;
        public String retensi ;
        public String dispo_date ;
        //public List<object> opini_data ;
        public String receiver ;
        public String create_time ;
        public String create_dept ;
        public int priority ;
        public String related_dispo ;
        public String mail_date ;
        public String receive_date ;
        public int dispo_id ;
        public String sender ;
        public String related_mail ;
        public String dispositior ;
        public String dispo_num ;
        public String create_name ;

    }


}
