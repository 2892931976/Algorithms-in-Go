package com.example.msmk.nettydemo05;


/**
 * Created by XiChuan on 2018-11-07.
 */

import java.util.Arrays;

/**
 * <pre>
 * 自己定义的协议
 *  数据包格式
 * +——----——+——-----——+——----——+
 * |协议开始标志|  消息类型长度  |  消息类型 |    数据长度   |   数据    |
 * +——----——+——-----——+——----——+
 * 1.协议开始标志head_data，为int类型的数据，16进制表示为0X76
 * 2.要传的协议类型长度(String的byte[]长度)
 * 3.要传的协议类型(String)
 * 4.传输数据的长度contentLength，int类型
 * 5.要传输的数据
 * </pre>
 */
public class MessageProtocol {
    /**
     * 消息的开头的信息标志
     */
    private int headData = Constant.HEAD;

    /**
     * 消息类型长度
     */
    private int typeLength;

    /**
     * 消息类型
     */
    private String type;

    /**
     * 消息的长度
     */
    private int contentLength;
    /**
     * 消息的内容
     */
    private byte[] content;

    /**
     * 用于初始化，SmartCarProtocol
     *
     * @param contentLength 协议里面，消息数据的长度
     * @param content       协议里面，消息的数据
     */
    public MessageProtocol(int typeLength, String type, int contentLength, byte[] content) {
        this.typeLength = typeLength;
        this.type = type;
        this.contentLength = contentLength;
        this.content = content;
    }

    public int getHeadData() {
        return headData;
    }

    public void setHeadData(int headData) {
        this.headData = headData;
    }

    public int getTypeLength() {
        return typeLength;
    }

    public void setTypeLength(int typeLength) {
        this.typeLength = typeLength;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getContentLength() {
        return contentLength;
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "MessageProtocol " +
                "[head_data=" + headData
                + ", typeLength=" + typeLength
                + ", type=" + type
                + ", contentLength=" + contentLength
                + ", content=" + Arrays.toString(content) + "]";
    }
}
