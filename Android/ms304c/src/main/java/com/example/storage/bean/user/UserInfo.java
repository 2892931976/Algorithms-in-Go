package com.example.storage.bean.user;

/**
 * Created by Administrator on 2019/12/6.
 */

//type Role struct {
//        Id        int64  `orm:"column(id);auto;pk" json:"id"`
//        Created   string `orm:"column(created)" json:"created"`
//        CreatedBy int64  `orm:"column(created_by)" json:"createdBy"`
//        Name      string `orm:"column(name);unique" json:"name"`
//        Status    int8   `orm:"column(status); default(1)" json:"status"`
//        Updated   string `orm:"column(updated)" json:"updated"`
//        UpdatedBy int64  `orm:"column(updated_by)" json:"updatedBy"`
//    	Name         string `orm:"column(name)" json:"materialName"`
//                MaterialCode string `orm:"column(material_code)" json:"materialCode"`
//                MaterialSpec string `orm:"column(material_spec)" json:"materialSpec"`
//        }

public class UserInfo {
    private int id;
    private String created;
    private int createdBy;
    private String name;
    private String username;
    private String passwd;
    private String card;
    private String finger;
    private int status;
    private String updated;
    private int updatedBy;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getFinger() {
        return finger;
    }

    public void setFinger(String finger) {
        this.finger = finger;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public int getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(int updatedBy) {
        this.updatedBy = updatedBy;
    }

    public UserInfo(int id, String created, int createdBy, String name, String username, String passwd, String card, String finger,int status, String updated,
                          int updatedBy) {
        super();
        this.id = id;
        this.created = created;
        this.createdBy = createdBy;
        this.name = name;
        this.username = username;
        this.passwd = passwd;
        this.card = card;
        this.finger = finger;
        this.status = status;
        this.updated = updated;
        this.updatedBy = updatedBy;
    }

    public UserInfo() {
        super();
    }
}
