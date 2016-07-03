package net.bangbao.ui;

import java.util.List;

import net.bangbao.R;
import net.bangbao.base.BaseActivity;
import net.bangbao.common.EditTextWatcher;
import net.bangbao.common.Ids;
import net.bangbao.common.UIHelper;
import net.bangbao.widget.SpinnerDropDown;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
/*
 * author:mosl
 * 忘记密码界面(无参)
 */
public class ForgetPassActivity extends BaseActivity implements View.OnClickListener{

	private View BackView;
	private List<String> mProblems;
	private View mViewPros;
	private TextView mQuestionText;
	private SpinnerDropDown mProbsDropDown;
	private ImageView mArrow;
	private Button mCommplete;
	
	private String mUserName;
	private int mProsId = 1;
	private String mAnswers;
	
	private EditText userNameEdit;
	private EditText answerEdit;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_forget_pass);
		init();
	}

	private void init() {
		mProblems = Ids.getPassAnswer();
		mViewPros = findViewById(R.id.pros_setting);
		mViewPros.setOnClickListener(this);
		BackView = findViewById(R.id.fram_back);
		mQuestionText = (TextView)findViewById(R.id.question_text);
		mArrow = (ImageView)findViewById(R.id.arrow);
		mCommplete = (Button)findViewById(R.id.complete);
		mCommplete.setOnClickListener(this);
		userNameEdit = (EditText)findViewById(R.id.user_name);
		answerEdit = (EditText)findViewById(R.id.probs_ans);
		BackView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
		userNameEdit.addTextChangedListener(new EditTextWatcher(userNameEdit,1));
		answerEdit.addTextChangedListener(new EditTextWatcher(answerEdit,0));
		}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pros_setting:
			mArrow.setImageResource(R.drawable.arrow_up);
			mViewPros.setBackgroundResource(R.drawable.layout_comms_up);
			mProbsDropDown = new SpinnerDropDown(mProblems, this, v.getMeasuredWidth(), 
				 new SpinnerDropDown.IOnClickItemListener() {
					
					@Override
					public void clickItem(int item) {
						mQuestionText.setText(mProblems.get(item));
						mProsId = item + 1;
					}
				},0);
			mProbsDropDown.setOnDismissListener(new OnDismissListener() {
				
				@Override
				public void onDismiss() {
					mViewPros.setBackgroundResource(R.drawable.layout_circle);
					mArrow.setImageResource(R.drawable.bt_down);
				}
			});
			mProbsDropDown.showAsDropDown(v, 0, 0);
			break;
		case R.id.complete:
			mUserName = userNameEdit.getText().toString().trim();
			if(mUserName == null || mUserName.length() == 0){
				UIHelper.showToastMessage("用户名没填");
				return;
			}
			
			if(mUserName.length() <6){
				UIHelper.showToastMessage("用户名长度是6-32");
				return;
			}
			mAnswers = answerEdit.getText().toString().trim();
			if(mAnswers == null || mAnswers.length() == 0){
				UIHelper.showToastMessage("答案没填");
				return;
			}
			Intent intent = new Intent(this,ResetPassActivity.class);
			intent.putExtra(ResetPassActivity.USERNAME,mUserName );
			intent.putExtra(ResetPassActivity.PROSID,mProsId);
			intent.putExtra(ResetPassActivity.PROANSWER, mAnswers);
			startActivity(intent);
		default:
			break;
		}
	}
	
	
}
