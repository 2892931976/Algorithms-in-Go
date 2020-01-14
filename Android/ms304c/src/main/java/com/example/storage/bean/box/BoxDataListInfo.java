package com.example.storage.bean.box;

import java.util.ArrayList;

/**
 * Created by Administrator on 2019/12/12.
 */

public class BoxDataListInfo {
    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public ArrayList<BoxInfo> getData() {
        return data;
    }

    public void setData(ArrayList<BoxInfo> data) {
        this.data = data;
    }

    private int total;
    private ArrayList<BoxInfo> data;

    public BoxDataListInfo(int total, ArrayList<BoxInfo> data) {
        super();
        this.total = total;
        this.data = data;
    }

    public BoxDataListInfo() {
        super();
    }
}
