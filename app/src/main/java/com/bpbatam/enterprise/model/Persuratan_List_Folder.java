package com.bpbatam.enterprise.model;

import java.util.List;

public class Persuratan_List_Folder {
    public String code ;
    public List<Datum> data ;


    public  String hashid;
    public  String userid;
    public String folder;

    public Persuratan_List_Folder(String hashid, String userid, String reqid, String folder) {
        this.hashid = hashid;
        this.userid = userid;
        this.folder = folder;
    }

    public class Datum
    {
        public String attc_exist ;
        public String attc_link ;
        public int bbs_id ;
        public String category_id ;
        public String bbs_date ;
        public String attc_size ;
        public String name ;
        public String read_sts;
        public int attc_count ;
        public String title ;
        public String attc_type ;
    }


}
