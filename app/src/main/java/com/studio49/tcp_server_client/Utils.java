package com.studio49.tcp_server_client;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;


//import org.apache.http.conn.util.InetAddressUtils;

public class Utils {

    public static String getIPAddress(Context context) {
        try {

            WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
            return ip;
        } catch (Exception ignored) { } // for now eat exceptions
        return "No IP";
    }

}
