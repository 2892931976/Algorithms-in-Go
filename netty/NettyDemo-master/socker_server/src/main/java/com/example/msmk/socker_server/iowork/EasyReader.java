package com.example.msmk.socker_server.iowork;

import com.example.msmk.socker_server.HandlerIO;
import com.example.msmk.socker_server.entity.DefaultReaderProtocol;
import com.example.msmk.socker_server.entity.IReaderProtocol;
import com.example.msmk.socker_server.entity.OriginReadData;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Author��Alex
 * Date��2019/6/1
 * Note��
 */
public class EasyReader implements IReader {
    /**
     * ������
     */
    private InputStream inputStream;

    /**
     * ��ȡ����ʱ��û����Ĳ������ݻ���
     */
    private ByteBuffer remainingBuf;
    /**
     * �������߳�
     */
    private Thread readerThread;

    private Socket socket;

    private boolean isShutdown;
    /**
     * ������յ�����
     */
    private HandlerIO handlerIO;

    public EasyReader(InputStream inputStream, Socket socket,HandlerIO handlerIO) {
        this.inputStream = inputStream;
        this.socket = socket;
        this.handlerIO=handlerIO;
    }

    @Override
    public void read() {
        OriginReadData originalData = new OriginReadData();
        IReaderProtocol headerProtocol = new DefaultReaderProtocol();
        int headerLength = headerProtocol.getHeaderLength(); //Ĭ�ϵİ�ͷ������4���ֽ�
        ByteBuffer headBuf = ByteBuffer.allocate(headerLength); //��ȡ���ݰ�ͷ�Ļ���
        headBuf.order(ByteOrder.BIG_ENDIAN);

        //���ȶ�ȡ���ݵ�header=====>>>
        try {
            //����������
            if (remainingBuf != null) {
                //flip������buffer���������䣬��limit�ƶ���buffer�����ݴ�С����λ�ã���position��Ϊ0�������Ϳ��Դ�0���λ�ÿ�ʼ��ȡ���ݣ�ֱ��limit��С��λ�ã�limit����buffer�������ݵĴ�С
                remainingBuf.flip();
                int length = Math.min(remainingBuf.remaining(), headerLength);
                //������Ϊlength������д��headBuf��
                headBuf.put(remainingBuf.array(), 0, length);
                if (length < headerLength) { //����һ��header
                    //there are no data left
                    remainingBuf = null;
                    //�ٴ�stream�ж�ȡheaderʣ�µĳ���
                    readHeaderFromSteam(headBuf, headerLength - length);
                } else { //��ȡheader֮������������
                    remainingBuf.position(headerLength); //�ƶ�ָ��λ��
                }
            }
            //û����������
            else {
                readHeaderFromSteam(headBuf, headBuf.capacity());
            }
            //��header��ֵ��ԭʼ������
            originalData.setHeaderData(headBuf.array());

            // ��ʼ��ȡbody����=====>>>
            int bodyLength = headerProtocol.getBodyLength(originalData.getHeaderData(), ByteOrder.BIG_ENDIAN);

            if (bodyLength > 0) {
                if (bodyLength > 5 * 1024 * 1024) { //�Ƿ�������Ķ�ȡ��
                    throw new RuntimeException("���������صĵ������ݵĴ�С�Ѿ������˹涨�����ֵ��Ϊ�˷�ֹ�ڴ��������淶�����Э��");
                }
                ByteBuffer byteBuffer = ByteBuffer.allocate(bodyLength);
                byteBuffer.order(ByteOrder.BIG_ENDIAN);
                //����������δ��ȡ
                if (remainingBuf != null) {
                    int bodyStartPosition = remainingBuf.position();
                    int length = Math.min(remainingBuf.remaining(), bodyLength);
                    //�������������ж�ȡ
                    byteBuffer.put(remainingBuf.array(), bodyStartPosition, length);
                    //�ƶ�positionλ��
                    remainingBuf.position(bodyStartPosition + length);

                    //��ʾ�������ݵĴ��ڴ��ڻ����body���ݵĴ�С
                    if (length == bodyLength) {
                        if (remainingBuf.remaining() > 0) { //������������
                            ByteBuffer temp = ByteBuffer.allocate(remainingBuf.remaining());
                            temp.order(ByteOrder.BIG_ENDIAN);
                            temp.put(remainingBuf.array(), remainingBuf.position(), remainingBuf.remaining());
                            remainingBuf = temp;
                        } else { //there are no data left
                            remainingBuf = null;
                        }

                        //����ȡ��body���ݸ�ֵ��ԭʼ����
                        originalData.setBodyData(byteBuffer.array());
                        //�ַ�����
                        handlerIO.handReceiveMsg(originalData.getBodyString());
                        //actionDispatch.dispatchAction(IOAction.ACTION_READ_COMPLETE, originalData);
                        //�˴ζ�ȡ������return
                        return;
                    }
                    //û������������
                    else { //there are no data left in buffer and some data pieces in channel
                        remainingBuf = null;
                    }
                }
                //������stream�ж�
                readBodyFromStream(byteBuffer);
                originalData.setBodyData(byteBuffer.array()); //��body���ݸ�ֵ
            }
            //����body����Ϊ0
            else if (bodyLength == 0) {
                originalData.setBodyData(new byte[0]);
                if (remainingBuf != null) {
                    //the body is empty so header remaining buf need set null
                    if (remainingBuf.hasRemaining()) {
                        ByteBuffer temp = ByteBuffer.allocate(remainingBuf.remaining());
                        temp.order(ByteOrder.BIG_ENDIAN);
                        temp.put(remainingBuf.array(), remainingBuf.position(), remainingBuf.remaining());
                        remainingBuf = temp;
                    } else {
                        remainingBuf = null;
                    }
                }
            } else if (bodyLength < 0) {
                throw new RuntimeException("��ȡʧ�ܣ���ȡ�������ݳ���С��0�������Ƕ�ȡ�Ĺ����и�socket���������Ͽ�������");
            }
            //����ȡ��һ���������ݷ�����ȥ
            handlerIO.handReceiveMsg(originalData.getBodyString());
            //actionDispatch.dispatchAction(IOAction.ACTION_READ_COMPLETE, originalData);
        } catch (Exception e) {
            e.printStackTrace();
            closeReader();
        }
    }

