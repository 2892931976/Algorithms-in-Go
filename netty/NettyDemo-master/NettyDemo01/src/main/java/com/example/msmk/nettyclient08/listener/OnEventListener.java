package com.example.msmk.nettyclient08.listener;

import com.example.msmk.nettyclient08.protobuf.MessageProtobuf;

/**
 * <p>@ProjectName:     NettyChat</p>
 * <p>@ClassName:       OnEventListener.java</p>
 * <p>@PackageName:     com.freddy.im.listener</p>
 * <b>
 * <p>@Description:     ��Ӧ�ò㽻����listener</p>
 * </b>
 * <p>@author:          FreddyChen</p>
 * <p>@date:            2019/03/31 20:06</p>
 * <p>@email:           chenshichao@outlook.com</p>
 */
public interface OnEventListener {

    /**
     * �ַ���Ϣ��Ӧ�ò�
     *
     * @param msg
     */
    void dispatchMsg(MessageProtobuf.Msg msg);

    /**
     * ��Ӧ�ò��ȡ�����Ƿ����
     *
     * @return
     */
    boolean isNetworkAvailable();

    /**
     * ��ȡ�������ʱ��
     *
     * @return
     */
    int getReconnectInterval();

    /**
     * ��ȡ���ӳ�ʱʱ��
     *
     * @return
     */
    int getConnectTimeout();

    /**
     * ��ȡӦ����ǰ̨ʱ�������ʱ��
     *
     * @return
     */
    int getForegroundHeartbeatInterval();

    /**
     * ��ȡӦ����ǰ̨ʱ�������ʱ��
     *
     * @return
     */
    int getBackgroundHeartbeatInterval();

    /**
     * ��ȡ��Ӧ�ò㹹���������Ϣ
     *
     * @return
     */
    MessageProtobuf.Msg getHandshakeMsg();

    /**
     * ��ȡ��Ӧ�ò㹹���������Ϣ
     *
     * @return
     */
    MessageProtobuf.Msg getHeartbeatMsg();

    /**
     * ��ȡӦ�ò���Ϣ����״̬������Ϣ����
     *
     * @return
     */
    int getServerSentReportMsgType();

    /**
     * ��ȡӦ�ò���Ϣ����״̬������Ϣ����
     *
     * @return
     */
    int getClientReceivedReportMsgType();

    /**
     * ��ȡӦ�ò���Ϣ���ͳ�ʱ�ط�����
     *
     * @return
     */
    int getResendCount();

    /**
     * ��ȡӦ�ò���Ϣ���ͳ�ʱ�ط����
     *
     * @return
     */
    int getResendInterval();
}
