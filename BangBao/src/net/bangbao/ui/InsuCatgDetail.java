package net.bangbao.ui;

import net.bangbao.R;
import net.bangbao.base.BaseActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * 右上角加分享--------------------------------------------
 * 
 * @author Spartacus26
 * @since 2015.3.16
 */
public class InsuCatgDetail extends BaseActivity {

	private WebView mWebContent;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.insucatg_detail);

		Intent intent = getIntent();
		mWebContent = (WebView) findViewById(R.id.web_content);
		mWebContent.setWebViewClient(new WebViewClient());
		mWebContent.setWebChromeClient(new WebChromeClient());

		if (intent != null) {
			mWebContent.loadUrl(intent.getStringExtra("key"));
		}
	}

	public void back(View v) {
		onBackPressed();
	}

}
