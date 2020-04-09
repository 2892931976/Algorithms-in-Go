package com.example.msmk.nettydemo05;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.util.Timeout;
import io.netty.util.Timer;
import io.netty.util.TimerTask;

import java.util.concurrent.TimeUnit;

/**
 * ������⹷�������ֵ�ǰ����·���ȶ��ر�֮�󣬽���12������
 */
@Sharable
public abstract class ConnectionWatchdog extends ChannelInboundHandlerAdapter implements TimerTask, ChannelHandlerHolder {


    private final Bootstrap bootstrap;
    private final Timer timer;
    private final int port;

    private final String host;

    private volatile boolean reconnect = true;
    private int attempts;


    public ConnectionWatchdog(Bootstrap bootstrap, Timer timer, int port, String host, boolean reconnect) {
        this.bootstrap = bootstrap;
        this.timer = timer;
        this.port = port;
        this.host = host;
        this.reconnect = reconnect;
    }

    /**
     * channel��·ÿ��active��ʱ�򣬽������ӵĴ�������? 0
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        System.out.println("��ǰ��·�Ѿ������ˣ��������Դ���������Ϊ:0");

        attempts = 0;
        ctx.fireChannelActive();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("���ӹر�");
        if (reconnect) {
            System.out.println("���ӹرգ�����������,��" + (attempts + 1) + "����");
            if (attempts < 12) {
                attempts++;
                //�����ļ��ʱ���Խ��Խ��
                int timeout = 2 << attempts;
                timer.newTimeout(this, timeout, TimeUnit.MILLISECONDS);
            }
        }
        ctx.fireChannelInactive();
    }

    public void run(Timeout timeout) throws Exception {

        ChannelFuture future;
        //bootstrap�Ѿ���ʼ�����ˣ�ֻ��Ҫ��handler����Ϳ�����
        synchronized (bootstrap) {
            bootstrap.handler(new ChannelInitializer<Channel>() {

                @Override
                protected void initChannel(Channel ch) throws Exception {
                    //handlers()������clientס�������Ѿ���ʼ����
                    ch.pipeline().addLast(handlers());
                }
            });
            future = bootstrap.connect(host, port);
        }

        //future����
        future.addListener(new ChannelFutureListener() {

            public void operationComplete(ChannelFuture f) throws Exception {
                boolean succeed = f.isSuccess();

                //�������ʧ�ܣ������ChannelInactive�������ٴγ��������¼���һֱ����12�Σ����ʧ����������
                if (!succeed) {
                    System.out.println("����ʧ��");
                    f.channel().pipeline().fireChannelInactive();
                } else {
                    System.out.println("�����ɹ�");
                }
            }
        });

    }
}