package com.example.msmk.nettyclient08.handler;

import com.example.msmk.nettyclient08.bean.AppMessage;

/**
 * <p>@ProjectName:     NettyChat</p>
 * <p>@ClassName:       IMessageHandler.java</p>
 * <p>@PackageName:     com.freddy.chat.im.handler</p>
 * <b>
 * <p>@Description:     ������</p>
 * </b>
 * <p>@author:          FreddyChen</p>
 * <p>@date:            2019/04/10 03:41</p>
 * <p>@email:           chenshichao@outlook.com</p>
 */
public interface IMessageHandler {

    void execute(AppMessage message);
}
