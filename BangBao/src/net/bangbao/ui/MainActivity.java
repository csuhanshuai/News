package net.bangbao.ui;

import java.util.ArrayList;
import java.util.List;

import net.bangbao.AppConfig;
import net.bangbao.R;
import net.bangbao.adapter.OnCheckedListener;
import net.bangbao.adapter.TabAdapter;
import net.bangbao.base.BaseActivity;
import net.bangbao.common.UIHelper;
import net.bangbao.fragment.AdvertiseFragment;
import net.bangbao.fragment.ChooseInsuranceFragment;
import net.bangbao.fragment.ConsultFragment;
import net.bangbao.fragment.CustomerMineFragment;
import net.bangbao.fragment.MessageFragment;
import net.bangbao.fragment.MineFragment;
import net.bangbao.service.RongYunService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

/*
 * author:mosl
 * description：主tab界面
 */
public class MainActivity extends BaseActivity implements View.OnClickListener{
	
	private long exitTime;
	public List<Fragment> fragments = new ArrayList<Fragment>();
	public OnCheckedListener checkedListener;
	private TabAdapter tabAdapter;
	private View tab1,tab2,tab3,tab4,tab5;
	
	//圆的那个
	private View framCircle;
	private TextView textCount;
	
	public static final String MSGACTION = "net.bangbao.action.msg.count";
	private ImageView tab1_image,tab2_image,tab3_image,tab4_image,tab5_image;
	private TextView tab1_text,tab2_text,tab3_text,tab4_text,tab5_text;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		setContentView(R.layout.activity_main);
        fragments.add(new MessageFragment());
        fragments.add(new AdvertiseFragment());
        fragments.add(new ChooseInsuranceFragment());
        fragments.add(new ConsultFragment());
//        fragments.add(new MineFragment());

        tabAdapter = new TabAdapter(this, savedInstanceState,fragments, R.id.tab_content);
        checkedListener = tabAdapter.getOnChangedListener();
        
        tab1 = findViewById(R.id.tab_rb_1);
        tab2 = findViewById(R.id.tab_rb_2);
        tab3 = findViewById(R.id.tab_rb_3);
        tab4 = findViewById(R.id.tab_rb_4);
        tab5 = findViewById(R.id.tab_rb_5);
        
        tab1.setOnClickListener(this);
        tab2.setOnClickListener(this);
        tab3.setOnClickListener(this);
        tab4.setOnClickListener(this);
        
