package com.bpbatam.enterprise.model;

import java.util.List;

public class BBS_Insert {
    public String code ;
    public String info ;
    public String data ;
    public String bbs_id ;

    public  String hashid;
    public  String userid;
    public String reqid;
    public String title;
    public String name;
    public String start_period;
    public String end_period;
    public String content;
    public String bbs_date;
    public String priority_id;
    public String read;
    public String category_id;
    public String create_by;
    public String reply_id;
    /*      "title": "forum123",
            "name": "iwan",
            "start_period": "2016-09-09",
            "end_period": "2016-09-12",
            "content": "content bbs contoh2",
            "bbs_date": "2016-09-20",
            "priority_id": "1",
            "read": "0",
            "category_id": "FRU",
            "create_by":"user2",
            "reply_id":"4",
            "create_time":"2016-09-02 20:09:09"
*/
    public BBS_Insert(String hashid, String userid, String reqid, String title,
                      String name,
                      String start_period,
                      String end_period,
                      String content,
                      String bbs_date,
                      String priority_id,
                      String read,
                      String category_id,
                      String create_by,
                      String reply_id) {
        this.hashid = hashid;
        this.userid = userid;
        this.reqid = reqid;
        this.read = read;
        this.name = name;
        this.title = title;
        this.start_period = start_period;
        this.end_period = end_period;
        this.content = content;
        this.bbs_date = bbs_date;
        this.priority_id = priority_id;
        this.category_id = category_id;
        this.create_by = create_by;
        this.reply_id = reply_id;
    }

    public String attc_link;
    public String file_type;
    public String file_size;
    public BBS_Insert(String hashid, String userid, String reqid, String bbs_id, String attc_link,
                      String file_type, String file_size){
        this.hashid = hashid;
        this.userid = userid;
        this.reqid = reqid;
        this.bbs_id = bbs_id;
        this.attc_link = attc_link;
        this.file_type = file_type;
        this.file_size = file_size;
    }

    public List<File> files;

    public class File
    {
        public String name ;
        public int size ;
        public String type ;
        public String url ;
        public String thumbnailUrl ;
        public String deleteUrl ;
        public String deleteType ;
    }

    //DELETE ATTACHEMTT
    public String link;
    public BBS_Insert(String hashid, String userid, String reqid, String bbs_id, String link){
        this.hashid = hashid;
        this.userid = userid;
        this.reqid = reqid;
        this.bbs_id = bbs_id;
        this.link = link;
    }

    //DELETE ATTACHEMTT
    public BBS_Insert(String hashid, String userid, String reqid, String bbs_id){
        this.hashid = hashid;
        this.userid = userid;
        this.reqid = reqid;
        this.bbs_id = bbs_id;
    }

    //UPDATE BBS
    public BBS_Insert(String hashid, String userid, String reqid, String bbs_id, String title,
                      String name,
                      String start_period,
                      String end_period,
                      String content,
                      String bbs_date,
                      String priority_id,
                      String read,
                      String category_id,
                      String create_by,
                      String reply_id) {
        this.hashid = hashid;
        this.userid = userid;
        this.reqid = reqid;
        this.bbs_id = bbs_id;
        this.read = read;
        this.name = name;
        this.title = title;
        this.start_period = start_period;
        this.end_period = end_period;
        this.content = content;
        this.bbs_date = bbs_date;
        this.priority_id = priority_id;
        this.category_id = category_id;
        this.create_by = create_by;
        this.reply_id = reply_id;
    }
}

