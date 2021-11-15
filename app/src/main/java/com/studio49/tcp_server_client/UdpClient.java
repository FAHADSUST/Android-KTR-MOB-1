package com.studio49.tcp_server_client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

public class UdpClient {

    public static final String TAG = UdpClient.class.getSimpleName();

    private DatagramSocket ds;
    InetAddress ip;
    int port;
    //private Scanner scanner;
    UdpClient(InetAddress serverAddress, int serverPort) throws Exception {
        ds = new DatagramSocket();
        this.ip = serverAddress;
        this.port = serverPort;
        // loop while user not enters "bye"

    }

    public void sendMsg(String inp) throws IOException {

        byte buf[] = null;

        // convert the String input into the byte array.
        buf = inp.getBytes();

        // Step 2 : Create the datagramPacket for sending
        // the data.
        DatagramPacket DpSend =
                new DatagramPacket(buf, buf.length, ip, port);

        // Step 3 : invoke the send call to actually send
        // the data.
        ds.send(DpSend);

        // break the loop if user enters "bye"
        if (inp.equals("bye"))
            return;
    }

}