        initTabs();
// tab5.setOnClickListener(this);
		// 判断是否登录及用户类型来显示不同的“我的”版块
		if (!AppConfig.isLogin()) {
			tab5.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					startActivity(new Intent(MainActivity.this,
							LoginActivity.class));
				}
			});
		} else if (AppConfig.isLogin() && AppConfig.getUser().getType() == 2) {
			fragments.add(new MineFragment());
		
		
        tab5.setOnClickListener(this);
	} else if (AppConfig.isLogin() && AppConfig.getUser().getType() == 1) {
			fragments.add(new CustomerMineFragment());
			tab5.setOnClickListener(this);
		}
        framCircle = findViewById(R.id.fram_circle);
        textCount = (TextView)findViewById(R.id.text_count);
        
        //启动融云 服务
        if(AppConfig.IMClient != null)
        startService(new Intent(this,RongYunService.class));
        
        IntentFilter intentFiler = new IntentFilter();
        intentFiler.addAction(MSGACTION);
        registerReceiver(msgReceiver, intentFiler);
        
	}

	
	private void initTabs() {
		tab1_image = (ImageView)findViewById(R.id.tab1_image);
		tab2_image = (ImageView)findViewById(R.id.tab2_image);
		tab3_image = (ImageView)findViewById(R.id.tab3_image);
		tab4_image = (ImageView)findViewById(R.id.tab4_image);
		tab5_image = (ImageView)findViewById(R.id.tab5_image);
		
		tab1.setOnClickListener(this);
		tab2.setOnClickListener(this);
		tab3.setOnClickListener(this);
		tab4.setOnClickListener(this);
		tab5.setOnClickListener(this);
		
		tab1_text = (TextView)findViewById(R.id.tab1_text);
		tab2_text = (TextView)findViewById(R.id.tab2_text);
		tab3_text = (TextView)findViewById(R.id.tab3_text);
		tab4_text = (TextView)findViewById(R.id.tab4_text);
		tab5_text = (TextView)findViewById(R.id.tab5_text);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(msgReceiver);
	}
	
	@Override
	public void onStop() {
		super.onStop();
	}
	
	 public void exitApp(){
		
	    if (System.currentTimeMillis() - this.exitTime > 2000L){
	      UIHelper.showToastMessage( getResources().getString(R.string.quit));
	      this.exitTime = System.currentTimeMillis();
	      return;
	    }
	    finish();
	    System.exit(0);
	  }
	public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent){
	    if (paramInt == 4)
	      exitApp();
	    return false;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tab_rb_1:
			checkedListener.onCheckedChanged(0);
			tab1_image.setBackgroundResource(R.drawable.message_pressed);
			tab2_image.setBackgroundResource(R.drawable.ad_unpressed);
			tab3_image.setBackgroundResource(R.drawable.insurance_unpressed);
			tab4_image.setBackgroundResource(R.drawable.consult_unpressed);
			tab5_image.setBackgroundResource(R.drawable.mine_unpressed);
			
			tab1_text.setTextColor(Color.rgb( 51,132,255));
			tab2_text.setTextColor(Color.rgb(102, 102, 102));
			tab3_text.setTextColor(Color.rgb(102, 102, 102));
			tab4_text.setTextColor(Color.rgb(102, 102, 102));
			tab5_text.setTextColor(Color.rgb(102, 102, 102));
			break;
		case R.id.tab_rb_2:
			checkedListener.onCheckedChanged(1);
			tab1_image.setBackgroundResource(R.drawable.message_unpressed);
			tab2_image.setBackgroundResource(R.drawable.ad_pressed);
			tab3_image.setBackgroundResource(R.drawable.insurance_unpressed);
			tab4_image.setBackgroundResource(R.drawable.consult_unpressed);
			tab5_image.setBackgroundResource(R.drawable.mine_unpressed);
			
			tab1_text.setTextColor(Color.rgb(102, 102, 102));
			tab2_text.setTextColor(Color.rgb( 51,132,255));
			tab3_text.setTextColor(Color.rgb(102, 102, 102));
			tab4_text.setTextColor(Color.rgb(102, 102, 102));
			tab5_text.setTextColor(Color.rgb(102, 102, 102));
			break;
		case R.id.tab_rb_3:
			checkedListener.onCheckedChanged(2);
			tab1_image.setBackgroundResource(R.drawable.message_unpressed);
			tab2_image.setBackgroundResource(R.drawable.ad_unpressed);
			tab3_image.setBackgroundResource(R.drawable.insurance_pressed);
			tab4_image.setBackgroundResource(R.drawable.consult_unpressed);
			tab5_image.setBackgroundResource(R.drawable.mine_unpressed);
			
			tab1_text.setTextColor(Color.rgb(102, 102, 102));
			tab2_text.setTextColor(Color.rgb(102, 102, 102));
			tab3_text.setTextColor(Color.rgb( 51,132,255));
			tab4_text.setTextColor(Color.rgb(102, 102, 102));
			tab5_text.setTextColor(Color.rgb(102, 102, 102));
			break;
		case R.id.tab_rb_4:
			checkedListener.onCheckedChanged(3);
			tab1_image.setBackgroundResource(R.drawable.message_unpressed);
			tab2_image.setBackgroundResource(R.drawable.ad_unpressed);
			tab3_image.setBackgroundResource(R.drawable.insurance_unpressed);
			tab4_image.setBackgroundResource(R.drawable.consult_pressed);
			tab5_image.setBackgroundResource(R.drawable.mine_unpressed);
			
			tab1_text.setTextColor(Color.rgb(102, 102, 102));
			tab2_text.setTextColor(Color.rgb(102, 102, 102));
			tab3_text.setTextColor(Color.rgb(102, 102, 102));
			tab4_text.setTextColor(Color.rgb( 51,132,255));
			tab5_text.setTextColor(Color.rgb(102, 102, 102));
			break;
		case R.id.tab_rb_5:
			checkedListener.onCheckedChanged(4);
			tab1_image.setBackgroundResource(R.drawable.message_unpressed);
			tab2_image.setBackgroundResource(R.drawable.ad_unpressed);
			tab3_image.setBackgroundResource(R.drawable.insurance_unpressed);
			tab4_image.setBackgroundResource(R.drawable.consult_unpressed);
			tab5_image.setBackgroundResource(R.drawable.mine_pressed);
			
			tab1_text.setTextColor(Color.rgb(102, 102, 102));
			tab2_text.setTextColor(Color.rgb(102, 102, 102));
			tab3_text.setTextColor(Color.rgb(102, 102, 102));
			tab4_text.setTextColor(Color.rgb(102, 102, 102));
			tab5_text.setTextColor(Color.rgb( 51,132,255));
			
			 //判断是否登录及用户类型来显示不同的“我的”版块
	        if(!AppConfig.isLogin()){
	        	startActivity(new Intent(MainActivity.this,LoginActivity.class));
	        }
			break;
		}
	}
	
	private BroadcastReceiver msgReceiver = new BroadcastReceiver(){

		@Override
		public void onReceive(Context context, Intent intent) {
			framCircle.setVisibility(View.VISIBLE);
			textCount.setText(String.valueOf(AppConfig.msgContents.size()));
		}
	};
}
