package net.bangbao.ui;

import net.bangbao.AppConfig;
import net.bangbao.R;
import net.bangbao.UserConfig;
import net.bangbao.base.BaseActivity;
import net.bangbao.base.BaseApi;
import net.bangbao.base.BaseApiClient;
import net.bangbao.common.Ids;
import net.bangbao.common.UIHelper;
import net.bangbao.dao.ConsultMessApi;
import net.bangbao.network.AgentApiClient;
import net.bangbao.network.RequestManager;
import net.bangbao.oath.QQHelper;
import net.bangbao.oath.WeiBoHelper;
import net.bangbao.oath.WeiChatHelper;
import net.bangbao.utils.DateUtils;
import net.bangbao.widget.CircleImage;
import net.bangbao.widget.ShareCardDialog;
import net.bangbao.widget.ShareCardDialog.IOnClickShare;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

/**
 * @author mosl 顾问详情页 调用者需传递顾问的 key = user_id(int) atte_num = 关注人数 (int)
 */
public class ConsultDetailActivity extends BaseActivity implements
		View.OnClickListener {

	private View LayoutZixun, LayoutFocus, fram_back, fram_card;
	private LinearLayout ShareCard;
	private Bitmap cardBitmap;
	private CircleImage userImage;
	// 代理人
	private Intent intent;
	private AgentApiClient agentClient = new AgentApiClient();
	// 加关注的text
	private TextView textFocus;

	/** 填写信息的属性 **/
	private TextView username_text;
	private ImageView sex_Iv;
	private TextView edu_text;
	private TextView mFromTextView;
	// private TextView mAttmTextView; // 关注人数
	private TextView years_text;
	private TextView honour_text;
	private TextView get_text; // 感悟
	private TextView mCommTextView;
	private TextView mStarCity; // 星座
	private ShareCardDialog mGenCarddialog;
	private PopupWindow mPopShare;

	private UserConfig mUserConfig;
	private int atte_num;
	/** 顾问id **/
	private int agentId;
	private CircleImage mPengyouquanShare, mWeixinShare, mQQshare, mWeiboShare;
	private WeiChatHelper weiChatHelper;
	private QQHelper qqHelper;
	private WeiBoHelper weiboHelper;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_consult_detail);

		intent = getIntent();
		if (intent != null) {
			agentId = intent.getIntExtra("key", -1);
			atte_num = intent.getIntExtra("atte_num", 0);
			init();
			mUserConfig = AppConfig.getUser();
			agentClient.getConsultDetail(agentId, this, ConsultMessApi.class,
					new BaseApiClient.CallBack<ConsultMessApi>() {

						@Override
						public void success(ConsultMessApi api) {

							username_text.setText(api.getNick_nm());
							// sex_Iv.setText(Ids.getSex(api.getSex()));
							// 根据性别确定图片
							if (Ids.getSex(api.getSex()).equals("男")) {
								sex_Iv.setImageResource(R.drawable.male);
							} else if (Ids.getSex(api.getSex()).equals("女")) {
								sex_Iv.setImageResource(R.drawable.female);
							} else {
								sex_Iv.setImageResource(R.drawable.nosex);
							}
							edu_text.setText(Ids.getEdu(api.getEdu_id()));
							mFromTextView.setText(Ids.getProvice(api
									.getProv_id()));
							// mAttmTextView.setText(String.valueOf(atte_num));
							years_text.setText(DateUtils.to_time(api
									.getBgn_tmtp()) + "年");
							honour_text.setText(api.getHonor_say());
							get_text.setText(api.getIns_say());
							mCommTextView.setText(Ids.getComm(api.getCo_id()));
							// TODO 根据生日算星座，getBday的时间是字符串：19800312
							// mStarCity.setText(DateUtils.date2Constellation(api.getBday()));
							// Ids.date2Constellation(api.getBday());
							String imageUrl = api.getImage_url();
							if (imageUrl != null)
								RequestManager.getInstance().loadImage(
										imageUrl, userImage);
						}

						@Override
						public void fail(String error) {

						}
					});
		}

	}

	private void init() {
		LayoutZixun = findViewById(R.id.button_zixun);
		LayoutFocus = findViewById(R.id.button_focus);
		fram_card = findViewById(R.id.fram_card);
		fram_card.setOnClickListener(this);
		LayoutFocus.setOnClickListener(this);
		LayoutZixun.setOnClickListener(this);
		fram_back = findViewById(R.id.fram_back);
		fram_back.setOnClickListener(this);
		textFocus = (TextView) findViewById(R.id.text_focus);
		username_text = (TextView) findViewById(R.id.username_text);
		sex_Iv = (ImageView) findViewById(R.id.consult_detail_sex_iv);
		edu_text = (TextView) findViewById(R.id.edu_text);
		mFromTextView = (TextView) findViewById(R.id.from_text);
		// mAttmTextView = (TextView) findViewById(R.id.star_text);
		years_text = (TextView) findViewById(R.id.years_text);
		honour_text = (TextView) findViewById(R.id.honour_text);
		get_text = (TextView) findViewById(R.id.get_text);
		ShareCard = (LinearLayout) findViewById(R.id.share_card);
		userImage = (CircleImage) findViewById(R.id.user_image);
		mCommTextView = (TextView) findViewById(R.id.comm_text);
		mStarCity = (TextView) findViewById(R.id.star_city);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.button_zixun:
			startChat();
			break;
		case R.id.button_focus:
			addFocus();
			break;
		case R.id.fram_back:
			onBackPressed();
			break;
		case R.id.fram_card:
			ShareCard.setDrawingCacheEnabled(true);
			ShareCard.buildDrawingCache();
			cardBitmap = ShareCard.getDrawingCache();
			mGenCarddialog = new ShareCardDialog(cardBitmap,
					new IOnClickShare() {

						@Override
						public void share() {
							mGenCarddialog.dismiss();
							shareToFriends();
						}
					});
			FragmentTransaction ft = getSupportFragmentManager()
					.beginTransaction();
			mGenCarddialog.show(ft, "tag");
			break;
		case R.id.pengyouquan:
			weiChatHelper = new WeiChatHelper(this);
			weiChatHelper.sendMedia(this, "我的名片", "这个保险顾问挺靠谱的，分享下！", "",
					cardBitmap, 1);
			break;
		case R.id.weixin:
			weiChatHelper = new WeiChatHelper(this);
			weiChatHelper.sendMedia(this, "我的名片", "这个保险顾问挺靠谱的，分享下！", "",
					cardBitmap, 0);
			break;
		case R.id.qq:
			qqHelper = new QQHelper(this);
			qqHelper.shareToQQ(this, "我的名片", "这个保险顾问挺靠谱的，分享下！", "", 1);
			break;
		case R.id.weibo:
			weiboHelper = new WeiBoHelper(this);
			weiboHelper.sendMultiMessage(this, "我的名片", cardBitmap, "");
			break;
		default:
			break;
		}
	}

	/** 在线咨询 **/
	private void startChat() {

		if (!AppConfig.isLogin()) {
			UIHelper.showToastMessage("您要登陆才能咨询");
			return;
		}

		if (mUserConfig.getType() == 2) {
			UIHelper.showToastMessage("顾问之间不能互相咨询");
			return;
		}
		/** 在线咨询 == 加关注 **/
		agentClient.addAgentFriends(mUserConfig.getUserId(),
				mUserConfig.getUserToken(), agentId, this, BaseApi.class,
				new BaseApiClient.CallBack<BaseApi>() {

					@Override
					public void success(BaseApi api) {
						textFocus.setText("取消关注");
					}

					@Override
					public void fail(String error) {

					}
				});

		Intent intent = new Intent(this, ChatActivity.class);
		intent.putExtra("target_id", String.valueOf(agentId));
		intent.putExtra("target_categry", ChatActivity.TaAgent);
		intent.putExtra("target_username", username_text.getText().toString());
		startActivity(intent);
	}

	/** 分享给朋友们 **/
	private void shareToFriends() {

		View popView = LayoutInflater.from(this).inflate(
				R.layout.layout_share_to_friends, null);
		Button btCancel = (Button) popView.findViewById(R.id.bt_cancel);
		mPengyouquanShare = (CircleImage) popView
				.findViewById(R.id.pengyouquan);
		mWeixinShare = (CircleImage) popView.findViewById(R.id.weixin);
		mQQshare = (CircleImage) popView.findViewById(R.id.qq);
		mWeiboShare = (CircleImage) popView.findViewById(R.id.weibo);
		mPengyouquanShare.setOnClickListener(this);
		mWeixinShare.setOnClickListener(this);
		mQQshare.setOnClickListener(this);
		mWeiboShare.setOnClickListener(this);
		btCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mPopShare.dismiss();
			}
		});
		mPopShare = new PopupWindow(popView,
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);

		mPopShare.setOutsideTouchable(true);
		mPopShare.setFocusable(true);
		mPopShare.setContentView(popView);
		// 无效果
		mPopShare.setBackgroundDrawable(new ColorDrawable(0x22000000));
		// 有效果，popwindow打开，背景变半透明
		backgroundAlpha(0.5f);
		mPopShare.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				backgroundAlpha(1f);
			}
		});

		mPopShare.showAtLocation(this.findViewById(R.id.back_layout),
				Gravity.BOTTOM, 0, 0);
	}

	/** 加关注 **/
	private void addFocus() {

		if (!AppConfig.isLogin()) {
			UIHelper.showToastMessage("您要登陆才能加关注");
			return;
		}
		if (mUserConfig.getType() == 2) {
			UIHelper.showToastMessage("顾问之间不能互相关注");
			return;
		}

		if (textFocus.getText().toString().endsWith("取消关注")) {
			cancelFocus();
			return;
		}
		agentClient.addAgentFriends(mUserConfig.getUserId(),
				mUserConfig.getUserToken(), agentId, this, BaseApi.class,
				new BaseApiClient.CallBack<BaseApi>() {

					@Override
					public void success(BaseApi api) {
						Log.d("aa", api.getRet_txt());
						textFocus.setText("取消关注");
					}

					@Override
					public void fail(String error) {

					}
				});
	}

	/** 取消关注 **/
	public void cancelFocus() {
		agentClient.cancelAgentFriends(mUserConfig.getUserId(),
				mUserConfig.getUserToken(), agentId, this, BaseApi.class,
				new BaseApiClient.CallBack<BaseApi>() {

					@Override
					public void success(BaseApi api) {
						textFocus.setText("加关注");
					}

					@Override
					public void fail(String error) {

					}
				});
	}

	/**
	 * 设置添加屏幕的背景透明度
	 * 
	 * @param bgAlpha
	 */
	private void backgroundAlpha(float bgAlpha) {
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.alpha = bgAlpha; // 0.0-1.0
		getWindow().setAttributes(lp);
	}
}
