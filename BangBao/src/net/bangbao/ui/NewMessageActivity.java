package net.bangbao.ui;

import net.bangbao.IReminder;
import net.bangbao.R;
import net.bangbao.ReminderConfigHandler;
import net.bangbao.base.BaseActivity;
import net.bangbao.common.WiperSwitchUtil;
import net.bangbao.common.WiperSwitchUtil.OnChangedListener;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

public class NewMessageActivity extends BaseActivity {
	
	private WiperSwitchUtil hk;
	private RelativeLayout r;
	private WiperSwitchUtil mVoiceWiper,mVirborWiper;
	
	private IReminder mReminder = new ReminderConfigHandler();
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.new_message);
		initViews();
	}

	private void initViews() {
		r = (RelativeLayout) findViewById(R.id.go_back3);
		r.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
		hk=(WiperSwitchUtil)findViewById(R.id.huadong);
		hk.setChecked(false);//初始化关闭
		hk.setOnChangedListener(new Changelisten());//绑定监听事件
		
		mVoiceWiper = (WiperSwitchUtil)findViewById(R.id.voice);
		mVirborWiper = (WiperSwitchUtil)findViewById(R.id.virbor);
		mVoiceWiper.setOnChangedListener(new OnChangedListener() {
			
			@Override
			public void OnChanged(WiperSwitchUtil wiperSwitch, boolean checkState) {
				mReminder.setVoiceEnable(checkState);
			}
		});
		
		mVirborWiper.setOnChangedListener(new OnChangedListener() {
			
			@Override
			public void OnChanged(WiperSwitchUtil wiperSwitch, boolean checkState) {
				mReminder.setVirborEnable(checkState);
			}
		});
	}
	private class Changelisten implements OnChangedListener{

		public void OnChanged(WiperSwitchUtil wiperSwitch, boolean checkState) {
			System.out.println(checkState);
		}
		
	}
	

}
