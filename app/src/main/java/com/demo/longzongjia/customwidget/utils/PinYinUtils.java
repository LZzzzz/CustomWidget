package com.demo.longzongjia.customwidget.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * Created by LZJ on 2016/11/3.
 */

public class PinYinUtils {
    public static String getPinYing(String s) {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        //无音调
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        //大写
        format.setCaseType(HanyuPinyinCaseType.UPPERCASE);

        StringBuffer sb = new StringBuffer();
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            //空格的话不添加
            if (Character.isSpaceChar(c)) {
                continue;
            }
            //非汉字不转化
            if (c >= -128 && c <= 127) {
                sb.append(c);
            } else {
                try {
                    //得到的是一个字符串数组，因为可能是一个多音字
                    String p = PinyinHelper.toHanyuPinyinStringArray(c, format)[0];
                    sb.append(p);
                } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                    badHanyuPinyinOutputFormatCombination.printStackTrace();
                }
            }

        }
        return sb.toString();
    }
}
