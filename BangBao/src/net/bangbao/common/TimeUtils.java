
package net.bangbao.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by mosl
 */
public class TimeUtils {

    public static CharSequence getListTime(String created_at) {
        Calendar calendar = Calendar.getInstance();
        Date date = null;
        SimpleDateFormat srcDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss Z", Locale.US);
        SimpleDateFormat dstDateFormat = new SimpleDateFormat("MMMM dd yyyy", Locale.US);
        try {
            date = srcDateFormat.parse(created_at);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dstDateFormat.format(date);
    }
    
    //将时间戳转为字符串   
    public static String getStrTime(long cc_time) {  
    String re_StrTime = null;  
    SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");  
    // 例如：cc_time=1291778220   
    //long lcc_time = Long.valueOf(cc_time);  
    re_StrTime = sdf.format(new Date(cc_time * 1000L));  
    return re_StrTime;  
    }  
    
    public static String getStrTime1(long cc_time) {  
        String re_StrTime = null;  
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");  
        // 例如：cc_time=1291778220   
        //long lcc_time = Long.valueOf(cc_time);  
        re_StrTime = sdf.format(new Date(cc_time * 1000L));  
        return re_StrTime;  
        }  
    
    //将字符串转为时间戳
    public static Long  getTime(String user_time){
    	long epoch = 0;
    	try {
			epoch = new java.text.SimpleDateFormat ("yyyy年MM月dd日").parse(user_time).getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return epoch;
//    	String re_time = null;
//    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
//    	Date d;
//    	try{
//    		d = sdf.parse(user_time);
//    		long l = d.getTime();
//    		String str = String.valueOf(l);
//    		re_time = str.substring(0, 10);
//    	} catch(ParseException e){
//    		e.printStackTrace();
//    	}
//    	return re_time;
    }

}
