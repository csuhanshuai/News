package net.bangbao.ui;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.bangbao.AppConfig;
import net.bangbao.R;
import net.bangbao.UserConfig;
import net.bangbao.base.BaseActivity;
import net.bangbao.base.BaseApi;
import net.bangbao.common.UIHelper;
import net.bangbao.dao.CustomerMessApi;
import net.bangbao.dao.User.CustomerMessageStruct;
import net.bangbao.dao.User.PasswordMessageStruct;
import net.bangbao.network.ApiClient;
import net.bangbao.network.RequestManager;
import net.bangbao.network.UploadAgent;
import net.bangbao.utils.EncryptionUtils;
import net.bangbao.widget.CircleImage;
import net.bangbao.widget.SelectPicPopupWindow;

import org.json.JSONObject;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.PopupWindow.OnDismissListener;

public class CustomerMessageActivity extends BaseActivity implements View.OnClickListener{
	
	private TextView u_name;
	private EditText nick;
	private TextView button;
	private RelativeLayout rel;
	private String url;
	private String strBeforeRecommend;
	private CircleImage toux;
//	private String ps = "";
//	private String old_ps = "";
	
	private Intent dataIntent = null;
	public  Bitmap bi,photo;
	public boolean isFromItent = false;
	SelectPicPopupWindow menuWindow;
	private View view;
	private boolean isPost = false;
	CustomerMessageStruct customer = new CustomerMessageStruct();
	
	
//	private EditText old_pwd,new_pwd,re_pwd;
	private TextView toast;
	
	private UserConfig userConfig;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.customer_message);
		initViews();
		setup1();
	}
	
	public static class CustomerUser1{
		public static String nick_nm1;
		public static String image_url1;
	}
	
	public static class CustomerUser2{
		public static String nick_nm2;
		public static String image_url2;
	}

	private void initViews() {
		userConfig = AppConfig.getUser();
		u_name = (TextView) findViewById(R.id.u_name);
		u_name.setText(userConfig.getUserName());
		
		toux = (CircleImage) findViewById(R.id.toux);
		view = findViewById(R.id.customer_tupian);
		view.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//实例化SelectPicPopupWindow  
                menuWindow = new SelectPicPopupWindow(CustomerMessageActivity.this, itemsOnClick);  
                //显示窗口  
                menuWindow.setOutsideTouchable(true);
                menuWindow.setFocusable(true);
               
               
                // 有效果，popwindow打开，背景变半透明
                backgroundAlpha(0.5f);
                menuWindow.setOnDismissListener(new OnDismissListener() {

                    @Override
                    public void onDismiss() {
                        backgroundAlpha(1f);
                    }
                });
                menuWindow.showAtLocation(CustomerMessageActivity.this.findViewById(R.id.nickname), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置  
            }  
        });  
		
		
		nick = (EditText) findViewById(R.id.nickname);
		nick.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				strBeforeRecommend = s.toString();
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				
				if(s.toString().equals(strBeforeRecommend)){  
            		return;  
            	}  
				int editStart = nick.getSelectionStart();
				int offset = s.toString().length() - strBeforeRecommend.length();
				if (s.toString().endsWith(" ")){
					UIHelper.showToastMessage("不能输入空格");
					s.delete(s.toString().length() - offset, s.toString().length());
					int tempSelection = editStart - offset;
					nick.setText(s);
					nick.setSelection(tempSelection);
				}
			}
		});

		button =  (TextView) findViewById(R.id.modify);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(button.getText().equals("完成")){
					if(isPost == true){
						new UploadAgent().uploadImage(getMyBitmap(), userConfig.getUserId(), userConfig.getUserToken(), 
								new UploadAgent.IUploadCallBack() {
									
									@Override
									public void uploadSuess(String success) {
										Map<String, Object> maps = getMapForJson(success);
										url = (String) maps.get("url");
										customer.image_url = url;
										String ni = nick.getText().toString().trim();
										if(ni == "")
											CustomerUser2.nick_nm2 = null;
										else
											CustomerUser2.nick_nm2 = ni;
										if(!CustomerUser1.nick_nm1.equals(CustomerUser2.nick_nm2))
											customer.nick_nm = CustomerUser2.nick_nm2;
										else
											customer.nick_nm = null;
										int ret = (Integer) maps.get("ret_cd");
										String txt = (String) maps.get("ret_txt");
										Log.d("bb", "---"+ret);
										if(ret == 0) {
											Log.d("bb", "---");
											complete2();
											isPost = false;
										}
										else
											UIHelper.showToastMessage(txt);
										Log.d("bb", url);
										Log.d("bb", "success"+success);
									}
									
									@Override
									public void uploadFail(String error) {
										Log.d("bb", "error"+error);
										isPost = false;
										
									}
								});
					}
					else {
						customer.image_url = null;
						String ni = nick.getText().toString().trim();
						if(ni == "")
							CustomerUser2.nick_nm2 = null;
						else
							CustomerUser2.nick_nm2 = ni;
						if(!CustomerUser1.nick_nm1.equals(CustomerUser2.nick_nm2)) {
							customer.nick_nm = CustomerUser2.nick_nm2;
							complete2();
							setup();
						}
						else
							setup();
					}
				}
				else if(button.getText().equals("修改")){
					setup1();
					
				}
				
			}
		});
		rel = (RelativeLayout) findViewById(R.id.go_back);
		rel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
		
