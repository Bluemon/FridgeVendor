package com.example.iot23.fridgevendor.smartVendor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.iot23.fridgevendor.R;
import com.example.iot23.fridgevendor.handlemessage.Data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by iot23 on 15. 7. 30.
 */
public class AdapterView extends LinearLayout{
    private TextView mText00;
    private TextView mText01;
    private TextView mText02;

    private Button mAddAmount;
    private Button mSubAmount;

    public AdapterView(final Context context, final Data aItem) {
        super(context);

        // Layout Inflation, 메모리 객체
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.order_item_list_fomat, this, true);

        // Set Icon
//		mIcon = (ImageView) findViewById(R.id.iconItem);
//		mIcon.setImageDrawable(aItem.getIcon());

        // Set Text 00
        mText00 = (TextView) findViewById(R.id.dataItem00);
        mText00.setText(aItem.getData(0));

        // Set Text 01
        mText01 = (TextView) findViewById(R.id.dataItem01);
        mText01.setText(aItem.getData(1));

        // Set Text 02
        mText02 = (TextView) findViewById(R.id.dataItem02);
        mText02.setText(aItem.getData(2));

        mAddAmount = (Button) findViewById(R.id.addAmount);
        mSubAmount = (Button) findViewById(R.id.subAmount);

        mAddAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double amount = Double.parseDouble(mText02.getText().toString());
                if(amount <= 29) {
                    mText02.setText(String.valueOf(amount+1.0));
                } else {
                    mText02.setText(String.valueOf(30));
                }

            }
        });

        mSubAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double amount = Double.parseDouble(mText02.getText().toString());
                if(amount >= 1) {
                    mText02.setText(String.valueOf(amount-1.0));
                } else {
                    mText02.setText(String.valueOf(0));
                }
            }
        });
    }

    public void setText(int index, String data) { // 데이터를 각 텍스트뷰에 설정
        if (index == 0){
            mText00.setText(data);
        } else if (index == 1){
            mText01.setText(data);
        } else if (index == 2) {
            mText02.setText(data);
        } else {
            throw new IllegalArgumentException();
        }
    }


    public void getText(int index, StringBuilder msg) { // 데이터를 각 텍스트뷰에 설정
        String str1 = "";

        if (index == 0){
            String str = mText00.getText().toString();
            msg.append(str + ", ");
        } else if (index == 1){
            //String str1 = mText00.getText().toString();


            switch(mText00.getText().toString()) {
                case "1":
                    str1 = getExpireDate(5);
                    break;
                case "2":
                    str1 = getExpireDate(10);
                    break;
                case "3":
                    str1 = getExpireDate(3);
                    break;
                case "4":
                    str1 = getExpireDate(7);;
                    break;
            }
            msg.append("'"+ str1 + "'" + ", ");
        } else if (index == 2) {
            String str2 = mText02.getText().toString();
            msg.append(str2);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void getStandardText(int index, StringBuilder msg) { // 데이터를 각 텍스트뷰에 설정

        if (index == 0){
            String str = mText00.getText().toString();
            msg.append(str + ", ");
        } else if (index == 2) {
            String str2 = mText02.getText().toString();
            msg.append(str2);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public static String getExpireDate (int exDay )
    {
        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat ("yyyy-MM-dd", Locale.KOREA );
        Date toDay = new Date();
        Date mDate = new Date();
        mDate.setTime(toDay.getTime() + (long)(1000 * 60 * 60 * 24 * exDay));

        String expireDate = mSimpleDateFormat.format(mDate);

        return expireDate;
    }
}
