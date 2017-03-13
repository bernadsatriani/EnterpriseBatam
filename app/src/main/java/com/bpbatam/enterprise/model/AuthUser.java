package com.bpbatam.enterprise.model;

import java.util.List;

/**
 * Created by User on 9/29/2016.
 */

public class AuthUser
{
    public  String code;
    public  String info;
    public  String hashid;
    public  String userid;
    public  String pass;
    public String reqid;

    public AuthUser(String hashid, String userid, String pass, String reqid) {
        this.hashid = hashid;
        this.userid = userid;
        this.pass = pass;
        this.reqid = reqid;
    }

    public List<Datum> data;

    public class Datum
    {
        public String user_id ;
        public String user_name ;
        public String password ;
        public String role_id ;
        public String level_id ;
        public String position_id ;
        public String id_number ;
        public String device_id ;
        public String device_type ;
        public String user_type ;
        public String join_date ;
        public String nik ;
        public String gender ;
        public String postal_code ;
        public String address1 ;
        public String address2 ;
        public String birth_date ;
        public String ishmrs_user ;

    }
    
    
}