package net.bangbao.widget;

import java.util.ArrayList;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import net.bangbao.R;
import net.bangbao.dao.NewsApi;
import net.bangbao.dao.NewsApi.NewsBean;
import net.bangbao.ui.ArticleContent;
import net.bangbao.utils.ImageCacheUtil;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


/**
 * 循环滚动切换图片(支持带标题,不带标题传null即可),自带适配器 支持显示本地res图片和网络图片，指定uri的图片 OnPagerClickCallback
 * onPagerClickCallback 处理page被点击的回调接口, 被用户手动滑动时，暂停滚动，增强用户友好性
 */
public class RollViewPager extends ViewPager {

    public RollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        viewPagerTask = new ViewPagerTask();

    }

    public RollViewPager(Context context) {
        super(context);
        this.context = context;
        viewPagerTask = new ViewPagerTask();
    }

    private String TAG = "RollViewPager";
    private Context context;
    private int currentItem;
    private ArrayList<String> uriList;// 图片地址
    private ArrayList<View> dots;// 点的位置不固定，所以需要让调用者传入
    private TextView title;// 用于显示每个图片的标题
    private String[] titles;
    private int[] resImageIds;
    private int dot_focus_resId;// 获取焦点的图片id
    private int dot_normal_resId;// 未获取焦点的图片id
    private OnPagerClickCallback onPagerClickCallback;
    private boolean isShowResImage = false;
    ViewPagerTask viewPagerTask;

    public ViewPagerAdapter mAdapter;
    private long start = 0;



    public class ViewPagerTask implements Runnable {
        @Override
        public void run() {
            currentItem =
                    (currentItem + 1) % (isShowResImage ? resImageIds.length : uriList.size());
            handler.obtainMessage().sendToTarget();
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            setCurrentItem(currentItem);
            startRoll();
        }
    };

    public void setDot(ArrayList<View> dots, int dot_focus_resId, int dot_normal_resId) {
        this.dots = dots;
        this.dot_focus_resId = dot_focus_resId;
        this.dot_normal_resId = dot_normal_resId;
    }

    public void setPagerCallback(OnPagerClickCallback onPagerClickCallback) {
        this.onPagerClickCallback = onPagerClickCallback;
    }

    /**
     * 设置网络图片的url集合，也可以是本地图片的uri 图片uriList集合，可以是网络地址，如：http://www.ssss.cn/3.jpg，也可以是本地的uri,如：
     * assest://image/3.jpg，uriList和resImageIds只需传入一个
     * 
     * @param uriList
     */
    public void setUriList(ArrayList<String> uriList) {
        isShowResImage = false;
        this.uriList = uriList;
    }

    /**
     * 设置res图片的id 图片uriList集合，可以是网络地址，如：http://www.ssss.cn/3.jpg，也可以是本地的uri,如：
     * assest://image/3.jpg，uriList和resImageIds只需传入一个
     * 
     * @param resImageIds
     */
    public void setResImageIds(int[] resImageIds) {
        isShowResImage = true;
        this.resImageIds = resImageIds;
    }

    /**
     * 标题相关
     * 
     * @param title 用于显示标题的TextView
     * @param titles 标题数组
     */
    public void setTitle(TextView title, String[] titles) {
        this.title = title;
        this.titles = titles;
        if (title != null && titles != null && titles.length > 0) title.setText(titles[0]);// 默认显示第一张的标题
    }

    private boolean hasSetAdapter = true;
    private final int SWITCH_DURATION = 3000;// 自动滑动的时间间隔

    /**
     * 开始滚动
     */
    public void startRoll() {
        /*
         * if (!hasSetAdapter) { hasSetAdapter = true; this.setOnPageChangeListener(new
         * MyOnPageChangeListener()); this.setAdapter(mAdapter); }
         */
        handler.postDelayed(viewPagerTask, SWITCH_DURATION);
    }

    class MyOnPageChangeListener implements OnPageChangeListener {
        int oldPosition = 0;

        @Override
        public void onPageSelected(int position) {
            currentItem = position;
            if (title != null) title.setText(titles[position]);
            if (dots != null && dots.size() > 0) {
                dots.get(position).setBackgroundResource(dot_focus_resId);
                dots.get(oldPosition).setBackgroundResource(dot_normal_resId);
            }
            oldPosition = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {}

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {}
    }

    /**
     * @Description 必须先调用setUriList方法,才能调用该方法 void
     */
    public ViewPagerAdapter initPagerAdapter() {
        mAdapter = new ViewPagerAdapter();
        return mAdapter;
    }

    /**
     * 自带设配器,需要回调类来处理page的点击事件
     * 
     * @author dance
     * 
     */
    public class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            if (uriList.size() != 0)
                return isShowResImage ? resImageIds.length : uriList.size();
            else
                return 0;
        }


        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View view = View.inflate(context, R.layout.viewpager_item, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.image1);

            /*imageView.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    Log.i("onClick: ", "go");
                    String url = uriList.get(getCurrentItem());
                    Intent intent = new Intent(getContext(), ArticleContent.class);
                    intent.putExtra("key", url);
                    getContext().startActivity(intent);
                }
            });

            view.setOnClickListener(new OnClickListener() {
                
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "view.onclick", 0).show();
                }
            });
            imageView.setOnTouchListener(new OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                    return false;
                }
            });
            view.setOnTouchListener(new OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                    return false;
                }
            });*/

            container.addView(view);
            if (isShowResImage) {
                imageView.setImageResource(resImageIds[position]);
            } else {
                ImageLoader imageLoader = ImageLoader.getInstance();
                imageLoader.init(ImageLoaderConfiguration.createDefault(getContext()));
                imageLoader.displayImage(uriList.get(position), imageView,
                        ImageCacheUtil.OPTIONS.options);
            }
            return view;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup arg0, int arg1, Object arg2) {
            // 将ImageView从ViewPager移除
            arg0.removeView((View) arg2);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        handler.removeCallbacksAndMessages(null);
        super.onDetachedFromWindow();
    }

    /**
     * 处理page点击的回调接口
     * 
     * @author dance
     * 
     */
    public interface OnPagerClickCallback {
        public abstract void onPagerClick();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        // TODO Auto-generated method stub
        return true;
    }


    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        switch (arg0.getAction()) {
            case MotionEvent.ACTION_DOWN:
                start = System.currentTimeMillis();
                handler.removeCallbacksAndMessages(null);
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                handler.removeCallbacks(viewPagerTask);
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_CANCEL:
                startRoll();
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_UP:
                long duration = System.currentTimeMillis() - start;
                if (duration <= 100) {
                    if (onPagerClickCallback != null) onPagerClickCallback.onPagerClick();
                }
                startRoll();
                break;
        }
        return super.onTouchEvent(arg0);
    }

}
