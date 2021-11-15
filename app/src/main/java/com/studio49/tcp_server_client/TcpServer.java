package com.studio49.tcp_server_client;

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpServer implements Runnable {

    private static final String TAG = "TcpServerThread";

    private ServerSocket server;
    Socket client = null;
    String clientAddress;
    BufferedReader in = null;
    OnReceivedData onReceivedData;

    public TcpServer(String ipAddress, int port, OnReceivedData onReceivedData) throws Exception {
        if (ipAddress != null && !ipAddress.isEmpty())
            this.server = new ServerSocket(port, 1, InetAddress.getByName(ipAddress));
        else
            this.server = new ServerSocket(port, 1, InetAddress.getLocalHost());

        this.onReceivedData = onReceivedData;

    }


    @Override
    public void run() {

            try {
                client = this.server.accept();
                clientAddress = client.getInetAddress().getHostAddress();
                in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                Log.e(TAG, "\r\nNew connection from " + clientAddress);

                listen();
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, e.toString());
            }

    }

    private void listen() throws Exception {
        String data = null;

        while (true) {
            try {
                data = in.readLine();
                if ((data == null) || data.equalsIgnoreCase("EXIT")) {
                    client.close();
                    Log.e(TAG, "Socket is closing");
                    return;
                } else {
                   //handle(line);
                    onReceivedData.newMsg(data);
                    Log.e(TAG, "\r\nMessage from " + clientAddress + ": " + data);
                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
    }

    public InetAddress getSocketAddress() {
        return this.server.getInetAddress();
    }

    public int getPort() {
        return this.server.getLocalPort();
    }


}
