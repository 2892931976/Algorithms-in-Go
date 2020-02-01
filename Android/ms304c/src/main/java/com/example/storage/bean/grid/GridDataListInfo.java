package com.example.storage.bean.grid;

import java.util.ArrayList;

/**
 * Created by Administrator on 2019/12/12.
 */

public class GridDataListInfo {
    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public ArrayList<GridListInfo> getData() {
        return data;
    }

    public void setData(ArrayList<GridListInfo> data) {
        this.data = data;
    }

    private int total;
    private ArrayList<GridListInfo> data;

    public GridDataListInfo(int total, ArrayList<GridListInfo> data) {
        super();
        this.total = total;
        this.data = data;
    }

    public GridDataListInfo() {
        super();
    }
}
