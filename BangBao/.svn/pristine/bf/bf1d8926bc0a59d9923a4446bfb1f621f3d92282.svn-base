package net.bangbao.adapter;

import java.util.List;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.NetworkImageView;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import net.bangbao.R;
import net.bangbao.common.TimeUtils;
import net.bangbao.dao.NewsApi.NewsBean;
import net.bangbao.network.RequestManager;
import net.bangbao.utils.ImageCacheUtil.OPTIONS;
import net.bangbao.widget.RoundImageView;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/*
 * author:mosl description:咨询 的Adapter
 */
public class NewsAdapter extends BaseAdapter {

    private Context mContext;
    private List<NewsBean> lists;
    private int ItemCategry;

    public static final int ItemNews = 0;
    public static final int ItemOutLine = 1;
    public ImageLoader mImageLoader;

    public NewsAdapter(Context context, List<NewsBean> lists, int ItemCategry,
            ImageLoader imageLoader) {
        this.lists = lists;
        this.mContext = context;
        this.ItemCategry = ItemCategry;
        mImageLoader = imageLoader;
    }

    public NewsAdapter(Context context, List<NewsBean> lists) {
        this.lists = lists;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView =
                    LayoutInflater.from(mContext).inflate(R.layout.newstable_list_item, parent,
                            false);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.author = (TextView) convertView.findViewById(R.id.author);
            holder.time = (TextView) convertView.findViewById(R.id.timer);
            holder.img = (RoundImageView) convertView.findViewById(R.id.newsImage);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        NewsBean bean = this.lists.get(position);
        if (ItemCategry == ItemNews) {
            holder.img.setVisibility(View.VISIBLE);
            //            holder.img.setImageUrl(bean.pic_url, mImageLoader);
            com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage(
                    bean.pic_url, holder.img, OPTIONS.options, null);
        }
        holder.title.setText(bean.tit);
        holder.title.setMaxLines(2); // 行数最大是两行
        holder.author.setText(bean.author);
        holder.time.setText(TimeUtils.getStrTime(bean.tmtp));

        return convertView;
    }

    public final class ViewHolder {
        public RoundImageView img;
        public TextView title;
        public TextView time;
        public TextView author;
    }



}
