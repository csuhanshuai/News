package net.bangbao.widget;

import net.bangbao.R;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

/**
 * @author mosl
 * 完善资料弹出来的对话框
 */
public class ConsummateDialog extends DialogFragment {
	
	private int indictor = 1; // 1笑脸  2 哭脸
	private IConsummate mIconsummate;
	
	public interface IConsummate{
		public void consummate();
		public void cancel();
	}
	
	public ConsummateDialog() {
		super();
	}
	
	public ConsummateDialog(int indictor, IConsummate mIconsummate) {
		super();
		this.indictor = indictor;
		this.mIconsummate = mIconsummate;
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
		View v = inflater.inflate(R.layout.consummate_dialog, null);
		Button consummate = (Button)v.findViewById(R.id.consummate);
		Button cancel = (Button)v.findViewById(R.id.cancel);
		consummate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mIconsummate.consummate();
			}
		});
		cancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mIconsummate.cancel();
			}
		});
		return v;
	}
}
