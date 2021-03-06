package net.bangbao.network;


import net.bangbao.AppInit;
import net.bangbao.R;
import net.bangbao.macro.HttpMacro;

import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.toolbox.Volley;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * @author mosl
 * 上传图片的一个代理
 */
public class UploadAgent {

	public static interface IUploadCallBack{
		
		//上传成功
		public void uploadSuess(String success);
		//上传失败
		public void uploadFail(String error);
	}
	public void uploadImage(Bitmap bitmap,int user_id,String token,
			final IUploadCallBack callBack){
		
		long timeMills = System.currentTimeMillis();
		String filename = String.valueOf(timeMills + ".png");
		
	    PostFormRequest request = new PostFormRequest(Method.POST, HttpMacro.UPLOAD_HOST,
	        	new PostFormRequest.Success() {
					
					@Override
					public void success(String success) {
						
						if(callBack != null)
							callBack.uploadSuess(success);
					}
				},new  ErrorListener() {

						@Override
						public void onErrorResponse(VolleyError error) {
							
							if(callBack != null)
								callBack.uploadFail(error.toString());
						}
				}, user_id, token,bitmap,filename);
	     Volley.newRequestQueue(AppInit.getContext()).add(request);
	}
}
