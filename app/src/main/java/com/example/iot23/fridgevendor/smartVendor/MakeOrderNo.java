package com.example.iot23.fridgevendor.smartVendor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by iot23 on 15. 7. 23.
 */
public class MakeOrderNo {

    //int num = 0;

    public StringBuilder getOrderNo() {
        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat ( "yyyyMMddHHmm", Locale.KOREA );
        Date currentTime = new Date ( );
        String mTime = mSimpleDateFormat.format ( currentTime );

        //++num;

//        NumberFormat numberFormat = NumberFormat.getNumberInstance();
//        numberFormat.setMinimumIntegerDigits(2);
        StringBuilder orderNo = new StringBuilder("" + mTime);

        return orderNo;
    }
}