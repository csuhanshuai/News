package net.bangbao.utils;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author mosl
 * 
 */
public class ABRegUtil {

    /**
     * 手机号码正则
     */
    public static final String REG_PHONE_CHINA = "^((13[0-9])|(15[^4,\\D])|(18[0,2,5-9]))\\d{8}$";

    /**
     * 邮箱正则
     */
    public static final String REG_EMAIL = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";

    
    /** 判断是否是手机号
     * @param str
     * @return
     */
    public static boolean isMobile(String str) {    
        Pattern p = null;   
        Matcher m = null;   
        boolean b = false;    
        p = Pattern.compile("^[1][3,4,5,8][0-9]{9}$"); // 验证手机号   
        m = p.matcher(str);   
        b = m.matches();    
        return b;   
    }   


    /**  
     * 判断是否为中文  
     * @param c  
     * @return  
     */    
    public static boolean isCN(String str) {
		try {
			byte[] bytes = str.getBytes("UTF-8");
			if (bytes.length == str.length()) {
				return false;
			} else {
				return true;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return false;
	}  
}
