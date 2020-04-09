package com.example.msmk.nettyclient08;

import com.example.msmk.nettyclient08.interf.IMSClientInterface;
import com.example.msmk.nettyclient08.protobuf.MessageProtobuf;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.netty.util.internal.StringUtil;

/**
 * <p>@ProjectName:     NettyChat</p>
 * <p>@ClassName:       MsgTimeoutTimerManager.java</p>
 * <p>@PackageName:     com.freddy.im</p>
 * <b>
 * <p>@Description:     ��Ϣ���ͳ�ʱ�����������ڹ�����Ϣ��ʱ�����������Ƴ���</p>
 * </b>
 * <p>@author:          FreddyChen</p>
 * <p>@date:            2019/04/09 22:42</p>
 * <p>@email:           chenshichao@outlook.com</p>
 */
public class MsgTimeoutTimerManager {

    private Map<String, MsgTimeoutTimer> mMsgTimeoutMap = new ConcurrentHashMap<>();
    private IMSClientInterface imsClient;// ims�ͻ���

    public MsgTimeoutTimerManager(IMSClientInterface imsClient) {
        this.imsClient = imsClient;
    }

    /**
     * �����Ϣ�����ͳ�ʱ������
     *
     * @param msg
     */
    public void add(MessageProtobuf.Msg msg) {
        if (msg == null || msg.getHead() == null) {
            return;
        }

        int handshakeMsgType = -1;
        int heartbeatMsgType = -1;
        int clientReceivedReportMsgType = imsClient.getClientReceivedReportMsgType();
        MessageProtobuf.Msg handshakeMsg = imsClient.getHandshakeMsg();
        if (handshakeMsg != null && handshakeMsg.getHead() != null) {
            handshakeMsgType = handshakeMsg.getHead().getMsgType();
        }
        MessageProtobuf.Msg heartbeatMsg = imsClient.getHeartbeatMsg();
        if (heartbeatMsg != null && heartbeatMsg.getHead() != null) {
            heartbeatMsgType = heartbeatMsg.getHead().getMsgType();
        }

        int msgType = msg.getHead().getMsgType();
        // ������Ϣ��������Ϣ���ͻ��˷��ص�״̬������Ϣ�������ط���
        if (msgType == handshakeMsgType || msgType == heartbeatMsgType || msgType == clientReceivedReportMsgType) {
            return;
        }

        String msgId = msg.getHead().getMsgId();
        if (!mMsgTimeoutMap.containsKey(msgId)) {
            MsgTimeoutTimer timer = new MsgTimeoutTimer(imsClient, msg);
            mMsgTimeoutMap.put(msgId, timer);
        }

        System.out.println("�����Ϣ�����ͳ�ʱ��������message=" + msg + "\t��ǰ��������Ϣ����" + mMsgTimeoutMap.size());
    }

    /**
     * �ӷ��ͳ�ʱ���������Ƴ���Ϣ����ֹͣ��ʱ��
     *
     * @param msgId
     */
    public void remove(String msgId) {
        if (StringUtil.isNullOrEmpty(msgId)) {
            return;
        }

        MsgTimeoutTimer timer = mMsgTimeoutMap.remove(msgId);
        MessageProtobuf.Msg msg = null;
        if (timer != null) {
            msg = timer.getMsg();
            timer.cancel();
            timer = null;
        }

        System.out.println("�ӷ�����Ϣ�������Ƴ���Ϣ��message=" + msg);
    }

    /**
     * �����ɹ��ص������������ֳɹ�ʱ���ط���Ϣ���ͳ�ʱ�����������е���Ϣ
     */
    public synchronized void onResetConnected() {
        for(Iterator<Map.Entry<String, MsgTimeoutTimer>> it = mMsgTimeoutMap.entrySet().iterator(); it.hasNext();) {
            it.next().getValue().sendMsg();
        }
    }
}
