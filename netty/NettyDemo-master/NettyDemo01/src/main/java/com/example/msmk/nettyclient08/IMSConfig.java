package com.example.msmk.nettyclient08;

/**
 * <p>@ProjectName:     NettyChat</p>
 * <p>@ClassName:       IMSConfig.java</p>
 * <p>@PackageName:     com.freddy.im</p>
 * <b>
 * <p>@Description:     IMSĬ�����ã�����ʹ��Ĭ�����ã�Ӧ�ṩset������Ӧ�ò�����</p>
 * </b>
 * <p>@author:          FreddyChen</p>
 * <p>@date:            2019/04/05 05:38</p>
 * <p>@email:           chenshichao@outlook.com</p>
 */
public class IMSConfig {

    // Ĭ������һ������ʧ�ܼ��ʱ��
    public static final int DEFAULT_RECONNECT_INTERVAL = 3 * 1000;
    // ���ӳ�ʱʱ��
    public static final int DEFAULT_CONNECT_TIMEOUT = 10 * 1000;
    // Ĭ��һ��������������
    public static final int DEFAULT_RECONNECT_COUNT = 3;
    // Ĭ��������ʼ��ʱʱ���������������n�Σ�ÿ����ʱn * ��ʼ��ʱʱ�������������ﵽn�κ�����
    public static final int DEFAULT_RECONNECT_BASE_DELAY_TIME = 3 * 1000;
    // Ĭ����Ϣ����ʧ���ط�����
    public static final int DEFAULT_RESEND_COUNT = 3;
    // Ĭ����Ϣ�ط����ʱ��
    public static final int DEFAULT_RESEND_INTERVAL = 8 * 1000;
    // Ĭ��Ӧ����ǰ̨ʱ������Ϣ���ʱ��
    public static final int DEFAULT_HEARTBEAT_INTERVAL_FOREGROUND = 3 * 1000;
    // Ĭ��Ӧ���ں�̨ʱ������Ϣ���ʱ��
    public static final int DEFAULT_HEARTBEAT_INTERVAL_BACKGROUND = 30 * 1000;
    // Ӧ����ǰ̨��ʶ
    public static final int APP_STATUS_FOREGROUND = 0;
    // Ӧ���ں�̨��ʶ
    public static final int APP_STATUS_BACKGROUND = -1;
    public static final String KEY_APP_STATUS = "key_app_status";
    // Ĭ�Ϸ���˷��ص���Ϣ���ͳɹ�״̬����
    public static final int DEFAULT_REPORT_SERVER_SEND_MSG_SUCCESSFUL = 1;
    // Ĭ�Ϸ���˷��ص���Ϣ����ʧ��״̬����
    public static final int DEFAULT_REPORT_SERVER_SEND_MSG_FAILURE = 0;
    // ims����״̬��������
    public static final int CONNECT_STATE_CONNECTING = 0;
    // ims����״̬�����ӳɹ�
    public static final int CONNECT_STATE_SUCCESSFUL = 1;
    // ims����״̬������ʧ��
    public static final int CONNECT_STATE_FAILURE = -1;
}
