package net.bangbao.widget;

import java.util.List;

import net.bangbao.R;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * @author mosl
 * 下拉选框,模仿下拉选项
 */
public class SpinnerDropDown extends PopupWindow {

	private List<String> mItems;
	private ListView mListView;
	private Context mContext;
	/** 下拉视图的宽度 */
	private int mViewWidth;
	/** 下拉视图的高度 */
	private int mViewHeight;
	private int mNeedsWidth;
	private int mItemCenter;
	
	private IOnClickItemListener onClickItemlistener;
	
	public SpinnerDropDown(List<String> items,Context context,
			int width,IOnClickItemListener listener,int itemCenter) {
		this.mItems = items;
		this.mContext = context;
		this.mNeedsWidth = width;
		this.onClickItemlistener = listener;
		this.mItemCenter = itemCenter;
		init();
	}


	public int getmViewWidth() {
		return mViewWidth;
	}

	public void setmViewWidth(int mViewWidth) {
		this.mViewWidth = mViewWidth;
	}

	public int getmViewHeight() {
		return mViewHeight;
	}

	public void setmViewHeight(int mViewHeight) {
		this.mViewHeight = mViewHeight;
	}
	
	private void init() {
		
		LinearLayout layout = (LinearLayout)LayoutInflater.from(mContext).inflate(R.layout.list_view_spinner_drop,
				null);
		mListView = (ListView)layout.findViewById(R.id.listview);
		mListView.setAdapter(new ItemAdapter());
		mListView.setSelector(R.drawable.list_selecter);
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				if(onClickItemlistener != null){
					onClickItemlistener.clickItem(position);
				}
				SpinnerDropDown.this.dismiss();
			}
		});
		setFocusable(true);
		setOutsideTouchable(true); 
		setBackgroundDrawable(new BitmapDrawable());
		setAnimationStyle(R.style.Animation_dropdown);
		// 必须设置
		setWidth(this.mNeedsWidth );
		setHeight(300);
		setContentView(layout);
		setFocusable(true);
	}
	
	public void setPopwindowHeight(int height){
		setHeight(height);
	}
//	private void onMeasured(View v) {
//		int w = View.MeasureSpec.makeMeasureSpec(0,
//				View.MeasureSpec.UNSPECIFIED);
//		int h = View.MeasureSpec.makeMeasureSpec(0,
//				View.MeasureSpec.UNSPECIFIED);
//		v.measure(w, h);
//		mViewWidth = v.getMeasuredWidth();
//		mViewHeight = v.getMeasuredHeight();
//	}
	
	/** 回调接口 **/
	public interface IOnClickItemListener{
		
		public void clickItem(int item);
	}
	
	public class ItemAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return mItems.size();
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
			String item = mItems.get(position);
			ViewHolder viewHolder = null;
			if(convertView == null){
				
				if(mItemCenter == 1){
					convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_spinner_drop, null);
					viewHolder = new ViewHolder();
					viewHolder.textView = (TextView)convertView.findViewById(R.id.text);
					viewHolder.textView.setText(item);
					convertView.setTag(viewHolder);
				}else{
					convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_spinner_drop_left, null);
					viewHolder = new ViewHolder();
					viewHolder.textView = (TextView)convertView.findViewById(R.id.text);
					viewHolder.textView.setText(item);
					convertView.setTag(viewHolder);
				}
				
			}else{
				viewHolder = (ViewHolder)convertView.getTag();
				viewHolder.textView.setText(item);
			}
			return convertView;
		}
		
		public class ViewHolder{
			public TextView textView;
		}
	}
	
}
