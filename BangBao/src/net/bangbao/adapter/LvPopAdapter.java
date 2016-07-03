package net.bangbao.adapter;

import java.util.ArrayList;
import java.util.List;

import net.bangbao.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class LvPopAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private List<String> list;
	
	public LvPopAdapter(Context context,List<String> lists) {
		this.inflater = LayoutInflater.from(context);
		this.list = list;
	}
	@Override
	public int getCount() {
		if(list == null)return 0;
		return list.size();
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
		
		if(convertView == null){
			convertView = inflater.inflate(R.layout.lv_pop_items, null);
		}
		TextView tv = (TextView)convertView.findViewById(R.id.text_select);
		tv.setText(list.get(position));
		return convertView;
	}

}
