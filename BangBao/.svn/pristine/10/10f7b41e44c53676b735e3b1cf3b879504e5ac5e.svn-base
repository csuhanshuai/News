package net.bangbao.adapter;

import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import net.bangbao.AppInit;
import net.bangbao.R;
import net.bangbao.dao.*;
/*
 * author:mosl
 * descripton:定点医院的List 的Adapter
 */
public class LocateHospAdapter extends BaseAdapter {

	private List<LocaHospoApi.HospAddr> lists;
	
	public LocateHospAdapter(List<LocaHospoApi.HospAddr> lists){
		this.lists = lists;
	}
	
	@Override
	 public boolean isEnabled(int position) {
         return false;//当前行是否可以点击

	 }
	@Override
	public int getCount() {
		return lists.size();
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
	public View getView(int position, View convertView,
			ViewGroup parent) {
		
		LocaHospoApi.HospAddr hospAddr = lists.get(position);
		if(convertView == null){
			convertView = LayoutInflater.from(AppInit.getContext()).inflate(
					R.layout.loca_addr_item, null);
			
			TextView loca_title = (TextView)convertView.findViewById(R.id.hosp_title);
			TextView hosp_addr = (TextView)convertView.findViewById(R.id.hosp_addr);
			loca_title.setText(hospAddr.getNm());
			hosp_addr.setText(hospAddr.getAddr());
		}
		return convertView;
	}

}
