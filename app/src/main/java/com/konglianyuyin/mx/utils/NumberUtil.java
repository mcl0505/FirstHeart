package com.konglianyuyin.mx.utils;

import java.math.BigDecimal;

public class NumberUtil {
    public static String getBigDecimal(String s) {
        if (s.length() < 5)
            return s;
        try {
            BigDecimal b = new BigDecimal(s);
            b = b.divide(new BigDecimal(10000), 0, BigDecimal.ROUND_DOWN); //小数位 直接舍去
            return b.toString() + "w";
        } catch (Exception e) {
            e.printStackTrace();
            return s;
        }
    }
}
