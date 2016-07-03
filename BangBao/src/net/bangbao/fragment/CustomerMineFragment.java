package net.bangbao.fragment;

import net.bangbao.AppConfig;
import net.bangbao.R;
import net.bangbao.UserConfig;
import net.bangbao.common.UIHelper;
import net.bangbao.dao.CustomApi;
import net.bangbao.dao.CustomerMessApi;
import net.bangbao.network.ApiClient;
import net.bangbao.network.RequestManager;
import net.bangbao.ui.ChatActivity;
import net.bangbao.ui.CustomerMessageActivity;
import net.bangbao.ui.MainActivity;
import net.bangbao.ui.MyConsultActivity;
import net.bangbao.ui.SettingActivity;
import net.bangbao.widget.CircleImage;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class CustomerMineFragment extends Fragment {
	
	private UserConfig userConfig;
	private TextView nick;
	public int customId;
	private CircleImage pic;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		
		super.onCreateView(inflater, container, savedInstanceState);
		View mine = inflater.inflate(R.layout.fragment_customer_mine, null);
		nick = (TextView) mine.findViewById(R.id.nick);
		pic = (CircleImage) mine.findViewById(R.id.customer_pic);
		userConfig = AppConfig.getUser();
		registerBoradcastReceiver();  
		
		framCircle = mine.findViewById(R.id.fram_circle);
        textCount = (TextView)mine.findViewById(R.id.text_count);
		new ApiClient().getCustomerMess(userConfig.getUserId(),userConfig.getUserToken(),this, CustomerMessApi.class, 
				new ApiClient.CallBack<CustomerMessApi>() {
			@Override
			public void success(CustomerMessApi api) {
				if(api == null)return;
				Log.d("bb", "name "+api.getNick_nm());
				if(api.getNick_nm()!=null)
					nick.setText(api.getNick_nm());
				if (api.getRet_cd() == 0) {

					if (api.getImage_url() != null) {
						RequestManager.getInstance().loadImage(
								api.getImage_url(),pic);
					}
				} else
					UIHelper.showToastMessage(api.getRet_txt());
			}

			@Override
			public void fail(String error) {
				
			}
		});
		
		mine.findViewById(R.id.consult).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),MyConsultActivity.class);
				startActivity(intent);
			}
		});
		mine.findViewById(R.id.person_message).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),CustomerMessageActivity.class);
				startActivity(intent);
			}
		});
		mine.findViewById(R.id.advice_back).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new ApiClient().getCustom(userConfig.getUserId(), userConfig.getUserToken(), this, CustomApi.class, 
						new ApiClient.CallBack<CustomApi>(){

							@Override
							public void success(CustomApi api) {
								if(api.getRet_cd() == 0) {
									customId = api.getSer_uid();
									String s = Integer.toString(customId);
									Intent intent = new Intent(CustomerMineFragment.this.getActivity(),ChatActivity.class);
									intent.putExtra("target_id", s);
									intent.putExtra("target_categry",ChatActivity.TaCustom);
									Log.d("bb", "ss"+customId);
									startActivity(intent);
								}
							}

							@Override
							public void fail(String error) {
								UIHelper.showToastMessage(
										"当前网络有问题！");
							}
					
				});
				
				
			}
		});
		mine.findViewById(R.id.setting1).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getActivity(),SettingActivity.class));
			}
		});
		
		return mine;
	}
	
	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver(){  
        @Override  
        public void onReceive(Context context, Intent intent) {  
            String action = intent.getAction();  
            if(action.equals("nick")){  
            	String s = intent.getStringExtra("nick");
            	nick.setText(s);
            }  
        }  
          
    };  
    
    
    public void registerBoradcastReceiver(){  
        IntentFilter myIntentFilter = new IntentFilter();  
        myIntentFilter.addAction("nick");  
        //注册广播        
        CustomerMineFragment.this.getActivity().registerReceiver(mBroadcastReceiver, myIntentFilter);
        
        IntentFilter intentFiler = new IntentFilter();
        intentFiler.addAction(MainActivity.MSGACTION);
        this.getActivity().registerReceiver(msgReceiver, intentFiler);
    }  

  //圆的那个
  	private View framCircle;
  	private TextView textCount;
  	private BroadcastReceiver msgReceiver = new BroadcastReceiver(){

  		@Override
  		public void onReceive(Context context, Intent intent) {
  			framCircle.setVisibility(View.VISIBLE);
  			textCount.setText(String.valueOf(AppConfig.msgContents.size()));
  		}
  	};
  	
  	public void onDestroy() {
  		super.onDestroy();
  		getActivity().unregisterReceiver(mBroadcastReceiver);
  		getActivity().unregisterReceiver(msgReceiver);
  	};
}
