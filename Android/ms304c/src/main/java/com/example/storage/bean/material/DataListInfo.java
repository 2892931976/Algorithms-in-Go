package com.example.storage.bean.material;

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

    public ArrayList<MaterialteInfo> getData() {
        return data;
    }

    public void setData(ArrayList<MaterialteInfo> data) {
        this.data = data;
    }

    private int total;
    private ArrayList<MaterialteInfo> data;

    public DataListInfo(int total, ArrayList<MaterialteInfo> data) {
        super();
        this.total = total;
        this.data = data;
    }

    public DataListInfo() {
        super();
    }
}
