package com.example.msmk.nettydemo03;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * Created by XiChuan on 2018-11-05.
 */
public class ClientHandler  extends ChannelInboundHandlerAdapter {

    // 客户端与服务端，连接成功的售后
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("active channel:" + ctx.channel());
        // 发送SmartCar协议的消息
        // 要发送的信息
        String data = "I am client ...";
        // 获得要发送信息的字节数组
        byte[] content = data.getBytes();

        Channel channel = ctx.channel();
        channel.writeAndFlush(content);
    }

    // 只是读数据，没有写数据的话
    // 需要自己手动的释放的消息
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        System.out.println("read channel:" + ctx.channel());
        try {
            // 用于获取客户端发来的数据信息
            System.out.println("Client receive message:" + new String((byte[])msg));

        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        ctx.close();
    }


}
