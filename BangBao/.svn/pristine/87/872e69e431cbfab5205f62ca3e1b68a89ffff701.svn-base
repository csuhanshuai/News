package net.bangbao.ui.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * 目前只用于找保险的矩形部分； 作用是：限制宽高比为1:1.5；
 * 
 * @author Spartacus26
 * 
 */
public class MyFrameLayoutRectangle2 extends FrameLayout {
	private float ratio = 1.5f;// 宽高比

	public MyFrameLayoutRectangle2(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	}

	public MyFrameLayoutRectangle2(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyFrameLayoutRectangle2(Context context) {
		super(context);
	}

	public void setRatio(int ratio) {
		this.ratio = ratio;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);

		widthSize = widthSize - getPaddingLeft() - getPaddingRight();

		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);

		heightSize = heightSize - getPaddingTop() - getPaddingBottom();

		// 高度是精确值
		widthSize = (int) (heightSize / ratio + 0.5f);

		widthSize = widthSize + getPaddingLeft() + getPaddingRight();
		heightSize = heightSize + getPaddingTop() + getPaddingBottom();

		widthMeasureSpec = MeasureSpec.makeMeasureSpec(widthSize,
				MeasureSpec.EXACTLY);
		heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize,
				MeasureSpec.EXACTLY);

		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		// setMeasuredDimension(measuredWidth, measuredHeight);//两者等价
	}

}
