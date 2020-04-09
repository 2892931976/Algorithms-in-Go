package com.example.msmk.nettyclient08.interf;

import com.example.msmk.nettyclient08.MsgDispatcher;
import com.example.msmk.nettyclient08.MsgTimeoutTimerManager;
import com.example.msmk.nettyclient08.listener.IMSConnectStatusCallback;
import com.example.msmk.nettyclient08.listener.OnEventListener;
import com.example.msmk.nettyclient08.protobuf.MessageProtobuf;

import java.util.Vector;

/**
 * <p>@ProjectName:     NettyChat</p>
 * <p>@ClassName:       IMSClientInterface.java</p>
 * <p>@PackageName:     com.freddy.im.interf</p>
 * <b>
 * <p>@Description:     ims����ӿڣ���Ҫ�л���������ʽʵ��im���ܣ�ʵ�ִ˽ӿڼ���</p>
 * </b>
 * <p>@author:          FreddyChen</p>
 * <p>@date:            2019/03/31 20:04</p>
 * <p>@email:           chenshichao@outlook.com</p>
 */
public interface IMSClientInterface {

    /**
     * ��ʼ��
     *
     * @param serverUrlList ��������ַ�б�
     * @param listener      ��Ӧ�ò㽻����listener
     * @param callback      ims����״̬�ص�
     */
    void init(Vector<String> serverUrlList, OnEventListener listener, IMSConnectStatusCallback callback);

    /**
     * �������ӣ�Ҳ��������
     * �״�����Ҳ����Ϊ������
     */
    void resetConnect();

    /**
     * �������ӣ�Ҳ��������
     * �״�����Ҳ����Ϊ������
     * ����
     *
     * @param isFirst �Ƿ��״�����
     */
    void resetConnect(boolean isFirst);

    /**
     * �ر����ӣ�ͬʱ�ͷ���Դ
     */
    void close();

    /**
     * ��ʶims�Ƿ��ѹر�
     *
     * @return
     */
    boolean isClosed();

    /**
     * ������Ϣ
     *
     * @param msg
     */
    void sendMsg(MessageProtobuf.Msg msg);

    /**
     * ������Ϣ
     * ����
     *
     * @param msg
     * @param isJoinTimeoutManager �Ƿ���뷢�ͳ�ʱ������
     */
    void sendMsg(MessageProtobuf.Msg msg, boolean isJoinTimeoutManager);

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
     * ��ȡӦ���ں�̨ʱ�������ʱ��
     *
     * @return
     */
    int getBackgroundHeartbeatInterval();

    /**
     * ����appǰ��̨״̬
     *
     * @param appStatus
     */
    void setAppStatus(int appStatus);

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

    /**
     * ��ȡ��Ϣת����
     *
     * @return
     */
    MsgDispatcher getMsgDispatcher();

    /**
     * ��ȡ��Ϣ���ͳ�ʱ��ʱ��
     *
     * @return
     */
    MsgTimeoutTimerManager getMsgTimeoutTimerManager();
}
