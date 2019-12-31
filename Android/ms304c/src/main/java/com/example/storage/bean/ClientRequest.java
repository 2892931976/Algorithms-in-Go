package com.example.storage.bean;

/**
 * Created by Administrator on 2019/12/26.
 */

public class ClientRequest {
    private int orderId;
    private int workOrderId;
    private int userId;
    private int materialId;
    private String sn;
    private int type;
    private int boxId;
    private int gridId;
    private int weight;
    private int operation;
    private int openWay;
    private String batchNo;

    public int getOpenWay() {
        return openWay;
    }

    public void setOpenWay(int openWay) {
        this.openWay = openWay;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(int workOrderId) {
        this.workOrderId = workOrderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMaterialId() {
        return materialId;
    }

    public void setMaterialId(int materialId) {
        this.materialId = materialId;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getBoxId() {
        return boxId;
    }

    public void setBoxId(int boxId) {
        this.boxId = boxId;
    }

    public int getGridId() {
        return gridId;
    }

    public void setGridId(int gridId) {
        this.gridId = gridId;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getOperation() {
        return operation;
    }

    public void setOperation(int operation) {
        this.operation = operation;
    }

    public ClientRequest(int orderId, int workOrderId, int userId, int materialId, String sn,int type, int boxId, int gridId, int weight,
                          int operation, int openWay, String batchNo) {
        super();
        this.orderId = orderId;
        this.workOrderId = workOrderId;
        this.userId = userId;
        this.materialId = materialId;
        this.sn = sn;
        this.type = type;
        this.boxId = boxId;
        this.gridId = gridId;
        this.weight = weight;
        this.operation = operation;
        this.openWay = openWay;
        this.batchNo = batchNo;
    }

    public ClientRequest() {
        super();
    }
}
