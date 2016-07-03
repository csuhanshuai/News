package net.bangbao.adapter;

import java.util.List;

import net.bangbao.R;
import net.bangbao.dao.InsuCatgApi.InsuCatgInfo;
import net.bangbao.utils.Utils;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ChooseInsuAdapter extends BaseAdapter {

	private Context mContext;
	private List<InsuCatgInfo> lists;

	// private RequestQueue requestQueue;

	public ChooseInsuAdapter(Context context, List<InsuCatgInfo> list) {
		this.lists = list;
		this.mContext = context;

	}

	@Override
	public int getCount() {
		return lists.size();
	}

	@Override
	public Object getItem(int position) {
		return this.lists.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		InsuCatgInfo insuCatgInfo = lists.get(position);

		final ViewHolder holder;

		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.accident_item2, null);

			holder = new ViewHolder();

			holder.mProductTitleTXT = (TextView) convertView
					.findViewById(R.id.product_title);
			holder.mFitPeopleTXT = (TextView) convertView
					.findViewById(R.id.fit_person);
			holder.mTypeTXT = (TextView) convertView.findViewById(R.id.type);
			holder.mImageView = (ImageView) convertView
					.findViewById(R.id.image);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.mProductTitleTXT.setMaxLines(1);
		holder.mProductTitleTXT.setText(insuCatgInfo.getTit());
		holder.mFitPeopleTXT.setText(insuCatgInfo.getIntro());
		// 所属公司
		int co_id = insuCatgInfo.getCo_id();
		// 保险类型
		int catg_id = insuCatgInfo.getCatg_id();
		holder.mTypeTXT.setText(Utils.catg_id2String(catg_id));
		holder.mImageView.setBackgroundResource(Utils.co_id2PicUrl(co_id));

		return convertView;
	}

	static class ViewHolder {
		ImageView mImageView;
		TextView mProductTitleTXT;
		TextView mFitPeopleTXT;
		TextView mTypeTXT;

	}
}
