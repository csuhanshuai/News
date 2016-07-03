package net.bangbao.fragment;

import net.bangbao.AppConfig;
import net.bangbao.R;
import net.bangbao.UserConfig;
import net.bangbao.common.UIHelper;
import net.bangbao.dao.ConsultMessApi;
import net.bangbao.dao.CustomApi;
import net.bangbao.network.ApiClient;
import net.bangbao.network.RequestManager;
import net.bangbao.ui.ChatActivity;
import net.bangbao.ui.ConsultMessageActivity;
import net.bangbao.ui.MainActivity;
import net.bangbao.ui.ConsultMessageActivity.ConsultUser1;
import net.bangbao.ui.MyCustomerActivity;
import net.bangbao.ui.SettingActivity;
import net.bangbao.utils.MyImageCache;
import net.bangbao.widget.CircleImage;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class MineFragment extends Fragment {

	private CircleImage touxiang;
	private TextView name;
	private UserConfig userConfig;
	public int customId;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		super.onCreateView(inflater, container, savedInstanceState);
		View mine = inflater.inflate(R.layout.fragment_mine, null);
		userConfig = AppConfig.getUser();
		touxiang = (CircleImage) mine.findViewById(R.id.touxiang);
		name = (TextView) mine.findViewById(R.id.nick);
		registerBoradcastReceiver();
	    framCircle = mine.findViewById(R.id.fram_circle);
        textCount = (TextView)mine.findViewById(R.id.text_count);
		new ApiClient().getConsultMess(userConfig.getUserId(),
				userConfig.getUserToken(), this, ConsultMessApi.class,
				new ApiClient.CallBack<ConsultMessApi>() {

					@Override
					public void success(ConsultMessApi api) {
						if (api == null)
							return;
						ConsultUser1.eng1 = api.getNick_nm();
						if (api.getNick_nm() != null)
							name.setText(ConsultUser1.eng1);
						if (api.getRet_cd() == 0) {

							if (api.getImage_url() != null) {
								RequestManager.getInstance().loadImage(
										api.getImage_url(),touxiang);
//								RequestQueue queue = Volley.newRequestQueue(getActivity());
//								ImageLoader imageLoader = new ImageLoader(queue, new MyImageCache());
//								touxiang.setImageUrl(api.getImage_url(), imageLoader);
							}
						} else
							UIHelper.showToastMessage(api.getRet_txt());
					}

					@Override
					public void fail(String error) {

					}
				});

		mine.findViewById(R.id.customer).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(getActivity(),
								MyCustomerActivity.class);
						startActivity(intent);
					}
				});
		// LinearLayout ll1 = (LinearLayout)
		// mine.findViewById(R.id.layout_person_message);
		mine.findViewById(R.id.layout_person_message).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(getActivity(),
								ConsultMessageActivity.class);
						startActivity(intent);
					}
				});
		mine.findViewById(R.id.check_iwatch).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						new ApiClient().getAppleWatch(userConfig.getUserId(),
								userConfig.getUserToken(), this,
								CustomApi.class,
								new ApiClient.CallBack<CustomApi>() {

									@Override
									public void success(CustomApi api) {
										if (api.getRet_cd() == 0) {
											customId = api.getSer_uid();
											String s = Integer
													.toString(customId);
											Intent intent = new Intent(
													MineFragment.this
															.getActivity(),
													ChatActivity.class);
											intent.putExtra("target_id", s);
											intent.putExtra("target_categry",
													ChatActivity.TaAppleWatch);
											Log.d("bb", "ss" + customId);
											startActivity(intent);
										}
									}

									@Override
									public void fail(String error) {
										UIHelper.showToastMessage("��ǰ���������⣡");
									}

								});
					}
				});
		mine.findViewById(R.id.setting).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						startActivity(new Intent(getActivity(),
								SettingActivity.class));
					}
				});
		return mine;
	}

	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals("nick")) {
				String s = intent.getStringExtra("nick");
				name.setText(s);
			}
		}

	};

	public void registerBoradcastReceiver() {
		IntentFilter myIntentFilter = new IntentFilter();
		myIntentFilter.addAction("nick");
		// ע��㲥
		MineFragment.this.getActivity().registerReceiver(mBroadcastReceiver,
				myIntentFilter);
		
		 IntentFilter intentFiler = new IntentFilter();
	        intentFiler.addAction(MainActivity.MSGACTION);
	     MineFragment.this.getActivity().registerReceiver(msgReceiver, intentFiler);
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
