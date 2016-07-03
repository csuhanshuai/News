package net.bangbao.fragment;

import java.util.ArrayList;
import java.util.List;


import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import net.bangbao.R;
import net.bangbao.base.BaseFragment;
import net.bangbao.common.DeviceUtil;
import net.bangbao.network.ApiClient;
import net.bangbao.ui.ArticleContent;
import net.bangbao.ui.LocateHospotal;

/*
 *  帮理赔
 */
public class SettleFragment extends BaseFragment implements View.OnClickListener,OnTouchListener{
	
	private TextView mSequence,mCoaddress,mHospotal;
	private ImageButton mButtonSequence,mButtonAddress,mButtonHospotal;
	//ListView
	private ListView mCompanyListView,mCategryListView,mAddressListView;
	//上下文
	private Context mContext;
	private LinearLayout content;
	
	//定义数组
	private String[] mCompanys = new String[]{"英国保诚","AIA友邦","AXA安盛","宏利","富卫"};
	private String[] mCategrys = new String[]{"意外险","危急险","理财险","人寿险","医疗险"};
	
	//定义容器
	private LinearLayout mCommLinear,mInsuLinear;
	private FrameLayout.LayoutParams mCommlps,mInsulps;
	
	private boolean isCommOpen;
	private boolean isInsuOpen;
	
	private float xDown; //x down
	private float xMove;
	
	private float screenWidth;
	
