package net.bangbao.ui;

import net.bangbao.R;
import net.bangbao.base.BaseActivity;
import net.bangbao.base.BaseApi;
import net.bangbao.base.BaseApiClient;
import net.bangbao.common.EditTextWatcher;
import net.bangbao.common.UIHelper;
import net.bangbao.network.CommonHttpClient;
import net.bangbao.utils.ABRegUtil;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

/**
 * @author mosl
 * 重置密码(无参)
 */
public class ResetPassActivity extends BaseActivity {
	
	private String mUserName;
	private int mProsId;
	private String mAnswers;
	
	public static final String USERNAME = "USERNAME";
	public static final String PROSID = "PROSID";
	public static final String PROANSWER = "PROANSWER";
	
	private Intent mIntent;
	private CommonHttpClient httpClient = new CommonHttpClient();
	
	private EditText password;
	private EditText conformPass;
	private TextView mConformText;
	
	private String strBefore;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		mIntent =getIntent();
		if(mIntent == null) finish();
		mUserName = mIntent.getStringExtra(USERNAME);
		mProsId = mIntent.getIntExtra(PROSID, -1);
		mAnswers = mIntent.getStringExtra(PROANSWER);
		setContentView(R.layout.activity_reset_pass);
		initViews();
	}

	private void initViews() {
		findViewById(R.id.fram_back).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
		password = (EditText)findViewById(R.id.edit_pass);
		conformPass = (EditText)findViewById(R.id.edit_pass_conform);
		mConformText = (TextView)findViewById(R.id.text_conform);
		password.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				mConformText.setVisibility(View.GONE);
				strBefore = s.toString();
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				int editStart = password.getSelectionStart();
				int offset = s.toString().length() - strBefore.length();
				if (ABRegUtil.isCN(s.toString()) || s.toString().endsWith(" ")){
					UIHelper.showToastMessage("不能输入中文或空格");
					s.delete(s.toString().length() - offset, s.toString().length());
					int tempSelection = editStart - offset;
					password.setText(s);
					password.setSelection(tempSelection);
				}
			}
		});
		conformPass.addTextChangedListener(new EditTextWatcher(conformPass, 1));
		findViewById(R.id.complete).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				resetPass();
			}
		});
	}
	
	private void resetPass(){
		if(password.getText().toString().trim().length() == 0){
			
			UIHelper.showToastMessage("密码不能为空!");
			return;
		}
        if(conformPass.getText().toString().trim().length() == 0){
			
			UIHelper.showToastMessage("请填写验证密码！");
			return;
		}
        if(!password.getText().toString().trim().equals(conformPass.getText().toString().trim())){
        	mConformText.setVisibility(View.VISIBLE);
        	return;
        }
		httpClient.resetPass(mUserName, mProsId, mAnswers, password.getText().toString(),this, BaseApi.class, new BaseApiClient.CallBack<BaseApi>() {

			@Override
			public void success(BaseApi api) {
				if(api.getRet_cd() == 0){
					UIHelper.showToastMessage("找回密码成功!");
					Intent intent = new Intent(ResetPassActivity.this,LoginActivity.class);
					startActivity(intent);
					finish();
				}
				else if(api.getRet_cd() != 0){
					UIHelper.showToastMessage(api.getRet_txt());
				}
			}

			@Override
			public void fail(String error) {
				
			}
		});
	}
}
