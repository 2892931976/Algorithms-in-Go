package com.example.msmk.nettydemo04;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.ssl.SslHandshakeCompletionEvent;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

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
            System.out.println("receive client message:" + message.toString());

            String str = "Hi I am Server ...";
            MessageProtocol protocol = new MessageProtocol(str.getBytes().length, str.getBytes());
            // 当服务端完成写操作后，关闭与客户端的连接
            ctx.channel().writeAndFlush(protocol);


            //没有定义将接收的数据改为什么类型此时就会用ByteBuf，在自定义协议的时候并没有用
            if (msg instanceof ByteBuf) {

                //将ascii码的二进制转换为对应的字符
                System.out.println("Buffer ");
                ByteBuf byteBuf = (ByteBuf) msg;
                System.out.println(byteBuf.toString(CharsetUtil.UTF_8));

                //转换为16进制字符串
                /*byte[] bytes = new byte[byteBuf.readableBytes()];
                byteBuf.readBytes(bytes);
                String byteStr = ByteBufUtil.hexDump(bytes);
                System.out.println(byteStr);*/

                //转换为16进制字符串
                byte[] byte2s = ByteBufUtil.getBytes(byteBuf);
                System.out.println("hex string:" + ByteBufUtil.hexDump(byte2s));
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
            if (e.state() == IdleState.READER_IDLE) {
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