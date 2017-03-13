package com.bpbatam.enterprise.model;

import java.util.List;

public class Disposisi_Distribusi_Opini {
    public String code ;
    public String info ;
    public List<Datum> data ;
    public String hashid;
    public String userid;
    public String reqid;
    public String content;
    public String dispo_id;

    public Disposisi_Distribusi_Opini(String hashid, String userid, String reqid, String dispo_id,
                                      String content) {
        this.hashid = hashid;
        this.reqid = reqid;
        this.userid = userid;
        this.dispo_id = dispo_id;
        this.content = content;
    }


    public class Datum
    {
        public String dispo_loc ;
        public String title ;

    }


}
