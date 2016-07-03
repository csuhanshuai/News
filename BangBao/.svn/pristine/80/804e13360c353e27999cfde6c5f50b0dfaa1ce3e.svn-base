package net.bangbao.ui;

import net.bangbao.R;
import net.bangbao.base.BaseActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.RelativeLayout;
/*
 * 关于界面
 */
public class AboutActivity extends BaseActivity {
	private WebView mWebContent;
	private RelativeLayout r;
	
	@Override
	protected void onCreate(Bundle budle) {
		super.onCreate(budle);
		setContentView(R.layout.about);
		r = (RelativeLayout) findViewById(R.id.go_back4);
		r.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
	}
	

}
