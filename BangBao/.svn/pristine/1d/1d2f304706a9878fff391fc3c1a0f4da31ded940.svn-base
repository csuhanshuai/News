package net.bangbao.adapter;

import java.util.List;

import net.bangbao.R;
import net.bangbao.adapter.NewsAdapter.ViewHolder;
import net.bangbao.common.TimeUtils;
import net.bangbao.dao.NewsApi.NewsBean;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class OutLineAdapter extends BaseAdapter {
	private Context mContext;
	private List<NewsBean> lists;
	
	public OutLineAdapter(Context context,List<NewsBean> lists) {
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
	NewsBean bean = this.lists.get(position);
		if(convertView == null){
			final ViewHolder holder = new ViewHolder();  
			convertView = LayoutInflater.from(mContext).inflate(R.layout.outline_table_item, null);
			holder.title = (TextView)convertView.findViewById(R.id.title);
			holder.author = (TextView)convertView.findViewById(R.id.author);
			holder.time = (TextView)convertView.findViewById(R.id.timer);
			holder.img = (ImageView)convertView.findViewById(R.id.image);
			convertView.setTag(holder);
			
			holder.title.setText(bean.tit);
			holder.title.setMaxLines(2); //行数最大是两行
			holder.author.setText(bean.author);
			holder.time.setText(TimeUtils.getStrTime(bean.tmtp));
		}else {  
			final ViewHolder holder = (ViewHolder) convertView.getTag();
			holder.title.setText(bean.tit);
			holder.title.setMaxLines(2); //行数最大是两行
			holder.author.setText(bean.author);
			holder.time.setText(TimeUtils.getStrTime(bean.tmtp));
        }  
		return convertView;
	}
	
	public final class ViewHolder {  
        public ImageView img;  
        public TextView title;  
        public TextView time;  
        public TextView author;
    }  

}
