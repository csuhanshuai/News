package net.bangbao.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.bangbao.R;
import net.bangbao.adapter.HeadNewsAdapter;
import net.bangbao.adapter.MessTabPagAdapter;
import net.bangbao.adapter.NewsAdapter;
import net.bangbao.adapter.OutLineAdapter;
import net.bangbao.adapter.ProblemsAdapter;
import net.bangbao.base.BaseApiClient;
import net.bangbao.base.BaseApiClient.Page;
import net.bangbao.common.DeviceUtil;
import net.bangbao.dao.NewsApi;
import net.bangbao.dao.NewsApi.NewsBean;
import net.bangbao.macro.MessageMacro;
import net.bangbao.network.ApiClient;
import net.bangbao.oath.Constants;
import net.bangbao.ui.ArticleContent;
import net.bangbao.utils.DateUtils;
import net.bangbao.utils.MyImageCache;
import net.bangbao.utils.Utils;
import net.bangbao.widget.AutoScrllViewPager;
import net.bangbao.widget.CustomSegment;
import net.bangbao.widget.ExpandScrollView;
import net.bangbao.widget.NewsHeadView;
import net.bangbao.widget.RollViewPager;
import net.bangbao.widget.RollViewPager.ViewPagerAdapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.webkit.WebView;
import android.widget.AbsListView.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.lib_refresh.PullToRefreshBase;
import com.lib_refresh.PullToRefreshBase.OnRefreshListener;
import com.lib_refresh.PullToRefreshListView;

/**
 * @author mosl 新闻Fragment
 */
public class MessageFragment extends Fragment implements View.OnClickListener, OnPageChangeListener {

    private ViewPager viewPager;
    private ImageView slideImage, talkImage;
    /**
     *@Description  资讯头部导航栏
     */
    private TextView newsTV, outlineTV, advantageTV, problemsTV;
    private List<View> views = new ArrayList<View>();
    private int offset = 0;
    private int currIndex = 0;
    private int bmpW;
    private Map<Integer, Integer> mTabs = new HashMap<Integer, Integer>();
    // 定义上下文
    // 定义容器
    private static Context mContext;
    private List<NewsBean> mHeadNews = new ArrayList<NewsBean>();
    private List<NewsBean> mNewsBean = new ArrayList<NewsBean>();
    private List<NewsBean> mOutLineBeans = new ArrayList<NewsBean>();
    private List<NewsBean> mProblemsBeans = new ArrayList<NewsBean>();
    // 定义刷新的ListView
    private PullToRefreshListView mNewsListView, mOutlineListView, mProblemsListView;
    //    private com.lib_refresh.PullToRefreshListView testListView;
    private PullToRefreshListView testListView;
    // 定义adapter
    private NewsAdapter mNewsAdapter;
    private OutLineAdapter mOutlineAdapter;
    // 定义刷新的标志
    private int mCurrPull;
    public static final int PULL_NEWS = 1;
    public static final int PULL_OUTLINE = 2;
    public static final int PULL_PROBLEMS = 3;
    // 定义新闻的ViewPager容器
    private List<NewsBean> mHeadNewsBean = new ArrayList<NewsBean>();
    private AutoScrllViewPager mNewsViewPager;

    private HeadNewsAdapter mHeadNewsAdapter;
    // 定义第一次请求数据的bool
    private boolean isFirstNews, isFirstOutline, isFirstProblems;
    // 分页需要的Page
    private Page newsPage = new Page(), outlinePage = new Page(), problemsPage = new Page();

    // 常见问题的segment
    private CustomSegment segmentGroup;

    private PullToRefreshListView problemsListView;

    public static final int TagPullUp = 1;
    public static final int TagPullDown = 2;

    private int problems_indictor;
    private ApiClient apiClient = new ApiClient();

    private ProblemsAdapter problemsAdapter;
    private List<NewsBean> mAllProblems = new ArrayList<NewsBean>();

