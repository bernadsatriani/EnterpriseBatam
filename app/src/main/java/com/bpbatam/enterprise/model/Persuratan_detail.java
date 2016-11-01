package com.bpbatam.enterprise.model;

import java.util.List;

public class Persuratan_Detail {
    public String code ;
    public List<Datum> data ;
    public String hashid;
    public String userid;
    public String reqid;
    public String mail_id;


    public Persuratan_Detail(String hashid, String userid, String reqid, String mail_id) {
        this.hashid = hashid;
        this.reqid = reqid;
        this.userid = userid;
        this.mail_id = mail_id;
    }

    public class ApprovalState
    {
        public String user_id ;
        public String user_name ;
        public String deptartement ;
        public String position ;
        public String reject_date ;
        public String recall_date ;
        public String approve_date ;
    }
    
    public class Datum
    {
        public String user_pos ;
        public String category_name ;
        public String user_name ;
        public String type ;
        public String title ;
        public String user_dept ;
        public String content ;
        public String create_by ;
        public String approval_user ;
        public String deadline ;
        public int mail_id ;
        public String create_time ;
        public String receiver ;
        public String priority ;
        public String expired_date ;
        public String mail_date ;
        public String mail_cc ;
        public String receive_date ;
        public String retency ;
        public String sender ;
        public String category ;
        public List<ApprovalState> approval_state ;

    }


}
