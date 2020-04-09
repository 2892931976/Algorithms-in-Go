package com.example.msmk.nettyclient08.bean;

import com.example.msmk.nettyclient08.utils.StringUtil;

/**
 * <p>@ProjectName:     NettyChat</p>
 * <p>@ClassName:       BaseMessage.java</p>
 * <p>@PackageName:     com.freddy.chat.bean</p>
 * <b>
 * <p>@Description:     ��Ϣ����</p>
 * </b>
 * <p>@author:          FreddyChen</p>
 * <p>@date:            2019/04/10 00:02</p>
 * <p>@email:           chenshichao@outlook.com</p>
 */
public class BaseMessage {

    protected String msgId;       // ��Ϣid
    protected int msgType;        // ��Ϣ����
    protected int msgContentType; // ��Ϣ��������
    protected String fromId;      // ������id
    protected String toId;        // ������id
    protected long timestamp;     // ��Ϣʱ���
    protected int statusReport;   // ��Ϣ״̬����
    protected String extend;      // ��չ�ֶΣ���key/value��ʽ���json
    protected String content;     // ��Ϣ����

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public int getMsgContentType() {
        return msgContentType;
    }

    public void setMsgContentType(int msgContentType) {
        this.msgContentType = msgContentType;
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatusReport() {
        return statusReport;
    }

    public void setStatusReport(int statusReport) {
        this.statusReport = statusReport;
    }

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int hashCode() {
        try {
            return this.msgId.hashCode();
        }catch (NullPointerException e) {
            e.printStackTrace();
        }

        return 1;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null || !(obj instanceof BaseMessage)) {
            return false;
        }

        return StringUtil.equals(this.msgId, ((BaseMessage) obj).getMsgId());
    }
}
