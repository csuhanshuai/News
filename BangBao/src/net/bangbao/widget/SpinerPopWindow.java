package net.bangbao.widget;

import java.util.List;

import net.bangbao.R;
import net.bangbao.adapter.AbstractSpinerAdapter;
import net.bangbao.adapter.AbstractSpinerAdapter.IOnItemSelectListener;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.PopupWindow;

public class SpinerPopWindow extends PopupWindow implements OnItemClickListener {
	
	private Context mContext;
	private ListView mListView;
	private AbstractSpinerAdapter mAdapter;
	private IOnItemSelectListener mItemSelectListener;
	
	public SpinerPopWindow(Context context){
		super(context);
		
		mContext = context;
		init();
	}
	
	public void setItemListener(IOnItemSelectListener listener){
		mItemSelectListener = listener;
	}
	
	public void setAdapter(AbstractSpinerAdapter adapter){
		mAdapter = adapter;
		mListView.setAdapter(mAdapter);
	}

	private void init() {
		// TODO Auto-generated method stub
		View view = LayoutInflater.from(mContext).inflate(R.layout.spiner_window, null);
		setContentView(view);
		setWidth(LayoutParams.WRAP_CONTENT);
		setHeight(LayoutParams.WRAP_CONTENT);
		
		setFocusable(true);
		ColorDrawable dw = new ColorDrawable(0x00);
		setBackgroundDrawable(dw);
		
		mListView = (ListView) view.findViewById(R.id.listview);
		mListView.setOnItemClickListener(this);
	}
	
	public <T> void refreshData(List<T> list,int selIndex){
		if(list!=null && selIndex!=-1){
			if(mAdapter!=null){
				mAdapter.refreshData(list, selIndex);
			}
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		dismiss();
		if(mItemSelectListener!=null){
			mItemSelectListener.onItemClick(position);
		}
		
	}

}
