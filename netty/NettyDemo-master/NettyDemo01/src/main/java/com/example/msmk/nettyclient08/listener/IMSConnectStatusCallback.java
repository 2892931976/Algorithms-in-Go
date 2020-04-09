package com.example.msmk.nettyclient08.listener;

/**
 * <p>@ProjectName:     NettyChat</p>
 * <p>@ClassName:       IMSConnectStatusCallback.java</p>
 * <p>@PackageName:     com.freddy.im.listener</p>
 * <b>
 * <p>@Description:     IMS����״̬�ص�</p>
 * </b>
 * <p>@author:          FreddyChen</p>
 * <p>@date:            2019/03/31 20:07</p>
 * <p>@email:           chenshichao@outlook.com</p>
 */
public interface IMSConnectStatusCallback {

    /**
     * ims������
     */
    void onConnecting();

    /**
     * ims���ӳɹ�
     */
    void onConnected();

    /**
     * ims����ʧ��
     */
    void onConnectFailed();
}
