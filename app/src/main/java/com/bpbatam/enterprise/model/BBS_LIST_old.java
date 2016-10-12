package com.bpbatam.enterprise.model;

import java.util.Objects;

public class BBS_LIST_old {
    private String code;
    private Object[][] data;
    private String[] structure;

    public  String hashid;
    public  String userid;
    public String reqid;

    public BBS_LIST_old(String hashid, String userid, String reqid) {
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
