package net.bangbao.fragment;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import net.bangbao.AppConfig;
import net.bangbao.R;
import net.bangbao.base.BaseFragment;
import net.bangbao.widget.CircleImage;

/**
 * @author mosl
 * 广告页面
 */
public class AdvertiseFragment extends BaseFragment implements View.OnClickListener{
	
	private WebView mAdWebView;
	private View mFramShare;
	private PopupWindow mPopShare;
	private CircleImage mPengyouquanShare, mWeixinShare, mQQshare, mWeiboShare;
	private View rootView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		rootView = LayoutInflater.from(getActivity()).inflate(R.layout.advertise_fragment, null);
		mAdWebView = (WebView)rootView.findViewById(R.id.ad_webview);
		mAdWebView.setWebChromeClient(new WebChromeClient());
		mFramShare = rootView.findViewById(R.id.fram_share);
		mFramShare.setOnClickListener(this);
		return rootView;
	}
	
	@Override
	public void onStart() {
		super.onStart();
		if(!AppConfig.isLogin()){
			mAdWebView.loadUrl("file:///android_asset/customer_ad.html");
		}
		if(AppConfig.isLogin()&&AppConfig.getUser().getType() == 2)
			mAdWebView.loadUrl("file:///android_asset/consult_ad.html");
		else if(AppConfig.isLogin()&&AppConfig.getUser().getType() == 1)
			mAdWebView.loadUrl("file:///android_asset/customer_ad.html");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.fram_share:
			shareToFriends();
			break;
		default:
			break;
		}
	}
	
	/** 分享给朋友们 **/
	private void shareToFriends() {
		View popView = LayoutInflater.from(this.getActivity()).inflate(
				R.layout.layout_share_to_friends, null);
		Button btCancel = (Button) popView.findViewById(R.id.bt_cancel);
		mPengyouquanShare = (CircleImage) popView
				.findViewById(R.id.pengyouquan);
		mWeixinShare = (CircleImage) popView.findViewById(R.id.weixin);
		mQQshare = (CircleImage) popView.findViewById(R.id.qq);
		mWeiboShare = (CircleImage) popView.findViewById(R.id.weibo);
		mPengyouquanShare.setOnClickListener(this);
		mWeixinShare.setOnClickListener(this);
		mQQshare.setOnClickListener(this);
		mWeiboShare.setOnClickListener(this);
		btCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mPopShare.dismiss();
			}
		});
		mPopShare = new PopupWindow(popView,
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);

		mPopShare.setOutsideTouchable(true);
		mPopShare.setFocusable(true);
		mPopShare.setContentView(popView);
		// 无效果
		mPopShare.setBackgroundDrawable(new ColorDrawable(0x22000000));
		// 有效果，popwindow打开，背景变半透明
		backgroundAlpha(0.5f);
		mPopShare.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				backgroundAlpha(1f);
			}
		});
		mPopShare.showAtLocation(rootView,
				Gravity.BOTTOM, 0, 0);
	}

	/**
	 * 设置添加屏幕的背景透明度
	 * 
	 * @param bgAlpha
	 */
	private void backgroundAlpha(float bgAlpha) {
		WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
		lp.alpha = bgAlpha; // 0.0-1.0
		getActivity().getWindow().setAttributes(lp);
	}
}
