package net.bangbao.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class DateUtils {

    public static final String[] constellationArr = {"水瓶座", "双鱼座", "牡羊座", "金牛座", "双子座", "巨蟹座",
            "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "摩羯座"};

    public static final int[] constellationEdgeDay = {20, 19, 21, 21, 21, 22, 23, 23, 23, 23, 22,
            22};

    /**
     * @param timetmtp
     * @return
     * 时间戳转字符串 格式：yyyy/MM/dd HH:mm:ss
     */
    public static String getStrTimetmtp(long timetmtp) {
        String tsStr = "";
        DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        try {
            tsStr = sdf.format(timetmtp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tsStr;
    }

    /** 
     * @Description 格式化时间
     * @param timetmtp
     * @param format 自定义格式
     * @return
     * String  
     */
    public static String getStrTimetmtp(long timetmtp,String format){
	    String tsStr = "";  
	    DateFormat sdf;
	    if(format.isEmpty())
	        sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");  
	    else
	        sdf = new SimpleDateFormat(format);
	    try {  
	        tsStr = sdf.format(timetmtp); 
	    } catch (Exception e) {  
	        e.printStackTrace();  
	    }  
	    return tsStr;
	}

    /**
     * @param tiemtmtp
     * @return 返回一个距今多少年
     */
    public static int to_time(long tiemtmtp) {

        int years = 0;
        int curYears = 0;
        try {

            String str = getStrTimetmtp(tiemtmtp);
            String strtmtp = str.substring(0, 4);
            years = Integer.parseInt(strtmtp);

            long theTime = System.currentTimeMillis();
            String theTimeStr = getStrTimetmtp(theTime);
            curYears = Integer.parseInt(theTimeStr.substring(0, 4));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return curYears - years;
    }

    /**
     * @param month 
     * @param day 
     * @return 返回星座
     */
    public static String date2Constellation(long timetmtp) {

        try {
            String strTimetp = getStrTimetmtp(timetmtp);
            int month = Integer.parseInt(strTimetp.substring(5, 7));
            int day = Integer.parseInt(strTimetp.substring(9, 10));
            int mon = month;
            int d = day;
            if (d < constellationEdgeDay[mon]) {
                mon = mon - 2;
            }
            if (mon >= 0) {
                return constellationArr[mon];
            }

            return constellationArr[11];
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

	/**
	 * @param beforeTimeTmtp
	 * @param afterTimeTmtp
	 * @return 返回时间间隔(秒)
	 */
	public static int timeTimpCompare(long beforeTimeTmtp,long afterTimeTmtp){
		
		return (int)(afterTimeTmtp - beforeTimeTmtp)/1000;
	}
}
