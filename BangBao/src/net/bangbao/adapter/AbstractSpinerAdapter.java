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
/*
 *  
 */
public abstract class AbstractSpinerAdapter<T> extends BaseAdapter {
	
	public static interface IOnItemSelectListener{
		public void onItemClick(int pos);
	}
	
	private Context mContext;
	private List<T> mObjects = new ArrayList<T>();
	private int mSelectItem = 0;
	
	private LayoutInflater mInflater;
	
	public AbstractSpinerAdapter(Context context){
		init(context);
	}
	
	public void refreshData(List<T> objects,int selIndex){
		mObjects = objects;
		if(selIndex < 0){
			selIndex = 0;
		}
		
		if(selIndex >= mObjects.size()){
			selIndex = mObjects.size()-1;
		}
		
		mSelectItem = selIndex;
	}

	private void init(Context context) {
		mContext = context;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mObjects.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mObjects.get(position).toString();
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		
		if(convertView == null) {
			convertView = mInflater.inflate(R.layout.spiner_item, null);
			viewHolder = new ViewHolder();
			viewHolder.mTextView = (TextView) convertView.findViewById(R.id.textView);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		Object item = getItem(position);
		viewHolder.mTextView.setText(item.toString());
		
		return convertView;
	}
	
	
	public static class ViewHolder{
		public TextView mTextView;
	}
	

}
