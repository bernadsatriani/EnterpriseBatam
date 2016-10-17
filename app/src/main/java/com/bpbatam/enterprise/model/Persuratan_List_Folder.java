package com.bpbatam.enterprise.model;

import java.util.List;

public class Persuratan_List_Folder {
    public String code ;
    public List<Datum> data ;
    public String hashid;
    public String userid;
    public String reqid;
    public String folder_code;
    public String min;
    public String max;

    public Persuratan_List_Folder(String hashid, String userid, String reqid, String folder_code,
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
        public int mail_id ;
        public String title ;
        public String folder_code ;
        public String deadline ;
        public String read_date ;
        public String mail_date ;
        public int approved_rule_id ;
        public String flag;


    }


}
