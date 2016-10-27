package com.bpbatam.enterprise.model;

public class Persuratan_proses {
    public String code ;
    public String info ;

    public String hashid;
    public String userid;
    public String reqid;
    public String password;
    public String mail_id;

    public Persuratan_proses(String hashid, String userid, String reqid, String mail_id) {
        this.hashid = hashid;
        this.reqid = reqid;
        this.userid = userid;
        this.mail_id = mail_id;
    }

    public Persuratan_proses(String hashid, String userid, String reqid,String password, String mail_id) {
        this.hashid = hashid;
        this.reqid = reqid;
        this.userid = userid;
        this.password = password;
        this.mail_id = mail_id;
    }

}
