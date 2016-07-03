package net.bangbao.ui;

import io.rong.imlib.RongIMClient;
import io.rong.imlib.RongIMClient.ConnectCallback;

import java.security.NoSuchAlgorithmException;

import net.bangbao.AppConfig;
import net.bangbao.R;
import net.bangbao.UserConfig;
import net.bangbao.base.BaseActivity;
import net.bangbao.common.EditTextWatcher;
import net.bangbao.common.UIHelper;
import net.bangbao.dao.LoginUserApi;
import net.bangbao.dao.ThirdLoginApi;
import net.bangbao.dao.User.ThirdLoginMessageStruct;
import net.bangbao.dao.WeixinRespApi;
import net.bangbao.dao.WeixinUserInfo;
import net.bangbao.network.ApiClient;
import net.bangbao.oath.AccessTokenKeeper;
import net.bangbao.oath.Constants;
import net.bangbao.service.RongYunService;
import net.bangbao.utils.ABRegUtil;
import net.bangbao.utils.EncryptionUtils;
import net.bangbao.widget.ConsummateDialog;
import net.bangbao.widget.SegmentedGroup;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.UsersAPI;
import com.sina.weibo.sdk.openapi.models.User;
import com.tencent.connect.UserInfo;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

/*
 * author:mosl
 * descrsion:Login
 */
