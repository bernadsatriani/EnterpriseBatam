package com.bpbatam.enterprise.model;

import java.util.List;

public class BBS_Insert {
    public String code ;
    public String info ;
    public String data ;

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

}
