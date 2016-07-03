package net.bangbao.wxapi;

import net.bangbao.ui.LoginActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("WXEntryActivity", "WXEntryActivity is create");

		if (LoginActivity.Weixin_Flag == 1) {
			LoginActivity.Weixin_Flag = 1;
			LoginActivity.iwxapi.handleIntent(getIntent(), this);
		}
		finish();
	}

	@Override
	public void onReq(BaseReq req) {
		Log.d("WXEntryActivity", "WXEntryActivity is onReq");
		finish();
	}

	// 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
	@Override
	public void onResp(BaseResp resp) {
		// api有问题，这里resp只能获得errCode和code，其他信息需要在code里面去获得
		Log.d("WXEntryActivity", "WXEntryActivity is onResp");
		switch (resp.errCode) {
		case BaseResp.ErrCode.ERR_OK:
			// 获得code
			String code = ((SendAuth.Resp) resp).code;
			Log.d("WXEntryActivity", "code==" + code);
			if (LoginActivity.Weixin_Flag == 1) {
				Toast.makeText(this, "登陆成功!", Toast.LENGTH_LONG).show();
				LoginActivity.Weixin_Flag = 0;
				Intent intent = new Intent("weixin_login");
				intent.putExtra("code", code); // 发送广播
				sendBroadcast(intent);
			}
			finish();

			break;
		case BaseResp.ErrCode.ERR_USER_CANCEL:
			Toast.makeText(this, "登陆取消!", Toast.LENGTH_LONG).show();
			break;
		case BaseResp.ErrCode.ERR_AUTH_DENIED:
			Toast.makeText(this, "登陆被拒绝", Toast.LENGTH_LONG).show();
			break;
		default:
			Toast.makeText(this, "登陆失败!", Toast.LENGTH_LONG).show();
			break;
		}
		finish();
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		Log.d("WXEntryActivity", "WXEntryActivity is onNewIntent");
		setIntent(intent);
		LoginActivity.iwxapi.handleIntent(intent, this);
		finish();
	}
}