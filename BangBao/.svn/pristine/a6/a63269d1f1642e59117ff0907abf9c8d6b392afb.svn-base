package net.bangbao.ui;

import net.bangbao.macro.VenterConstants;

import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.constant.WBConstants;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class WeiboCallbackActivity extends Activity implements
		IWeiboHandler.Response {
	/** 微博微博分享接口实例 */
	private IWeiboShareAPI mWeiboShareAPI = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 创建微博分享接口实例
		mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(this,
				VenterConstants.WeiBo_APP_KEY);
		// 注册第三方应用到微博客户端中，注册成功后该应用将显示在微博的应用列表中。
		// 但该附件栏集成分享权限需要合作申请，详情请查看 Demo 提示
		// NOTE：请务必提前注册，即界面初始化的时候或是应用程序初始化时，进行注册
		mWeiboShareAPI.registerApp();
		
		this.finish();
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		mWeiboShareAPI.handleWeiboResponse(intent, this);
		//this.finish();
	}

	@Override
	public void onResponse(BaseResponse baseResponse) {
		switch (baseResponse.errCode) {
		case WBConstants.ErrorCode.ERR_OK:
			Toast.makeText(this, "分享成功", Toast.LENGTH_LONG).show();
			//this.finish();
			break;
		case WBConstants.ErrorCode.ERR_CANCEL:
			Toast.makeText(this, "分享取消", Toast.LENGTH_LONG).show();
			//this.finish();
			break;
		case WBConstants.ErrorCode.ERR_FAIL:
			Toast.makeText(this, "Error Message: " + baseResponse.errMsg,
					Toast.LENGTH_LONG).show();
			this.finish();
			break;
		default:
			//this.finish();
			break;
		}
		//this.finish();

	}
}
