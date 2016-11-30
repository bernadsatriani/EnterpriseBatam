package com.bpbatam.enterprise.model;

import java.util.List;

public class Disposisi_Attachment {
    public String code ;
    public List<Datum> data ;
    public String info ;

    public String hashid;
    public String userid;
    public String reqid;
    public String dispo_id;


    public Disposisi_Attachment(String hashid, String userid, String reqid, String dispo_id) {
        this.hashid = hashid;
        this.reqid = reqid;
        this.userid = userid;
        this.dispo_id = dispo_id;
    }

    public class Datum
    {
        public String attc_link ;
        public int dispo_id ;
        public String file_type ;
        public String file_size ;

    }


}
