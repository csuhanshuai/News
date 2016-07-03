package net.bangbao.ui;

import java.util.ArrayList;
import java.util.List;

import net.bangbao.AppConfig;
import net.bangbao.R;
import net.bangbao.base.BaseActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * author:mosl App start 启动页
 */
public class AppStartActivity extends BaseActivity {

	private ViewPager mViewPager;
	private List<View> mList;// 滑动的布局
	private ViewGroup mPge1, mPage2, mPage3;
	private Button mEnterBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (AppConfig.isStartEd()) {
			AppConfig.firstStarted();
			startActivity(new Intent(this, MainActivity.class));
			finish();
		}
		setContentView(R.layout.activity_app_start);
		initViews();
	}

	private void initViews() {

		mPge1 = (ViewGroup) LayoutInflater.from(this).inflate(
				R.layout.activity_appstart_page1, null);
		mPage2 = (ViewGroup) LayoutInflater.from(this).inflate(
				R.layout.activity_appstart_page2, null);
		mPage3 = (ViewGroup) LayoutInflater.from(this).inflate(
				R.layout.activity_appstart_page3, null);
		mList = new ArrayList<View>();
		mList.add(mPge1);
		mList.add(mPage2);
		mList.add(mPage3);
		mViewPager = (ViewPager) findViewById(R.id.app_start_view_pager);
		mViewPager.setAdapter(new ViewPagerAdapter());

		mEnterBtn = (Button) mPage3.findViewById(R.id.enter_btn);
		mEnterBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AppConfig.firstStarted();
				startActivity(new Intent(AppStartActivity.this,
						MainActivity.class));
				finish();
			}
		});
	}

	class ViewPagerAdapter extends PagerAdapter {

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(mList.get(position));
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(mList.get(position));
			return mList.get(position);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		public int getCount() {
			return mList.size();
		}

	}
}
