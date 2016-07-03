package net.bangbao.adapter;

import java.util.ArrayList;
import java.util.List;

import net.bangbao.R;
import net.bangbao.common.DeviceUtil;
import net.bangbao.common.ImageUtil;
import net.bangbao.dao.NewsApi.NewsBean;
import net.bangbao.network.RequestManager;
import net.bangbao.ui.ArticleContent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;

//置顶咨询的adapter
public class HeadNewsAdapter extends PagerAdapter {

	private List<NewsBean> lists;
	private List<View> listViews = new ArrayList<View>();
	private Context context;

	public HeadNewsAdapter(Context context, List<NewsBean> lists) {
		this.context = context;
		this.lists = lists;
	}

	public HeadNewsAdapter(FragmentManager childFragmentManager,
			List<NewsBean> mHeadNewsBean) {
		this.lists = mHeadNewsBean;
	}

	@Override
	public int getCount() {
		return lists.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(listViews.get(position));
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {

		final NewsBean newsBean = lists.get(position);
		View headFramlayout = LayoutInflater.from(context).inflate(
				R.layout.ad_head_layout, null);
		final ImageView imageView = (ImageView) headFramlayout
				.findViewById(R.id.ad_head_image);
		imageView.setImageResource(R.drawable.news);
		TextView title = (TextView) headFramlayout.findViewById(R.id.text_head);
		title.setText(newsBean.tit);
		RequestManager.getInstance().loadImage(newsBean.pic_url,
				new ImageListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {

					}

					@Override
					public void onResponse(ImageContainer arg0, boolean arg1) {
						if (arg0.getBitmap() == null) {
							imageView.setImageBitmap(ImageUtil.scaleToWidth(
									BitmapFactory.decodeResource(
											context.getResources(),
											R.drawable.news), DeviceUtil
											.getScreenWidth(context)));
						} else {
							imageView.setImageBitmap(ImageUtil.scaleToWidth(
									arg0.getBitmap(),
									DeviceUtil.getScreenWidth(context)));
						}
					}
				});
		imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, ArticleContent.class);
				intent.putExtra("key", newsBean.cntt_url);
				context.startActivity(intent);
			}
		});

		listViews.add(headFramlayout);
		container.addView(headFramlayout, 0);
		return headFramlayout;
	}
	
}