//		old_pwd = (EditText) findViewById(R.id.old_pwd);
//		old_pwd.addTextChangedListener(new TextWatcher() {
//			
//			@Override
//			public void onTextChanged(CharSequence s, int start, int before, int count) {
//				
//			}
//			
//			@Override
//			public void beforeTextChanged(CharSequence s, int start, int count,
//					int after) {
//				strBeforeRecommend = s.toString();
//			}
//			
//			@Override
//			public void afterTextChanged(Editable s) {
//				
//				if(s.toString().equals(strBeforeRecommend)){  
//            		return;  
//            	}  
//				int editStart = old_pwd.getSelectionStart();
//				int offset = s.toString().length() - strBeforeRecommend.length();
//				if (s.toString().endsWith(" ")){
//					UIHelper.showToastMessage("不能输入空格");
//					s.delete(s.toString().length() - offset, s.toString().length());
//					int tempSelection = editStart - offset;
//					old_pwd.setText(s);
//					old_pwd.setSelection(tempSelection);
//				}
//			}
//		});
//		new_pwd = (EditText) findViewById(R.id.new_pwd);
//		new_pwd.addTextChangedListener(new TextWatcher() {
//			
//			@Override
//			public void onTextChanged(CharSequence s, int start, int before, int count) {
//				old_pwd.setHint("");
//			}
//			
//			@Override
//			public void beforeTextChanged(CharSequence s, int start, int count,
//					int after) {
//				strBeforeRecommend = s.toString();
//			}
//			
//			@Override
//			public void afterTextChanged(Editable s) {
//				
//				if(s.toString().equals(strBeforeRecommend)){  
//            		return;  
//            	}  
//				int editStart = new_pwd.getSelectionStart();
//				int offset = s.toString().length() - strBeforeRecommend.length();
//				if (s.toString().endsWith(" ")){
//					UIHelper.showToastMessage("不能输入空格");
//					s.delete(s.toString().length() - offset, s.toString().length());
//					int tempSelection = editStart - offset;
//					new_pwd.setText(s);
//					new_pwd.setSelection(tempSelection);
//				}
//			}
//		});
//		re_pwd = (EditText) findViewById(R.id.confirm);
//		re_pwd.addTextChangedListener(new TextWatcher() {
//			
//			@Override
//			public void onTextChanged(CharSequence s, int start, int before, int count) {
//				old_pwd.setHint("");
//			}
//			
//			@Override
//			public void beforeTextChanged(CharSequence s, int start, int count,
//					int after) {
//				strBeforeRecommend = s.toString();
//			}
//			
//			@Override
//			public void afterTextChanged(Editable s) {
//				
//				if(s.toString().equals(strBeforeRecommend)){  
//            		return;  
//            	}  
//				int editStart = re_pwd.getSelectionStart();
//				int offset = s.toString().length() - strBeforeRecommend.length();
//				if (s.toString().endsWith(" ")){
//					UIHelper.showToastMessage("不能输入空格");
//					s.delete(s.toString().length() - offset, s.toString().length());
//					int tempSelection = editStart - offset;
//					re_pwd.setText(s);
//					re_pwd.setSelection(tempSelection);
//				}
//			}
//		});
		toast = (TextView) findViewById(R.id.toast);
		
		new ApiClient().getCustomerMess(userConfig.getUserId(),userConfig.getUserToken(),this, CustomerMessApi.class, 
				new ApiClient.CallBack<CustomerMessApi>() {

			@Override
			public void success(CustomerMessApi api) {
				if(api == null)return;
				CustomerUser1.nick_nm1 = api.getNick_nm();
				nick.setText(CustomerUser1.nick_nm1);
				
				Log.d("ab", "image_url"+api.getImage_url());
				CustomerUser1.image_url1 = api.getImage_url();
				
				if(api.getImage_url()!=null){
					RequestManager.getInstance().loadImage(api.getImage_url(), toux);
					}
			}

			@Override
			public void fail(String error) {
				
			}
		});
		
	}
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	switch (requestCode) {
		case 1:
			try{
				Log.d("bb", "---start");
				startPhotoZoom(data.getData());
				Log.d("bb", "---end");
			}catch(NullPointerException e){
				e.printStackTrace();
			}
			
			break;
		case 2:
			if(data != null){
				dataIntent = data;
				setPicToView(data);
			}
			break;
		case 3:
			File temp = new File(Environment.getExternalStorageDirectory()
					+ "/hs.jpg");
			Log.d("bb", "uri " + Uri.fromFile(temp));
			startPhotoZoom(Uri.fromFile(temp));
			break;

		default:
			break;
		}
    	super.onActivityResult(requestCode, resultCode, data);
    }
    /**
	 * 裁剪图片方法实现
	 * @param uri
	 */
	public void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		//下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 300);
		intent.putExtra("outputY", 300);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, 2);
	}
	
	/**
	 * 保存裁剪之后的图片数据
	 * @param picdata
	 */
	private void setPicToView(Intent picdata) {
		Bundle extras = picdata.getExtras();
		if (extras != null) {
			photo = extras.getParcelable("data");
//			Drawable drawable = new BitmapDrawable(photo);
			toux.setImageBitmap(null);
			toux.setImageBitmap(photo);
			isPost = true;
		}
	}
	
	private Bitmap getMyBitmap(){
			Log.d("test","bit");
			toux.setDrawingCacheEnabled(true);
			if(toux.getDrawingCache() == null)
				Log.d("test","bit is null");
			if(isFromItent == false){
				return toux.getDrawingCache();
			}
			else
				return getBitmap(dataIntent);
			
	}
	
	//从Intent获取图片资源
	
	private Bitmap getBitmap(Intent picdata) {
		Bundle bun = picdata.getExtras();
		isFromItent = true;
		Bitmap photo = null;
		if(bun != null) {
			photo = bun.getParcelable("data");
		}
		
		return photo;
	}
	

