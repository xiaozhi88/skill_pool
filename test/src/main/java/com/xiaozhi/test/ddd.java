package com.xiaozhi.test;

import java.awt.*;

/**
 * 类描述
 *
 * @author zhangzy
 * @date 2021/3/3-14:34
 * @since v1.0
 */
public class ddd {
    public static void main(String[] args) {
        String hexString = colorToHexValue(Color.RED);

        System.out.println("16进制字符串:" + hexString);

        Color color = fromStrToARGB(hexString);

        System.out.println("16进制字符串转为颜色的ARGB值:("+String.valueOf(color.getAlpha())+","+String.valueOf(color.getRed())+","

                +String.valueOf(color.getGreen())+","+String.valueOf(color.getBlue())+")");

        int a = 126;
        System.out.println((a/5)*5);

    }

    private static String colorToHexValue(Color color) {
        return intToHexValue(color.getAlpha()) + intToHexValue(color.getRed()) + intToHexValue(color.getGreen()) + intToHexValue(color.getBlue());

    }

    private static String intToHexValue(int number) {
        StringBuilder result = new StringBuilder(Integer.toHexString(number & 0xff));

        while (result.length() < 2) {
            result.insert(0, "0");
        }

        return result.toString().toUpperCase();
    }

    private static Color fromStrToARGB(String str) {
        String str1 = str.substring(0, 2);

        String str2 = str.substring(2, 4);

        String str3 = str.substring(4, 6);

        String str4 = str.substring(6, 8);

        int alpha = Integer.parseInt(str1, 16);

        int red = Integer.parseInt(str2, 16);

        int green = Integer.parseInt(str3, 16);

        int blue = Integer.parseInt(str4, 16);

        Color color = new Color(red, green, blue, alpha);

        return color;

    }
}