	private View framBack;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = getActivity();
	
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_help_settle, null);
		initViews(view);
		content = (LinearLayout)view.findViewById(R.id.content);
		return view;
	}
	
	private void initViews(View view) {
		
		framBack = view.findViewById(R.id.fram_back);
		framBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(isCommOpen){
					new ScrollTask().execute(-30);
					isCommOpen = false;
					framBack.setVisibility(View.GONE);
				}
			}
		});
		mCommLinear = (LinearLayout)view.findViewById(R.id.comm_layout);
		mCommLinear.setOnTouchListener(this);
		mInsuLinear = (LinearLayout)view.findViewById(R.id.insu_layout);
		mCommlps = (FrameLayout.LayoutParams)mCommLinear.getLayoutParams();
		mInsulps = (FrameLayout.LayoutParams)mInsuLinear.getLayoutParams();
		
		mCommlps.leftMargin = DeviceUtil.getScreenWidth(getActivity());
		mCommLinear.setLayoutParams(mCommlps);
		
		mInsuLinear.setPadding(0, 145, 0, 0);
		screenWidth = DeviceUtil.getScreenWidth(getActivity());
		mInsulps.leftMargin = DeviceUtil.getScreenWidth(getActivity());
		mInsuLinear.setLayoutParams(mInsulps);
		
		mButtonSequence = (ImageButton)view.findViewById(R.id.button_sequence);
		mButtonAddress = (ImageButton)view.findViewById(R.id.button_address);
		mButtonHospotal = (ImageButton)view.findViewById(R.id.button_hospotal);
		
		mButtonSequence.setOnClickListener(this);
		mButtonHospotal.setOnClickListener(this);
		mButtonAddress.setOnClickListener(this);
		
		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		mCompanyListView = new ListView(mContext);
		mCategryListView = new ListView(mContext);
		mAddressListView = new ListView(mContext);
		
		mCompanyListView.setLayoutParams(lp);
		mCategryListView.setLayoutParams(lp);
		mAddressListView.setLayoutParams(lp);
		//公司列表
		mCompanyListView.setAdapter(new BaseAdapter() {
			
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				
				LinearLayout linear = new LinearLayout(mContext);
				LayoutParams linLp = new LayoutParams(LayoutParams.MATCH_PARENT,
						LayoutParams.WRAP_CONTENT);
				linear.setLayoutParams(linLp);
				TextView text = new TextView(mContext);
				LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, 80);
				text.setLayoutParams(lp);
				text.setTextSize(15.f);
				text.setText(mCompanys[position]);
				text.setGravity(Gravity.CENTER);
				text.setBackgroundColor(Color.rgb(172, 207, 226));
				text.setTextColor(Color.BLACK);
				LinearLayout.LayoutParams TextlinLp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
						80);
				TextlinLp.setMargins(0, 0, 0, 0);
				linear.addView(text, TextlinLp);
				return linear;
			}
			
			@Override
			public long getItemId(int position) {
				return position;
			}
			
			@Override
			public Object getItem(int position) {
				return position;
			}
			
			@Override
			public int getCount() {
				return mCompanys.length;
			}
		});
		
		//种类列表
		mCategryListView.setAdapter(new BaseAdapter() {
			
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				LinearLayout linear = new LinearLayout(mContext);
				LayoutParams linLp = new LayoutParams(LayoutParams.MATCH_PARENT,
						LayoutParams.WRAP_CONTENT);
				linear.setLayoutParams(linLp);
				TextView text = new TextView(mContext);
				LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, 80);
				text.setLayoutParams(lp);
				text.setTextSize(15.f);
				text.setText(mCategrys[position]);
				text.setGravity(Gravity.CENTER);
				text.setBackgroundColor(Color.rgb(172, 207, 226));
				LinearLayout.LayoutParams TextlinLp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
						80);
				TextlinLp.setMargins(0, 10, 0, 0);
				linear.addView(text, TextlinLp);
				return linear;
			}
			
			@Override
			public long getItemId(int position) {
				return 0;
			}
			
			@Override
			public Object getItem(int position) {
				return null;
			}
			
			@Override
			public int getCount() {
				return mCategrys.length;
			}
		});
		
		mCompanyListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				new ScrollTask2().execute(30);
				mInsuLinear.addView(mCategryListView);
			}
		});
		
		mCategryListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				Intent intent = new Intent(mContext,ArticleContent.class);
				ArrayList<Integer> list = new ArrayList();
				list.add(2);
				list.add(2);
				intent.putIntegerArrayListExtra("key", list);
				startActivity(intent);
			}
		});
		//地址列表
		mAddressListView.setAdapter(new BaseAdapter() {
			
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				
				LinearLayout linear = new LinearLayout(mContext);
				linear.setPadding(10, 40, 20, 20);
				LayoutParams linLp = new LayoutParams(LayoutParams.MATCH_PARENT,
						LayoutParams.WRAP_CONTENT);
				linear.setOrientation(LinearLayout.VERTICAL);
				linear.setLayoutParams(linLp);
				//添加text
				TextView text = new TextView(mContext);
				LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,
						LayoutParams.MATCH_PARENT);
				text.setLayoutParams(lp);
				text.setTextSize(15.f);
				text.setText(mCompanys[position]);
				text.setGravity(Gravity.LEFT);
				linear.addView(text);
				
				//添加地址栏logo
				LinearLayout address  = new LinearLayout(mContext);
				LayoutParams linLp2 = new LayoutParams(LayoutParams.MATCH_PARENT,
						LayoutParams.WRAP_CONTENT);
				address.setLayoutParams(linLp2);
				address.setOrientation(LinearLayout.HORIZONTAL);
				
				ImageView address_logo = new ImageView(mContext);
				LayoutParams lp2 = new LayoutParams(50,
						50);
				address_logo.setLayoutParams(lp2);
				address_logo.setImageResource(R.drawable.address);
				address.addView(address_logo);
				
				//地址栏文本
				TextView text_address = new TextView(mContext);
				LayoutParams lp3 = new LayoutParams(LayoutParams.WRAP_CONTENT,
						LayoutParams.WRAP_CONTENT);
				text_address.setLayoutParams(lp3);
				text_address.setTextSize(15.f);
				text_address.setText("这里是文本");
				text_address.setGravity(Gravity.LEFT);
				LinearLayout.LayoutParams lptext_address = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
						LayoutParams.WRAP_CONTENT);
				lptext_address.weight = 1;
				address.addView(text_address,lptext_address);
				linear.addView(address);
				return linear;
			}
			
			@Override
			public long getItemId(int position) {
				return 0;
			}
			
			@Override
			public Object getItem(int position) {
				return null;
			}
			
			@Override
			public int getCount() {
				return mCompanys.length;
			}
		});
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			xDown = event.getRawX();
			break;
		case MotionEvent.ACTION_MOVE:
		    xMove = event.getRawX();
		    int distanceX = (int)(xMove - xDown);
		    mCommlps.leftMargin = distanceX;
		    mCommLinear.setLayoutParams(mCommlps);
			break;
		default:
			break;
		}
		return true;
	}
	
	class ScrollTask2 extends AsyncTask<Integer, Integer, Integer>{

		@Override
		protected Integer doInBackground(Integer... speed) {
			int leftMargin = mInsulps.leftMargin;
			// 根据传入的速度来滚动界面，当滚动到达左边界或右边界时，跳出循环。
			while (true) {
				leftMargin = leftMargin - speed[0];
				if (leftMargin <120) {
					break;
				}
				publishProgress(leftMargin);
				// 为了要有滚动效果产生，每次循环使线程睡眠20毫秒，这样肉眼才能够看到滚动动画。
				sleep(20);
			}
			return leftMargin;
		}
		
		@Override
		protected void onProgressUpdate(Integer... leftMargin) {
			mInsulps.leftMargin = leftMargin[0];
			mInsuLinear.setLayoutParams(mInsulps);
		}

		@Override
		protected void onPostExecute(Integer leftMargin) {
			mInsulps.leftMargin = leftMargin;
			mInsuLinear.setLayoutParams(mInsulps);
		}
	}
	
	class ScrollTask extends AsyncTask<Integer, Integer, Integer> {

		@Override
		protected Integer doInBackground(Integer... speed) {
			int leftMargin = mCommlps.leftMargin;
			// 根据传入的速度来滚动界面，当滚动到达左边界或右边界时，跳出循环。
			while (true) {
				leftMargin = leftMargin - speed[0];
				if (leftMargin <120) {
					break;
				}
				if(leftMargin > screenWidth)
					break;
				publishProgress(leftMargin);
				// 为了要有滚动效果产生，每次循环使线程睡眠20毫秒，这样肉眼才能够看到滚动动画。
				sleep(20);
			}
			return leftMargin;
		}

		@Override
		protected void onProgressUpdate(Integer... leftMargin) {
			mCommlps.leftMargin = leftMargin[0];
			mCommLinear.setLayoutParams(mCommlps);
		}

		@Override
		protected void onPostExecute(Integer leftMargin) {
			mCommlps.leftMargin = leftMargin;
			mCommLinear.setLayoutParams(mCommlps);
		}
	}

	/**
	 * 使当前线程睡眠指定的毫秒数。
	 * 
	 * @param millis
	 *            指定当前线程睡眠多久，以毫秒为单位
	 */
	private void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.button_sequence:
			if(!isCommOpen){
				mCommLinear.removeAllViews();
				mCommLinear.setVisibility(View.VISIBLE);
				mCommLinear.setPadding(0, 145, 0, 0);
				mCommLinear.addView(mCompanyListView);
			    new ScrollTask().execute(30);
			    framBack.setVisibility(View.VISIBLE);
			    isCommOpen = true;
			}else{
				
				//isCommOpen = false;
			}
			break;
		case R.id.text_hospotal:
			Intent intent = new Intent(mContext,LocateHospotal.class);
			mContext.startActivity(intent);
			break;
		case R.id.button_address:
			mCommLinear.removeAllViews();
			mCommLinear.setVisibility(View.VISIBLE);
			mCommLinear.setPadding(0, 20, 0, 0);
			mCommLinear.addView(mAddressListView);
			break;
		}
	}
	
}
