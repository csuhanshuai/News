package net.bangbao.ui.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * 目前只用于新闻滚动的矩形部分； 作用是：限制宽高比为7/16 宽度是填充父窗体（精确的值）——屏幕的宽 高度是需要计算的值
 * xml文件中，宽度使用match_parent,高度使用wrap_content
 * 
 * @author Spartacus26
 * 
 */
public class MyFrameLayoutRectangle3 extends FrameLayout {
	private float ratio = (7 / 16);// 宽高比

	public MyFrameLayoutRectangle3(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	}

	public MyFrameLayoutRectangle3(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyFrameLayoutRectangle3(Context context) {
		super(context);
	}

	public void setRatio(int ratio) {
		this.ratio = ratio;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		// int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);

		widthSize = widthSize - getPaddingLeft() - getPaddingRight();

		// int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);

		widthSize = widthSize - getPaddingLeft() - getPaddingRight();

		// TODO　宽度是精确值，如果宽高比相反，这里的"*"变为"/"
		heightSize = (int) (widthSize * ratio + 0.5f);

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
