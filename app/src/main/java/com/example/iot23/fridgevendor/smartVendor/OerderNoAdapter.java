package com.example.iot23.fridgevendor.smartVendor;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.iot23.fridgevendor.handlemessage.Data;


import java.util.ArrayList;

/**
 * Created by iot23 on 15. 7. 27.
 */
public class OerderNoAdapter extends BaseAdapter {
    OrderNoView [] orderNoViews;
    private Context mContext;
    private ArrayList<Data> arrayData;

    // 생성자
    public OerderNoAdapter(Context context) {
        mContext = context;
        arrayData = new ArrayList<Data>();
    }

    @Override
    public int getCount() {
        orderNoViews = new OrderNoView[arrayData.size()];
        return arrayData.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        if (convertView == null) {
            orderNoViews[position] = new OrderNoView(mContext, arrayData.get(position));
        } else {
            orderNoViews[position] = (OrderNoView) convertView;
            orderNoViews[position].setText(0, arrayData.get(position).getData(0));
        }
        Log.i("getview", "get view");
        //Log.i("msg", "log " + arrayData.get(position).getData(0));

        return orderNoViews[position];
    }

    public void addReceiveNo(int position, StringBuilder msg) {
        orderNoViews[position].getReceiveNo(0, msg);
    }

    public void add(ArrayList<Data> arr) {
        for (int i = 0 ; i < arr.size(); ++i) {
            if(!arr.get(i).getData(0).equals("")){
                arrayData.add(arr.get(i));
            }
        }
        Log.i("getview", "add");
    }

    public void clear() {
        arrayData.clear();
    }
}
