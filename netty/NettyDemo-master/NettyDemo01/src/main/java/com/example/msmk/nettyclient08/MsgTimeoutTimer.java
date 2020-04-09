package com.example.msmk.nettyclient08;

import com.example.msmk.nettyclient08.interf.IMSClientInterface;
import com.example.msmk.nettyclient08.protobuf.MessageProtobuf;

import java.util.Timer;
import java.util.TimerTask;

/**
 * <p>@ProjectName:     NettyChat</p>
 * <p>@ClassName:       MsgTimeoutTimer.java</p>
 * <p>@PackageName:     com.freddy.im</p>
 * <b>
 * <p>@Description:     ��Ϣ���ͳ�ʱ��ʱ����ÿһ����Ϣ��Ӧһ����ʱ��</p>
 * </b>
 * <p>@author:          FreddyChen</p>
 * <p>@date:            2019/04/09 22:38</p>
 * <p>@email:           chenshichao@outlook.com</p>
 */
public class MsgTimeoutTimer extends Timer {

    private IMSClientInterface imsClient;// ims�ͻ���
    private MessageProtobuf.Msg msg;// ���͵���Ϣ
    private int currentResendCount;// ��ǰ�ط�����
    private MsgTimeoutTask task;// ��Ϣ���ͳ�ʱ����

    public MsgTimeoutTimer(IMSClientInterface imsClient, MessageProtobuf.Msg msg) {
        this.imsClient = imsClient;
        this.msg = msg;
        task = new MsgTimeoutTask();
        this.schedule(task, imsClient.getResendInterval(), imsClient.getResendInterval());
    }

    /**
     * ��Ϣ���ͳ�ʱ����
     */
    private class MsgTimeoutTask extends TimerTask {

        @Override
        public void run() {
            if (imsClient.isClosed()) {
                if (imsClient.getMsgTimeoutTimerManager() != null) {
                    imsClient.getMsgTimeoutTimerManager().remove(msg.getHead().getMsgId());
                }

                return;
            }

            currentResendCount++;
            if (currentResendCount > imsClient.getResendCount()) {
                // �ط��������ڿ��ط�������ֱ�ӱ�ʶΪ����ʧ�ܣ���ͨ����Ϣת����֪ͨӦ�ò�
                try {
                    MessageProtobuf.Msg.Builder builder = MessageProtobuf.Msg.newBuilder();
                    MessageProtobuf.Head.Builder headBuilder = MessageProtobuf.Head.newBuilder();
                    headBuilder.setMsgId(msg.getHead().getMsgId());
                    headBuilder.setMsgType(imsClient.getServerSentReportMsgType());
                    headBuilder.setTimestamp(System.currentTimeMillis());
                    headBuilder.setStatusReport(IMSConfig.DEFAULT_REPORT_SERVER_SEND_MSG_FAILURE);
                    builder.setHead(headBuilder.build());

                    // ֪ͨӦ�ò���Ϣ����ʧ��
                    imsClient.getMsgDispatcher().receivedMsg(builder.build());
                } finally {
                    // ����Ϣ���ͳ�ʱ�������Ƴ�����Ϣ
                    imsClient.getMsgTimeoutTimerManager().remove(msg.getHead().getMsgId());
                    // ִ�е������Ϊ�����ѶϿ����ȶ�����������
                    imsClient.resetConnect();
                    currentResendCount = 0;
                }
            } else {
                // ������Ϣ�������ټ��볬ʱ���������ﵽ�����ʧ�ܴ���������
                sendMsg();
            }
        }
    }

    public void sendMsg() {
        System.out.println("�����ط���Ϣ��message=" + msg);
        imsClient.sendMsg(msg, false);
    }

    public MessageProtobuf.Msg getMsg() {
        return msg;
    }

    @Override
    public void cancel() {
        if (task != null) {
            task.cancel();
            task = null;
        }

        super.cancel();
    }
}
