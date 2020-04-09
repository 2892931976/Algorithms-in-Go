package com.example.msmk.nettydemo05;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.HashedWheelTimer;

import java.util.concurrent.TimeUnit;

/**
 * Created by XiChuan on 2018-11-05.
 */
public class Client {

    protected final HashedWheelTimer timer = new HashedWheelTimer();

    private Bootstrap b;

    private String host;

    private int port;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void run() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();

        b = new Bootstrap();
        b.group(group).channel(NioSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO));

        final ConnectionWatchdog watchdog = new ConnectionWatchdog(b, timer, port, host, true) {

            public ChannelHandler[] handlers() {
                return new ChannelHandler[]{
                        this,
                        new IdleStateHandler(0, 4, 0, TimeUnit.SECONDS),
                        //idleStateTrigger,
                        new MessageProtocolDecoder(),
                        new MessageProtocolEncoder(),
                        new ClientHandler()
                };
            }
        };

        ChannelFuture future;
        //进行连接
        try {
            synchronized (b) {
                b .handler(new LoggingHandler(LogLevel.INFO))
                        .handler(new ChannelInitializer<Channel>() {

                    //初始化channel
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        ch.pipeline().addLast(watchdog.handlers());
                    }
                });

                future = b.connect(host, port);
            }

            // 以下代码在synchronized同步块外面是安全的
            future.sync();
            //future.channel().closeFuture().sync();
        } catch (Throwable t) {
            throw new Exception("connects to  fails", t);
        } finally {
            //group.shutdownGracefully();
            //System.out.println("client release resource...");
        }
    }

    public static void main(String[] args) throws Exception {
        new Client("127.0.0.1", 8081).run();

    }
}
