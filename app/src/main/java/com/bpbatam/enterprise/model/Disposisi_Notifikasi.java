package com.bpbatam.enterprise.model;

import java.util.List;

public class Disposisi_Notifikasi {
    public String code ;
    public List<Datum> data ;
    public String hashid;
    public String userid;
    public String reqid;



    public Disposisi_Notifikasi(String hashid, String userid, String reqid) {
        this.hashid = hashid;
        this.reqid = reqid;
        this.userid = userid;
    }

    public class Datum
    {
        public int notif_id ;
        public String title ;
        public String receive_as ;
        public String location ;
        public String sender_id ;
        public String sender_name ;
        public String receive_date ;
        public String notif_type ;
    }


}
