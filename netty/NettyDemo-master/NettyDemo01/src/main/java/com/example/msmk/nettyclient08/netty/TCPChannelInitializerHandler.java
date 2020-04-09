package com.example.msmk.nettyclient08.netty;

import com.example.msmk.nettyclient08.HeartbeatRespHandler;
import com.example.msmk.nettyclient08.LoginAuthRespHandler;
import com.example.msmk.nettyclient08.protobuf.MessageProtobuf;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;

/**
 * <p>@ProjectName:     NettyChat</p>
 * <p>@ClassName:       TCPChannelInitializerHandler.java</p>
 * <p>@PackageName:     com.freddy.im.netty</p>
 * <b>
 * <p>@Description:     Channel��ʼ������</p>
 * </b>
 * <p>@author:          FreddyChen</p>
 * <p>@date:            2019/04/05 07:11</p>
 * <p>@email:           chenshichao@outlook.com</p>
 */
public class TCPChannelInitializerHandler extends ChannelInitializer<Channel> {

    private NettyTcpClient imsClient;

    public TCPChannelInitializerHandler(NettyTcpClient imsClient) {
        this.imsClient = imsClient;
    }

    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();

        // netty�ṩ���Զ��峤�Ƚ����������TCP���/ճ������
        pipeline.addLast("frameEncoder", new LengthFieldPrepender(2));
        pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(65535,
                0, 2, 0, 2));

        // ����protobuf�����֧��
        pipeline.addLast(new ProtobufEncoder());
        pipeline.addLast(new ProtobufDecoder(MessageProtobuf.Msg.getDefaultInstance()));

        // ������֤��Ϣ��Ӧ����handler
        pipeline.addLast(LoginAuthRespHandler.class.getSimpleName(), new LoginAuthRespHandler(imsClient));
        // ������Ϣ��Ӧ����handler
        pipeline.addLast(HeartbeatRespHandler.class.getSimpleName(), new HeartbeatRespHandler(imsClient));
        // ������Ϣ����handler
        pipeline.addLast(TCPReadHandler.class.getSimpleName(), new TCPReadHandler(imsClient));
    }
}
