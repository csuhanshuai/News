package net.bangbao.ui.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
/**
 * 目前只用于找保险的圆形部分；
 * 作用是：限制宽高比为1:1；
 * 
 * @author Spartacus26
 *
 */
public class MyFrameLayoutRectangle extends FrameLayout {
	private float ratio = 1f;//宽高比

	public MyFrameLayoutRectangle(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public MyFrameLayoutRectangle(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyFrameLayoutRectangle(Context context) {
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

		int side = 0;// 正方形的边长
		side = Math.min(heightSize, widthSize);

		heightSize = side;
		widthSize = side;

		widthSize = widthSize + getPaddingLeft() + getPaddingRight();
		heightSize = heightSize + getPaddingTop() + getPaddingBottom();

		widthMeasureSpec = MeasureSpec.makeMeasureSpec(widthSize,
				MeasureSpec.EXACTLY);
		heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize,
				MeasureSpec.EXACTLY);
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

}
