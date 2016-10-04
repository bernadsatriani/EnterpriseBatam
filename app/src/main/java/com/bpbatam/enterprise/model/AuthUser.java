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

    public AuthUser(String hashid, String userid, String pass) {
        this.hashid = hashid;
        this.userid = userid;
        this.pass = pass;
    }

    public List<Datum> data;

    public class Datum
    {
        public String userId ;
        public String userName ;
        public String roleId ;
        public String levelId ;
        public String positionId ;
        public String idNumber ;
        public String deviceId ;
        public String deviceType ;
        public String userType ;
        public String joinDate ;
        public String nik ;
        public String gender ;
        public String postalCode ;
        public String address1 ;
        public String address2 ;
        public String birthDate ;
        public String ishmrsUser ;
    }
    
    
}