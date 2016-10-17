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

    public class Datum
    {
        public int mail_id ;
        public String category ;
        public String expired_date ;
        public String mail_no ;
        public String priority ;
        public String sender ;
        public String mail_date ;
        public String received_date ;
        public String sender_inst ;
        public String create_time ;
        public String create_by ;
        public String update_time ;
        public String need_approval ;
        public String retency ;
        public String deadline ;
        public int approval_rule_id ;
        public String title ;

    }


}
