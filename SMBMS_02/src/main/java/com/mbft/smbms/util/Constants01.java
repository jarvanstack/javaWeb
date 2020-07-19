package com.mbft.smbms.util;

/**
 * 这个是Constants常量类，
 * 如果一个值是常量应该在这里定义比如
 * USER_SERSSION，作为session的键值
 */
public class Constants01 {
    public static final String USER_SERSSION = "USER_SERSSION";//session储存的Attribute键值
    public static final String METHOD = "method";//前端出入的method的参数值的不同，调用不同的方法。
    public static final String MESSAGE = "message";//前端出入的前端展示的message信息，比如旧密码不正确
    public static final String RESULT = "result";//前端ajax 验证信息，例如 result : sessionerror
    public static final int PAGESIZE = 5;//用户查询也的每页 容量。

}
