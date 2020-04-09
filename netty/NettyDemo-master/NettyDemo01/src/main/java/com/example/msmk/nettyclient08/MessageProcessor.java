package com.example.msmk.nettyclient08;

//import android.util.Log;

import com.example.msmk.nettyclient08.bean.AppMessage;
import com.example.msmk.nettyclient08.bean.BaseMessage;
import com.example.msmk.nettyclient08.bean.ContentMessage;
import com.example.msmk.nettyclient08.handler.IMessageHandler;
import com.example.msmk.nettyclient08.handler.MessageHandlerFactory;
import com.example.msmk.nettyclient08.utils.CThreadPoolExecutor;

/**
 * <p>@ProjectName:     NettyChat</p>
 * <p>@ClassName:       MessageProcessor.java</p>
 * <p>@PackageName:     com.freddy.chat.im</p>
 * <b>
 * <p>@Description:     ��Ϣ������</p>
 * </b>
 * <p>@author:          FreddyChen</p>
 * <p>@date:            2019/04/10 03:27</p>
 * <p>@email:           chenshichao@outlook.com</p>
 */
public class MessageProcessor implements IMessageProcessor {

    private static final String TAG = MessageProcessor.class.getSimpleName();

    private MessageProcessor() {

    }

    private static class MessageProcessorInstance {
        private static final IMessageProcessor INSTANCE = new MessageProcessor();
    }

    public static IMessageProcessor getInstance() {
        return MessageProcessorInstance.INSTANCE;
    }

    /**
     * ������Ϣ
     * @param message
     */
    @Override
    public void receiveMsg(final AppMessage message) {
        CThreadPoolExecutor.runInBackground(new Runnable() {

            @Override
            public void run() {
                try {
                    IMessageHandler messageHandler = MessageHandlerFactory.getHandlerByMsgType(message.getHead().getMsgType());
                    if (messageHandler != null) {
                        messageHandler.execute(message);
                    } else {
//                        Log.e(TAG, "δ�ҵ���Ϣ����handler��msgType=" + message.getHead().getMsgType());
                    }
                } catch (Exception e) {
//                    Log.e(TAG, "��Ϣ�������reason=" + e.getMessage());
                }
            }
        });
    }

    /**
     * ������Ϣ
     *
     * @param message
     */
    @Override
    public void sendMsg(final AppMessage message) {
        CThreadPoolExecutor.runInBackground(new Runnable() {

            @Override
            public void run() {
                boolean isActive = IMSClientBootstrap.getInstance().isActive();
                if (isActive) {
                    IMSClientBootstrap.getInstance().sendMessage(MessageBuilder.getProtoBufMessageBuilderByAppMessage(message).build());
                } else {
//                    Log.e(TAG, "������Ϣʧ��");
                }
            }
        });
    }

    /**
     * ������Ϣ
     *
     * @param message
     */
    @Override
    public void sendMsg(ContentMessage message) {
        this.sendMsg(MessageBuilder.buildAppMessage(message));
    }

    /**
     * ������Ϣ
     *
     * @param message
     */
    @Override
    public void sendMsg(BaseMessage message) {
        this.sendMsg(MessageBuilder.buildAppMessage(message));
    }
}
