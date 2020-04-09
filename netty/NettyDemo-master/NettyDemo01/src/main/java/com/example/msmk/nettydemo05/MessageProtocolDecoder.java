package com.example.msmk.nettydemo05;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by XiChuan on 2018-11-06.
 */


public class MessageProtocolDecoder extends ByteToMessageDecoder {

    /**
     * <pre>
     * 协议开始的标准head_data，int类型，占据4个字节.
     * 表示数据的长度contentLength，int类型，占据4个字节.
     * </pre>
     */
    public final int BASE_LENGTH = 4 + 4;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buffer,
                          List<Object> out) throws Exception {
        // 可读长度必须大于基本长度
        //System.out.println("buff的可读长度是："+buffer.readableBytes());
        if (buffer.readableBytes() >= BASE_LENGTH) {
            // 防止socket字节流攻击
            // 防止，客户端传来的数据过大
            // 因为，太大的数据，是不合理的
            if (buffer.readableBytes() > 2048) {
                buffer.skipBytes(buffer.readableBytes());
            }

            // 记录包头开始的index
            int beginReader;

            while (true) {
                // 获取包头开始的index
                beginReader = buffer.readerIndex();
                //System.out.println("记录包头开始的index:"+beginReader);
                // 标记包头开始的index
                buffer.markReaderIndex();
                // 读到了协议的开始标志，结束while循环
                int head = buffer.readInt();
                //System.out.println("读取的int："+head);
                if (head == Constant.HEAD) {
                    break;
                }

                // 未读到包头，略过一个字节
                // 每次略过，一个字节，去读取，包头信息的开始标记
                buffer.resetReaderIndex();
                buffer.readByte();

                // 当略过，一个字节之后，
                // 数据包的长度，又变得不满足
                // 此时，应该结束。等待后面的数据到达
                if (buffer.readableBytes() < BASE_LENGTH) {
                    return;
                }
            }

            //类型长度
            int typeLength = buffer.readInt();
            //消息类型
            byte[] typeBytes = new byte[typeLength];
            buffer.readBytes(typeBytes);
            String type = new String(typeBytes);

            // 内容长度
            int dataLength = buffer.readInt();
            // 读取data数据
            byte[] data = new byte[dataLength];
            buffer.readBytes(data);

            MessageProtocol protocol = new MessageProtocol(typeLength, type, dataLength, data);
            out.add(protocol);
        }
    }
}
