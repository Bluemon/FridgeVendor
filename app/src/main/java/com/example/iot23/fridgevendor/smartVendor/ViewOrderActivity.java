package com.example.iot23.fridgevendor.smartVendor;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.iot23.fridgevendor.R;
import com.example.iot23.fridgevendor.handlemessage.Data;
import com.example.iot23.fridgevendor.handlemessage.HandleMessage;

import java.util.ArrayList;


public class ViewOrderActivity extends Activity {

    ListView listView1;
    OerderNoAdapter oerderNoAdapter;
    //OrderItemAdapter ad
    ArrayList<Data> arrayData;
    MySocket socket;
    MySocket sendSocket;
    HandleMessage sendMsg;
    HandleMessage sendOrderMsg;
    StringBuilder orderMsg =  new StringBuilder("");
    StringBuilder getMsg;
    StringBuilder receiveNumber;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order);

        socket = new MySocket(handler);
        socket.start();

        // 리스트뷰 객체 참조
        listView1 = (ListView)findViewById(R.id.listViewOrder);
        arrayData = new ArrayList<Data>();

        // 어댑터 객체 생성
        oerderNoAdapter = new OerderNoAdapter(this);

        listView1.setAdapter(oerderNoAdapter); // 리스트뷰에 어댑터 설정

        sendMsg = new HandleMessage();

        receiveNumber = new StringBuilder("");

    }

    public void onButtonOrderNoSelectClicked(View view) {
        Intent intent;
        intent = new Intent(getBaseContext(), SendReceiveListActivity.class);
        receiveNumber = new StringBuilder("");
        oerderNoAdapter.addReceiveNo(0, receiveNumber);
        intent.putExtra("0", receiveNumber.toString());
        startActivityForResult(intent, 1);

    }
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case MySocket.MessageTypeClass.SOCK_CONNECTED:
                    Log.i("View Order :", "connected");
                    String command = sendMsg.makeSendMessage("3a");
                    socket.sendString(command);
                    break;
                case MySocket.MessageTypeClass.SOCK_DATA:
                    Log.e("View Order", "socket data");

                    //if(!((ArrayList<Data>) msg.obj).get(0).getData(0).equals("")) {
                        oerderNoAdapter.add((ArrayList<Data>) msg.obj);
                    //}
                    oerderNoAdapter.notifyDataSetChanged();
                    Log.e("View Order", "receive socket data");
                    break;
                case MySocket.MessageTypeClass.SOCK_DISCONNECTED:
                    Log.i("View Order :", "disconnected");
                    oerderNoAdapter.notifyDataSetChanged();
                    break;
            }
            return false;
        }
    });

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        socket = new MySocket(handler);
        socket.start();
    }
}
