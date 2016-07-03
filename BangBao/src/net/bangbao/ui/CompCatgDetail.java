package net.bangbao.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import net.bangbao.R;
import net.bangbao.base.BaseActivity;

/*
 * author:mosl
 * 公司保险类别，细节
 */
public class CompCatgDetail extends BaseActivity {

	private WebView mWebContent;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.commcatg_detail);

		Intent intent = getIntent();
		mWebContent = (WebView) findViewById(R.id.web_content);
		mWebContent.setWebViewClient(new WebViewClient());
		mWebContent.setWebChromeClient(new WebChromeClient());
		// webview支持缩放
		mWebContent.getSettings().setSupportZoom(true);
		mWebContent.getSettings().setBuiltInZoomControls(true);
		if (intent != null) {
			mWebContent.loadUrl(intent.getStringExtra("key"));
		}
	}

	public void back(View v) {
		onBackPressed();
	}

}
