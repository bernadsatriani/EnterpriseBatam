package com.bpbatam.enterprise.model;

import java.util.List;

public class Diposisi_List_Folder {
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

    public Diposisi_List_Folder(){}

    public Diposisi_List_Folder(String hashid, String userid, String reqid, String password,
                                String dead_line, String dispo_num, String priority,
                                String retensi, String related_mail, String related_dispo,
                                String sender, String dispo_date, String mail_no, String mail_date,
                                String receive_date, String about, String receiver,
                                String dispositior, String dispo_category, String create_by,
                                String dispo_parent, String dispo_origin, String content, String dispo_dist,
                                String dispo_cc) {
        this.hashid = hashid;
        this.reqid = reqid;
        this.userid = userid;
        this.password = password;
        this.dead_line = dead_line;
        this.dispo_num = dispo_num;
        this.priority = priority;
        this.retensi = retensi;
        this.related_mail = related_mail;
        this.related_dispo = related_dispo;
        this.sender = sender;
        this.dispo_date = dispo_date;
        this.mail_no = mail_no;
        this.mail_date = mail_date;
        this.receive_date = receive_date;
        this.about = about;
        this.receiver = receiver;
        this.dispositior = dispositior;
        this.dispo_category = dispo_category;
        this.create_by = create_by;
        this.dispo_parent = dispo_parent;
        this.dispo_origin = dispo_origin;
        this.content = content;
        this.dispo_dist = dispo_dist;
        this.dispo_cc = dispo_cc;
    }

    public Diposisi_List_Folder(String hashid, String userid, String reqid, String mail_id) {
        this.hashid = hashid;
        this.reqid = reqid;
        this.userid = userid;
        this.mail_id = mail_id;
    }

    public String dispo_id;
    public String distribution_to;
    public Diposisi_List_Folder(String hashid, String userid, String reqid, String dispo_id,
                                String distribution_to) {
        this.hashid = hashid;
        this.reqid = reqid;
        this.userid = userid;
        this.dispo_id = dispo_id;
        this.distribution_to = distribution_to;
    }

    public Diposisi_List_Folder(String hashid, String userid, String reqid, String folder_code,
                                String min, String max) {
        this.hashid = hashid;
        this.reqid = reqid;
        this.userid = userid;
        this.folder_code = folder_code;
        this.min = min;
        this.max = max;
    }

    public class Datum
    {
        public String dispo_loc ;
        public String title ;
        public String priority ;
        public int dispo_origin ;
        public String read_date ;
        public String dispo_cc ;
        public String mail_date ;
        public String dead_line ;
        public String dispo_origin_name ;
        //public List<object> distribusi_detail ;
        public String attc_exist ;
        public int dispo_id ;
        public String read_time ;
        public List<CcDetail> cc_detail ;
        public String dispo_date ;
        public String dispo_by ;
        public String receive_as ;
        public String name ;
        public String category ;
        public String dispo_num ;

        public String attach_link;
        public String file_size;
        public String file_type;
        public String flag;
    }

    public class CcDetail
    {
        public String receive_date ;
        public String user_id ;
        public String user_name ;
        public String deptartement ;
        public String position ;
        public String read_date ;
    }

}
