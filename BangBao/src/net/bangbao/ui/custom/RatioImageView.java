package net.bangbao.ui.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class RatioImageView extends ImageView {

	private float ratio = 1f;

	public RatioImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public RatioImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public RatioImageView(Context context) {
		super(context);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		
		
		
	}
}
