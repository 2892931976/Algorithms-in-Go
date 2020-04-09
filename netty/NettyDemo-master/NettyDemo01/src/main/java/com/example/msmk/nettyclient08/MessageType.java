package com.example.msmk.nettyclient08;

/**
 * <p>@ProjectName:     NettyChat</p>
 * <p>@ClassName:       MessageType.java</p>
 * <p>@PackageName:     com.freddy.chat.im</p>
 * <b>
 * <p>@Description:     ��Ϣ����</p>
 * </b>
 * <p>@author:          FreddyChen</p>
 * <p>@date:            2019/04/08 00:04</p>
 * <p>@email:           chenshichao@outlook.com</p>
 */
public enum MessageType {

    /*
     * ������Ϣ
     */
    HANDSHAKE(1001),

    /*
     * ������Ϣ
     */
    HEARTBEAT(1002),

    /*
     * �ͻ����ύ����Ϣ����״̬����
     */
    CLIENT_MSG_RECEIVED_STATUS_REPORT(1009),

    /*
     * ����˷��ص���Ϣ����״̬����
     */
    SERVER_MSG_SENT_STATUS_REPORT(1010),

    /**
     * ������Ϣ
     */
    SINGLE_CHAT(2001),

    /**
     * Ⱥ����Ϣ
     */
    GROUP_CHAT(3001);

    private int msgType;

    MessageType(int msgType) {
        this.msgType = msgType;
    }

    public int getMsgType() {
        return this.msgType;
    }

    public enum MessageContentType {

        /**
         * �ı���Ϣ
         */
        TEXT(101),

        /**
         * ͼƬ��Ϣ
         */
        IMAGE(102),

        /**
         * ������Ϣ
         */
        VOICE(103);

        private int msgContentType;

        MessageContentType(int msgContentType) {
            this.msgContentType = msgContentType;
        }

        public int getMsgContentType() {
            return this.msgContentType;
        }
    }
}
