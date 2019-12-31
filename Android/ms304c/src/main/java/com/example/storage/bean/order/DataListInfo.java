package com.example.storage.bean.order;

import java.util.ArrayList;

/**
 * Created by Administrator on 2019/12/12.
 */

public class DataListInfo {
    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public ArrayList<OrderDetailInfo> getData() {
        return data;
    }

    public void setData(ArrayList<OrderDetailInfo> data) {
        this.data = data;
    }

    private int total;
    private ArrayList<OrderDetailInfo> data;

    public DataListInfo(int total, ArrayList<OrderDetailInfo> data) {
        super();
        this.total = total;
        this.data = data;
    }

    public DataListInfo() {
        super();
    }
}
