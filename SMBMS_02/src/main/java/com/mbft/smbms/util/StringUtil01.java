package com.mbft.smbms.util;

import java.util.regex.Pattern;

/**
 * 这个类是String工具类，提供String操作的一些常量方法
 */
public class StringUtil01 {
    /**
     * 判断字符串是为null ""," "..，如果就返回true.
     *
     * @param string
     * @return
     */
    public static boolean isNullOrEmpty(String string) {
        boolean flag = false;
        //如果为null 就返回 true;
        if (string == null) {
            return true;
        } else if ("".equals(string)) {
            //如果为 "" 就返回true
            return true;
        } else if (" ".equals(string)) {
            //如果为 "" 就返回true
            return true;
        } else if ("  ".equals(string)) {
            //如果为 "" 就返回true
            return true;
        }else if ("   ".equals(string)) {
            //如果为 "" 就返回true
            return true;
        }else if ("0".equals(string)){
            //如果传入的值为0，这里的 pageIndex 因该为 ,1,2,3
            return true;
        }
        else {
            return false;
        }


    }

    /**
     * 判断字符串不为空
     * @param string
     * @return
     */
    public static boolean isNotNull(String string){
        return !isNullOrEmpty(string);
    }
    /**
     * 判断是为Integer，true
     * @param str
     * @return
     */
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }
}