public class LoginActivity extends BaseActivity implements ConnectCallback,
		View.OnClickListener {

	/** 新用户注册TextView */
	private TextView mRegisterTXT;
	/** 忘记密码TextView */
	private TextView mForgetPassTXT;

	/** 用户名EditText */
	private EditText mUsernameET;
	/** 密码EditText */
	private EditText mPasswordET;

	/** 登陆or注册单选按钮 */
	private SegmentedGroup mSegmentedGroup;
	/** 0 客户 ，1 保险顾问，默认为0 */
	public int mCategryItem = 0;
	/** 第三方登陆的view，保险顾问登陆时消失 */
	private View mThirdLoginView;

	/** 微博登陆 */
	private ImageView mWeiboLoginIV;
	/** 新浪的用户信息 */
	private AuthInfo mAuthInfo;
	/** 新浪微博，封装了 "access_token"，"expires_in"，"refresh_token"，并提供了他们的管理功能 */
	private Oauth2AccessToken mAccessToken;
	/** 新浪微博，注意：SsoHandler 仅当 SDK 支持 SSO 时有效 */
	private SsoHandler mSsoHandler;

	/** QQ登陆 */
	private ImageView mQQLoginIV;
	/** 腾讯登陆的事例 */
	private Tencent mTencent;
	/** QQ的UserInfo */
	private UserInfo mInfo;

	/** 微信登陆 */
	private ImageView mWeixinLoginIV;
	/** IWXAPI是第三方app和微信通信的openid接口 */
	public static IWXAPI iwxapi;

	private ApiClient apiClient = new ApiClient();

	private ProgressDialog mProgressDialog;
	private String mBeforeUserName;
	private String mPasswordStr = "";
	private String mUserNameStr = "";

	private ConsummateDialog consummateDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		consummateDialog = new ConsummateDialog(1,
				new ConsummateDialog.IConsummate() {

					@Override
					public void consummate() {
						Intent intent = new Intent(LoginActivity.this,
								ConsultMessageActivity.class);
						startActivity(intent);
					}

					@Override
					public void cancel() {
						consummateDialog.dismiss();
					}

				});
		setContentView(R.layout.activity_login);

		// 注册广播 ，提取成一个方法
		IntentFilter myIntentFilter = new IntentFilter();
		myIntentFilter.addAction("weixin_login");
		registerReceiver(mBroadcastReceiver, myIntentFilter);

		// 初始化界面
		initViews();
		initEditWatcher();
		// 切换登陆和注册界面
		mSegmentedGroup
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						switch (checkedId) {
						case R.id.custom:
							mCategryItem = 0;
							mThirdLoginView.setVisibility(View.VISIBLE);
							break;
						default:
							mCategryItem = 1;
							mThirdLoginView.setVisibility(View.GONE);
							break;
						}
					}
				});

		findViewById(R.id.fram_back).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
	}

	private void initEditWatcher() {

		mUsernameET.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				mBeforeUserName = s.toString();
			}

			@Override
			public void afterTextChanged(Editable s) {
				if (s.toString().equals(mBeforeUserName)) {
					return;
				}
				int editStart = mUsernameET.getSelectionStart();
				int offset = s.toString().length() - mBeforeUserName.length();
				if (ABRegUtil.isCN(s.toString()) || s.toString().endsWith(" ")) {
					UIHelper.showToastMessage("不能输入中文或空格");
					s.delete(s.toString().length() - offset, s.toString()
							.length());
					int tempSelection = editStart - offset;
					mUsernameET.setText(s);
					mUsernameET.setSelection(tempSelection);
				}
			}
		});
		mPasswordET.addTextChangedListener(new EditTextWatcher(mPasswordET, 1));
	}

	private void initViews() {

		mProgressDialog = new ProgressDialog(this);
		mUsernameET = (EditText) findViewById(R.id.login_username);
		mPasswordET = (EditText) findViewById(R.id.login_password);

		mThirdLoginView = findViewById(R.id.thid_login);

		mRegisterTXT = (TextView) findViewById(R.id.register_text);
		mRegisterTXT.setOnClickListener(this);

		mForgetPassTXT = (TextView) findViewById(R.id.forget_pass);
		mForgetPassTXT.setOnClickListener(this);
		mSegmentedGroup = (SegmentedGroup) findViewById(R.id.segmented);
		// 微博登陆
		mWeiboLoginIV = (ImageView) findViewById(R.id.weibo_login);
		mWeiboLoginIV.setOnClickListener(this);

		// QQ登陆
		mQQLoginIV = (ImageView) findViewById(R.id.qq_login);
		mQQLoginIV.setOnClickListener(this);

		// 微信登陆
		mWeixinLoginIV = (ImageView) findViewById(R.id.weixin_login);
		mWeixinLoginIV.setOnClickListener(this);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Log.d("LoginActivity", "onActivityResult");
		// SSO 授权回调
		// 重要：发起 SSO 登陆的 Activity 必须重写 onActivityResult
		if (mSsoHandler != null) {
			mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
		}
	}

	/** 第三方登陆信息 */
	private ThirdLoginMessageStruct thirdLogin = new ThirdLoginMessageStruct();

	/**
	 * 微博用户监听
	 * 
	 * @author mosl
	 * 
	 */
	class AuthListener implements WeiboAuthListener {

		@Override
		public void onCancel() {
			Log.d("mosl", "mosl test cancel ");
		}

		// 新浪微博登陆成功
		@Override
		public void onComplete(Bundle values) {
			// 从 Bundle 中解析 Token
			mAccessToken = Oauth2AccessToken.parseAccessToken(values);
			if (mAccessToken.isSessionValid()) {
				// 显示 Token
				// updateTokenView(false);

				// 保存 Token 到 SharedPreferences
				AccessTokenKeeper.writeAccessToken(LoginActivity.this,
						mAccessToken);

				thirdLogin.third_uid = mAccessToken.getUid();
				thirdLogin.third_id = 3;// 标示微博登陆

				// 获取微博用户的个人信息
				UsersAPI usersAPI = new UsersAPI(LoginActivity.this,
						Constants.WeiBo_APP_KEY, mAccessToken);
				usersAPI.show(Long.parseLong(mAccessToken.getUid()), mListener);
			}

			else {
				// 以下几种情况，您会收到 Code：
				// 1. 当您未在平台上注册的应用程序的包名与签mAccessToken名时；
				// 2. 当您注册的应用程序包名与签名不正确时；
				// 3. 当您在平台上注册的包名和签名与您当前测试的应用的包名和签名不匹配时。
				String code = values.getString("code");
				String message = "fail";
				if (!TextUtils.isEmpty(code)) {
					message = message + "\nObtained the code: " + code;
				}
				Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG)
						.show();
			}

		}

		@Override
		public void onWeiboException(WeiboException arg0) {

		}

	}

	/**
	 * 微博 OpenAPI 回调接口。
	 */
	private RequestListener mListener = new RequestListener() {
		@Override
		public void onComplete(String response) {
			if (!TextUtils.isEmpty(response)) {
				// 调用 User#parse 将JSON串解析成User对象
				User user = User.parse(response);
				if (user != null) {
					Toast.makeText(LoginActivity.this, "登陆成功！",
							Toast.LENGTH_LONG).show();
					thirdLogin.nick_nm = user.screen_name;
					Log.d("thirdLogin.nick_nm", thirdLogin.nick_nm);
					// 到帮保服务器注册
					LoginBangbao();

				} else {
					Toast.makeText(LoginActivity.this, response,
							Toast.LENGTH_LONG).show();
				}
			}
		}

		@Override
		public void onWeiboException(WeiboException e) {

		}
	};

	public void complete(View v) {

		if (mUsernameET.getText().toString().trim().length() == 0
				|| mPasswordET.getText().toString().trim().length() == 0) {
			UIHelper.showToastMessage("用户名和密码不能为空");
			return;
		}
		if (mUsernameET.getText().toString().trim().length() < 3) {
			UIHelper.showToastMessage("用户名长度3-32");
			return;
		}

		mUserNameStr = mUsernameET.getText().toString().trim();
		try {
			mPasswordStr = EncryptionUtils.string2MD5(mPasswordET.getText()
					.toString().trim());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		// String password = mPasswordET.getText().toString().trim();
		mProgressDialog.show();

		apiClient.loginUser(mUsernameET.getText().toString().trim(),
				mPasswordStr, this, LoginUserApi.class,
				new ApiClient.CallBack<LoginUserApi>() {

					@Override
					public void success(LoginUserApi api) {

						if (api == null)
							return;
						if (api.getRet_cd() == 0) {
							UIHelper.showToastMessage("登陆成功");
							AppConfig.login();
							UserConfig user = new UserConfig();
							user.setUserId(api.getUser_id());
							user.setUserToken(String.valueOf(api.getToken()));
							user.setUserName(mUserNameStr);
							// 保存登录人员类型
							user.setType(api.getType());
							AppConfig.putUser(user);
							AppConfig.saveRongYunToken(api.getRc_token());
							loginRongYun();
						} else {
							UIHelper.showToastMessage(api.getRet_txt());
							mProgressDialog.dismiss();
						}
					}

					@Override
					public void fail(String error) {
						UIHelper.showToastMessage("网络有问题");
						mProgressDialog.dismiss();
					}

				});
	}

	/** 融云登陆 */
	protected void loginRongYun() {
		try {

			AppConfig.IMClient = RongIMClient.connect(
					AppConfig.getRongYunToken(), this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 融云登陆错误
	@Override
	public void onError(ErrorCode arg0) {
		Intent intent = new Intent(LoginActivity.this, MainActivity.class);
		startActivity(intent);
	}

	// 融云登陆成功
	@Override
	public void onSuccess(String arg0) {
		Log.d("rongyun", "融云登陆成功");
		Intent intent = new Intent(LoginActivity.this, MainActivity.class);
		startActivity(intent);
		/** 启动一个Service去监听消息 **/
		if (AppConfig.IMClient != null)
			startService(new Intent(this, RongYunService.class));

		consummateDialog.show(getSupportFragmentManager(), "tag");
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	/** 登陆是1，默认是0 */
	public static int Weixin_Flag = 0;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.weibo_login:

			mAuthInfo = new AuthInfo(LoginActivity.this,
					Constants.WeiBo_APP_KEY, Constants.WeiBo_REDIRECT_URL,
					Constants.WeiBo_SCOPE);
			mSsoHandler = new SsoHandler(LoginActivity.this, mAuthInfo);
			mSsoHandler.authorizeClientSso(new AuthListener());

			break;

		case R.id.qq_login:

			mTencent = Tencent.createInstance(Constants.QQ_App_ID,
					this.getApplicationContext());
			if (!mTencent.isSessionValid()) {
				mTencent.login(this, "all", loginListener);
			}

			break;
		case R.id.weixin_login:
			Weixin_Flag = 1;
			iwxapi = WXAPIFactory
					.createWXAPI(this, Constants.WeiX_App_ID, true);
			iwxapi.registerApp(Constants.WeiX_App_ID);
			// send oauth request
			SendAuth.Req req = new SendAuth.Req();
			req.scope = "snsapi_userinfo";
			req.state = "wechat_sdk_demo";
			iwxapi.sendReq(req);

			break;
		case R.id.register_text:
			Intent registerIntent = new Intent(LoginActivity.this,
					RegisterActivity.class);
			registerIntent.putExtra("item", mCategryItem);
			startActivity(registerIntent);
			break;
		case R.id.forget_pass:
			Intent fogetPassIntent = new Intent(LoginActivity.this,
					ForgetPassActivity.class);
			startActivity(fogetPassIntent);
			break;

		default:
			break;

		}

	}

	/** QQ登陆的监听 */
	IUiListener loginListener = new BaseUiListener() {
		@Override
		protected void doComplete(JSONObject jsonObject) {
			// 数据在values里面，测试一下
			try {
				String token = jsonObject.getString("access_token");
				String expires = jsonObject.getString("expires_in");
				String openId = jsonObject.getString("openid");
				mTencent.setAccessToken(token, expires);
				mTencent.setOpenId(openId);
				thirdLogin.third_id = 2;
				thirdLogin.third_uid = openId;
				updateUserInfo();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	};

	/** QQ登陆相关 */
	private void updateUserInfo() {
		if (mTencent != null && mTencent.isSessionValid()) {
			IUiListener listener = new IUiListener() {

				@Override
				public void onCancel() {

				}

				@Override
				public void onComplete(final Object response) {
					JSONObject jsonObject = (JSONObject) response;
					try {
						thirdLogin.nick_nm = jsonObject.getString("nickname");
					} catch (JSONException e) {
						e.printStackTrace();
					}
					Log.d("nickname", thirdLogin.nick_nm);
					LoginBangbao();
				}

				@Override
				public void onError(UiError arg0) {

				}

			};
			mInfo = new UserInfo(this, mTencent.getQQToken());
			mInfo.getUserInfo(listener);

		}
	}

	/** QQ登陆的相关 */
	private class BaseUiListener implements IUiListener {

		@Override
		public void onComplete(Object response) {
			if (null == response) {
				// Util.showResultDialog(LoginActivity.this, "返回为空", "登录失败");
				return;
			}
			JSONObject jsonResponse = (JSONObject) response;

			if (null != jsonResponse && jsonResponse.length() == 0) {
				// Util.showResultDialog(MainActivity.this, "返回为空", "登录失败");
				return;
			}
			// Util.showResultDialog(MainActivity.this, response.toString(),
			// "登录成功");
			doComplete((JSONObject) response);
		}

		protected void doComplete(JSONObject values) {

		}

		@Override
		public void onError(UiError e) {
			// Util.toastMessage(MainActivity.this, "onError: " +
			// e.errorDetail);
			// Util.dismissDialog();
		}

		@Override
		public void onCancel() {
			// Util.toastMessage(MainActivity.this, "onCancel: ");
			// Util.dismissDialog();
		}
	}

	/**
	 * @author Spartacus26 从微信登陆界面发送过来的广播，这里可以写进onActivityResult里面 暂时写在这里，看起来方便。
	 */
	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals("weixin_login")) {
				Log.d("LoginActivity",
						"code == " + intent.getStringExtra("code"));
				// 根据code获取用户信息
				String code = intent.getStringExtra("code");
				getAccessTokenFromWeiXin(code);

			}
		}

	};

	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mBroadcastReceiver);
	};

	/**
	 * 微信根据code得到accessToken
	 * 
	 * @author Spartacus26
	 * */
	private void getAccessTokenFromWeiXin(String code) {
		// https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code

		String requestUrlAppId = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
				+ Constants.WeiX_App_ID;
		String requestUrlAppSecret = "&secret=" + Constants.WeiX_App_Secret;
		String requestUrlCode = "&code=" + code;
		String requestUrlLast = "&grant_type=authorization_code";

		String requestUrl = requestUrlAppId + requestUrlAppSecret
				+ requestUrlCode + requestUrlLast;
		new ApiClient().getWeixinAccessToken(requestUrl, this,
				WeixinRespApi.class, new ApiClient.CallBack<WeixinRespApi>() {

					@Override
					public void success(WeixinRespApi api) {
						Log.d("LoginActivity",
								"Access_token==" + api.getAccess_token());
						// 得到token，然后请求得到用户名
						// https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID
						String getUserInfoStr = "https://api.weixin.qq.com/sns/userinfo?access_token="
								+ api.getAccess_token()
								+ "&openid="
								+ Constants.WeiX_App_ID + "&lang=zh_CN";// 最后这个表示得到中文的信息

						new ApiClient().getWeixinUserInfo(getUserInfoStr, this,
								WeixinUserInfo.class,
								new ApiClient.CallBack<WeixinUserInfo>() {

									@Override
									public void success(WeixinUserInfo api) {
										Log.d("LoginActivity", "Nickname=="
												+ api.getNickname());
										String nickNameStr = api.getNickname();
										thirdLogin.third_uid = api.getOpenid();
										thirdLogin.third_id = 1;
										thirdLogin.nick_nm = nickNameStr;
										LoginBangbao();
									}

									@Override
									public void fail(String error) {

									}

								});

					}

					@Override
					public void fail(String error) {
						Log.d("LoginActivity", error);
					}

				});

	}

	/**
	 * 到帮保服务器注册
	 */
	private void LoginBangbao() {

		new ApiClient().thirdLogin(thirdLogin, this, ThirdLoginApi.class,
				new ApiClient.CallBack<ThirdLoginApi>() {

					@Override
					public void success(ThirdLoginApi api) {
						if (api == null)
							return;
						if (api.getRet_cd() == 0) {
							// 从帮保服务器返回的信息在api里面

							UIHelper.showToastMessage("登陆成功");
							AppConfig.login();
							UserConfig user = new UserConfig();
							user.setUserId(api.getUser_id());
							user.setUserToken(String.valueOf(api.getToken()));
							user.setUserName(thirdLogin.nick_nm);
							// 保存登录人员类型
							user.setType(api.getType());

							AppConfig.putUser(user);
							AppConfig.saveRongYunToken(api.getRc_token());

							UIHelper.showToastMessage(api.getRet_txt());
							// 登陆融云服务器
							loginRongYun();
							// 跳转到MainActivity
							Intent weiboLoginIntent = new Intent(
									LoginActivity.this, MainActivity.class);

							startActivity(weiboLoginIntent);
							LoginActivity.this.finish();
						}
						if (api.getRet_cd() == 14) {
							UIHelper.showToastMessage(api.getRet_txt());
						}
					}

					@Override
					public void fail(String error) {

					}
				});
	}
}
