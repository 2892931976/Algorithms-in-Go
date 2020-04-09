package com.example.msmk.nettyclient08;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
//import com.example.msmk.imlib.interf.IMSClientInterface;
import com.example.msmk.nettyclient08.netty.NettyTcpClient;
import com.example.msmk.nettyclient08.protobuf.MessageProtobuf;

//import java.util.UUID;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * <p>@ProjectName:     NettyChat</p>
 * <p>@ClassName:       LoginAuthRespHandler.java</p>
 * <p>@PackageName:     com.freddy.im</p>
 * <b>
 * <p>@Description:     ������֤��Ϣ��Ӧ����handler</p>
 * </b>
 * <p>@author:          FreddyChen</p>
 * <p>@date:            2019/04/07 23:11</p>
 * <p>@email:           chenshichao@outlook.com</p>
 */
public class LoginAuthRespHandler extends ChannelInboundHandlerAdapter {

    private NettyTcpClient imsClient;

    public LoginAuthRespHandler(NettyTcpClient imsClient) {
        this.imsClient = imsClient;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        MessageProtobuf.Msg handshakeRespMsg = (MessageProtobuf.Msg) msg;
        if (handshakeRespMsg == null || handshakeRespMsg.getHead() == null) {
            return;
        }

        MessageProtobuf.Msg handshakeMsg = imsClient.getHandshakeMsg();
        if (handshakeMsg == null || handshakeMsg.getHead() == null) {
            return;
        }

        int handshakeMsgType = handshakeMsg.getHead().getMsgType();
        if (handshakeMsgType == handshakeRespMsg.getHead().getMsgType()) {
            System.out.println("�յ������������Ӧ��Ϣ��message=" + handshakeRespMsg);
            int status = -1;
            try {
                JSONObject jsonObj = JSON.parseObject(handshakeRespMsg.getHead().getExtend());
                status = jsonObj.getIntValue("status");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (status == 1) {
                    // ���ֳɹ��������ȷ���һ��������Ϣ�������������ƹ�������HeartbeatHandler
                    MessageProtobuf.Msg heartbeatMsg = imsClient.getHeartbeatMsg();
                    if (heartbeatMsg == null) {
                        return;
                    }

                    // ���ֳɹ��������Ϣ���ͳ�ʱ���������Ƿ��з��ͳ�ʱ����Ϣ������У���ȫ���ط�
                    imsClient.getMsgTimeoutTimerManager().onResetConnected();

                    System.out.println("����������Ϣ��" + heartbeatMsg + "��ǰ�������Ϊ��" + imsClient.getHeartbeatInterval() + "ms\n");
                    imsClient.sendMsg(heartbeatMsg);

                    // ���������Ϣ����handler
                    imsClient.addHeartbeatHandler();
                } else {
                    imsClient.resetConnect(false);// ����ʧ�ܣ���������
                }
            }
        } else {
            // ��Ϣ͸��
            ctx.fireChannelRead(msg);
        }
    }
}
