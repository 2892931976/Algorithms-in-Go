package com.example.storage.bean.grid;

public class GridInfo {
    public int image;
    private int gridId;
    private String created;
    private int createdBy;
    private int boxId;
    private int sensorId;
    private String gridName;
    private int boxChannel;
    private int lockChannel;
    private int channel;
    private int fullscale;
    private int precision;
    private String img;
    private int status;
    private String updated;
    private int updatedBy;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getGridId() {
        return gridId;
    }

    public void setGridId(int gridId) {
        this.gridId = gridId;
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

    public int getBoxId() {
        return boxId;
    }

    public void setBoxId(int boxId) {
        this.boxId = boxId;
    }

    public int getSensorId() {
        return sensorId;
    }

    public void setSensorId(int sensorId) {
        this.sensorId = sensorId;
    }

    public String getGridName() {
        return gridName;
    }

    public void setGridName(String gridName) {
        this.gridName = gridName;
    }

    public int getBoxChannel() {
        return boxChannel;
    }

    public void setBoxChannel(int boxChannel) {
        this.boxChannel = boxChannel;
    }

    public int getLockChannel() {
        return lockChannel;
    }

    public void setLockChannel(int lockChannel) {
        this.lockChannel = lockChannel;
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public int getFullscale() {
        return fullscale;
    }

    public void setFullscale(int fullscale) {
        this.fullscale = fullscale;
    }

    public int getPrecision() {
        return precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
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

    public GridInfo(String boxName) {
        this.gridName = boxName;
    }

    public GridInfo(int boxId, String created, int createdBy, int sysBoxId, String gridName,
                        int boxChannel, int lockChannel, int channel, int status, int fullscale, int precision, String img, String updated, int updatedBy) {
        this.boxId = boxId;
        this.created = created;
        this.createdBy = createdBy;
        this.sensorId = sysBoxId;
        this.gridName = gridName;
        this.boxChannel = boxChannel;
        this.lockChannel = lockChannel;
        this.channel = channel;
        this.fullscale = fullscale;
        this.precision = precision;
        this.img = img;
        this.status = status;
        this.updated = updated;
        this.updatedBy = updatedBy;
    }

}
