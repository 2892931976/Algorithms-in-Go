package com.example.msmk.nettyclient08.netty;


import com.example.msmk.nettyclient08.ExecutorServiceFactory;
import com.example.msmk.nettyclient08.HeartbeatHandler;
import com.example.msmk.nettyclient08.IMSConfig;
import com.example.msmk.nettyclient08.MsgDispatcher;
import com.example.msmk.nettyclient08.MsgTimeoutTimerManager;
import com.example.msmk.nettyclient08.interf.IMSClientInterface;
import com.example.msmk.nettyclient08.listener.IMSConnectStatusCallback;
import com.example.msmk.nettyclient08.listener.OnEventListener;
import com.example.msmk.nettyclient08.protobuf.MessageProtobuf;

import java.util.Vector;
import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.internal.StringUtil;

/**
 * <p>@ProjectName:     NettyChat</p>
 * <p>@ClassName:       NettyTcpClient.java</p>
 * <p>@PackageName:     com.freddy.im.netty</p>
 * <b>
 * <p>@Description:     ����nettyʵ�ֵ�tcp ims</p>
 * </b>
 * <p>@author:          FreddyChen</p>
 * <p>@date:            2019/03/31 20:41</p>
 * <p>@email:           chenshichao@outlook.com</p>
 */
public class NettyTcpClient implements IMSClientInterface {

    private static volatile NettyTcpClient instance;

    private Bootstrap bootstrap;
    private Channel channel;

    private boolean isClosed = false;// ��ʶims�Ƿ��ѹر�
    private Vector<String> serverUrlList;// ims��������ַ��
    private OnEventListener mOnEventListener;// ��Ӧ�ò㽻����listener
    private IMSConnectStatusCallback mIMSConnectStatusCallback;// ims����״̬�ص�������
    private MsgDispatcher msgDispatcher;// ��Ϣת����
    private ExecutorServiceFactory loopGroup;// �̳߳ع���

    private boolean isReconnecting = false;// �Ƿ����ڽ�������
    private int connectStatus = IMSConfig.CONNECT_STATE_FAILURE;// ims����״̬����ʼ��Ϊ����ʧ��
    // �������ʱ��
    private int reconnectInterval = IMSConfig.DEFAULT_RECONNECT_BASE_DELAY_TIME;
    // ���ӳ�ʱʱ��
    private int connectTimeout = IMSConfig.DEFAULT_CONNECT_TIMEOUT;
    // �������ʱ��
    private int heartbeatInterval = IMSConfig.DEFAULT_HEARTBEAT_INTERVAL_FOREGROUND;
    // Ӧ���ں�̨ʱ�������ʱ��
    private int foregroundHeartbeatInterval = IMSConfig.DEFAULT_HEARTBEAT_INTERVAL_FOREGROUND;
    // Ӧ����ǰ̨ʱ�������ʱ��
    private int backgroundHeartbeatInterval = IMSConfig.DEFAULT_HEARTBEAT_INTERVAL_BACKGROUND;
    // appǰ��̨״̬
    private int appStatus = IMSConfig.APP_STATUS_FOREGROUND;
    // ��Ϣ���ͳ�ʱ�ط�����
    private int resendCount = IMSConfig.DEFAULT_RESEND_COUNT;
    // ��Ϣ����ʧ���ط����ʱ��
    private int resendInterval = IMSConfig.DEFAULT_RESEND_INTERVAL;

    private String currentHost = null;// ��ǰ����host
    private int currentPort = -1;// ��ǰ����port

    private MsgTimeoutTimerManager msgTimeoutTimerManager;// ��Ϣ���ͳ�ʱ��ʱ������

    private NettyTcpClient() {
    }

    public static NettyTcpClient getInstance() {
        if (null == instance) {
            synchronized (NettyTcpClient.class) {
                if (null == instance) {
                    instance = new NettyTcpClient();
                }
            }
        }

        return instance;
    }

    /**
     * ��ʼ��
     *
     * @param serverUrlList ��������ַ�б�
     * @param listener      ��Ӧ�ò㽻����listener
     * @param callback      ims����״̬�ص�
     */
    @Override
    public void init(Vector<String> serverUrlList, OnEventListener listener, IMSConnectStatusCallback callback) {
        close();
        isClosed = false;
        this.serverUrlList = serverUrlList;
        this.mOnEventListener = listener;
        this.mIMSConnectStatusCallback = callback;
        msgDispatcher = new MsgDispatcher();
        msgDispatcher.setOnEventListener(listener);
        loopGroup = new ExecutorServiceFactory();
        loopGroup.initBossLoopGroup();// ��ʼ�������߳���
        msgTimeoutTimerManager = new MsgTimeoutTimerManager(this);

        resetConnect(true);// ���е�һ������
    }

