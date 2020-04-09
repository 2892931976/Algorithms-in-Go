package com.example.msmk.nettyclient07;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {

    /** Called when the activity is first created. */
    private Button btn_receive;
    private TextView txt;
    private String line;

    private static final String HOST = "192.168.1.132";
    private static final int PORT = 9999;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initControl();
    }

    private void initControl() {
        btn_receive = (Button) findViewById(R.id.btn_receive);
        txt = (TextView) findViewById(R.id.txt);
        btn_receive.setOnClickListener(new ReceiverListener());
    }


    @SuppressLint("HandlerLeak")
    class ReceiverListener implements OnClickListener {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            new Thread() {
                @Override
                public void run() {
                    try {
                        // Create Socket instance
                        Socket socket = new Socket(HOST, PORT);
                        // Get input buffer
                        BufferedReader br = new BufferedReader(
                                new InputStreamReader(socket.getInputStream()));
                        line = br.readLine();
                        br.close();
                    } catch (UnknownHostException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    handler.sendEmptyMessage(0);
                }
            }.start();
        }

    }

    // Define Handler object
    private Handler handler = new Handler() {
        @Override
        // When there is message, execute this method
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            // Update UI
            txt.setText(line);
            Log.i("PDA", "----->" + line);
        }
    };
}
