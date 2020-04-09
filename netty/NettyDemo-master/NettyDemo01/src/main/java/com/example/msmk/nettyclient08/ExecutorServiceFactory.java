package com.example.msmk.nettyclient08;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p>@ProjectName:     NettyChat</p>
 * <p>@ClassName:       ExecutorServiceFactory.java</p>
 * <p>@PackageName:     com.freddy.im</p>
 * <b>
 * <p>@Description:     �̳߳ع��������������������̵߳���</p>
 * </b>
 * <p>@author:          FreddyChen</p>
 * <p>@date:            2019/04/05 05:12</p>
 * <p>@email:           chenshichao@outlook.com</p>
 */
public class ExecutorServiceFactory {

    private ExecutorService bossPool;// �����߳��飬��������
    private ExecutorService workPool;// �����߳��飬��������

    /**
     * ��ʼ��boss�̳߳�
     */
    public synchronized void initBossLoopGroup() {
        initBossLoopGroup(1);
    }

    /**
     * ��ʼ��boss�̳߳�
     * ����
     *
     * @param size �̳߳ش�С
     */
    public synchronized void initBossLoopGroup(int size) {
        destroyBossLoopGroup();
        bossPool = Executors.newFixedThreadPool(size);
    }

    /**
     * ��ʼ��work�̳߳�
     */
    public synchronized void initWorkLoopGroup() {
        initWorkLoopGroup(1);
    }

    /**
     * ��ʼ��work�̳߳�
     * ����
     *
     * @param size �̳߳ش�С
     */
    public synchronized void initWorkLoopGroup(int size) {
        destroyWorkLoopGroup();
        workPool = Executors.newFixedThreadPool(size);
    }

    /**
     * ִ��boss����
     *
     * @param r
     */
    public void execBossTask(Runnable r) {
        if (bossPool == null) {
            initBossLoopGroup();
        }
        bossPool.execute(r);
    }

    /**
     * ִ��work����
     *
     * @param r
     */
    public void execWorkTask(Runnable r) {
        if (workPool == null) {
            initWorkLoopGroup();
        }
        workPool.execute(r);
    }

    /**
     * �ͷ�boss�̳߳�
     */
    public synchronized void destroyBossLoopGroup() {
        if (bossPool != null) {
            try {
                bossPool.shutdownNow();
            } catch (Throwable t) {
                t.printStackTrace();
            } finally {
                bossPool = null;
            }
        }
    }

    /**
     * �ͷ�work�̳߳�
     */
    public synchronized void destroyWorkLoopGroup() {
        if (workPool != null) {
            try {
                workPool.shutdownNow();
            } catch (Throwable t) {
                t.printStackTrace();
            } finally {
                workPool = null;
            }
        }
    }

    /**
     * �ͷ������̳߳�
     */
    public synchronized void destroy() {
        destroyBossLoopGroup();
        destroyWorkLoopGroup();
    }
}
