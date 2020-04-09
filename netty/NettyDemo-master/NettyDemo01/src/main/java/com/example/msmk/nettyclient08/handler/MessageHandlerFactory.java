package com.example.msmk.nettyclient08.handler;

//import android.util.SparseArray;

import com.example.msmk.nettyclient08.MessageType;

/**
 * <p>@ProjectName:     NettyChat</p>
 * <p>@ClassName:       MessageHandlerFactory.java</p>
 * <p>@PackageName:     com.freddy.chat.im.handler</p>
 * <b>
 * <p>@Description:     ��Ϣ����handler����</p>
 * </b>
 * <p>@author:          FreddyChen</p>
 * <p>@date:            2019/04/10 03:44</p>
 * <p>@email:           chenshichao@outlook.com</p>
 */
public class MessageHandlerFactory {

    private MessageHandlerFactory() {

    }

//    private static final SparseArray<IMessageHandler> HANDLERS = new SparseArray<>();

//    static {
//        /** ������Ϣ����handler */
//        HANDLERS.put(MessageType.SINGLE_CHAT.getMsgType(), new SingleChatMessageHandler());
//        /** Ⱥ����Ϣ����handler */
//        HANDLERS.put(MessageType.GROUP_CHAT.getMsgType(), new GroupChatMessageHandler());
//        /** ����˷��ص���Ϣ����״̬���洦��handler */
//        HANDLERS.put(MessageType.SERVER_MSG_SENT_STATUS_REPORT.getMsgType(), new ServerReportMessageHandler());
//    }

    /**
     * ������Ϣ���ͻ�ȡ��Ӧ�Ĵ���handler
     *
     * @param msgType
     * @return
     */
    public static IMessageHandler getHandlerByMsgType(int msgType) {
//        return HANDLERS.get(msgType);
        return null;
    }
}
