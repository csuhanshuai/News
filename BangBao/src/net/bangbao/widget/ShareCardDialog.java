package net.bangbao.widget;

import net.bangbao.R;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

/**
 * @author mosl
 * 分享名片
 */
public class ShareCardDialog extends DialogFragment {

	private Bitmap bp;
	private Button mBtCancel;
	private Button mBtShare;
	private IOnClickShare mShareListener;
	
	/**回调 **/
	public interface IOnClickShare{
		public void share();
	}
	
	public ShareCardDialog(Bitmap bp,IOnClickShare share) {
		this.bp = bp;
		this.mShareListener = share;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); 
	    
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);  
		View v = inflater.inflate(R.layout.share_card_layout, null);
		mBtShare = (Button)v.findViewById(R.id.share_button);
		mBtCancel = (Button)v.findViewById(R.id.cancel_button);
		mBtShare.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				if(mShareListener != null){
					mShareListener.share();
				}
			}
		});
		mBtCancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
		ImageView image = (ImageView)v.findViewById(R.id.share_card);
		image.setImageBitmap(bp);
		return v;
	}
}
