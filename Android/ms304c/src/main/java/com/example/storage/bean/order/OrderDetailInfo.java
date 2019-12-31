package com.example.storage.bean.order;

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

public class OrderDetailInfo {
    private int id;
    private String created;
    private int createdBy;
    private String materialName;
    private String materialCode;
    private String materialSpec;
    private String name;
    private int qty;
    private int status;
    private String updated;
    private int updatedBy;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public String getMaterialSpec() {
        return materialSpec;
    }

    public void setMaterialSpec(String materialSpec) {
        this.materialSpec = materialSpec;
    }

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

    public OrderDetailInfo(int id, String created, int createdBy, String materialName, String materialCode, String materialSpec, String name, int qty, int status, String updated,
                           int updatedBy) {
        super();
        this.id = id;
        this.created = created;
        this.createdBy = createdBy;
        this.materialName = materialName;
        this.materialCode = materialCode;
        this.materialSpec = materialSpec;
        this.name = name;
        this.qty = qty;
        this.status = status;
        this.updated = updated;
        this.updatedBy = updatedBy;
    }

    public OrderDetailInfo() {
        super();
    }
}
