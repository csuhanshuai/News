package net.bangbao.ui;

import java.util.ArrayList;
import java.util.List;

import net.bangbao.R;
import net.bangbao.adapter.ChooseInsuAdapter;
import net.bangbao.base.BaseActivity;
import net.bangbao.base.BaseApiClient;
import net.bangbao.base.BaseApiClient.Page;
import net.bangbao.common.UIHelper;
import net.bangbao.dao.InsuCatgApi;
import net.bangbao.dao.InsuCatgApi.InsuCatgInfo;
import net.bangbao.network.ApiClient;
import net.bangbao.utils.Utils;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.lib_refresh.PullToRefreshBase;
import com.lib_refresh.PullToRefreshBase.OnRefreshListener;
import com.lib_refresh.PullToRefreshListView;

// 点击选保险进来的，从选保险Fragment界面传过来3个参数
// CLICK_TYPE -- 点击类型：1险种分类还是2品牌分类
// company：公司名称，对应关系如下：
// ID 名称 
// --------
// 1, 英国保诚
// 2, AIA友邦 
// 3, AXA安盛 
// 4, 宏利    
// 5, 富卫   
// categry 对应5个图标按顺序排列
//FIXME 这里还没有做完优化：代码：两个adapter合并成一个，报文的cmd不同，内容也有些许不同。
/**
 * 保险类别 InsuranceCategory。 上一个界面是ChooseInsuranceFragment，下一个界面是
 * 
 * @author Spartacus26
 * @since 2015.3.17
 */
public class InsuCatgActivity extends BaseActivity {

	/** 从选保险点击进来的listview，只有下拉加载更多，没有上拉刷新 */
	private PullToRefreshListView mPullToRefreshListView;
	/** PullToRefreshListView里面的listview */
	private ListView mListView;
	/** 分类列表 */
	private List<InsuCatgInfo> mCategoryList = new ArrayList<InsuCatgInfo>();

	private ChooseInsuAdapter mInsucatgAdapter;

	/** CLICK_TYPE -- 点击类型：1险种分类还是2品牌分类 */
	private int mClickType;
	/** 险种分类中的5类 */
	private int mCategryInt;
	/** 品牌分类中的5类 */
	private int mCompanyInt;

	private ApiClient apiClient = new ApiClient();
	/** 页数，从0开始，每页递增1 */
	private Page mPage = new Page();

	/** 从服务器获取的当前页的item的总数 */
	private int mCountItem;

	/** listview的条数，刷新的时候显示最新的数据：记录上一次刷新的条数+1 */
	private int mCount;

