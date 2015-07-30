package com.example.iot23.fridgevendor.smartVendor;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.iot23.fridgevendor.handlemessage.Data;

import java.util.ArrayList;

/**
 * Created by iot23 on 15. 7. 30.
 */
public class Adapter extends BaseAdapter{

    AdapterView [] adapterViews;
    private Context mContext;
    private ArrayList<Data> arrayData;

    // 생성자
    public Adapter(Context context) {

        mContext = context;
        arrayData = new ArrayList<Data>();

    }

    public void setArrayData(ArrayList<Data> arrD) {
        this.arrayData = arrD;
    }

    @Override
    public int getCount() {
        adapterViews = new AdapterView[arrayData.size()];
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
            adapterViews[position] = new AdapterView(mContext, arrayData.get(position));
        } else {
            adapterViews[position] = (AdapterView) convertView;

            adapterViews[position].setText(0, arrayData.get(position).getData(0));
            adapterViews[position].setText(1, arrayData.get(position).getData(1));
            adapterViews[position].setText(2, arrayData.get(position).getData(2));
        }
        Log.e("getview", "시발");
        //Log.i("msg", "log " + arrayData.get(position).getData(0) + " " + arrayData.get(position).getData(1) + arrayData.get(position).getData(2) + " ");

        return adapterViews[position];
    }

    public void addText(int position, StringBuilder msg) {
        adapterViews[position].getText(0, msg);
        adapterViews[position].getText(1, msg);
        adapterViews[position].getText(2, msg);
    }

    public void addStandardText(int position, StringBuilder msg) {
        adapterViews[position].getStandardText(0, msg);
        adapterViews[position].getStandardText(1, msg);
        adapterViews[position].getStandardText(2, msg);
    }

    public void add(ArrayList<Data> arr) {
        for (int i = 0 ; i < arr.size(); ++i) {
            arrayData.add(arr.get(i));
        }

        Log.e("getview", "슈발");
    }
}
