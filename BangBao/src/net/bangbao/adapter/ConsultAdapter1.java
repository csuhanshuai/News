package net.bangbao.adapter;

import io.rong.imlib.RongIMClient.Message;

import java.util.List;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.toolbox.ImageRequest;

import net.bangbao.AppConfig;
import net.bangbao.R;
import net.bangbao.UserConfig;
import net.bangbao.base.BaseApiClient;
import net.bangbao.common.Ids;
import net.bangbao.dao.ConsultMessApi;
import net.bangbao.dao.ConsultApi.ConsultInfo;
import net.bangbao.network.AgentApiClient;
import net.bangbao.network.RequestManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ConsultAdapter1 extends BaseAdapter {

	private List<ConsultInfo> infos;
	private Context context;

	private UserConfig userConfig = AppConfig.getUser();

	public ConsultAdapter1(List<ConsultInfo> list, Context context) {
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

		ConsultInfo info = infos.get(position);

		// if(convertView == null){
		// convertView = LayoutInflater.from(context).inflate(
		// R.layout.consult_item, null);
		// username = (TextView)convertView.findViewById(R.id.name);
		//
		// username.setText(info.getNick_nm());
		//
		// comm = (TextView) convertView.findViewById(R.id.comm);
		// String com = Ids.getComm(info.getCo_id());
		// comm.setText(com);
		//
		// sex = (TextView) convertView.findViewById(R.id.text_gg);
		// String s = Ids.getSex(info.getSex());
		// sex.setText(s);
		//
		// num = (TextView) convertView.findViewById(R.id.num_focus);
		// String n = Integer.toString(info.getAtte_num());
		// num.setText(n);
		//
		// edu = (TextView) convertView.findViewById(R.id.xueli);
		// String e = Ids.getEdu(info.getEdu_id());
		// edu.setText(e);
		//
		//
		// }
		// return convertView;
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.look_consult_list_item, null);
			viewHolder.username = (TextView) convertView
					.findViewById(R.id.name);
			viewHolder.comm = (TextView) convertView.findViewById(R.id.comm);
			viewHolder.mSexIV = (ImageView) convertView
					.findViewById(R.id.sex_iv);
			viewHolder.num = (TextView) convertView
					.findViewById(R.id.num_focus);
			viewHolder.edu = (TextView) convertView.findViewById(R.id.xueli);
			viewHolder.image = (ImageView) convertView.findViewById(R.id.image);
			
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
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.username.setText(info.getNick_nm());
		String com = Ids.getComm(info.getCo_id());
		viewHolder.comm.setText(com);
		String n = Integer.toString(info.getAtte_num());
		viewHolder.num.setText(n);
		String e = Ids.getEdu(info.getEdu_id());
		viewHolder.edu.setText(e);
		String s = Ids.getSex(info.getSex());

		if (s.equals("男")) {
			viewHolder.mSexIV.setImageResource(R.drawable.male);
		} else if (s.equals("女")) {
			viewHolder.mSexIV.setImageResource(R.drawable.female);
		} else {
			viewHolder.mSexIV.setImageResource(R.drawable.nosex);
		}

		// 防止图片错位的处理,tag和URL绑定
        loadImage(info.user_id, viewHolder.image);

		
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

		return convertView;
	}

	public final class ViewHolder {
		public ImageView image;
		public TextView username, comm, num, edu;
		ImageView mSexIV;
		public View circle;
		public TextView textViewCircle;
	}
	
	// TODO 这里没有规范，应该放在network包里面
    private void loadImage(int user_id, final ImageView imageView) {

        new AgentApiClient().getConsultDetail(user_id, null,
                ConsultMessApi.class,
                new BaseApiClient.CallBack<ConsultMessApi>() {

                    @Override
                    public void success(ConsultMessApi api) {
                        imageView.setTag(api.getImage_url());
                        // 预设图片
                        imageView.setImageResource(R.drawable.app_logo);

                        Log.d("Image_url", "Image_url--" + api.getImage_url());

                        if (api.getImage_url() != null
                                && api.getImage_url() != "") {
                            // 这里还要判断url的合法性，符合http://开始
                            if (api.getImage_url().startsWith("http://")) {
                                // 设置头像
                                loadBitmap(api.getImage_url(), imageView);
                            }

                        }
                    }

                    @Override
                    public void fail(String error) {

                    }
                }

        );

    }

    private void loadBitmap(final String bitmapUrl, final ImageView imageView) {
        ImageRequest imageRequest = new ImageRequest(bitmapUrl,
                new Response.Listener<Bitmap>() {

                    @Override
                    public void onResponse(Bitmap bitmap) {
                        imageView.setImageBitmap(bitmap);
                    }
                }, 72, 72, null, new ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // 没有效果
                        Log.d("VolleyError", error.toString());
                        error.printStackTrace();
                    }
                });
        RequestManager.getInstance().addRequest(imageRequest, null);
    }

}
