package com.studio49.tcp_server_client;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class UdpServer implements Runnable {

    private static final String TAG = "TcpServerThread";

    private DatagramSocket ds;
    OnReceivedData onReceivedData;

    public UdpServer(String ipAddress, int port, OnReceivedData onReceivedData) throws Exception {
        ds = new DatagramSocket(port);
        this.onReceivedData = onReceivedData;
    }


    @Override
    public void run() {

            try {
                listen();
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, e.toString());
            }

    }

    private void listen() throws Exception {
        byte[] receive = new byte[65535];
        DatagramPacket DpReceive = null;
        while (true)
        {

            // Step 2 : create a DatgramPacket to receive the data.
            DpReceive = new DatagramPacket(receive, receive.length);

            // Step 3 : revieve the data in byte buffer.
            ds.receive(DpReceive);

            String msg = data(receive).toString();
            Log.e(TAG, "Client:-" + msg);

            onReceivedData.newMsg(msg);
            // Exit the server if the client sends "bye"
            if (data(receive).toString().equals("bye"))
            {
                Log.e(TAG, "Client sent bye.....EXITING");
                break;
            }

            // Clear the buffer after every message.
            receive = new byte[65535];
        }
    }

    
    public static StringBuilder data(byte[] a)
    {
        if (a == null)
            return null;
        StringBuilder ret = new StringBuilder();
        int i = 0;
        while (a[i] != 0)
        {
            ret.append((char) a[i]);
            i++;
        }
        return ret;
    }

}
