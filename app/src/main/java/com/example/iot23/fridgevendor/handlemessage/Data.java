package com.example.iot23.fridgevendor.handlemessage;

import android.provider.BaseColumns;

/**
 * Created by iao on 15. 7. 20.
 */
public class Data implements BaseColumns {
    public String [] data;

    public Data (int cnt) {
        data = new String[cnt];

        for (int i = 0; i < cnt; ++i)
            data[i] = "";
    }

    public String getData(int i) {
        return data[i];
    }

}