//为弹出窗口实现监听类  
private OnClickListener  itemsOnClick = new OnClickListener(){  

    public void onClick(View v) {  
        menuWindow.dismiss();  
        switch (v.getId()) {  
        case R.id.btn_take_photo:  
        	Intent intent = new Intent(
					MediaStore.ACTION_IMAGE_CAPTURE);
			//下面这句指定调用相机拍照后的照片存储的路径
			intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri
					.fromFile(new File(Environment
							.getExternalStorageDirectory(),
							"hs.jpg")));
			startActivityForResult(intent, 3);
            break;  
        case R.id.btn_pick_photo:
        	Intent pickIntent = new Intent(Intent.ACTION_PICK,null);
        	pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        	startActivityForResult(pickIntent, 1);
            break;  
        default:  
            break;  
        }  
          
              
    }  
      
};  

public static Bitmap convertStringToIcon(String st)  
{  
    // OutputStream out;  
    Bitmap bitmap = null;  
    try  
    {  
        // out = new FileOutputStream("/sdcard/aa.jpg");  
        byte[] bitmapArray;  
        bitmapArray = Base64.decode(st, Base64.DEFAULT);  
        bitmap =  
                BitmapFactory.decodeByteArray(bitmapArray, 0,  
                        bitmapArray.length);  
        // bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);  
        return bitmap;  
    }  
    catch (Exception e)  
    {  
        return null;  
    }  
}  
	
	public static Map<String, Object> getMapForJson(String jsonStr){  
        JSONObject jsonObject ;  
        try {  
            jsonObject = new JSONObject(jsonStr);  
              
            Iterator<String> keyIter= jsonObject.keys();  
            String key;  
            Object value ;  
            Map<String, Object> valueMap = new HashMap<String, Object>();  
            while (keyIter.hasNext()) {  
                key = keyIter.next();  
                value = jsonObject.get(key);  
                valueMap.put(key, value);  
            }  
            return valueMap;  
        } catch (Exception e) {  
            // TODO: handle exception  
            e.printStackTrace();  
        }  
        return null;  
    }  
	
	
	public void setup(){
		button.setText("修改");
		nick.setEnabled(false);
		toux.setEnabled(false);
		view.setEnabled(false);
//		LinearLayout li = (LinearLayout) findViewById(R.id.old);
//		LinearLayout li1 = (LinearLayout) findViewById(R.id.pw);
//		LinearLayout li2 = (LinearLayout) findViewById(R.id.repw);
//		li.setVisibility(View.GONE);
//		li1.setVisibility(View.GONE);
//		li2.setVisibility(View.GONE);
	}
	
	public void setup1(){
		button.setText("完成");
//		old_pwd.setHint("********");
		nick.setEnabled(true);
		toux.setEnabled(true);
		view.setEnabled(true);
//		LinearLayout li = (LinearLayout) findViewById(R.id.old);
//		LinearLayout li1 = (LinearLayout) findViewById(R.id.pw);
//		LinearLayout li2 = (LinearLayout) findViewById(R.id.repw);
//		li.setVisibility(View.VISIBLE);
//		li1.setVisibility(View.VISIBLE);
//		li2.setVisibility(View.VISIBLE);
	}
	
	public void complete2(){
		if(nick.getText().toString().trim().length() == 0){
			UIHelper.showToastMessage("昵称不能为空");
			return;
		}
//		if(old_pwd.getText().toString().trim().length() ==0
//				&&(new_pwd.getText().toString().trim().length() != 0
//				||re_pwd.getText().toString().trim().length() != 0)){
//			UIHelper.showToastMessage("原始密码不能为空");
//			return;
//		}
		
		
//		PasswordMessageStruct pass = new PasswordMessageStruct();
		//昵称
//		String ni = nick.getText().toString().trim();
//		CustomerUser2.nick_nm2 = ni;
//		customer.nick_nm = ni;
		//图像
//		String ur = url;
//		CustomerUser2.image_url2 = ur;
		
//		if(!CustomerUser1.nick_nm1.equals(CustomerUser2.nick_nm2))
//			customer.nick_nm = CustomerUser2.nick_nm2;
//		else
//			customer.nick_nm = null;
//		if(CustomerUser1.image_url1!="0"&&!CustomerUser1.image_url1.equals(CustomerUser2.image_url2))
//			customer.image_url = CustomerUser2.image_url2;
//		else
//			customer.image_url = null;
//		Log.d("ab", "customer_image_url"+customer.image_url);
		//原始密码
//		String old = old_pwd.getText().toString().trim();
//		try {
//			old_ps = EncryptionUtils.string2MD5(old);
//		} catch (NoSuchAlgorithmException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		pass.old_pwd = old_ps;
//		//新密码
//		String ne = new_pwd.getText().toString().trim();
//		try {
//			ps = EncryptionUtils.string2MD5(ne);
//		} catch (NoSuchAlgorithmException e) {
//			e.printStackTrace();
//		}
//		pass.new_pwd = ps;
//		//确认密码
//		String re = re_pwd.getText().toString().trim();
//		if(!re.equals(ne)){
//			re_pwd.setText(null);
//			toast.setText("密码不一致,请重新确认密码");
//			return;
//		}
//		if(new_pwd.getText().toString().trim().length()!=0&&(new_pwd.getText().toString().trim().length() < 3 || new_pwd.getText().toString().trim().length() > 16)) {
//			UIHelper.showToastMessage("密码不能低于3位，或高于16位");
//			return;
//		}
//		button.setEnabled(false);
//		if(old_pwd.getText().toString().trim().length()!=0){
//		new ApiClient().changePassword(pass, userConfig.getUserName(), this, BaseApi.class,
//				new ApiClient.CallBack<BaseApi>(){
//
//					@Override
//					public void success(BaseApi api) {
//						button.setEnabled(true);
//						if(api == null) return;
//						if(api.getRet_cd() == 0){
//							UIHelper.showToastMessage("密码修改成功");
//							old_pwd.setText(null);
//							new_pwd.setText(null);
//							re_pwd.setText(null);
//							toast.setText(null);
//						}
//						if(api.getRet_cd() == 14){
//							UIHelper.showToastMessage(api.getRet_txt());
//							toast.setText(null);
//						}
//					}
//
//					@Override
//					public void fail(String error) {
//						UIHelper.showToastMessage(
//								"当前网络有问题！");
//						button.setEnabled(true);
//					}
//			
//		});
//		}
			new ApiClient().deliverCustomerMessage(customer, userConfig.getUserId(), userConfig.getUserToken(), this, BaseApi.class,
					new  ApiClient.CallBack<BaseApi>(){
	
						@Override
						public void success(BaseApi api) {
							button.setEnabled(true);
							if(api == null) return;
							if(api.getRet_cd() == 0){
								//重新把最新的昵称传递给变量CustomerUser1.nick_nm1;
								CustomerUser1.nick_nm1 = CustomerUser2.nick_nm2;
								UIHelper.showToastMessage("昵称提交成功");
								
								Intent mIntent = new Intent("nick");
								mIntent.putExtra("nick",CustomerUser2.nick_nm2);
//								mIntent.putExtra("image", customer.image_url);
								sendBroadcast(mIntent);
								setup();
							}
							else{
								UIHelper.showToastMessage(api.getRet_txt());
							}
						}
	
						@Override
						public void fail(String error) {
							UIHelper.showToastMessage(
									"当前网络有问题！");
							button.setEnabled(true);
						}
				
			});
		
		
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
//		case R.id.go_back:
//			onBackPressed();

		default:
			break;
		}
		
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
