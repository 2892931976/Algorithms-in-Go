package com.example.msmk.nettyclient08.netty;

import com.alibaba.fastjson.JSONObject;
import com.example.msmk.nettyclient08.IMSConfig;
import com.example.msmk.nettyclient08.interf.IMSClientInterface;
import com.example.msmk.nettyclient08.protobuf.MessageProtobuf;

import java.util.UUID;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.internal.StringUtil;

/**
 * <p>@ProjectName:     NettyChat</p>
 * <p>@ClassName:       TCPReadHandler.java</p>
 * <p>@PackageName:     com.freddy.im.netty</p>
 * <b>
 * <p>@Description:     ��Ϣ���մ���handler</p>
 * </b>
 * <p>@author:          FreddyChen</p>
 * <p>@date:            2019/04/07 21:40</p>
 * <p>@email:           chenshichao@outlook.com</p>
 */
public class TCPReadHandler extends ChannelInboundHandlerAdapter {

    private NettyTcpClient imsClient;

    public TCPReadHandler(NettyTcpClient imsClient) {
        this.imsClient = imsClient;
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        System.err.println("TCPReadHandler channelInactive()");
        Channel channel = ctx.channel();
        if (channel != null) {
            channel.close();
            ctx.close();
        }

        // ��������
        imsClient.resetConnect(false);
    }



    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        System.err.println("TCPReadHandler exceptionCaught()");
        Channel channel = ctx.channel();
        if (channel != null) {
            channel.close();
            ctx.close();
        }

        // ��������
        imsClient.resetConnect(false);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        MessageProtobuf.Msg message = (MessageProtobuf.Msg) msg;
        if (message == null || message.getHead() == null) {
            return;
        }

        int msgType = message.getHead().getMsgType();
        if (msgType == imsClient.getServerSentReportMsgType()) {
            int statusReport = message.getHead().getStatusReport();
            System.out.println(String.format("�����״̬���棺��%d��, 1����ɹ���0����ʧ��", statusReport));
            if (statusReport == IMSConfig.DEFAULT_REPORT_SERVER_SEND_MSG_SUCCESSFUL) {
                System.out.println("�յ��������Ϣ����״̬���棬message=" + message + "���ӳ�ʱ�������Ƴ�");
                imsClient.getMsgTimeoutTimerManager().remove(message.getHead().getMsgId());
            }
        } else {
            // ������Ϣ
            // �յ���Ϣ�����������˻�һ����Ϣ����״̬����
            System.out.println("�յ���Ϣ��message=" + message);
            MessageProtobuf.Msg receivedReportMsg = buildReceivedReportMsg(message.getHead().getMsgId());
            if(receivedReportMsg != null) {
                imsClient.sendMsg(receivedReportMsg);
            }
        }

        // ������Ϣ������Ϣת����ת����Ӧ�ò�
        imsClient.getMsgDispatcher().receivedMsg(message);
    }

    /**
     * �����ͻ�����Ϣ����״̬����
     * @param msgId
     * @return
     */
    private MessageProtobuf.Msg buildReceivedReportMsg(String msgId) {
        if (StringUtil.isNullOrEmpty(msgId)) {
            return null;
        }

        MessageProtobuf.Msg.Builder builder = MessageProtobuf.Msg.newBuilder();
        MessageProtobuf.Head.Builder headBuilder = MessageProtobuf.Head.newBuilder();
        headBuilder.setMsgId(UUID.randomUUID().toString());
        headBuilder.setMsgType(imsClient.getClientReceivedReportMsgType());
        headBuilder.setTimestamp(System.currentTimeMillis());
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("msgId", msgId);
        headBuilder.setExtend(jsonObj.toString());
        builder.setHead(headBuilder.build());

        return builder.build();
    }
}
