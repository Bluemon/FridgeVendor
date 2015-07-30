package com.example.iot23.fridgevendor.handlemessage;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by iao on 15. 7. 20.
 */
public class DivideMessage {
    public static final String TAG = "DivideMessage";

    private ArrayList<Data> dataArrayList;
    String rMsg;

    public DivideMessage(String str) {
        rMsg = str;
    }

    public ArrayList<Data>  divide() {
        int end = rMsg.indexOf('$');                    // 받은 문자의 마지막 인덱스를 가져옴
        String subStr = rMsg.substring(4, end);         // 필요 부분만 자름
        String [] rows = subStr.split("\\|");            // 특수문자로 자를땐 \\을 앞에 넣어 줘야함.

        dataArrayList = new ArrayList<>();

        for(int i = 0; i < rows.length; ++i) {
            String [] columns = rows[i].split("\\*");
            int length = columns.length;
            Data data = new Data(length);
            for(int j = 0; j < length; ++j) {
                data.data[j] = columns[j];
              //  Log.d(TAG, data.data[j]);
            }
            dataArrayList.add(data);
           // Log.e(TAG, rows[i]);
        }

        return  dataArrayList;
    }

    public void printArraylist() {
        for (int i = 0; i < dataArrayList.size(); ++i) {
            Data d = dataArrayList.get(i);
            for (int j = 0; j < d.data.length; ++j)
                Log.d(TAG, d.data[j]);
        }
    }
}
