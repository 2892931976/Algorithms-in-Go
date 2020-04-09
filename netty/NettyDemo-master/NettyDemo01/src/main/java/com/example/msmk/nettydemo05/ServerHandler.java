package com.example.msmk.nettydemo05;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.ssl.SslHandshakeCompletionEvent;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by XiChuan on 2018-11-05.
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {

    private AtomicInteger channelCount = new AtomicInteger(0); //通道数量

    /**
     * 读数据
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("read channel=" + ctx.channel() + ", total channel=" + channelCount);
        try {

            MessageProtocol message = (MessageProtocol) msg;
//            System.out.println("time:"+new Date().toLocaleString()+"=="+"receive client message:" + message.toString() + ",ip:" + ctx.channel().remoteAddress());

//            String str = "Hi I am Server ...";
//            com.example.msmk.nettydemo04.MessageProtocol protocol = new com.example.msmk.nettydemo04.MessageProtocol(str.getBytes().length, str.getBytes());

            System.out.println("time:"+new Date().toLocaleString()+"=="+"receive client message:" + message.getType() + ",ip:" + ctx.channel().remoteAddress());
            if (Constant.TYPE_MESSAGE.compareTo(message.getType()) == 0) {
                System.out.println("time:"+new Date().toLocaleString()+"=="+"receive client message:" + message.toString() + ",ip:" + ctx.channel().remoteAddress());

                // 发送自定义Message协议的消息
                // 要发送的信息
                String type = Constant.TYPE_MESSAGE;
                int typeLength = type.getBytes().length;
                String str = "Hi I am Server ...";
                // 获得要发送信息的字节数组
                byte[] content = str.getBytes();
                // 要发送信息的长度
                int contentLength = content.length;
                MessageProtocol protocol = new MessageProtocol(typeLength, type, contentLength, content);
                System.out.println("server send message:" + protocol.toString());

                // 当服务端完成写操作后，关闭与客户端的连接
                ctx.channel().writeAndFlush(protocol);
            }

        } finally {
            // 抛弃收到的数据
            ReferenceCountUtil.release(msg);
        }
    }

    /**
     * 心跳检测的超时时会触发
     *
     * @param ctx
     * @param evt
     * @throws Exception
     */
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            if (e.state() == IdleState.READER_IDLE) {  //读取心跳超时后，会将此channel连接断开
                System.out.println("trigger channel =" + ctx.channel());
                ctx.close();  //如果超时，关闭这个通道
            }
        } else if (evt instanceof SslHandshakeCompletionEvent) {
            System.out.println("ssl handshake done");
            //super.userEventTriggered(ctx,evt);
        }
    }

    /**
     * 当通道活动时
     *
     * @param ctx
     * @throws Exception
     */
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        channelCount.incrementAndGet();//当有新通道连接进来时，将通道数+1
        System.out.println("active channel=" + ctx.channel() + ", total channel=" + channelCount + ", id=" + ctx.channel().id().asShortText());

    }

    /**
     * 当通道不活动时
     *
     * @param ctx
     * @throws Exception
     */
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //当通道关闭时，将通道数-1
        ctx.close();
        channelCount.decrementAndGet();
        System.out.println("inactive channel,channel=" + ctx.channel() + ", id=" + ctx.channel().id().asShortText());
    }

    /**
     * 异常获取
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("exception channel=" + ctx.channel() + " cause=" + cause); //如果不活跃关闭此通道
        ctx.close();
    }
}
