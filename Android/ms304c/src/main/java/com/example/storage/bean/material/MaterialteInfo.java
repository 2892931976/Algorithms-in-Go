package com.example.storage.bean.material;

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

public class MaterialteInfo {

    private int materialId;
    private String created;
    private int createdBy;
    private String materialName;
    private String materialCode;
    private String materialSpec;
    private String tag;
    private String img;
    private int weight;
    private int precision;
    private String code;
    private int status;
    private String updated;
    private int updatedBy;

    public int getMaterialId() {
        return materialId;
    }

    public void setMaterialId(int materialId) {
        this.materialId = materialId;
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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getPrecision() {
        return precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public MaterialteInfo(int materialId, String created, int createdBy, String materialName,
                          String materialCode, String materialSpec, String tag, String img,
                          int weight, int precision, String code, int status, String updated,
                          int updatedBy) {
        super();
        this.materialId = materialId;
        this.created = created;
        this.createdBy = createdBy;
        this.materialName = materialName;
        this.materialCode = materialCode;
        this.materialSpec = materialSpec;
        this.tag = tag;
        this.img = img;
        this.weight = weight;
        this.precision = precision;
        this.code = code;
        this.status = status;
        this.updated = updated;
        this.updatedBy = updatedBy;
    }

    public MaterialteInfo() {
        super();
    }
}
