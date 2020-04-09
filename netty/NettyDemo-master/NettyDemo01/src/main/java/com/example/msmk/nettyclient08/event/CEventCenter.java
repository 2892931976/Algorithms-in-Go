package com.example.msmk.nettyclient08.event;

//import android.text.TextUtils;
//import android.util.Log;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * �¼����ķ���ʵ������ϵͳ�㲥�Ĺ��ܣ�
 *
 * Created by Freddy on 2015/11/3.
 * chenshichao@outlook.com
 */
public class CEventCenter {

    /**
     * �¼����ķ�������
     */
    public static final String SYMBOLIC_NAME = "EventCenter";

    /**
     * �������б�֧��һ�Զ�洢
     */
    private static final HashMap<String, Object> mListenerMap = new HashMap<String, Object>();

    /**
     * �������б���
     */
    private static final Object mListenerLock = new Object();

    /**
     * �¼������
     */
    private static final CEvenObjPool mPool = new CEvenObjPool(5);

    /**
     * ע��/ע��������
     *
     * @param toBind
     * @param listener ������
     * @param topics ���⣨һ��������Է�����������¼���
     */
    public static void onBindEvent(boolean toBind, I_CEventListener listener, String[] topics) {
        if(toBind) {
            registerEventListener(listener, topics);
        }else {
            unregisterEventListener(listener, topics);
        }
    }

    /**
     * ע�������
     * @param listener
     *                  ������
     * @param topic
     *                  ����
     */
    public static void registerEventListener(I_CEventListener listener, String topic) {
        registerEventListener(listener, new String[]{ topic });
    }

    /**
     * ע�������
     * @param listener
     *                  ������
     * @param topics
     *                  ���⣨һ��������Է�����������¼���
     */
    public static void registerEventListener(I_CEventListener listener, String[] topics) {
        if(null == listener || null == topics) {
            return;
        }

        synchronized (mListenerLock) {
            for(String topic : topics) {
//                if(TextUtils.isEmpty(topic)) {
//                    continue;
//                }
                Object obj = mListenerMap.get(topic);
                if(null == obj) {
                    // ��û�м�������ֱ�ӷŵ�Map����
                    mListenerMap.put(topic, listener);
                }else if(obj instanceof I_CEventListener) {
                    // ��һ��������
                    I_CEventListener oldListener = (I_CEventListener) obj;
                    if(listener == oldListener) {
                        // ȥ��
                        continue;
                    }
                    LinkedList<I_CEventListener> list = new LinkedList<I_CEventListener>();
                    list.add(oldListener);
                    list.add(listener);
                    mListenerMap.put(topic, list);
                }else if(obj instanceof List) {
                    // �ж��������
                    LinkedList<I_CEventListener> listenerList = (LinkedList<I_CEventListener>) obj;
                    if(listenerList.indexOf(listener) >= 0) {
                        // ȥ��
                        continue;
                    }
                    listenerList.add(listener);
                }
            }
        }
    }

    /**
     * ע��������
     * @param listener
     *                  ������
     * @param topic
     *                  ע���Ը�����ļ���
     */
    public static void unregisterEventListener(I_CEventListener listener, String topic) {
        unregisterEventListener(listener, new String[]{topic});
    }

    /**
     * ע��������
     * @param listener
     *                  ������
     * @param topics
     *                  ע���Ը����⣨һ��������Է�����������¼����ļ���
     */
    public static void unregisterEventListener(I_CEventListener listener, String[] topics) {
        if(null == listener || null == topics) {
            return;
        }
        synchronized (mListenerLock) {
            for(String topic : topics) {
//                if(TextUtils.isEmpty(topic)) {
//                    continue;
//                }
                Object obj = mListenerMap.get(topic);
                if(null == obj) {
                    continue;
                }else if(obj instanceof I_CEventListener) {
                    // ��һ��������
                    if(obj == listener) {
                        mListenerMap.remove(topic);
                    }
                }else if(obj instanceof List) {
                    // �ж��������
                    LinkedList<I_CEventListener> list = (LinkedList<I_CEventListener>) obj;
                    list.remove(listener);
                }
            }
        }
    }

    /**
     * ͬ���ַ��¼�
     *
     * @param topic
     *              ����
     * @param msgCode
     *              ��Ϣ����
     * @param resultCode
     *              Ԥ������
     * @param obj
     *              �ص���������
     *
     */
    public static void dispatchEvent(String topic, int msgCode, int resultCode, Object obj) {
//        if(!TextUtils.isEmpty(topic)) {
            CEvent event = mPool.get();
            event.topic = topic;
            event.msgCode = msgCode;
            event.resultCode = resultCode;
            event.obj = obj;
            dispatchEvent(event);
//        }
    }

    public static void dispatchEvent(CEvent event) {
        if(mListenerMap.size() == 0) {// û�м�������ֱ���������룬����ִ�����´���
            return;
        }

//        if(null != event && !TextUtils.isEmpty(event.topic)) {
            String topic = event.topic;
            // ֪ͨ�¼������������¼�
            I_CEventListener listener = null;
            LinkedList<I_CEventListener> listenerList = null;

            synchronized (mListenerLock) {
//                Log.d(SYMBOLIC_NAME, "dispatchEvent | topic = " + topic + " msgCode = " + event.msgCode);
                Object obj = mListenerMap.get(topic);
                if(null != obj) {
                    if(obj instanceof I_CEventListener) {
                        listener = (I_CEventListener) obj;
                    }else if(obj instanceof List) {
                        listenerList = (LinkedList<I_CEventListener>) ((LinkedList<I_CEventListener>) obj).clone();
                    }
                }
//            }

            if(null != listener) {
                listener.onCEvent(topic, event.msgCode, event.resultCode, event.obj);
            }else if(null != listenerList) {
                for(I_CEventListener l : listenerList) {
                    l.onCEvent(topic, event.msgCode, event.resultCode, event.obj);
                }
            }

            mPool.returnObj(event);
        }
    }
}