	// listView.setselection(listView.getBottom) 或者
	// listView.setselection(adapter.getCount())
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_insucatg);
		initView();
		
		// 得到从上一个界面ChooseInsuranceFragment传递过来的内容
		Intent intent = getIntent();
		if (intent != null) {
			mClickType = intent.getIntExtra("CLICK_TYPE", 0);
			if (mClickType == 1) {
				// 说明点击的是险种分类
				// 用不同的adapter，写好了的
				mCategryInt = intent.getIntExtra("categry", -1);
				if (Utils.internetDdetect(InsuCatgActivity.this)) {
					getCategryData();
					mInsucatgAdapter = new ChooseInsuAdapter(this,
							mCategoryList);
					mListView.setAdapter(mInsucatgAdapter);
				} else {
					// 没有网络，提示用户打开网络
					Utils.openNetword(InsuCatgActivity.this);
					finish();
				}

			} else if (mClickType == 2) {
				// 说明点击的是品牌分类
				mCompanyInt = intent.getIntExtra("company", 0);
				if (Utils.internetDdetect(InsuCatgActivity.this)) {
					getCommInsuData();
					mInsucatgAdapter = new ChooseInsuAdapter(this,
							mCategoryList);
					mListView.setAdapter(mInsucatgAdapter);
				} else {
					// 没有网络，提示用户打开网络
					Utils.openNetword(InsuCatgActivity.this);
					finish();
				}
			} else {
				// 0的情况，暂时没有
			}

		}

	}

	private void initView() {

		mPullToRefreshListView = (PullToRefreshListView) findViewById(R.id.accident_list);
		mPullToRefreshListView.setPullRefreshEnabled(false);
		mPullToRefreshListView.setPullLoadEnabled(true);
		mListView = mPullToRefreshListView.getRefreshableView();

		// 设置分割线，顺序不能变
		mListView.setDivider(this.getResources()
				.getDrawable(R.color.gray_title));
		// 这个方法颜色有误差
		// mListView.setDivider(new ColorDrawable(R.color.gray_title));
		mListView.setDividerHeight(1);
		mListView.setVerticalScrollBarEnabled(false);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(InsuCatgActivity.this,
						ArticleContent.class);
				intent.putExtra("key", mCategoryList.get(position)
						.getCntt_url());
				intent.putExtra("title", mCategoryList.get(position)
						.getTit());
				intent.putExtra("description", "");
				InsuCatgActivity.this.startActivity(intent);
			}
		});
		// 下拉加载历史
		mPullToRefreshListView
				.setOnRefreshListener(new OnRefreshListener<ListView>() {

					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<ListView> refreshView) {

					}

					@Override
					public void onPullUpToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						// 加载更多
						// mPage.page_index++;
						// 先判断刷新类型：1险种分类还是2品牌分类
						if (mClickType == 1) {
							if (Utils.internetDdetect(InsuCatgActivity.this)) {
								// 没有历史记录了，不发起网络请求
								if (mCountItem < mPage.page_size) {
									mPullToRefreshListView
											.onPullUpRefreshComplete();
									UIHelper.showToastMessage("没有历史记录了！");
								} else {
									getCategryData();
									mListView.setAdapter(mInsucatgAdapter);
								}

							} else {
								mPullToRefreshListView
										.onPullUpRefreshComplete();
								// 没有网络，提示用户打开网络
								Utils.openNetword(InsuCatgActivity.this);

							}
						} // 品牌分类
						else {
							if (Utils.internetDdetect(InsuCatgActivity.this)) {
								// 没有历史记录了，不发起网络请求
								if (mCountItem < mPage.page_size) {
									mPullToRefreshListView
											.onPullUpRefreshComplete();
									UIHelper.showToastMessage("没有历史记录了！");
								} else {
									getCommInsuData();
									mListView.setAdapter(mInsucatgAdapter);
								}

							} else {
								mPullToRefreshListView
										.onPullUpRefreshComplete();
								// 没有网络，提示用户打开网络
								Utils.openNetword(InsuCatgActivity.this);

							}
						}
					}
				});

	}

	/**
	 * 在xml里面写了onClick方法，不建议这么使用
	 * 
	 * @param v
	 */
	public void back(View v) {
		onBackPressed();
	}

	/**
 * 
 */
	private void getCategryData() {

		apiClient.getInsuCatg(mPage, mCategryInt, this, InsuCatgApi.class,
				new BaseApiClient.CallBack<InsuCatgApi>() {

					@Override
					public void success(InsuCatgApi api) {
						if (api.getRet_cd() == 0) {
							mPage.id_index = api.getId_index();
							mPage.page_size = api.getPage_size();
							mCountItem = api.getItem().size();
							mPage.page_index = api.getPage_index() + 1;
							mCategoryList.addAll(api.getItem());
							mInsucatgAdapter.notifyDataSetChanged();
							// 显示上次的listview的最后一条+1的数据，即最新数据
							mListView.setSelection(mCount);
							// 重新计算listview总数
							mCount = mCategoryList.size();
							mPullToRefreshListView.onPullUpRefreshComplete();
						} else {
							UIHelper.showToastMessage("没有历史记录了！");
							mListView.setSelection(mCount);
							mPullToRefreshListView.onPullUpRefreshComplete();
						}

					}

					@Override
					public void fail(String error) {
						Log.d("InsuCatgActivity", error);

					}

				});

	}

	private void getCommInsuData() {
		apiClient.getCommInsu(mPage, mCompanyInt, this, InsuCatgApi.class,
				new BaseApiClient.CallBack<InsuCatgApi>() {

					@Override
					public void success(InsuCatgApi api) {
						if (api.getRet_cd() == 0) {
							mPage.id_index = api.getId_index();
							mPage.page_size = api.getPage_size();
							mPage.page_index = api.getPage_index() + 1;
							mCountItem = api.getItem().size();
							mCategoryList.addAll(api.getItem());
							mInsucatgAdapter.notifyDataSetChanged();
							// 显示上次的listview的最后一条+1的数据，即最新数据
							mListView.setSelection(mCount);
							// 从新计算listview总数
							mCount = mCategoryList.size();
							mPullToRefreshListView.onPullUpRefreshComplete();
						} else {
							UIHelper.showToastMessage("没有历史记录了！");
							mListView.setSelection(mCount);
							mPullToRefreshListView.onPullUpRefreshComplete();
						}
					}

					@Override
					public void fail(String error) {
						Log.d("InsuCatgActivity", error);

					}

				});
	}

}
