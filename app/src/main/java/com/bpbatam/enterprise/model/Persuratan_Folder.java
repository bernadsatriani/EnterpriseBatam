package com.bpbatam.enterprise.model;

import java.util.List;

public class Persuratan_Folder {
    public String code ;
    public List<Datum> data ;
    public String hashid;
    public String userid;
    public String reqid;
    


    public Persuratan_Folder(String hashid, String userid, String reqid) {
        this.hashid = hashid;
        this.reqid = reqid;
        this.userid = userid;
    }

    public class Datum
    {
        public int unread_count ;
        public int total_count ;
        public String folder_name ;
        public String folder_code ;
    }


}
