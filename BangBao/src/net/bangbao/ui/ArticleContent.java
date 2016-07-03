package net.bangbao.ui;

import net.bangbao.R;
import net.bangbao.base.BaseActivity;
import net.bangbao.oath.QQHelper;
import net.bangbao.oath.WeiBoHelper;
import net.bangbao.oath.WeiChatHelper;
import net.bangbao.widget.CircleImage;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

/*
 * author：mosl
 * description:文章详情
 * 调用者需要传递 key = url(string 类型的)
 * 需要分享的图片bitmap = bitmap(bitmap 类型);[static 进行传递]
 * 分享的title = title(string)
 * 分享的描述 description = description:
 * 
 */
public class ArticleContent extends BaseActivity implements
		View.OnClickListener {

	public static Bitmap mShareBitmap = null;
	private WebView mWebContent;

	private TextView mTitleTextView;
	private WeiChatHelper weiChatHelper;
	private QQHelper qqHelper;
	private WeiBoHelper weiboHelper;

	private View mFramShare;
	private String mTitle;
	private String mDescription;
	private String mUrl;

	private PopupWindow mPopShare;
	private CircleImage mPengyouquanShare, mWeixinShare, mQQshare, mWeiboShare;

	@Override
	protected void onCreate(Bundle budle) {
		super.onCreate(budle);
		setContentView(R.layout.activity_article_content);
		Intent intent = getIntent();
		initViews();
		handleIntent(intent);
	}

	private void initViews() {
		mFramShare = findViewById(R.id.fram_share);
		mFramShare.setOnClickListener(this);
		mWebContent = (WebView) findViewById(R.id.web_content);
		mWebContent.setWebViewClient(new WebViewClient());
		mWebContent.setWebChromeClient(new WebChromeClient());
		// webview支持缩放
		mWebContent.getSettings().setSupportZoom(true);
		 mWebContent.getSettings().setBuiltInZoomControls(true);

		mTitleTextView = (TextView) findViewById(R.id.article_title);
	}

	private void handleIntent(Intent intent) {
		if (intent != null) {
			mUrl = intent.getStringExtra("key");
			mDescription = intent.getStringExtra("description");
			mTitle = intent.getStringExtra("title");
			if (mTitle != null)
				mTitleTextView.setText(mTitle);
			if (mUrl != null)
				mWebContent.loadUrl(mUrl);
		}
	}

	public void back(View v) {
		onBackPressed();
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.fram_share:
			
			shareToFriends();
			break;
		case R.id.pengyouquan:
			weiChatHelper = new WeiChatHelper(this);
			weiChatHelper.sendMedia(ArticleContent.this, mTitle, mDescription,
					mUrl, mShareBitmap, 1);
			break;
		case R.id.weixin:
			weiChatHelper = new WeiChatHelper(this);
			weiChatHelper.sendMedia(ArticleContent.this, mTitle, mDescription,
					mUrl, mShareBitmap, 0);
			break;
		case R.id.qq:
			qqHelper = new QQHelper(this);
			qqHelper.shareToQQ(ArticleContent.this, mTitle, mDescription, mUrl,
					1);
			break;
		case R.id.weibo:
			weiboHelper = new WeiBoHelper(this);
			weiboHelper.sendMultiMessage(ArticleContent.this, mTitle, "", mUrl);
			break;
		default:
			break;
		}
	}

	/** 分享给朋友们 **/
	private void shareToFriends() {
		View popView = LayoutInflater.from(this).inflate(
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
		mPopShare.showAtLocation(this.findViewById(R.id.acticle_root),
				Gravity.BOTTOM, 0, 0);
	}

	/**
	 * 设置添加屏幕的背景透明度
	 * 
	 * @param bgAlpha
	 */
	private void backgroundAlpha(float bgAlpha) {
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.alpha = bgAlpha; // 0.0-1.0
		getWindow().setAttributes(lp);
	}
}