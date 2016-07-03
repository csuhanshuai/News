package net.bangbao.widget;

import java.util.ArrayList;
import java.util.List;

import net.bangbao.R;
import net.bangbao.adapter.NewsHeadAdapter;
import net.bangbao.dao.NewsApi.NewsBean;
import net.bangbao.ui.ArticleContent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;


public class NewsHeadView implements OnPageChangeListener {

    private List<ImageView> mImageViews;
    private Context mContext;
    private ViewPager mViewPager;
    private TextView tvDescription;
    private LinearLayout llPoints;
    private boolean isLoop = true;
    private ImageLoader mImageLoader;
    private List<String> mImageDescriptions;
    private int previousSelectPosition = 0;
    private NewsHeadAdapter mAdapter;
    private View mView;
    /**
     *@Description  图片总数和当前图片编号
     */
    private TextView newsNo,newsSum;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
            Log.i("4", previousSelectPosition + ",");
//            loop();
        }
    };
    private ViewPagerTask mViewPagerTask;

    public View getHeadView() {
        return mView;
    }

    public NewsHeadView(Context context,ImageLoader imageLoader) {
        mContext = context;
        mImageViews = new ArrayList<ImageView>();
        mImageDescriptions = new ArrayList<String>();
        mImageLoader = imageLoader;
        mView = LayoutInflater.from(mContext).inflate(R.layout.activity_splash_viewpager, null);
        mViewPagerTask = new ViewPagerTask();
        loop();
        initView();
    }


    /** 
     * @Description 页面循环自动滚动
     * void  
     */
    public void loop() {
        // 自动切换页面功能
        new Thread(new Runnable() {

            @Override
            public void run() {
                while (isLoop) {
                    SystemClock.sleep(3000);
                    mHandler.sendEmptyMessage(0);
                }
            }
        }).start();
        //        mHandler.postDelayed(mViewPagerTask, 3000);

    }

    class ViewPagerTask implements Runnable {

        @Override
        public void run() {
            previousSelectPosition = (previousSelectPosition + 1) % mImageViews.size();
            Log.i("3", previousSelectPosition + ",");
            mHandler.obtainMessage().sendToTarget();
        }

    }

    /** 
     * @Description 初始化所有组件以及图片,图片描述,小圆点个数,以免发生数组下标越界
     * void  
     */
    public void initView() {
        mViewPager = (ViewPager) mView.findViewById(R.id.news_head_viewpager);
        tvDescription = (TextView) mView.findViewById(R.id.news_head_description);
        newsNo = (TextView) mView.findViewById(R.id.news_head_number);
        newsSum = (TextView) mView.findViewById(R.id.news_head_sum);
//        llPoints = (LinearLayout) mView.findViewById(R.id.news_head_point);

        mAdapter = new NewsHeadAdapter(mImageViews);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOnPageChangeListener(this);
        /*  mViewPager.setOnTouchListener(new OnTouchListener() {

              @Override
              public boolean onTouch(View v, MotionEvent event) {
                  switch (event.getAction()) {
                      case MotionEvent.ACTION_DOWN:
                          mHandler.removeCallbacksAndMessages(null);
                          mViewPager.getParent().requestDisallowInterceptTouchEvent(true);
                          break;
                      case MotionEvent.ACTION_MOVE:
                          mHandler.removeCallbacks(mViewPagerTask);
                          mViewPager.getParent().requestDisallowInterceptTouchEvent(true);
                          break;
                      case MotionEvent.ACTION_CANCEL:
                          loop();
                          mViewPager.getParent().requestDisallowInterceptTouchEvent(true);
                          break;
                      case MotionEvent.ACTION_UP:
                          loop();
                          mViewPager.getParent().requestDisallowInterceptTouchEvent(true);
                          break;
                  }
                  return false;
              }
          });*/
        ImageView iv;
        View view;
        for (int i = 0; i < 5; i++) {
//            view = new View(mContext);
            iv = new ImageView(mContext);
            iv.setImageResource(R.drawable.app_logo);
            mImageViews.add(iv);
            mImageDescriptions.add("");
//            llPoints.addView(view);
        }

        /*tvDescription.setText(imageDescriptions[previousSelectPosition]);
        llPoints.getChildAt(previousSelectPosition).setEnabled(true);*/

        /**
         * 2147483647 / 2 = 1073741820 - 1 
         * 设置ViewPager的当前项为一个比较大的数，以便一开始就可以左右循环滑动
         */
        int n = Integer.MAX_VALUE / 2 % mImageViews.size();
        int itemPosition = Integer.MAX_VALUE / 2 - n;

        mViewPager.setCurrentItem(itemPosition);
        newsSum.setText("/"+mImageViews.size());
        newsNo.setText(String.valueOf(1));
        Log.i("2", itemPosition + ""); 
    }

    /** 
     * @Description 设置该View的数据源(图片Url地址和描述)  
     * void  
     */
    public void prepareData(List<NewsBean> data) {
        NetworkImageView imageView;
        View view;
        mImageViews.clear();
        mImageDescriptions.clear();
//        llPoints.removeAllViews();
        for (final NewsBean nb : data) {
            imageView = new NetworkImageView(mContext);
            imageView.setScaleType(ScaleType.FIT_XY);
            imageView.setImageUrl(nb.pic_url, mImageLoader);
            imageView.setOnClickListener(new OnClickListener() {//设置每张图片的点击事件

                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mContext, ArticleContent.class);
                            intent.putExtra("key", nb.cntt_url);
                            intent.putExtra("title", nb.tit);
                            mContext.startActivity(intent);
                        }
                    });
            mImageViews.add(imageView);

            mImageDescriptions.add(nb.tit);
            mAdapter.notifyDataSetChanged();
     /*       // 添加点view对象
            view = new View(mContext);
            view.setBackgroundResource(R.drawable.ic_launcher);
            LayoutParams lp = new LayoutParams(10, 10);
            lp.leftMargin = 10;
            view.setLayoutParams(lp);
            llPoints.addView(view);*/
        }
        for (int i = 0; i < 3; i++) {//测试数据,获取到完整数据后需要删除
            ImageView iv = new ImageView(mContext);
            iv.setBackgroundResource(R.drawable.app_logo);
            mImageDescriptions.add("");
            mImageViews.add(iv);
            mAdapter.notifyDataSetChanged();
            
       /* view = new View(mContext);
            view.setBackgroundResource(R.drawable.ic_launcher);
            LayoutParams lp = new LayoutParams(10, 10);
            lp.leftMargin = 10;
            view.setLayoutParams(lp);
            llPoints.addView(view);*/
        }
        
        newsSum.setText("/"+String.valueOf(mImageViews.size()) );
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {}

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {}

    @Override
    public void onPageSelected(int position) {

        // 改变图片的描述信息
        tvDescription.setText(mImageViews.size() == 0 ? "" : mImageDescriptions.get(position
                % mImageViews.size()));
        /*// 切换选中的点,把前一个点置为normal状态
        llPoints.getChildAt(previousSelectPosition).setBackgroundResource(R.drawable.ic_launcher);
        // 把当前选中的position对应的点置为enabled状态
        llPoints.getChildAt(position % mImageViews.size()).setBackgroundResource(R.drawable.app_logo);*/
        previousSelectPosition = mImageViews.size() == 0 ? 0 : position % mImageViews.size();
        newsNo.setText(String.valueOf(previousSelectPosition +1) );//如果传入int值,则系统会自动认为这个是资源id
    }

}
