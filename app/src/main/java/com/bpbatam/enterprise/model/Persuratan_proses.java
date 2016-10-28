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


    public Persuratan_proses(String hashid, String userid, String reqid,String password, String mail_id, String category) {
        this.hashid = hashid;
        this.reqid = reqid;
        this.userid = userid;
        this.password = password;
        this.mail_id = mail_id;

        /*
        "category": "6",
	"expired_date": "2016-09-12",
	"priority": "1",
	"sender": "admin1",
	"mail_date": "2016-10-21",
	"received_date": "2016-10-21",
	"sender_inst": "5",
	"need_approval": "N",
	"content": "test content ",
	"receiver": "",
	"mail_cc": "",
	"retency": "1",
	"related_doc": "3",
	"password": "",
	"deadline": "2016-10-30",
	"create_by":"admin1",
	"approval_rule_id": "3",
	"title": "judul surat"

         */
    }

}
