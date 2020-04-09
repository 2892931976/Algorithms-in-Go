package com.example.msmk.nettydemo05;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;

import java.util.Date;

/**
 * Created by XiChuan on 2018-11-05.
 */
public class ClientHandler  extends ChannelInboundHandlerAdapter {

    // 客户端与服务端，连接成功的的处理
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client active channel:" + ctx.channel()+",time:"+new Date().toLocaleString());
//        ctx.fireChannelActive();

        // 发送自定义Message协议的消息
        // 要发送的信息
        String type = Constant.TYPE_MESSAGE;
        int typeLength = type.getBytes().length;
        String str = "I am client ...";
        // 获得要发送信息的字节数组
        byte[] content = str.getBytes();
        // 要发送信息的长度
        int contentLength = content.length;
        MessageProtocol protocol = new MessageProtocol(typeLength,type,contentLength, content);
        System.out.println("client send message:"+protocol.toString());
        Channel channel = ctx.channel();
        channel.writeAndFlush(protocol);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client inactive channel:" + ctx.channel()+",time:"+new Date().toLocaleString());

    }

    // 只是读数据，没有写数据的话
    // 需要自己手动的释放的消息
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        System.out.println("read channel:" + ctx.channel());
        try {
            MessageProtocol messageProtocol = (MessageProtocol) msg;
            System.out.println("time:"+new Date().toLocaleString()+"=="+"receive server message:" + messageProtocol.toString());
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        ctx.close();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        if (evt instanceof IdleStateEvent) {
            IdleState state = ((IdleStateEvent) evt).state();
            if (state == IdleState.WRITER_IDLE) {
                // write heartbeat to server
                String message = "heart beat ...";
                MessageProtocol messageProtocol = new MessageProtocol(
                        Constant.TYPE_PING.getBytes().length,
                        Constant.TYPE_PING,
                        message.getBytes().length,
                        message.getBytes());
                System.out.println("client ping message:" + messageProtocol.toString());
                ctx.writeAndFlush(messageProtocol);
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
}

