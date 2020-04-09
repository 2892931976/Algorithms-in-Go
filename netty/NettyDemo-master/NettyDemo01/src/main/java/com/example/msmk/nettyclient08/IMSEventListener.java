package com.example.msmk.nettyclient08;

//import android.content.Context;
//import android.net.ConnectivityManager;
//import android.net.NetworkInfo;

import com.alibaba.fastjson.JSONObject;
//import com.freddy.chat.NettyChatApp;
import com.example.msmk.nettyclient08.listener.OnEventListener;
import com.example.msmk.nettyclient08.protobuf.MessageProtobuf;

import java.util.UUID;

/**
 * <p>@ProjectName:     NettyChat</p>
 * <p>@ClassName:       IMSEventListener.java</p>
 * <p>@PackageName:     com.freddy.chat.im</p>
 * <b>
 * <p>@Description:     ��ims������listener</p>
 * </b>
 * <p>@author:          FreddyChen</p>
 * <p>@date:            2019/04/07 23:55</p>
 * <p>@email:           chenshichao@outlook.com</p>
 */
public class IMSEventListener implements OnEventListener {

    private String userId;
    private String token;

    public IMSEventListener(String userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    /**
     * ����imsת����������Ϣ
     *
     * @param msg
     */
    @Override
    public void dispatchMsg(MessageProtobuf.Msg msg) {
        MessageProcessor.getInstance().receiveMsg(MessageBuilder.getMessageByProtobuf(msg));
    }

    /**
     * �����Ƿ����
     *
     * @return
     */
    @Override
    public boolean isNetworkAvailable() {
//        ConnectivityManager cm = (ConnectivityManager) NettyChatApp.sharedInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo info = cm.getActiveNetworkInfo();
//        return info != null && info.isConnected();
        return true;
    }

    /**
     * ����ims�������ʱ����0��ʾĬ��ʹ��ims��ֵ
     *
     * @return
     */
    @Override
    public int getReconnectInterval() {
        return 0;
    }

    /**
     * ����ims���ӳ�ʱʱ����0��ʾĬ��ʹ��ims��ֵ
     *
     * @return
     */
    @Override
    public int getConnectTimeout() {
        return 0;
    }

    /**
     * ����Ӧ����ǰ̨ʱims�������ʱ����0��ʾĬ��ʹ��ims��ֵ
     *
     * @return
     */
    @Override
    public int getForegroundHeartbeatInterval() {
        return 0;
    }

    /**
     * ����Ӧ���ں�̨ʱims�������ʱ����0��ʾĬ��ʹ��ims��ֵ
     *
     * @return
     */
    @Override
    public int getBackgroundHeartbeatInterval() {
        return 0;
    }

    /**
     * ����������Ϣ
     *
     * @return
     */
    @Override
    public MessageProtobuf.Msg getHandshakeMsg() {
        MessageProtobuf.Msg.Builder builder = MessageProtobuf.Msg.newBuilder();
        MessageProtobuf.Head.Builder headBuilder = MessageProtobuf.Head.newBuilder();
        headBuilder.setMsgId(UUID.randomUUID().toString());
        headBuilder.setMsgType(MessageType.HANDSHAKE.getMsgType());
        headBuilder.setFromId(userId);
        headBuilder.setTimestamp(System.currentTimeMillis());

        JSONObject jsonObj = new JSONObject();
        jsonObj.put("token", token);
        headBuilder.setExtend(jsonObj.toString());
        builder.setHead(headBuilder.build());

        return builder.build();
    }

    /**
     * ����������Ϣ
     *
     * @return
     */
    @Override
    public MessageProtobuf.Msg getHeartbeatMsg() {
        MessageProtobuf.Msg.Builder builder = MessageProtobuf.Msg.newBuilder();
        MessageProtobuf.Head.Builder headBuilder = MessageProtobuf.Head.newBuilder();
        headBuilder.setMsgId(UUID.randomUUID().toString());
        headBuilder.setMsgType(MessageType.HEARTBEAT.getMsgType());
        headBuilder.setFromId(userId);
        headBuilder.setTimestamp(System.currentTimeMillis());
        builder.setHead(headBuilder.build());

        return builder.build();
    }

    /**
     * ����˷��ص���Ϣ����״̬������Ϣ����
     *
     * @return
     */
    @Override
    public int getServerSentReportMsgType() {
        return MessageType.SERVER_MSG_SENT_STATUS_REPORT.getMsgType();
    }

    /**
     * �ͻ����ύ����Ϣ����״̬������Ϣ����
     *
     * @return
     */
    @Override
    public int getClientReceivedReportMsgType() {
        return MessageType.CLIENT_MSG_RECEIVED_STATUS_REPORT.getMsgType();
    }

    /**
     * ����ims��Ϣ���ͳ�ʱ�ط�������0��ʾĬ��ʹ��ims��ֵ
     *
     * @return
     */
    @Override
    public int getResendCount() {
        return 0;
    }

    /**
     * ����ims��Ϣ���ͳ�ʱ�ط����ʱ����0��ʾĬ��ʹ��ims��ֵ
     *
     * @return
     */
    @Override
    public int getResendInterval() {
        return 0;
    }
}
