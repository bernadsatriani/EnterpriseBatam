package com.bpbatam.enterprise.model;

/**
 * Created by User on 10/8/2016.
 */

public class BBS_LIST_Data {
    private int bbs_id = 0;
    private String title = "";
    private String name = "";
    private String bbs_date = "";
    private String category_id = "";

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getBbs_date() {
        return bbs_date;
    }

    public void setBbs_date(String bbs_date) {
        this.bbs_date = bbs_date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getBbs_id() {
        return bbs_id;
    }

    public void setBbs_id(int bbs_id) {
        this.bbs_id = bbs_id;
    }

    private String description;

}
