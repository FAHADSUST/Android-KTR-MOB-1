package com.studio49.tcp_server_client;

import android.util.Log;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class TcpClient{

    public static final String TAG = TcpClient.class.getSimpleName();

    private Socket socket;
    //private Scanner scanner;
    TcpClient(InetAddress serverAddress, int serverPort) throws Exception {
        this.socket = new Socket(serverAddress, serverPort);
        //this.scanner = new Scanner(System.in);
    }

    public void sendMsg(String input) throws IOException {
        PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
        out.println(input);
        out.flush();
    }

}
