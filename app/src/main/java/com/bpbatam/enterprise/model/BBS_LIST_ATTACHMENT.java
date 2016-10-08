package com.bpbatam.enterprise.model;

public class BBS_LIST_ATTACHMENT {
    private String code;
    private Object[][] data;
    private String[] structure;

    public  String hashid;
    public  String userid;
    public String reqid;
    public String bbs_id;

    public BBS_LIST_ATTACHMENT(String hashid, String userid, String reqid, String bbs_id) {
        this.hashid = hashid;
        this.userid = userid;
        this.reqid = reqid;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object[][] getData() {
        return this.data;
    }

    public void setData(Object[][] data) {
        this.data = data;
    }

    public String[] getStructure() {
        return this.structure;
    }

    public void setStructure(String[] structure) {
        this.structure = structure;
    }


}
