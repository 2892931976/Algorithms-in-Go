package com.example.msmk.nettyclient08.event;

/**
 * �¼�������
 *
 * Created by Freddy on 2015/11/3.
 * chenshichao@outlook.com
 */
public interface I_CEventListener {

    /**
     * �¼��ص�����<br />
     * <b>ע�⣺</b><br />
     * ��� obj ʹ���˶���أ��� socket �¼��Ķ��󣩣�<br />
     * ��ô�¼���ɺ�obj ���Զ����յ�����أ��벻Ҫ�������̼߳���ʹ�ã�������ܻᵼ�����ݲ�����
     *
     * @param topic
     *              �¼�����
     * @param msgCode
     *              ��Ϣ����
     * @param resultCode
     *              Ԥ������
     * @param obj
     *              ���ݶ���
     */
    void onCEvent(String topic, int msgCode, int resultCode, Object obj);
}
