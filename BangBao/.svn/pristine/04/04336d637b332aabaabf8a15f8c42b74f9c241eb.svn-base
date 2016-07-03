package net.bangbao.base;

import net.bangbao.AppManager;
import net.bangbao.network.RequestManager;

import com.android.volley.Request;

import android.app.PendingIntent.OnFinished;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

/*   author:mosl
 *   function:base Activity
 *   date:2015.1.26
 */
public class BaseActivity extends FragmentActivity {
	
	    @Override
	    protected void onCreate(Bundle arg0) {
	     super.onCreate(arg0);
	      AppManager.getAppManager().addActivity(this);
	     }
	     @Override
	    public void onStop() {
	        super.onStop();
	        RequestManager.getInstance().cancelAll(this);
	    }
	     
	    protected void executeRequest(Request request) {
	        RequestManager.getInstance().addRequest(request, this);
	    }
	    
	    protected void onBack(View v){
	    	onBackPressed();
	    }
}
