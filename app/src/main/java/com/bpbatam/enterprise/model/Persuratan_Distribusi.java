package com.bpbatam.enterprise.model;

import java.util.List;

public class Persuratan_Distribusi {
    public String code ;
    public String info ;
    public List<Datum> data ;
    public String hashid;
    public String userid;
    public String password;
    public String reqid;
    public String folder_code;
    public String min;
    public String max;
    public String mail_id;


    public String dead_line;
    public String dispo_num;
    public String priority;
    public String retensi;
    public String related_mail;
    public String related_dispo;
    public String sender;
    public String dispo_date;
    public String mail_no;
    public String mail_date;
    public String receive_date;
    public String about;
    public String receiver;
    public String dispositior;
    public String dispo_category;
    public String create_by;
    public String dispo_parent;
    public String dispo_origin;
    public String dispo_dist;
    public String dispo_cc;
    public String content;

    public String dispo_id;
    public String distribution_to;
    public Persuratan_Distribusi(String hashid, String userid, String reqid, String mail_id,
                                 String distribution_to) {
        this.hashid = hashid;
        this.reqid = reqid;
        this.userid = userid;
        this.mail_id = mail_id;
        this.distribution_to = distribution_to;
    }


    public class Datum
    {
        public String dispo_loc ;
        public String title ;
        public String priority ;
        public String read_date ;
        public String dispo_cc ;
        public String dead_line ;
        public String attc_exist ;
        public int dispo_id ;
        public String read_time ;
        public String dispo_date ;
        public String dispo_by ;
        public String name ;
        public String category ;
        public String dispo_num ;
        public String flag;
    }


}