    /**
     * �������ӣ�Ҳ��������
     * �״�����Ҳ����Ϊ������
     */
    @Override
    public void resetConnect() {
        this.resetConnect(false);
    }

    /**
     * �������ӣ�Ҳ��������
     * �״�����Ҳ����Ϊ������
     * ����
     *
     * @param isFirst �Ƿ��״�����
     */
    @Override
    public void resetConnect(boolean isFirst) {
        if (!isFirst) {
            try {
                Thread.sleep(IMSConfig.DEFAULT_RECONNECT_INTERVAL);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // ֻ�е�һ�������߲��ܸ�ֵ����������
        if (!isClosed && !isReconnecting) {
            synchronized (this) {
                if (!isClosed && !isReconnecting) {
                    // ��ʶ���ڽ�������
                    isReconnecting = true;
                    // �ص�ims����״̬
                    onConnectStatusCallback(IMSConfig.CONNECT_STATE_CONNECTING);
                    // �ȹر�channel
                    closeChannel();
                    // ִ����������
                    loopGroup.execBossTask(new ResetConnectRunnable(isFirst));
                }
            }
        }
    }

    /**
     * �ر����ӣ�ͬʱ�ͷ���Դ
     */
    @Override
    public void close() {
        if (isClosed) {
            return;
        }

        isClosed = true;

        // �ر�channel
        try {
            closeChannel();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // �ر�bootstrap
        try {
            if (bootstrap != null) {
                bootstrap.group().shutdownGracefully();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            // �ͷ��̳߳�
            if (loopGroup != null) {
                loopGroup.destroy();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (serverUrlList != null) {
                    serverUrlList.clear();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            isReconnecting = false;
            channel = null;
            bootstrap = null;
        }
    }

    /**
     * ��ʶims�Ƿ��ѹر�
     *
     * @return
     */
    @Override
    public boolean isClosed() {
        return isClosed;
    }

    /**
     * ������Ϣ
     *
     * @param msg
     */
    @Override
    public void sendMsg(MessageProtobuf.Msg msg) {
        this.sendMsg(msg, true);
    }

    /**
     * ������Ϣ
     * ����
     *
     * @param msg
     * @param isJoinTimeoutManager �Ƿ���뷢�ͳ�ʱ������
     */
    @Override
    public void sendMsg(MessageProtobuf.Msg msg, boolean isJoinTimeoutManager) {
        if (msg == null || msg.getHead() == null) {
            System.out.println("������Ϣʧ�ܣ���ϢΪ��\tmessage=" + msg);
            return;
        }

        if(!StringUtil.isNullOrEmpty(msg.getHead().getMsgId())) {
            if(isJoinTimeoutManager) {
                msgTimeoutTimerManager.add(msg);
            }
        }

        if (channel == null) {
            System.out.println("������Ϣʧ�ܣ�channelΪ��\tmessage=" + msg);
        }

        try {
            channel.writeAndFlush(msg);
        } catch (Exception ex) {
            System.out.println("������Ϣʧ�ܣ�reason:" + ex.getMessage() + "\tmessage=" + msg);
        }
    }

    /**
     * ��ȡ�������ʱ��
     *
     * @return
     */
    @Override
    public int getReconnectInterval() {
        if (mOnEventListener != null && mOnEventListener.getReconnectInterval() > 0) {
            return reconnectInterval = mOnEventListener.getReconnectInterval();
        }

        return reconnectInterval;
    }

    /**
     * ��ȡ���ӳ�ʱʱ��
     *
     * @return
     */
    @Override
    public int getConnectTimeout() {
        if (mOnEventListener != null && mOnEventListener.getConnectTimeout() > 0) {
            return connectTimeout = mOnEventListener.getConnectTimeout();
        }

        return connectTimeout;
    }

    /**
     * ��ȡӦ����ǰ̨ʱ�������ʱ��
     *
     * @return
     */
    @Override
    public int getForegroundHeartbeatInterval() {
        if (mOnEventListener != null && mOnEventListener.getForegroundHeartbeatInterval() > 0) {
            return foregroundHeartbeatInterval = mOnEventListener.getForegroundHeartbeatInterval();
        }

        return foregroundHeartbeatInterval;
    }

    /**
     * ��ȡӦ����ǰ̨ʱ�������ʱ��
     *
     * @return
     */
    @Override
    public int getBackgroundHeartbeatInterval() {
        if (mOnEventListener != null && mOnEventListener.getBackgroundHeartbeatInterval() > 0) {
            return backgroundHeartbeatInterval = mOnEventListener.getBackgroundHeartbeatInterval();
        }

        return backgroundHeartbeatInterval;
    }

    /**
     * ����appǰ��̨״̬
     *
     * @param appStatus
     */
    @Override
    public void setAppStatus(int appStatus) {
        this.appStatus = appStatus;
        if (this.appStatus == IMSConfig.APP_STATUS_FOREGROUND) {
            heartbeatInterval = foregroundHeartbeatInterval;
        } else if (this.appStatus == IMSConfig.APP_STATUS_BACKGROUND) {
            heartbeatInterval = backgroundHeartbeatInterval;
        }

        addHeartbeatHandler();
    }

    /**
     * ��ȡ��Ӧ�ò㹹���������Ϣ
     *
     * @return
     */
    @Override
    public MessageProtobuf.Msg getHandshakeMsg() {
        if (mOnEventListener != null) {
            return mOnEventListener.getHandshakeMsg();
        }

        return null;
    }

    /**
     * ��ȡ��Ӧ�ò㹹���������Ϣ
     *
     * @return
     */
    @Override
    public MessageProtobuf.Msg getHeartbeatMsg() {
        if (mOnEventListener != null) {
            return mOnEventListener.getHeartbeatMsg();
        }

        return null;
    }

    /**
     * ��ȡӦ�ò���Ϣ����״̬������Ϣ����
     *
     * @return
     */
    @Override
    public int getServerSentReportMsgType() {
        if (mOnEventListener != null) {
            return mOnEventListener.getServerSentReportMsgType();
        }

        return 0;
    }

    /**
     * ��ȡӦ�ò���Ϣ����״̬������Ϣ����
     *
     * @return
     */
    @Override
    public int getClientReceivedReportMsgType() {
        if (mOnEventListener != null) {
            return mOnEventListener.getClientReceivedReportMsgType();
        }

        return 0;
    }

    /**
     * ��ȡ�������ʱ��
     *
     * @return
     */
    public int getHeartbeatInterval() {
        return this.heartbeatInterval;
    }

    /**
     * ��ȡӦ�ò���Ϣ���ͳ�ʱ�ط�����
     *
     * @return
     */
    @Override
    public int getResendCount() {
        if (mOnEventListener != null && mOnEventListener.getResendCount() != 0) {
            return resendCount = mOnEventListener.getResendCount();
        }

        return resendCount;
    }

    /**
     * ��ȡӦ�ò���Ϣ���ͳ�ʱ�ط����
     *
     * @return
     */
    @Override
    public int getResendInterval() {
        if (mOnEventListener != null && mOnEventListener.getReconnectInterval() != 0) {
            return resendInterval = mOnEventListener.getResendInterval();
        }

        return resendInterval;
    }

    /**
     * ��ȡ�̳߳�
     *
     * @return
     */
    public ExecutorServiceFactory getLoopGroup() {
        return loopGroup;
    }

    /**
     * ��ȡ��Ϣת����
     *
     * @return
     */
    @Override
    public MsgDispatcher getMsgDispatcher() {
        return msgDispatcher;
    }

    /**
     * ��ȡ��Ϣ���ͳ�ʱ��ʱ��
     *
     * @return
     */
    @Override
    public MsgTimeoutTimerManager getMsgTimeoutTimerManager() {
        return msgTimeoutTimerManager;
    }

    /**
     * ��ʼ��bootstrap
     */
    private void initBootstrap() {
        EventLoopGroup loopGroup = new NioEventLoopGroup(4);
        bootstrap = new Bootstrap();
        bootstrap.group(loopGroup).channel(NioSocketChannel.class);
        // ���ø�ѡ���Ժ��������Сʱ��û�����ݵ�ͨ��ʱ��TCP���Զ�����һ���̽�����ݱ���
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
        // ���ý���nagle�㷨
        bootstrap.option(ChannelOption.TCP_NODELAY, true);
        // �������ӳ�ʱʱ��
        bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, getConnectTimeout());
        // ���ó�ʼ��Channel
        bootstrap.handler(new TCPChannelInitializerHandler(this));
    }

    /**
     * �ص�ims����״̬
     *
     * @param connectStatus
     */
    private void onConnectStatusCallback(int connectStatus) {
        this.connectStatus = connectStatus;
        switch (connectStatus) {
            case IMSConfig.CONNECT_STATE_CONNECTING: {
                System.out.println("ims������...");
                if (mIMSConnectStatusCallback != null) {
                    mIMSConnectStatusCallback.onConnecting();
                }
                break;
            }

            case IMSConfig.CONNECT_STATE_SUCCESSFUL: {
                System.out.println(String.format("ims���ӳɹ���host��%s��, port��%s��", currentHost, currentPort));
                if (mIMSConnectStatusCallback != null) {
                    mIMSConnectStatusCallback.onConnected();
                }
                // ���ӳɹ�������������Ϣ
                MessageProtobuf.Msg handshakeMsg = getHandshakeMsg();
                if (handshakeMsg != null) {
                    System.out.println("����������Ϣ��message=" + handshakeMsg);
                    sendMsg(handshakeMsg, false);
                } else {
                    System.err.println("��Ӧ�ò㹹��������Ϣ��");
                }
                break;
            }

            case IMSConfig.CONNECT_STATE_FAILURE:
            default: {
                System.out.println("ims����ʧ��");
                if (mIMSConnectStatusCallback != null) {
                    mIMSConnectStatusCallback.onConnectFailed();
                }
                break;
            }
        }
    }

    /**
     * ���������Ϣ����handler
     */
    public void addHeartbeatHandler() {
        if (channel == null || !channel.isActive() || channel.pipeline() == null) {
            return;
        }

        try {
            // ֮ǰ���ڵĶ�д��ʱhandler�����Ƴ��������������
            if (channel.pipeline().get(IdleStateHandler.class.getSimpleName()) != null) {
                channel.pipeline().remove(IdleStateHandler.class.getSimpleName());
            }
            // 3������û��Ӧ�����������ѶϿ�
            channel.pipeline().addFirst(IdleStateHandler.class.getSimpleName(), new IdleStateHandler(
                    heartbeatInterval * 3, heartbeatInterval, 0, TimeUnit.MILLISECONDS));

            // �������HeartbeatHandler
            if (channel.pipeline().get(HeartbeatHandler.class.getSimpleName()) != null) {
                channel.pipeline().remove(HeartbeatHandler.class.getSimpleName());
            }
            if (channel.pipeline().get(TCPReadHandler.class.getSimpleName()) != null) {
                channel.pipeline().addBefore(TCPReadHandler.class.getSimpleName(), HeartbeatHandler.class.getSimpleName(),
                        new HeartbeatHandler(this));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("���������Ϣ����handlerʧ�ܣ�reason��" + e.getMessage());
        }
    }

    /**
     * �Ƴ�ָ��handler
     *
     * @param handlerName
     */
    private void removeHandler(String handlerName) {
        try {
            if (channel.pipeline().get(handlerName) != null) {
                channel.pipeline().remove(handlerName);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("�Ƴ�handlerʧ�ܣ�handlerName=" + handlerName);
        }
    }

    /**
     * �ر�channel
     */
    private void closeChannel() {
        try {
            if (channel != null) {
                try {
                    removeHandler(HeartbeatHandler.class.getSimpleName());
                    removeHandler(TCPReadHandler.class.getSimpleName());
                    removeHandler(IdleStateHandler.class.getSimpleName());
                } finally {
                    try {
                        channel.close();
                    } catch (Exception ex) {
                    }
                    try {
                        channel.eventLoop().shutdownGracefully();
                    } catch (Exception ex) {
                    }

                    channel = null;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("�ر�channel����reason:" + ex.getMessage());
        }
    }

    /**
     * ��Ӧ�ò��ȡ�����Ƿ����
     *
     * @return
     */
    private boolean isNetworkAvailable() {
        if (mOnEventListener != null) {
            return mOnEventListener.isNetworkAvailable();
        }

        return false;
    }

    /**
     * �������ӷ������ĵط�
     */
    private void toServer() {
        try {
            channel = bootstrap.connect(currentHost, currentPort).sync().channel();
        } catch (Exception e) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            System.err.println(String.format("����Server(ip[%s], port[%s])ʧ��", currentHost, currentPort));
            channel = null;
        }
    }

    /**
     * ��������
     */
    private class ResetConnectRunnable implements Runnable {

        private boolean isFirst;

        public ResetConnectRunnable(boolean isFirst) {
            this.isFirst = isFirst;
        }

        @Override
        public void run() {
            System.out.println("run:");
            // ���״ν���������ִ�е����Ｔ�����Ѿ�����ʧ�ܣ��ص�����״̬��Ӧ�ò�
            if (!isFirst) {
                System.out.println("isFirst:");
                onConnectStatusCallback(IMSConfig.CONNECT_STATE_FAILURE);
            }

            try {
                // ����ʱ���ͷŹ����߳��飬Ҳ����ֹͣ����
                loopGroup.destroyWorkLoopGroup();

                System.out.println("isClosed:");
                System.out.println(isClosed);
                System.out.println("isClosed:");
                while (!isClosed) {
                    System.out.println("isNetworkAvailable:");
                    System.out.println(isNetworkAvailable());
                    if(!isNetworkAvailable()) {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        continue;
                    }

                    // ������òŽ�������
                    int status;
                    if ((status = reConnect()) == IMSConfig.CONNECT_STATE_SUCCESSFUL) {
                        onConnectStatusCallback(status);
                        // ���ӳɹ�������ѭ��
                        break;
                    }

                    if (status == IMSConfig.CONNECT_STATE_FAILURE) {
                        onConnectStatusCallback(status);
                        try {
                            Thread.sleep(IMSConfig.DEFAULT_RECONNECT_INTERVAL);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } finally {
                // ��ʶ��������ֹͣ
                isReconnecting = false;
            }
        }

        /**
         * �������״�����Ҳ��Ϊ�ǵ�һ������
         *
         * @return
         */
        private int reConnect() {
            System.out.println("reConnect");
            // δ�رղ�ȥ����
            if (!isClosed) {
                try {
                    // ���ͷ�EventLoop�߳���
                    if (bootstrap != null) {
                        bootstrap.group().shutdownGracefully();
                    }
                } finally {
                    bootstrap = null;
                }

                // ��ʼ��bootstrap
                initBootstrap();
                return connectServer();
            }
            return IMSConfig.CONNECT_STATE_FAILURE;
        }

        /**
         * ���ӷ�����
         *
         * @return
         */
        private int connectServer() {
            // �����������ַ��Ч��ֱ�ӻص�����״̬�����ٽ�������
            // ��Ч�ķ�������ַʾ����127.0.0.1 8860
            if (serverUrlList == null || serverUrlList.size() == 0) {
                return IMSConfig.CONNECT_STATE_FAILURE;
            }

            for (int i = 0; (!isClosed && i < serverUrlList.size()); i++) {
                String serverUrl = serverUrlList.get(i);
                // �����������ַ��Ч��ֱ�ӻص�����״̬�����ٽ�������
                if (StringUtil.isNullOrEmpty(serverUrl)) {
                    return IMSConfig.CONNECT_STATE_FAILURE;
                }

                String[] address = serverUrl.split(" ");
                for (int j = 1; j <= IMSConfig.DEFAULT_RECONNECT_COUNT; j++) {
                    // ���ims�ѹرգ������粻���ã�ֱ�ӻص�����״̬�����ٽ�������
                    if (isClosed || !isNetworkAvailable()) {
                        return IMSConfig.CONNECT_STATE_FAILURE;
                    }

                    // �ص�����״̬
                    if (connectStatus != IMSConfig.CONNECT_STATE_CONNECTING) {
                        onConnectStatusCallback(IMSConfig.CONNECT_STATE_CONNECTING);
                    }
                    System.out.println(String.format("���ڽ��С�%s���ĵڡ�%d�������ӣ���ǰ������ʱʱ��Ϊ��%dms��", serverUrl, j, j * getReconnectInterval()));

                    try {
                        currentHost = address[0];// ��ȡhost
                        currentPort = Integer.parseInt(address[1]);// ��ȡport

                        toServer();// ���ӷ�����

                        // channel��Ϊ�գ�����Ϊ�����ѳɹ�
                        if (channel != null) {
                            return IMSConfig.CONNECT_STATE_SUCCESSFUL;
                        } else {
                            // ����ʧ�ܣ����߳�����n * �������ʱ��
                            Thread.sleep(j * getReconnectInterval());
                        }
                    } catch (InterruptedException e) {
                        close();
                        break;// �̱߳��жϣ���ǿ�ƹر�
                    }
                }
            }

            // ִ�е������������ʧ��
            return IMSConfig.CONNECT_STATE_FAILURE;
        }
    }
}
