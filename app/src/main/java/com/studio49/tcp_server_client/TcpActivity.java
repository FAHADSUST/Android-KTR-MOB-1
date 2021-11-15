package com.studio49.tcp_server_client;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class TcpActivity extends AppCompatActivity implements OnReceivedData{

    EditText portEd, ipEd, msgEd;
    TextView rcvMessage, myIP;
    Button connectB, connectToServerBtn, sendMsgToServerB;

    TcpClient mTcpClient;

    String serverIP, msg;
    int mPort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tcp);

        ((AppCompatActivity)this).getSupportActionBar().setTitle("TCP");

        portEd = (EditText) findViewById(R.id.portED);
        ipEd = (EditText) findViewById(R.id.ip_address);
        msgEd = (EditText) findViewById(R.id.messageEd);

        rcvMessage = (TextView) findViewById(R.id.rcvTxt);
        myIP = (TextView) findViewById(R.id.myIP_Txt);

        connectB = (Button) findViewById(R.id.connectB);
        connectToServerBtn = (Button) findViewById(R.id.sendB);
        sendMsgToServerB = (Button) findViewById(R.id.stop_thread);


        myIP.setText(Utils.getIPAddress(getApplicationContext()));

        connectB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                serverIP = ipEd.getText().toString();
                msg = msgEd.getText().toString();
                mPort = Integer.parseInt(portEd.getText().toString());


                TcpServer rc = null;
                try {
                    rc = new TcpServer(serverIP, mPort, TcpActivity.this);
                    Thread t = new Thread(rc);
                    t.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                connectB.setEnabled(false);
            }
        });

        connectToServerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                serverIP = ipEd.getText().toString();
                msg = msgEd.getText().toString();
                mPort = Integer.parseInt(portEd.getText().toString());

                new TcpActivity.TestAsync().execute();


            }
        });


        sendMsgToServerB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                serverIP = ipEd.getText().toString();
                msg = msgEd.getText().toString();
                mPort = Integer.parseInt(portEd.getText().toString());

                new TcpActivity.TestAsync2(msg).execute();


            }
        });


        ToggleButton toggleButton = (ToggleButton)findViewById(R.id.toggleButton);

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!b){
                    sendMsgToServerB.setVisibility(View.INVISIBLE);
                    connectToServerBtn.setVisibility(View.INVISIBLE);
                    connectB.setVisibility(View.VISIBLE);
                }else{
                    sendMsgToServerB.setVisibility(View.VISIBLE);
                    connectToServerBtn.setVisibility(View.VISIBLE);
                    connectB.setVisibility(View.INVISIBLE);
                }
            }
        });

        if(toggleButton.getText().equals("Server")){
            sendMsgToServerB.setVisibility(View.INVISIBLE);
            connectToServerBtn.setVisibility(View.INVISIBLE);
            connectB.setVisibility(View.VISIBLE);
        }else{
            sendMsgToServerB.setVisibility(View.VISIBLE);
            connectToServerBtn.setVisibility(View.VISIBLE);
            connectB.setVisibility(View.INVISIBLE);
        }

    }

    Handler handler = new Handler();
    @Override
    public void newMsg(String msg) {

        handler.post(new Runnable() {
            public void run() {
                rcvMessage.setText(rcvMessage.getText()+"\n"+msg);
            }
        });

    }

    TcpClient tcpClient = null;

    class TestAsync extends AsyncTask<Void, Integer, String> {
        String TAG = getClass().getSimpleName();


        protected void onPreExecute() {
            super.onPreExecute();
            Log.d(TAG + " PreExceute","On pre Exceute......");
        }

        protected String doInBackground(Void...arg0) {
            try {
                InetAddress inetAddresses = InetAddress.getByName(serverIP);
                try {
                    tcpClient = new TcpClient(inetAddresses, mPort);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }

            return "You are at PostExecute";
        }

    }

    class TestAsync2 extends AsyncTask<Void, Integer, String> {
        String TAG = getClass().getSimpleName();
        String msg;

        public TestAsync2(String msg){
            this.msg = msg;
        }

        protected void onPreExecute() {
            super.onPreExecute();
            Log.d(TAG + " PreExceute","On pre Exceute......");
        }

        protected String doInBackground(Void...arg0) {
            if(msg!= null) {
                try {
                    tcpClient.sendMsg(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return "You are at PostExecute";
        }

    }
}