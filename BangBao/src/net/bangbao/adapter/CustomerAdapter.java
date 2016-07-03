package net.bangbao.adapter;

import io.rong.imlib.RongIMClient.Message;

import java.util.List;

import net.bangbao.AppConfig;
import net.bangbao.R;
import net.bangbao.UserConfig;
import net.bangbao.adapter.ConsultAdapter1.ViewHolder;
import net.bangbao.base.BaseApiClient;
import net.bangbao.base.BaseApiClient.Page;
import net.bangbao.common.Ids;
import net.bangbao.dao.CustomerApi;
import net.bangbao.dao.AgentListApi.AgentInfo;
import net.bangbao.dao.CustomerApi.CustomerInfo;
import net.bangbao.dao.InsuCatgApi.InsuCatgInfo;
import net.bangbao.network.ApiClient;
import net.bangbao.network.InsuranceApi;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
/*
 * author:hs
 * date:3/5
 * description:我的客户适配器
 */
public class CustomerAdapter extends BaseAdapter {

	private TextView username;
	private List<CustomerInfo> infos;
	private Context context;
	
	public CustomerAdapter(List<CustomerInfo> list,Context context) {
		this.infos = list;
		this.context = context;
	}
	
	@Override
	public int getCount() {
		return infos.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		CustomerInfo info = infos.get(position);
		ViewHolder viewHolder = null;
		if(convertView == null){
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.customer_item, null);
			viewHolder.username = (TextView)convertView.findViewById(R.id.user);
			viewHolder.username.setText(info.getNick_nm());
			viewHolder.circle = convertView.findViewById(R.id.fram_circle);
			viewHolder.textViewCircle = (TextView)convertView.findViewById(R.id.text_count);
			
			List<Message> list = AppConfig.msgContents;
			int messageCounts = 0;
			for(int i = 0;i<list.size();i++){
				Message msg = list.get(i);
				if(msg.getSenderUserId().endsWith(String.valueOf(info.getUser_id()))){
					messageCounts ++;
				}
			}
			if(messageCounts != 0){
				viewHolder.circle.setVisibility(View.VISIBLE);
				viewHolder.textViewCircle.setText(String.valueOf(messageCounts));
				info.setMsgCount(messageCounts);
			}
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder)convertView.getTag();
			viewHolder.username.setText(info.getNick_nm());
			
			List<Message> list = AppConfig.msgContents;
			int messageCounts = 0;
			for(int i = 0;i<list.size();i++){
				Message msg = list.get(i);
				if(msg.getSenderUserId().endsWith(String.valueOf(info.getUser_id()))){
					messageCounts ++;
				}
			}
			if(messageCounts != 0){
				viewHolder.circle.setVisibility(View.VISIBLE);
				viewHolder.textViewCircle.setText(String.valueOf(messageCounts));
				info.setMsgCount(messageCounts);
			}
		}
		return convertView;
	}
	
	public final class ViewHolder{
		public ImageView image;
		public TextView username;
		public View circle;
		public TextView textViewCircle;
	}
	

}
