package com.example.msmk.nettydemo05;


//import ch.qos.logback.core.encoder.ByteArrayUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by XiChuan on 2018-11-07.
 */

public class MessageProtocolEncoder extends MessageToByteEncoder<MessageProtocol> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, MessageProtocol msg, ByteBuf byteBuf) throws Exception {

        // 写入消息SmartCar的具体内容
        // 1.写入消息的开头的信息标志(int类型)
        byteBuf.writeInt(msg.getHeadData());
        //2写入消息类型长度信息(int)
        byteBuf.writeInt(msg.getTypeLength());
        //3写入消息类型(byte[])
        byteBuf.writeBytes(msg.getType().getBytes());
        // 4.写入消息的长度(int 类型)
        byteBuf.writeInt(msg.getContentLength());
        // 5.写入消息的内容(byte[]类型)
        byteBuf.writeBytes(msg.getContent());
    }
}
