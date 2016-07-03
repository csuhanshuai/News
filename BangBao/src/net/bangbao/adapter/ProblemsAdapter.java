package net.bangbao.adapter;

import java.util.List;

import net.bangbao.R;
import net.bangbao.base.BaseApiClient;
import net.bangbao.dao.NewsApi.NewsBean;
import net.bangbao.dao.Problems;
import net.bangbao.fragment.MessageFragment;
import net.bangbao.network.ApiClient;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @Description TODO
 * @Author 修订者:Leo
 * @Since 2015-3-19 下午8:25:31
 */
public class ProblemsAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private List<NewsBean> list;

    private ImageView imageArrow;
    private ApiClient mClient;

    public ProblemsAdapter(LayoutInflater layoutInflater, List<NewsBean> list) {
        this.layoutInflater = layoutInflater;
        mClient = new ApiClient();
        this.list = list;
    }

    @Override
    public int getCount() {
        return this.list.size();
    }

    public void setList(List<NewsBean> list) {
        this.list = list;
        notifyDataSetChanged();
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

        final NewsBean news = this.list.get(position);
        convertView = layoutInflater.inflate(R.layout.layout_problems_item, null);
        TextView pro_title = (TextView) convertView.findViewById(R.id.pro_title);
        final ImageView image_arrow = (ImageView) convertView.findViewById(R.id.image_arrow);
        pro_title.setText(news.tit);
        final TextView pro_detail = (TextView) convertView.findViewById(R.id.pro_detail);
        convertView.findViewById(R.id.layout_pro).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                if (pro_detail.getVisibility() == 0) { // 可见
                    pro_detail.setVisibility(View.GONE);
                    image_arrow.setImageResource(R.drawable.bt_down);
                } else {
                    mClient.getProDetail(MessageFragment.getDownloadTag(), news.id,
                            ProblemsAdapter.this, Problems.class,
                            new BaseApiClient.CallBack<Problems>() {
                                @Override
                                public void success(Problems api) {
                                    image_arrow.setImageResource(R.drawable.arrow_up);
                                    pro_detail.setText(Html.fromHtml(api.getCntt()));
                                    pro_detail.setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void fail(String error) {

                                }

                            });
                }
            }
        });
        return convertView;
    }

}
