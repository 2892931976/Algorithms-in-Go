package com.example.msmk.nettydemo07;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final int PORT = 9999;
    public static void main(String[] args) {

        try {
            // Create ServerSocket instance and bind it to port 9999
            ServerSocket server = new ServerSocket(PORT);
            while (true) {
                Socket socket = server.accept();
                // Get output buffer
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream()));
                // Write output
                writer.write("这是一段来自服务器的问候：Hello沃德！");
                writer.flush();
                writer.close();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
