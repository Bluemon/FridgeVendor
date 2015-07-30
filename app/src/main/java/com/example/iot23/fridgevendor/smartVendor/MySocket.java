package com.example.iot23.fridgevendor.smartVendor;

import android.os.Handler;
import android.os.Message;
import android.provider.BaseColumns;
import android.util.Log;

import com.example.iot23.fridgevendor.handlemessage.Data;
import com.example.iot23.fridgevendor.handlemessage.DivideMessage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by iao on 15. 7. 10.
 */
public class MySocket extends Thread {
    private static final String TAG = "MySocket";
    private Socket mSocket;

    private BufferedReader buffRecv;
    private BufferedWriter buffSend;

    private String  mAddr = "192.168.6.111";
    private int     mPort = 5006;
    private boolean mConnected = false;
    private Handler mHandler = null;

    DivideMessage dMsg;

    public interface MessageTypeClass extends BaseColumns{
        public static final int SOCK_CONNECTED = 1;
        public static final int SOCK_DATA = 2;
        public static final int SOCK_DISCONNECTED = 3;
    }

    public MySocket(Handler handler){
        mHandler = handler;
    }

    private void makeMessage (int type, Object obj){
        Message msg = Message.obtain();
        msg.what = type;
        msg.obj = obj;
        mHandler.sendMessage(msg);
    }

    private boolean connect (){
        try {
            InetSocketAddress socketAddress = new InetSocketAddress(InetAddress.getByName(mAddr), mPort);
            mSocket = new Socket();
            mSocket.connect(socketAddress, 5000);
        } catch (IOException e) {
            Log.e(TAG, e.toString());
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void run() {
        super.run();
        while(!connect())
            Log.i(TAG, "reconnect...");
        if (mSocket == null)
            return;
        Log.i(TAG, "Connected!");
        try {
            buffRecv = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
            buffSend = new BufferedWriter(new OutputStreamWriter(mSocket.getOutputStream()));
            Log.i(TAG, "Buffer Connected!!");
        } catch (IOException e) {
            Log.e(TAG, e.toString());
            e.printStackTrace();
        }
        mConnected = true;
        makeMessage(MessageTypeClass.SOCK_CONNECTED, 0);
        try {
            //String str =buffRecv.readLine();
            char [] str = new char[10000];
            buffRecv.read(str);
            String tmp = new String(str, 0, str.length);
            Log.i(TAG, tmp);
            dMsg = new DivideMessage(tmp);
            ArrayList<Data> arrayList = dMsg.divide();
            dMsg.printArraylist();
            makeMessage(MessageTypeClass.SOCK_DATA, arrayList);

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Log.i(TAG, "close");
            buffRecv.close();
            buffSend.close();
            makeMessage(MessageTypeClass.SOCK_DISCONNECTED, 0);
        } catch (IOException e) {
            Log.e(TAG, e.toString());
            e.printStackTrace();
        }

        mConnected = false;

    }

    synchronized public boolean ismConnected(){
        return mConnected;
    }

    public void sendString(String str) {
        PrintWriter out = new PrintWriter(buffSend, true);
        out.println(str);
    }
}
