package com.example.iot23.fridgevendor.smartVendor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.iot23.fridgevendor.R;
import com.example.iot23.fridgevendor.handlemessage.Data;
import com.example.iot23.fridgevendor.handlemessage.HandleMessage;

import java.util.ArrayList;


public class SendReceiveListActivity extends Activity {

    String orderNo;
    ListView listView1;
    Adapter adapter;
    ArrayList<Data> arrayData;
    MySocket socket;
    MySocket sendSocket;
    MySocket sendReceiveSocket;
    HandleMessage sendMsg;
    HandleMessage sendOrderMsg;
    StringBuilder orderMsg =  new StringBuilder("");
    StringBuilder getMsg;
    StringBuilder receiveMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_receive_list);
        Intent intent = getIntent();
        orderNo = intent.getStringExtra("0");

        socket = new MySocket(handler);
        socket.start();

        // 리스트뷰 객체 참조
        listView1 = (ListView)findViewById(R.id.receiveListView);
        arrayData = new ArrayList<Data>();

        // 어댑터 객체 생성
        adapter = new Adapter(this);

         listView1.setAdapter(adapter); // 리스트뷰에 어댑터 설정

        sendMsg = new HandleMessage();

//        makeOrderNo = new MakeOrderNo();
//        orderMonthMsg = makeOrderNo.getOrderNo("yyyyMM");
    }

    public void onSendButtonClicked(View view) {
        int count = listView1.getCount();
        receiveMsg = new StringBuilder("");
        for(int i = 0; i < count; ++i) {
            getMsg = new StringBuilder(orderNo+",");
            adapter.addText(i, getMsg);
            receiveMsg.append(getMsg);
            if(i != count-1) {
                receiveMsg.append(",");
            }
        }

        Log.i("send : ", receiveMsg.toString());

        sendSocket = new MySocket(sendHandler);
        sendSocket.start();


    }

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case MySocket.MessageTypeClass.SOCK_CONNECTED:
                    Log.i("send receive list", "connected");
                    String command = sendMsg.makeSendMessage("3b", orderNo);
                    socket.sendString(command);
                    Log.i("send receive list", "send msg");
                    break;
                case MySocket.MessageTypeClass.SOCK_DATA:
                    adapter.add((ArrayList<Data>) msg.obj);
                    adapter.notifyDataSetChanged();
                    Log.i("send receive list", "receive socket data");
                    break;
                case MySocket.MessageTypeClass.SOCK_DISCONNECTED:
                    Log.i("send receive list", "disconnected");
                    adapter.notifyDataSetChanged();
                    break;
            }
            return false;
        }
    });

    Handler sendHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case MySocket.MessageTypeClass.SOCK_CONNECTED:
                    Log.i("send receive list", "connected");
                    String command = sendMsg.makeSendMessage("3c", orderNo);
                    sendSocket.sendString(command);
                    Log.i("send receive list", "send msg");
                    break;
                case MySocket.MessageTypeClass.SOCK_DATA:
                    Log.i("send receive list", "receive");
                    sendReceiveSocket = new MySocket(sendReceiveHandler);
                    sendReceiveSocket.start();
                    break;
                case MySocket.MessageTypeClass.SOCK_DISCONNECTED:
                    Log.i("send receive list", "disconnected");
                    //adapter.notifyDataSetChanged();
                    break;
            }
            return false;
        }
    });

    Handler sendReceiveHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case MySocket.MessageTypeClass.SOCK_CONNECTED:
                    Log.i("send receive list", "connected");
                    String command = sendMsg.makeSendMessage("3d", receiveMsg.toString());
                    sendReceiveSocket.sendString(command);
                    Log.i("send receive list", "send msg");
                    break;
                case MySocket.MessageTypeClass.SOCK_DATA:
                    Log.i("send receive listr", "receive");
                    finish();
                    break;
                case MySocket.MessageTypeClass.SOCK_DISCONNECTED:
                    Log.i("send receive list", "disconnected");
                    //adapter.notifyDataSetChanged();
                    break;
            }
            return false;
        }
    });

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent();
        setResult(RESULT_OK);
    }
}
