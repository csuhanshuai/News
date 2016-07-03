package net.bangbao.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.View;
import android.webkit.WebView;
import android.widget.ScrollView;

public class ExpandScrollView extends ScrollView {
	
	public ExpandScrollView(Context context) {
		super(context);
	}
	public ExpandScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	//让webView不自动滚动到最后
	@Override    
    public void requestChildFocus(View child, View focused) {    
        if (focused instanceof WebView )    
           return;   
    super.requestChildFocus(child, focused);   
    }  

}
