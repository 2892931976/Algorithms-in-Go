package com.example.msmk.nettychat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.msmk.nettychat.bean.SingleMessage;
import com.example.msmk.nettychat.event.CEvent;
import com.example.msmk.nettychat.event.CEventCenter;
import com.example.msmk.nettychat.event.Events;
import com.example.msmk.nettychat.event.I_CEventListener;
import com.example.msmk.nettychat.im.IMSClientBootstrap;
import com.example.msmk.nettychat.im.MessageProcessor;
import com.example.msmk.nettychat.im.MessageType;
import com.example.msmk.nettychat.utils.CThreadPoolExecutor;

import java.util.UUID;

public class MainActivity extends AppCompatActivity implements I_CEventListener {

    private EditText mEditText;
    private TextView mTextView;

    String userId = "100001";
    String token = "token_" + userId;
    String hosts = "[{\"host\":\"192.168.1.132\", \"port\":8857}]";

    private static final String[] EVENTS = {
            Events.CHAT_SINGLE_MESSAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditText = findViewById(R.id.et_content);
        mTextView = findViewById(R.id.tv_msg);

        IMSClientBootstrap.getInstance().init(userId, token, hosts, 1);

        CEventCenter.registerEventListener(this, EVENTS);
    }

    public void sendMsg(View view) {
        SingleMessage message = new SingleMessage();
        message.setMsgId(UUID.randomUUID().toString());
        message.setMsgType(MessageType.SINGLE_CHAT.getMsgType());
        message.setMsgContentType(MessageType.MessageContentType.TEXT.getMsgContentType());
        message.setFromId(userId);
        message.setToId("100002");
        message.setTimestamp(System.currentTimeMillis());
        message.setContent(mEditText.getText().toString());

        MessageProcessor.getInstance().sendMsg(message);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CEventCenter.unregisterEventListener(this, EVENTS);
    }

    @Override
    public void onCEvent(String topic, int msgCode, int resultCode, Object obj) {
        switch (topic) {
            case Events.CHAT_SINGLE_MESSAGE: {
                final SingleMessage message = (SingleMessage) obj;
                CThreadPoolExecutor.runOnMainThread(new Runnable() {

                    @Override
                    public void run() {
                        mTextView.setText(message.getContent());
                    }
                });
                break;
            }

            default:
                break;
        }
    }
}