    // 常见问题的数据源
    private List<NewsBean> mAboutInsurance = new ArrayList<NewsBean>();
    private List<NewsBean> mAboutProduct = new ArrayList<NewsBean>();
    private List<NewsBean> mAboutSettle = new ArrayList<NewsBean>();
    private List<NewsBean> mAboutNeeds = new ArrayList<NewsBean>();

    private boolean AboutInsu, AboutPro, AboutSettle, AboutNeeds;

    private RollViewPager mRollViewPager;

    private ImageLoader mImageLoader;

    /**
     *@Description  新闻头部轮循控件
     */
    private NewsHeadView mNewsHeadView;
    private static final String NEWS = "news";
    private static final String OUTLINES = "outlines";
    // private RollViewPager.
    // 定义运行在主线程中的Handler，这个逻辑是错误的，应该是在数据加载完成的时候刷新
    private Handler handler = new Handler() {

        public void handleMessage(Message msg) {

            switch (msg.what) {
                case PULL_NEWS:
                    mNewsListView.onPullDownRefreshComplete();
                    mNewsListView.onPullUpRefreshComplete();
                    break;
                case PULL_OUTLINE:
                    mOutlineListView.onPullUpRefreshComplete();
                    mOutlineListView.onPullDownRefreshComplete();
                    break;
                case PULL_PROBLEMS:
                    mProblemsListView.onPullDownRefreshComplete();
                    mProblemsListView.onPullUpRefreshComplete();
                    break;
                default:
                    break;
            }
        };
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        requestProblems(getDownloadTag());
        RequestQueue queue = Volley.newRequestQueue(mContext);
        mImageLoader = new ImageLoader(queue, new MyImageCache());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_insurance_message, null);
        initTabs();
        InitViewPager(view);
        initViews(view);
        // 进入之后是十大优势
        checkedTab(2);
        viewPager.setCurrentItem(2);
        return view;
    }

    //FIXME 顶部导航栏
    private void initTabs() {

        mTabs.put(R.id.news, 0);
        mTabs.put(R.id.outlines, 1);
        mTabs.put(R.id.advantages, 2);
        mTabs.put(R.id.problems, 3);
    }

    private void initViews(View view) {

        slideImage = (ImageView) view.findViewById(R.id.cursor);
        int screen_width = DeviceUtil.getScreenWidth(getActivity());
        LinearLayout.LayoutParams lps = (LinearLayout.LayoutParams) slideImage.getLayoutParams();
        lps.width = screen_width / 4;
        slideImage.setLayoutParams(lps);

        bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.slide_line).getWidth();
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;
        offset = (screenW / 4 - bmpW) / 2;
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        slideImage.setImageMatrix(matrix);

        newsTV = (TextView) view.findViewById(R.id.news);
        outlineTV = (TextView) view.findViewById(R.id.outlines);
        advantageTV = (TextView) view.findViewById(R.id.advantages);
        problemsTV = (TextView) view.findViewById(R.id.problems);

        newsTV.setOnClickListener(this);
        outlineTV.setOnClickListener(this);
        advantageTV.setOnClickListener(this);
        problemsTV.setOnClickListener(this);

    }

    /** 
     * @Description 设置下次刷新时看到的最后刷新时间.
     * void  
     */
    public void setLastRefreshTime(String viewName) {
        if (getDownloadTag() == Constants.DOWNLOAD_NET) { //只有当数据源是来自网络时,才记录刷新时间
            String timeLabel = DateUtils.getStrTimetmtp(System.currentTimeMillis(), "MM/dd HH:mm");
            if (viewName.equals(NEWS))
                mNewsListView.setLastUpdatedLabel(timeLabel.isEmpty() ? "" : timeLabel);//设置上次刷新时间
            else if (viewName.equals(OUTLINES))
                mOutlineListView.setLastUpdatedLabel(timeLabel.isEmpty() ? "" : timeLabel);
        }
    }

    /** 
     * @Description 点击item后跳转到详情页面 
     * @param newsApi 数据源
     * void  
     */
    public void goNewsDetail(final NewsApi newsApi) {
        NewsBean nb = newsApi.getItem().get(mRollViewPager.getCurrentItem());
        Intent intent = new Intent(mContext, ArticleContent.class);
        intent.putExtra("key", nb.cntt_url);
        intent.putExtra("title", nb.tit);
        mContext.startActivity(intent);
    }

    private void InitViewPager(View view) {

        // FIXME 添加新闻
        LinearLayout Newslayout =
                (LinearLayout) LayoutInflater.from(getActivity()).inflate(
                        R.layout.pullrefresh_listview, null);
        mNewsListView = (PullToRefreshListView) Newslayout.findViewById(R.id.pull_refresh_list);
        mNewsAdapter = new NewsAdapter(mContext, mNewsBean, 0, mImageLoader);
        ListView newsListView = mNewsListView.getRefreshableView();
        newsListView.setDividerHeight(1);
        LayoutParams lp =
                new LayoutParams(LayoutParams.MATCH_PARENT, (int) DeviceUtil.getRawSize(
                        getActivity(), TypedValue.COMPLEX_UNIT_DIP, 180));

        mNewsHeadView = new NewsHeadView(mContext, mImageLoader);
        View headView = mNewsHeadView.getHeadView();
        headView.setLayoutParams(lp);

        mNewsListView.setPullLoadEnabled(true);
        newsListView.setSelector(R.drawable.list_selecter);
        newsListView.addHeaderView(headView);

        mNewsListView.setOnRefreshListener(new OnRefreshListener<ListView>() {

            // 下拉
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                // FIXME 下拉刷新
                requestNews(getDownloadTag(), false, TagPullUp);
                apiClient.getHeadNews(getDownloadTag(), null, NewsApi.class,
                        new BaseApiClient.CallBack<NewsApi>() {

                            @Override
                            public void success(NewsApi api) {
                                mNewsHeadView.prepareData(api.getItem());
                            }

                            @Override
                            public void fail(String error) {}
                        });

            }



            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                requestNews(getDownloadTag(), false, TagPullDown);
            }
        });
        mNewsListView.getRefreshableView().setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext, ArticleContent.class);
                intent.putExtra("key", mNewsBean.get((int) id).cntt_url);
                intent.putExtra("title", mNewsBean.get((int) id).tit);
                mContext.startActivity(intent);
            }
        });

        mNewsListView.getRefreshableView().setAdapter(mNewsAdapter);
        // 添加概述
        LinearLayout Outlinelayout =
                (LinearLayout) LayoutInflater.from(getActivity()).inflate(
                        R.layout.pullrefresh_listview, null);
        mOutlineListView =
                (PullToRefreshListView) Outlinelayout.findViewById(R.id.pull_refresh_list);
        mOutlineAdapter = new OutLineAdapter(getActivity(), mOutLineBeans);
        mOutlineListView.getRefreshableView().setDividerHeight(1);
        mOutlineListView.setPullLoadEnabled(true); // 设置可上拉
        mOutlineListView.setOnRefreshListener(new OnRefreshListener<ListView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                handler.sendEmptyMessageDelayed(PULL_OUTLINE, 3 * 1000);
                requestOutline(getDownloadTag(), false, TagPullUp);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                handler.sendEmptyMessageDelayed(PULL_OUTLINE, 3 * 1000);
                requestOutline(getDownloadTag(), false, TagPullDown);
            }
        });

        ImageView outlineImageView = new ImageView(mContext);
        LayoutParams lp2 =
                new LayoutParams(LayoutParams.MATCH_PARENT, (int) DeviceUtil.getRawSize(
                        getActivity(), TypedValue.COMPLEX_UNIT_DIP, 180));
        outlineImageView.setLayoutParams(lp2);
        outlineImageView.setImageResource(R.drawable.outline_image1);
        outlineImageView.setScaleType(ScaleType.FIT_XY);
        mOutlineListView.getRefreshableView().addHeaderView(outlineImageView);
        mOutlineListView.getRefreshableView().setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    Intent intent = new Intent(mContext, ArticleContent.class);
                    intent.putExtra("key", mOutLineBeans.get((int) id).cntt_url);
                    mContext.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        mOutlineListView.getRefreshableView().setAdapter(mOutlineAdapter);
        // 添加常见问题
        LinearLayout Problemslayout =
                (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.layout_problems,
                        null);
        ImageView imagePros = (ImageView) Problemslayout.findViewById(R.id.ad_problems);
        LinearLayout.LayoutParams lps = (LinearLayout.LayoutParams) imagePros.getLayoutParams();
        lps.width = DeviceUtil.getScreenWidth(getActivity());
        lps.height = (int) DeviceUtil.getRawSize(getActivity(), TypedValue.COMPLEX_UNIT_DIP, 180);
        imagePros.setLayoutParams(lps);
        segmentGroup = (CustomSegment) Problemslayout.findViewById(R.id.segmented_problems);
        segmentGroup.setOnItemClickListener(new CustomSegment.OnItemClickListener() {

            @Override
            public void onItemClick(int checkedId) {

                if (checkedId == 1) { // 关于投保

                    if (problems_indictor != checkedId) { // 第一次

                        if (mAboutInsurance.size() == 0)
                            requestAboutInsurance(getDownloadTag());
                        problemsAdapter.setList(mAboutInsurance);
                    } else if (problems_indictor == checkedId) {
                        problems_indictor = -1;
                        problemsAdapter.setList(mAllProblems);
                        segmentGroup.clearChecked();
                        return;
                    }

                } else if (checkedId == 2) { // 关于产品
                    if (problems_indictor != checkedId) {

                        if (mAboutProduct.size() == 0) requestAboutProduct(getDownloadTag());
                        problemsAdapter.setList(mAboutProduct);
                    } else {

                        problems_indictor = -1;
                        problemsAdapter.setList(mAllProblems);
                        segmentGroup.clearChecked();
                        return;
                    }
                } else if (checkedId == 3) { // 关于理赔

                    if (problems_indictor != checkedId) {

                        if (mAboutSettle.size() == 0) requestAboutSettle(getDownloadTag());
                        problemsAdapter.setList(mAboutSettle);
                    } else {

                        problems_indictor = -1;
                        problemsAdapter.setList(mAllProblems);
                        segmentGroup.clearChecked();
                        return;
                    }

                } else if (checkedId == 4) { // 关于需求

                    if (problems_indictor != checkedId) {

                        if (mAboutNeeds.size() == 0) requestAboutNeeds(getDownloadTag());
                        problemsAdapter.setList(mAboutNeeds);
                    } else {

                        problems_indictor = -1;
                        problemsAdapter.setList(mAllProblems);
                        segmentGroup.clearChecked();
                        return;
                    }
                }
                problems_indictor = checkedId;
            }
        });
        problemsListView =
                (PullToRefreshListView) Problemslayout
                        .findViewById(R.id.problems_expandable_list_view);
        problemsAdapter = new ProblemsAdapter(LayoutInflater.from(getActivity()), mAllProblems);
        Log.i("p.hashCode: ", problemsAdapter.hashCode() + "");
        problemsListView.getRefreshableView().setAdapter(problemsAdapter);
        problemsListView.setPullRefreshEnabled(false);
        problemsListView.setPullLoadEnabled(true);
        // FIXME 常见问题的上载
        problemsListView.setOnRefreshListener(new OnRefreshListener<ListView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

            }
        });

        // 增加十大优势
        ExpandScrollView scrollView = new ExpandScrollView(mContext);
        LayoutParams scrollLp =
                new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
        scrollView.setLayoutParams(scrollLp);

        LinearLayout advantageLayout = new LinearLayout(mContext);
        LinearLayout.LayoutParams adLp =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
        advantageLayout.setLayoutParams(adLp);
        advantageLayout.setOrientation(LinearLayout.VERTICAL);



        WebView webView = new WebView(mContext);
        LayoutParams textLp =
                new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
        webView.setLayoutParams(textLp);

        webView.loadUrl("file:///android_asset/ten.html");
        // String text = FileUtil.readFileFromAssets(mContext, "ten.html");
        //
        // webView.getSettings().setDefaultTextEncodingName("utf-8") ;
        // webView.loadDataWithBaseURL(null, text, "text/html", "utf-8", null);
        //
        // ImageView ad = new ImageView(mContext);
        // LayoutParams ad2 = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT
        // );
        // ad.setLayoutParams(ad2);
        // ad.setImageResource(R.drawable.advantage_all);
        // ad.setScaleType(ScaleType.FIT_XY);
        //
        advantageLayout.addView(webView);
        scrollView.addView(advantageLayout);

        views.add(Newslayout);
        views.add(Outlinelayout);
        views.add(scrollView);
        views.add(Problemslayout);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        viewPager.setAdapter(new MessTabPagAdapter(views));
        viewPager.setOnPageChangeListener(this);

        apiClient.getHeadNews(getDownloadTag(), this, NewsApi.class,
                new ApiClient.CallBack<NewsApi>() {

                    @Override
                    public void success(NewsApi api) {
                        //  首次进入新闻页面,读取网络数据
                        mNewsHeadView.prepareData(api.getItem());
                        setLastRefreshTime(NEWS);
                    }

                    /**
                     * @Description 新闻栏目的广告版块:回调得到数据后,显示在滚动的ViewPager中
                     * @param api void
                     */
                    /*      public final void setRollViewPager(NewsApi api) {
                              final ArrayList<String> urlLists = new ArrayList<String>();
                              for (NewsBean nb : api.getItem()) {
                                  urlLists.add(nb.pic_url);
                                  Log.i("newsBean.pic_url", nb.pic_url);
                              }
                              mRollViewPager.setUriList(urlLists);
                              mNewsHeadAdapter = mRollViewPager.initPagerAdapter();
                              mRollViewPager.setAdapter(mNewsHeadAdapter);
                              mNewsHeadAdapter.notifyDataSetChanged();
                              final NewsApi apiTemp = api;
                              mRollViewPager.setPagerCallback(new OnPagerClickCallback() {

                                  @Override
                                  public void onPagerClick() {
                                      goNewsDetail(apiTemp); 
                                  }
                              });
                              mRollViewPager.setOnClickListener(new OnClickListener() {
                                  
                                  @Override
                                  public void onClick(View v) {
                                      goNewsDetail(apiTemp);
                                  }
                              });
                              mRollViewPager.startRoll();
                          }
                    */
                    @Override
                    public void fail(String object) {

                    }
                });
    }

    private void checkedTab(int index) {
        Set<Integer> ides = mTabs.keySet();
        Iterator<Integer> iter = ides.iterator();
        while (iter.hasNext()) {
            int id = iter.next();
            int d = mTabs.get(id);
            if (d == index) {
                checkTab(id);
                break;
            }
        }
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        viewPager.setCurrentItem(mTabs.get(id));
        checkTab(id);
    }

    private void checkTab(int id) {
        switch (id) {
            case R.id.news:
                newsTV.setTextColor(getResources().getColor(R.color.blue_title));
                outlineTV.setTextColor(getResources().getColor(R.color.black));
                advantageTV.setTextColor(getResources().getColor(R.color.black));
                problemsTV.setTextColor(getResources().getColor(R.color.black));
                break;
            case R.id.outlines:
                newsTV.setTextColor(getResources().getColor(R.color.black));
                outlineTV.setTextColor(getResources().getColor(R.color.blue_title));
                advantageTV.setTextColor(getResources().getColor(R.color.black));
                problemsTV.setTextColor(getResources().getColor(R.color.black));
                break;
            case R.id.advantages:
                newsTV.setTextColor(getResources().getColor(R.color.black));
                outlineTV.setTextColor(getResources().getColor(R.color.black));
                advantageTV.setTextColor(getResources().getColor(R.color.blue_title));
                problemsTV.setTextColor(getResources().getColor(R.color.black));
                break;
            case R.id.problems:
                newsTV.setTextColor(getResources().getColor(R.color.black));
                outlineTV.setTextColor(getResources().getColor(R.color.black));
                advantageTV.setTextColor(getResources().getColor(R.color.black));
                problemsTV.setTextColor(getResources().getColor(R.color.blue_title));
                break;
            default:
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    @Override
    public void onPageSelected(int arg0) {

        int one = offset * 2 + bmpW;
        int two = one * 2;

        Animation animation = new TranslateAnimation(one * currIndex, one * arg0, 0, 0);
        currIndex = arg0;
        animation.setFillAfter(true);
        animation.setDuration(300);
        slideImage.startAnimation(animation);
        checkedTab(currIndex);
        requestFirstDataPage(currIndex);
    }

    private void requestNews(int downloadTag, boolean isFirst, int requestCategry) {

        if (isFirst) { // 第一次获取数据
            apiClient.getNewsRequest(downloadTag, new Page(), MessageMacro.NEWS, this,
                    NewsApi.class, new ApiClient.CallBack<NewsApi>() {
                        @Override
                        public void success(NewsApi api) {
                            handler.sendEmptyMessageDelayed(PULL_NEWS, 1 * 1000);
                            if (api == null) return;
                            newsPage.id_index = api.getId_index();
                            newsPage.page_index = api.getPage_index() + 1;
                            newsPage.page_size = api.getPage_size();
                            if (api.getItem() == null) return;
                            if (api.getItem() != null) {
                                mNewsBean.addAll(api.getItem());
                                mNewsAdapter.notifyDataSetChanged();
                            } else
                                Toast.makeText(mContext, "没有最新的数据了", Toast.LENGTH_SHORT).show();
                            setLastRefreshTime(NEWS);
                        }

                        @Override
                        public void fail(String error) {
                            handler.sendEmptyMessageDelayed(PULL_NEWS, 1 * 1000);
                        }

                    });
        }
        if (!isFirst && requestCategry == TagPullUp) { // 刷最新的咨询
            apiClient.getNewsRequest(downloadTag, new Page(), MessageMacro.NEWS, this,
                    NewsApi.class, new ApiClient.CallBack<NewsApi>() {
                        @Override
                        public void success(NewsApi api) {
                            handler.sendEmptyMessageDelayed(PULL_NEWS, 1 * 1000);
                            if (api == null) return;
                            if (api.getItem() == null) return;
                            List<NewsBean> list = api.getItem();
                            int count = 0;
                            for (int i = 0; i < list.size(); i++) {
                                NewsBean bean = list.get(i);
                                for (int j = 0; j < mNewsBean.size(); j++) {
                                    if (bean.id == mNewsBean.get(j).id) count++;
                                }
                            }
                            if (count == 0) {
                                mNewsBean.addAll(list);
                                mNewsAdapter.notifyDataSetChanged();
                            } else
                                Toast.makeText(mContext, "没有最新的数据了", Toast.LENGTH_SHORT).show();
                            setLastRefreshTime(NEWS);
                        }

                        @Override
                        public void fail(String error) {
                            handler.sendEmptyMessageDelayed(PULL_NEWS, 1 * 1000);
                        }

                    });
        } else if (!isFirst && requestCategry == TagPullDown) { // 刷历史

            apiClient.getNewsRequest(downloadTag, newsPage, MessageMacro.NEWS, this, NewsApi.class,
                    new ApiClient.CallBack<NewsApi>() {
                        @Override
                        public void success(NewsApi api) {
                            handler.sendEmptyMessageDelayed(PULL_NEWS, 1 * 1000);
                            if (api == null) return;

                            if (api.getRet_cd() == 97) { // 无记录
                                Toast.makeText(mContext, "当前没有最新历史了", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            newsPage.id_index = api.getId_index();
                            newsPage.page_index = api.getPage_index() + 1;
                            newsPage.page_size = api.getPage_size();
                            if (api.getItem() == null) return;
                            List<NewsBean> list = api.getItem();
                            int count = 0;
                            for (int i = 0; i < list.size(); i++) {
                                NewsBean bean = list.get(i);
                                for (int j = 0; j < mNewsBean.size(); j++) {
                                    if (bean.id == mNewsBean.get(j).id) count++;
                                }
                            }
                            if (count == 0) {
                                mNewsBean.addAll(list);
                                mNewsAdapter.notifyDataSetChanged();
                            } else
                                Toast.makeText(mContext, "没有最新的数据了", Toast.LENGTH_SHORT).show();
                            setLastRefreshTime(NEWS);
                        }

                        @Override
                        public void fail(String error) {
                            handler.sendEmptyMessageDelayed(PULL_NEWS, 1 * 1000);
                        }

                    });
        }
    }

    // FIXME 获取港险概述的内容
    private void requestOutline(int downloadTag, boolean isFirst, int requestCategry) {

        if (isFirst) { // 第一次获取
            apiClient.getNewsRequest(downloadTag, null, MessageMacro.OUTLINE, this, NewsApi.class,
                    new ApiClient.CallBack<NewsApi>() {

                        @Override
                        public void success(NewsApi api) {
                            if (api == null) return;
                            if (api.getRet_cd() == 97) return;

                            outlinePage.id_index = api.getId_index();
                            outlinePage.page_index = api.getPage_index() + 1;
                            outlinePage.page_size = api.getPage_size();

                            mOutLineBeans.addAll(api.getItem());
                            mOutlineAdapter.notifyDataSetChanged();
                            setLastRefreshTime(OUTLINES);
                        }

                        @Override
                        public void fail(String error) {

                        }

                    });
        }

        if (!isFirst && requestCategry == TagPullUp) { // 刷新
            apiClient.getNewsRequest(downloadTag, new Page(), MessageMacro.OUTLINE, this,
                    NewsApi.class, new ApiClient.CallBack<NewsApi>() {

                        @Override
                        public void success(NewsApi api) {
                            if (api == null) return;
                            if (api.getRet_cd() == 97) return;

                            if (api.getItem() == null) return;
                            List<NewsBean> list = api.getItem();
                            int count = 0;
                            for (int i = 0; i < list.size(); i++) {
                                NewsBean bean = list.get(i);
                                for (int j = 0; j < mOutLineBeans.size(); j++) {
                                    if (bean.id == mOutLineBeans.get(j).id) count++;
                                }
                            }
                            if (count == 0) {
                                mOutLineBeans.addAll(api.getItem());
                                mOutlineAdapter.notifyDataSetChanged();
                            } else
                                Toast.makeText(mContext, "没有最新的数据了", Toast.LENGTH_SHORT).show();
                            setLastRefreshTime(OUTLINES);
                        }

                        @Override
                        public void fail(String error) {

                        }

                    });
        }

        if (!isFirst && requestCategry == TagPullDown) { // 刷历史
            apiClient.getNewsRequest(downloadTag, outlinePage, MessageMacro.OUTLINE, this,
                    NewsApi.class, new ApiClient.CallBack<NewsApi>() {

                        @Override
                        public void success(NewsApi api) {
                            if (api == null) return;
                            if (api.getRet_cd() == 97) {
                                Toast.makeText(mContext, "没有最新的数据了", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            outlinePage.id_index = api.getId_index();
                            outlinePage.page_index = api.getPage_index() + 1;
                            outlinePage.page_size = api.getPage_size();

                            mOutLineBeans.addAll(api.getItem());
                            mOutlineAdapter.notifyDataSetChanged();
                            setLastRefreshTime(OUTLINES);
                        }

                        @Override
                        public void fail(String error) {

                        }

                    });
        }
    }

    // FIXME 获取常见问题
    private void requestProblems(int downloadTag) {
        apiClient.getNewsRequest(downloadTag, null, MessageMacro.PROBLEMS, this,
                NewsApi.class, new ApiClient.CallBack<NewsApi>() {

                    @Override
                    public void success(NewsApi api) {

                        if (api == null || api.getItem() == null) {
                            return;
                        }
                        mAllProblems.addAll(api.getItem());
                        // -----------------------here--------------------------------------------
                        if (problemsAdapter == null)
                            problemsAdapter =
                                    new ProblemsAdapter(LayoutInflater.from(getActivity()),
                                            mAllProblems);
                        problemsAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void fail(String error) {

                    }

                });

    }

    private void requestAboutInsurance(int downloadTag) {
        apiClient.getNewsRequest(downloadTag, new Page(), MessageMacro.ABOUT_INSURANCE, this,
                NewsApi.class, new ApiClient.CallBack<NewsApi>() {

                    @Override
                    public void success(NewsApi api) {
                        if (api == null) return;
                        mAboutInsurance.addAll(api.getItem());
                        problemsAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void fail(String error) {

                    }

                });
    }

    private void requestAboutProduct(int downloadTag) {
        apiClient.getNewsRequest(downloadTag, new Page(), MessageMacro.ABOUT_PRODUCT, this,
                NewsApi.class, new ApiClient.CallBack<NewsApi>() {
                    @Override
                    public void success(NewsApi api) {
                        if (api == null) return;
                        mAboutProduct.addAll(api.getItem());
                        problemsAdapter.setList(mAboutProduct);

                    }

                    @Override
                    public void fail(String error) {

                    }

                });
    }

    private void requestAboutSettle(int downloadTag) {

        apiClient.getNewsRequest(downloadTag, new Page(), MessageMacro.ABOUT_SETTLE, this,
                NewsApi.class, new ApiClient.CallBack<NewsApi>() {
                    @Override
                    public void success(NewsApi api) {
                        if (api == null) return;
                        mAboutSettle.addAll(api.getItem());
                        problemsAdapter.setList(mAboutSettle);
                    }

                    @Override
                    public void fail(String error) {

                    }

                });
    }

    private void requestAboutNeeds(int downloadTag) {

        apiClient.getNewsRequest(downloadTag, new Page(), MessageMacro.ABOUT_NEEDS, this,
                NewsApi.class, new ApiClient.CallBack<NewsApi>() {
                    @Override
                    public void success(NewsApi api) {
                        if (api == null) return;
                        mAboutNeeds.addAll(api.getItem());
                        problemsAdapter.setList(mAboutNeeds);
                    }

                    @Override
                    public void fail(String error) {

                    }

                });
    }

    // 滑动到某一页的时候获取数据
    private void requestFirstDataPage(int requestIndex) {

        switch (requestIndex) {
            case 0: // news
                if (!isFirstNews) {
                    requestNews(getDownloadTag(), true, TagPullUp);
                }
                isFirstNews = true;
                break;
            case 1: // outline
                if (!isFirstOutline) {
                    requestOutline(getDownloadTag(), true, TagPullUp);
                }
                isFirstOutline = true;
                break;
            case 2:
                break;
            case 4: // problems
                if (!isFirstProblems) {
                    requestProblems(getDownloadTag());
                }
                isFirstProblems = true;
        }
    }

    /**
     * @Description 根据当前网络连接情况判断是否读缓存
     * @return int
     */
    public static int getDownloadTag() {
        Boolean flag = Utils.internetDdetect(mContext);
        Log.i("downloadTag: ", flag + "");
        return flag ? Constants.DOWNLOAD_NET : Constants.DOWNLOAD_SDCARD;
    }

}
