package com.bpbatam.enterprise.model;

import java.util.List;

/**
 * Created by User on 9/29/2016.
 */

public class AuthUser
{
    public  String code;
    public List<Datum> data;

    public class Datum
    {
        public  String user_name;
        public  String role_id;
        public  String level_id;
        public  String sub_level_id;
        public  String dept_id ;
        public  String create_date;
        public  String nip;
        public  String device_id;
        public  String device_type;
        public  String user_type;
    }
}