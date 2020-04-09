package com.example.msmk.nettyclient08.bean;

/**
 * <p>@ProjectName:     NettyChat</p>
 * <p>@ClassName:       AppMessage.java</p>
 * <p>@PackageName:     com.freddy.chat.bean</p>
 * <b>
 * <p>@Description:     App��Ϣ�����ڰ�protobuf��Ϣת����app���õ���Ϣ����</p>
 * </b>
 * <p>@author:          FreddyChen</p>
 * <p>@date:            2019/04/10 00:01</p>
 * <p>@email:           chenshichao@outlook.com</p>
 */
public class AppMessage {

    private Head head;  // ��Ϣͷ
    private String body;// ��Ϣ��

    public Head getHead() {
        return head;
    }

    public void setHead(Head head) {
        this.head = head;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "AppMessage{" +
                "head=" + head +
                ", body='" + body + '\'' +
                '}';
    }
}
