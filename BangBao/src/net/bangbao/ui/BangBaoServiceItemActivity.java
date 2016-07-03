package net.bangbao.ui;

import android.os.Bundle;
import android.webkit.WebView;
import net.bangbao.R;
import net.bangbao.base.BaseActivity;

/*
 * 帮保服务条款
 */
public class BangBaoServiceItemActivity extends BaseActivity {

	private WebView mAgreementWb;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.bangbao_service_item_activity);
		mAgreementWb = (WebView) findViewById(R.id.agreement_wb);
		mAgreementWb.loadUrl("file:///android_asset/RegistrationAgreement.html");

	}
}
