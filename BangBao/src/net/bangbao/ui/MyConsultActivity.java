package net.bangbao.ui;

/**
 * @author 帅
 * 我的顾问
 * 
 */

import java.util.ArrayList;
import java.util.List;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.baoyz.swipemenulistview.SwipeMenuListView.OnMenuItemClickListener;
import com.baoyz.swipemenulistview.SwipeMenuListView.OnSwipeListener;

import net.bangbao.AppConfig;
import net.bangbao.R;
import net.bangbao.UserConfig;
import net.bangbao.adapter.ConsultAdapter1;
import net.bangbao.base.BaseActivity;
import net.bangbao.base.BaseApi;
import net.bangbao.base.BaseApiClient;
import net.bangbao.base.BaseApiClient.Page;
import net.bangbao.common.UIHelper;
import net.bangbao.dao.ConsultApi;
import net.bangbao.dao.ConsultApi.ConsultInfo;
import net.bangbao.dao.CustomerHandler;
import net.bangbao.network.AgentApiClient;
import net.bangbao.network.ApiClient;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MyConsultActivity extends BaseActivity {
	private SwipeMenuListView listView1;
	private int agentId;
	private AgentApiClient agentClient = new AgentApiClient();
	private RelativeLayout r;
	private List<ConsultInfo> consultList = new ArrayList<ConsultInfo>();
	private ConsultAdapter1 consultAdapter;
	private ApiClient apiClient = new ApiClient();
	private UserConfig userConfig = AppConfig.getUser();
	private Page page = new Page();
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.consult_list);
		initViews();
		
		IntentFilter intentFiler = new IntentFilter();
	    intentFiler.addAction(MainActivity.MSGACTION);
	    registerReceiver(msgReceiver, intentFiler);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(msgReceiver);
	}
	
	private void initViews() {
		
		r = (RelativeLayout) findViewById(R.id.go_back1);
		r.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
		listView1 = (SwipeMenuListView) findViewById(R.id.consult_list);
		listView1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(MyConsultActivity.this,ConsultDetailActivity.class);
				agentId = MyConsultActivity.this.consultList.get(position).getUser_id();
				intent.putExtra("key",agentId);
				MyConsultActivity.this.startActivity(intent);
			}
		});
		consultAdapter = new ConsultAdapter1(consultList,this);
		listView1.setAdapter(consultAdapter);
		SwipeMenuCreator creator = new SwipeMenuCreator() {

			@Override
			public void create(SwipeMenu menu) {
				// create "open" item
//				SwipeMenuItem openItem = new SwipeMenuItem(
//						getApplicationContext());
//				// set item background
//				openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
//						0xCE)));
//				// set item width
//				openItem.setWidth(dp2px(90));
//				// set item title
//				openItem.setTitle("Open");
//				// set item title fontsize
//				openItem.setTitleSize(18);
//				// set item title font color
//				openItem.setTitleColor(Color.WHITE);
//				// add to menu
//				menu.addMenuItem(openItem);

				// create "delete" item
				SwipeMenuItem cancleFoucusItem = new SwipeMenuItem(
						getApplicationContext());
				// set item background
				cancleFoucusItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
						0x3F, 0x25)));
				// set item width
				cancleFoucusItem.setWidth(dp2px(90));
				// set a icon
				cancleFoucusItem.setTitle("不再关注");
				cancleFoucusItem.setTitleSize(18);
				cancleFoucusItem.setTitleColor(Color.WHITE);
				// add to menu
				menu.addMenuItem(cancleFoucusItem);
			}
		};
		listView1.setMenuCreator(creator);

		// step 2. listener item click event
		listView1.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public void onMenuItemClick(int position, SwipeMenu menu, int index) {
				ConsultInfo item = consultList.get(position);
				switch (index) {
//				case 0:
//					// open
////					open(item);
//					break;
				case 0:
					// delete
					new CustomerHandler().cancelFocus(consultList.get(position).getUser_id(), new BaseApiClient.CallBack<BaseApi>() {
						
						@Override
						public void success(BaseApi api) {
							
						}
						
						@Override
						public void fail(String error) {
							
						}
					});
					consultList.remove(position);
					consultAdapter.notifyDataSetChanged();
					break;
				}
			}
		});
		listView1.setOnSwipeListener(new OnSwipeListener() {
			
			@Override
			public void onSwipeStart(int position) {
				// swipe start
			}
			
			@Override
			public void onSwipeEnd(int position) {
				// swipe end
			}
		});
		listView1.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				Toast.makeText(getApplicationContext(), position + " long click", 0).show();
				return false;
			}
		});
//		pullToRefreshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
//
//			@Override
//			public void onPullDownToRefresh(
//					PullToRefreshBase<ListView> refreshView) {
//				refreshHandler.sendEmptyMessageDelayed(1, 2*1000);
//			}
//
//			@Override
//			public void onPullUpToRefresh(
//					PullToRefreshBase<ListView> refreshView) {
//				refreshHandler.sendEmptyMessageDelayed(1, 2*1000);
//			}
//		});
		getConsult();
//		jundeRequest();
		
		
	}
	
	protected void getConsult(){
			apiClient.getMyconsult(page, userConfig.getUserId(), userConfig.getUserToken(), this, ConsultApi.class,
					new ApiClient.CallBack<ConsultApi>(){

						@Override
						public void success(ConsultApi api) {
							if(api == null)return;
							if(api.getRet_cd() == 97){
								UIHelper.showToastMessage("没有记录了！");
								return;
							}
							else if(api.getRet_cd() != 0) {
								UIHelper.showToastMessage(api.getRet_txt());
								return;
							}
							if(api.getItem() == null)return;
							consultList.addAll(api.getItem());
							consultAdapter.notifyDataSetChanged();
							page.id_index = api.getId_index();
							page.page_total = api.getPage_total();
							page.page_size = api.getPage_size();
							for(int i = 1;i < page.page_total;i++){
								page.page_index = api.getPage_index() + 1;
								getConsult1();
							}
						}

						@Override
						public void fail(String error) {
							Log.d("json",error);
						}
				
			});
	}
	
	protected void getConsult1(){
			apiClient.getMyconsult(page, userConfig.getUserId(), userConfig.getUserToken(), this, ConsultApi.class,
					new ApiClient.CallBack<ConsultApi>(){

						@Override
						public void success(ConsultApi api) {
							if(api == null)return;
							if(api.getRet_cd() == 97){
								UIHelper.showToastMessage("没有记录了！");
								return;
							}
							else if(api.getRet_cd() != 0){
								UIHelper.showToastMessage(api.getRet_txt());
								return;
							}
							if(api.getItem() == null)return;
							consultList.addAll(api.getItem());
							consultAdapter.notifyDataSetChanged();
							page.id_index = api.getId_index();
							page.page_total = api.getPage_total();
							page.page_size = api.getPage_size();
//							page.page_index = api.getPage_index() + 1;
						}

						@Override
						public void fail(String error) {
							Log.d("json",error);
						}
				
			});
	}
	

	
	private int dp2px(int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				getResources().getDisplayMetrics());
	}
	
	
	public void back(View v)
	{
		onBackPressed();
	}

	private BroadcastReceiver msgReceiver = new BroadcastReceiver(){

		@Override
		public void onReceive(Context context, Intent intent) {
			consultAdapter.notifyDataSetChanged();
		}
	};
}
