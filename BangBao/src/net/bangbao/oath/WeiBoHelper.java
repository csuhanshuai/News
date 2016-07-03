package net.bangbao.oath;

import net.bangbao.macro.VenterConstants;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;

import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;

/*
 * 新浪微博帮助类
 */
public class WeiBoHelper {

	private Context context;

	/** 微博微博分享接口实例 */
	private IWeiboShareAPI mWeiboShareAPI = null;

	public static final int SHARE_CLIENT = 1;
	public static final int SHARE_ALL_IN_ONE = 2;

	public WeiBoHelper(Context context) {
		this.context = context;

		// 创建微博分享接口实例
		mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(context,
				VenterConstants.WeiBo_APP_KEY);
		// 注册第三方应用到微博客户端中，注册成功后该应用将显示在微博的应用列表中。
		// 但该附件栏集成分享权限需要合作申请，详情请查看 Demo 提示
		// NOTE：请务必提前注册，即界面初始化的时候或是应用程序初始化时，进行注册
		mWeiboShareAPI.registerApp();
	}

	public void sendMultiMessage(final Activity activity, String title,
			String imageUrl, String url) {

		WeiboMultiMessage weiboMessage = new WeiboMultiMessage();

		TextObject textObject = new TextObject();
		textObject.text = title + url;
		weiboMessage.textObject = textObject;

		SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
		request.transaction = String.valueOf(System.currentTimeMillis());
		request.multiMessage = weiboMessage;
		mWeiboShareAPI.sendRequest(activity, request);

	}

	public void sendMultiMessage(final Activity activity, String title,
			Bitmap bitmap, String url) {

		WeiboMultiMessage weiboMessage = new WeiboMultiMessage();

		TextObject textObject = new TextObject();
		textObject.text = title + url;
		weiboMessage.textObject = textObject;
		
		ImageObject imageObject = new ImageObject();
		
		imageObject.setImageObject(bitmap);
		weiboMessage.imageObject = imageObject;
		SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
		request.transaction = String.valueOf(System.currentTimeMillis());
		request.multiMessage = weiboMessage;
		mWeiboShareAPI.sendRequest(activity, request);

	}
}