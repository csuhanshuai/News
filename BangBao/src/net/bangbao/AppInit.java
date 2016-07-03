package net.bangbao;

import io.rong.imlib.RongIMClient;
import net.bangbao.crash.MyExceptionHandler;
import net.bangbao.macro.VenterConstants;
import net.bangbao.network.RequestManager;
import net.bangbao.oath.Constants;
import net.bangbao.utils.ImageCacheUtil;
import net.bangbao.utils.SDCardUtils;
import android.app.Application;
import android.content.Context;

/**
 * Created by mosl AppInit
 */
public class AppInit extends Application {

	private static Context sContext;

	@Override
	public void onCreate() {
		super.onCreate();
		 if (SDCardUtils.isUsable()) {
	            SDCardUtils.initCacheDir(Constants.IMAGE_PATH);// 初始化SDCard目录
	            SDCardUtils.initCacheDir(Constants.JSON_PATH);// 初始化SDCard目录
	        }
		sContext = getApplicationContext();
		RequestManager.getInstance().init(sContext);
		RongIMClient.init(getApplicationContext(), VenterConstants.RongYun_key,
				R.drawable.ic_launcher);
		// 保存错误信息到本地
		Thread.setDefaultUncaughtExceptionHandler(MyExceptionHandler
				.getInstance(getApplicationContext()));
		ImageCacheUtil.init(sContext);//初始化ImageCache
	}

	public static Context getContext() {
		return sContext;
	}
	

}
