package net.bangbao.widget;

import net.bangbao.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;

public class CustomSegment extends LinearLayout implements View.OnClickListener {

	private int oneDP;
	private Resources resources;
	private int mTintColor;
	private int mCheckedTextColor = Color.WHITE;

	private OnItemClickListener onItemClickListener;
	private RadioButton radio1, radio2, radio3, radio4;

	public interface OnItemClickListener {
		/* 点击某项 */
		public void onItemClick(int checkId);
	}

	public void setOnItemClickListener(OnItemClickListener listener) {
		this.onItemClickListener = listener;
	}

	public void clearChecked() {

		radio1.setChecked(false);
		radio2.setChecked(false);
		radio3.setChecked(false);
		radio4.setChecked(false);

	}

	public CustomSegment(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		resources = getResources();
		mTintColor = resources.getColor(R.color.radio_button_selected_color);
		oneDP = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1,
				resources.getDisplayMetrics());
	}

	public CustomSegment(Context context, AttributeSet attrs) {
		super(context, attrs);
		resources = getResources();
		mTintColor = resources.getColor(R.color.radio_button_selected_color);
		oneDP = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1,
				resources.getDisplayMetrics());
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);

		radio1 = (RadioButton) getChildAt(0);
		radio2 = (RadioButton) getChildAt(1);
		radio3 = (RadioButton) getChildAt(2);
		radio4 = (RadioButton) getChildAt(3);

		radio1.setOnClickListener(this);
		radio2.setOnClickListener(this);
		radio3.setOnClickListener(this);
		radio4.setOnClickListener(this);

	}

	public CustomSegment(Context context) {
		super(context);
		resources = getResources();
		mTintColor = resources.getColor(R.color.radio_button_selected_color);
		oneDP = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1,
				resources.getDisplayMetrics());
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		// Use holo light for default
		updateBackground();
	}

	public void setTintColor(int tintColor) {
		mTintColor = tintColor;
		updateBackground();
	}

	public void setTintColor(int tintColor, int checkedTextColor) {
		mTintColor = tintColor;
		mCheckedTextColor = checkedTextColor;
		updateBackground();
	}

	public void updateBackground() {
		int count = super.getChildCount();
		if (count > 1) {
			View child = getChildAt(0);
			LayoutParams initParams = (LayoutParams) child.getLayoutParams();
			LayoutParams params = new LayoutParams(initParams.width,
					initParams.height, initParams.weight);
			// Check orientation for proper margins
			if (getOrientation() == LinearLayout.HORIZONTAL) {
				params.setMargins(0, 0, -oneDP, 0);
			} else {
				params.setMargins(0, 0, 0, -oneDP);
			}
			child.setLayoutParams(params);
			// Check orientation for proper layout
			if (getOrientation() == LinearLayout.HORIZONTAL) {
				updateBackground(getChildAt(0), R.drawable.radio_checked_left,
						R.drawable.radio_unchecked_left);
			} else {
				updateBackground(getChildAt(0), R.drawable.radio_checked_top,
						R.drawable.radio_unchecked_top);
			}
			for (int i = 1; i < count - 1; i++) {
				// Check orientation for proper layout
				if (getOrientation() == LinearLayout.HORIZONTAL) {
					updateBackground(getChildAt(i),
							R.drawable.radio_checked_middle,
							R.drawable.radio_unchecked_middle);
				} else {
					// Middle radiobutton when checked is the same as
					// horizontal.
					updateBackground(getChildAt(i),
							R.drawable.radio_checked_middle,
							R.drawable.radio_unchecked_middle_vertical);
				}
				View child2 = getChildAt(i);
				initParams = (LayoutParams) child2.getLayoutParams();
				params = new LayoutParams(initParams.width, initParams.height,
						initParams.weight);
				// Check orientation for proper margins
				if (getOrientation() == LinearLayout.HORIZONTAL) {
					params.setMargins(0, 0, -oneDP, 0);
				} else {
					params.setMargins(0, 0, 0, -oneDP);
				}
				child2.setLayoutParams(params);
			}
			// Check orientation for proper layout
			if (getOrientation() == LinearLayout.HORIZONTAL) {
				updateBackground(getChildAt(count - 1),
						R.drawable.radio_checked_right,
						R.drawable.radio_unchecked_right);
			} else {
				updateBackground(getChildAt(count - 1),
						R.drawable.radio_checked_bottom,
						R.drawable.radio_unchecked_bottom);
			}
		} else if (count == 1) {
			updateBackground(getChildAt(0), R.drawable.radio_checked_default,
					R.drawable.radio_unchecked_default);
		}
	}

	@SuppressLint("NewApi")
	private void updateBackground(View view, int checked, int unchecked) {
		// Set text color
		ColorStateList colorStateList = new ColorStateList(
				new int[][] {
						{ android.R.attr.state_pressed },
						{ -android.R.attr.state_pressed,
								-android.R.attr.state_checked },
						{ -android.R.attr.state_pressed,
								android.R.attr.state_checked } }, new int[] {
						Color.GRAY, mTintColor, mCheckedTextColor });
		((Button) view).setTextColor(colorStateList);

		// Redraw with tint color
		Drawable checkedDrawable = resources.getDrawable(checked).mutate();
		Drawable uncheckedDrawable = resources.getDrawable(unchecked).mutate();
		((GradientDrawable) checkedDrawable).setColor(mTintColor);
		((GradientDrawable) uncheckedDrawable).setStroke(oneDP, mTintColor);

		// Create drawable
		StateListDrawable stateListDrawable = new StateListDrawable();
		stateListDrawable.addState(new int[] { -android.R.attr.state_checked },
				uncheckedDrawable);
		stateListDrawable.addState(new int[] { android.R.attr.state_checked },
				checkedDrawable);

		// Set button background
		if (Build.VERSION.SDK_INT >= 16) {
			view.setBackground(stateListDrawable);
		} else {
			view.setBackgroundDrawable(stateListDrawable);
		}
	}

	@Override
	public void onClick(View v) {

		if (v == radio1) { // 关于保险
			if (onItemClickListener != null)
				onItemClickListener.onItemClick(1);
			radio2.setChecked(false);
			radio3.setChecked(false);
			radio4.setChecked(false);

		} else if (v == radio2) {
			if (onItemClickListener != null)
				onItemClickListener.onItemClick(2);
			radio1.setChecked(false);
			radio3.setChecked(false);
			radio4.setChecked(false);

		} else if (v == radio3) {
			if (onItemClickListener != null)
				onItemClickListener.onItemClick(3);
			radio1.setChecked(false);
			radio2.setChecked(false);
			radio4.setChecked(false);
		} else if (v == radio4) {
			if (onItemClickListener != null)
				onItemClickListener.onItemClick(4);
			radio1.setChecked(false);
			radio2.setChecked(false);
			radio3.setChecked(false);

		}
	}

}
