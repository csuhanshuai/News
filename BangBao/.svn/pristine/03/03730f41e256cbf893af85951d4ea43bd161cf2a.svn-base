package net.bangbao.oath;

import net.bangbao.macro.VenterConstants;

import com.tencent.connect.share.QQShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

public class QQHelper {
	
	public static Tencent mTencent;
	private int shareType = QQShare.SHARE_TO_QQ_TYPE_DEFAULT;
	private int mExtarFlag = 0x00;
	private Context context;
	
	public QQHelper(Context context){
		
		this.context = context;
		mTencent = Tencent.createInstance(VenterConstants.QQ_App_ID,context);
	}
	
	public void shareToQQ(Activity activity,String title,String desc,
			String url,int type){
		final Bundle params = new Bundle();
		params.putString(QQShare.SHARE_TO_QQ_TITLE, title);
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,url);
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, desc);
        mTencent.shareToQQ(activity, params, qqShareListener);
	}
	
	IUiListener qqShareListener = new IUiListener() {
        @Override
        public void onCancel() {
            if (shareType != QQShare.SHARE_TO_QQ_TYPE_IMAGE) {
                //Util.toastMessage(context, "onCancel: ");
            }
        }
        @Override
        public void onComplete(Object response) {
            // TODO Auto-generated method stub
          //  Util.toastMessage(QQShareActivity.this, "onComplete: " + response.toString());
        }
        @Override
        public void onError(UiError e) {
            // TODO Auto-generated method stub
           // Util.toastMessage(QQShareActivity.this, "onError: " + e.errorMessage, "e");
        }
    };
}