    @Override
    public void openReader() {
        isShutdown = false;
        readerThread = new Thread(readerTask, "reader thread");
        readerThread.start();
    }

    /**
     * ��ȡ����������
     */
    private Runnable readerTask = new Runnable() {
        @Override
        public void run() {
            while (socket.isConnected() && !isShutdown) {
                read();
            }
        }
    };


    private void readHeaderFromSteam(ByteBuffer headBuf, int readLength) throws IOException {
        for (int i = 0; i < readLength; i++) {
            byte[] bytes = new byte[1];
            int value = inputStream.read(bytes); //���������ж�ȡ��Ӧ���ȵ�����
            if (value == -1) {
                throw new RuntimeException("��ȡ���ݵİ�ͷʧ�ܣ���" + value + "λ�öϿ��ˣ���������Ϊsocket���������Ͽ�������");
            }
            headBuf.put(bytes);
        }
    }

    private void readBodyFromStream(ByteBuffer byteBuffer) throws IOException {
        //body��С�ǻ���buffer�Ƿ���ʣ��ռ�
        while (byteBuffer.hasRemaining()) {
            try {
                byte[] bufArray = new byte[50]; //�ӷ������е��ζ�ȡ�Ļ������ݴ�С
                int len = inputStream.read(bufArray);
                if (len == -1) {
                    break;
                }
                int remaining = byteBuffer.remaining();
                if (len > remaining) { //������
                    byteBuffer.put(bufArray, 0, remaining);
                    remainingBuf = ByteBuffer.allocate(len - remaining);
                    remainingBuf.order(ByteOrder.BIG_ENDIAN);
                    remainingBuf.put(bufArray, remaining, len - remaining);
                } else { //��û�����߸ոպ�
                    byteBuffer.put(bufArray, 0, len);
                }
            } catch (Exception e) {
                throw e;
            }
        }
    }

    @Override
    public void closeReader() {
        try {
            if (inputStream != null)
                inputStream.close();
            shutDownThread();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setOption(Object o) {

    }

    private void shutDownThread() {
        isShutdown = true;
        if (readerThread != null && readerThread.isAlive() && !readerThread.isInterrupted()) {
            readerThread.interrupt();
        }
    }
}
