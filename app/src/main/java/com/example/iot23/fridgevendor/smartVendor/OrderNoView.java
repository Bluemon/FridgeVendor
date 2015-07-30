package com.example.iot23.fridgevendor.smartVendor;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.iot23.fridgevendor.R;
import com.example.iot23.fridgevendor.handlemessage.Data;


/**
 * Created by iot23 on 15. 7. 27.
 */
public class OrderNoView extends LinearLayout{
    private TextView mText0;
    private Button select;
//    ViewSwitcher viewSwitcher;
//    private int flag = 0;   // 0: receive number View    // 1: receive list

    public OrderNoView(final Context context, final Data aItem) {
        super(context);

        // Layout Inflation, 메모리 객체
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.select_order_no_list_fomat, this, true);

        // Set Text 01
        mText0 = (TextView) findViewById(R.id.dataOrderNo);
        mText0.setText(aItem.getData(0));

        select = (Button) findViewById(R.id.orderNoSelectButton);
    }

    public void setText(int index, String data) { // 데이터를 각 텍스트뷰에 설정
        if(index == 0)
            mText0.setText(data);
        else
            throw new IllegalArgumentException();
    }

    public void getReceiveNo(int index, StringBuilder msg) { // 데이터를 각 텍스트뷰에 설정

        if(index == 0) {
            String str = mText0.getText().toString();
            msg.append(str);
        }
        else
            throw new IllegalArgumentException();
    }
}
