package com.example.storage.bean.grid;

import com.example.storage.R;

import java.util.ArrayList;

public class GridListInfo {
    public int image;
    private int boxId;
    private String created;
    private int createdBy;
    private int sysBoxId;
    private String tag;
    private String boxName;
    private int row;
    private int cell;
    private int status;
    private String updated;
    private int updatedBy;
    private ArrayList<GridInfo> gridResponses;

    public ArrayList<GridInfo> getGridResponses() {
        return gridResponses;
    }

    public void setGridResponses(ArrayList<GridInfo> gridResponses) {
        this.gridResponses = gridResponses;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getBoxId() {
        return boxId;
    }

    public void setBoxId(int boxId) {
        this.boxId = boxId;
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

    public int getSysBoxId() {
        return sysBoxId;
    }

    public void setSysBoxId(int sysBoxId) {
        this.sysBoxId = sysBoxId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getBoxName() {
        return boxName;
    }

    public void setBoxName(String boxName) {
        this.boxName = boxName;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCell() {
        return cell;
    }

    public void setCell(int cell) {
        this.cell = cell;
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

    public static int[] getIconArray() {
        return iconArray;
    }

    public static void setIconArray(int[] iconArray) {
        GridListInfo.iconArray = iconArray;
    }

    public static String[] getNameArray() {
        return nameArray;
    }

    public static void setNameArray(String[] nameArray) {
        GridListInfo.nameArray = nameArray;
    }

    public static String[] getDescArray() {
        return descArray;
    }

    public static void setDescArray(String[] descArray) {
        GridListInfo.descArray = descArray;
    }

    public GridListInfo(String boxName, String tag) {
        this.boxName = boxName;
        this.tag = tag;
    }

    public GridListInfo(int boxId, String created, int createdBy, int sysBoxId, String tag,
                   String boxName, int row, int cell, int status, String updated, int updatedBy) {
        this.boxId = boxId;
        this.created = created;
        this.createdBy = createdBy;
        this.sysBoxId = sysBoxId;
        this.created = created;
        this.tag = tag;
        this.boxName = boxName;
        this.row = row;
        this.cell = cell;
        this.status = status;
        this.updated = updated;
        this.updatedBy = updatedBy;
    }

    private static int[] iconArray = {R.drawable.shuixing, R.drawable.jinxing, R.drawable.diqiu,
            R.drawable.huoxing, R.drawable.muxing, R.drawable.tuxing};
    private static String[] nameArray = {"水星", "金星", "地球", "火星", "木星", "土星"};
    private static String[] descArray = {
            "水星是太阳系八大行星最内侧也是最小的一颗行星，也是离太阳最近的行星",
            "金星是太阳系八大行星之一，排行第二，距离太阳0.725天文单位",
            "地球是太阳系八大行星之一，排行第三，也是太阳系中直径、质量和密度最大的类地行星，距离太阳1.5亿公里",
            "火星是太阳系八大行星之一，排行第四，属于类地行星，直径约为地球的53%",
            "木星是太阳系八大行星中体积最大、自转最快的行星，排行第五。它的质量为太阳的千分之一，但为太阳系中其它七大行星质量总和的2.5倍",
            "土星为太阳系八大行星之一，排行第六，体积仅次于木星"
    };

    public static ArrayList<GridListInfo> getDefaultList() {
        ArrayList<GridListInfo> planetList = new ArrayList<GridListInfo>();
        for (int i = 0; i < iconArray.length; i++) {
            planetList.add(new GridListInfo(nameArray[i], descArray[i]));
        }
        return planetList;
    }
}
