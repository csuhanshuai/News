package net.bangbao.oath;

import java.io.File;

import android.os.Environment;

public class Constants {
	/** 微博的APP_KEY */
	public static final String WeiBo_APP_KEY = "4046822880";
	/** 微博的APP_Secret */
	public static final String WeiBo_App_Secret = "ccc8cb7c2888e222ed9931b55c1e4215";
	/** 微博的重定向 */
	public static final String WeiBo_REDIRECT_URL = "http://www.sina.com";
	/** 申请的微博权限 */
	public static final String WeiBo_SCOPE = "";
	/** 融云的key */
	public static final String RongYun_key = "x18ywvqf8dohc";
	/** QQ的App_ID */
	public static final String QQ_App_ID = "1104291905";
	/** QQ的APP_KEY */
	public static final String QQ_App_Key = "8BtAz0Mwjxhwo5St";
	/** 微信的APP_ID */
	public static final String WeiX_App_ID = "wxa5fb040a84ba174e";
	/** 微信的APP_Secret */
	public static final String WeiX_App_Secret = "272b0b56f291cf15dd74255053008f73";

	/** 申请微信用户信息的地址 */
	public static final String WeiX_User_Req_URL = 
	"https://api.weixin.qq.com/sns/oauth2/access_token?appid="+ WeiX_App_ID + "&secret="+WeiX_App_Secret+"&code=";
	//+code+"&grant_type=authorization_code"
	
   //https://api.weixin.qq.com/sns/userinfo?access_token= " + token + "&openid=" + openid+“ &lang=zh_CN”;

	 /**
     * @Description SDCard缓存路径
     */
    public static final String BATH_PATH = Environment.getExternalStorageDirectory()
            .getAbsolutePath() + File.separator + "bangbao" + File.separator;
    /**
     *@Description  图片缓存路径
     */
    public static final String IMAGE_PATH = BATH_PATH + "imageCache";
    
    /**
     *@Description  json数据缓存路径
     */
    public static final String JSON_PATH = BATH_PATH + "jsonCache";
    
    /**
     *@Description  所得数据的来源的是网络
     */
    public static final int DOWNLOAD_NET = 1; 
    /**
     *@Description  所得数据的来源是SDCard
     */
    public static final int DOWNLOAD_SDCARD = 2; 
    
    /**
     *@Description  所得数据的来源是SQLite数据库
     */
    public static final int DOWNLOAD_DATABASE = 3; 

}
