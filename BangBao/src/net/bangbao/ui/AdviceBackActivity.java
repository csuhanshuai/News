package net.bangbao.ui;

import net.bangbao.R;
import net.bangbao.base.BaseActivity;
import net.bangbao.base.BaseApi;
import net.bangbao.common.UIHelper;
import net.bangbao.dao.User.FeedbackStruct;
import net.bangbao.network.ApiClient;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
/*
 * 意见反馈
 */
public class AdviceBackActivity extends BaseActivity implements View.OnClickListener{
	private EditText textSugg;
	private EditText textCtt;
	private Button finish;
	private ImageView back;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.advice_back);
		initViews();
	}

	private void initViews() {
		textSugg = (EditText) findViewById(R.id.sugg);
		textCtt = (EditText) findViewById(R.id.ctt);
		finish = (Button) findViewById(R.id.finish1);
		finish.setOnClickListener(this);
		back = (ImageView) findViewById(R.id.back1);
		back.setOnClickListener(this);
	}
	
	public void finish1(){
		if(textSugg.getText().toString().trim().length() ==0){
			UIHelper.showToastMessage("建议不能为空");
			return;
		}
		FeedbackStruct feedbackStruct = new FeedbackStruct();
		String mySugg = textSugg.getText().toString().trim();
		String myCtt = textCtt.getText().toString().trim();
		feedbackStruct.sugg = mySugg;
		feedbackStruct.ctt = myCtt;
		finish.setEnabled(false);
		new ApiClient().feedBack(feedbackStruct, this, BaseApi.class, new ApiClient.CallBack<BaseApi>() {

			@Override
			public void success(BaseApi api) {
				// TODO Auto-generated method stub
				finish.setEnabled(true);
				Log.d("debug", "api"+api);
				if(api == null)return;
				if(api.getRet_cd() == 0){
					UIHelper.showToastMessage("提交成功！");
				}
			}

			@Override
			public void fail(String error) {
				// TODO Auto-generated method stub
				UIHelper.showToastMessage(
						"当前网络有问题！");
				finish.setEnabled(true);
			}
		});
		
	}
//	public void back(View v)
//	{
//		onBackPressed();
//	}
////
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.finish1:
			finish1();
			break;
		case R.id.back1:
			onBackPressed();

		default:
			break;
		}
		
	}

}
