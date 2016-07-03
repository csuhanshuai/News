package net.bangbao.base;

import net.bangbao.network.RequestManager;

import com.android.volley.Request;

import android.os.Bundle;
import android.support.v4.app.Fragment;
 
/*   author:mosl
 *   function: base Fragment
 *   date:2015.1.26
 */

public class BaseFragment extends Fragment {

	    @Override
	    public void onStop() {
	        super.onStop();
	        RequestManager.getInstance().cancelAll(this);
	    }

	    protected void executeRequest(Request request) {
	        RequestManager.getInstance().addRequest(request, this);
	    }
	    
	    public void handleSomething(){
	    	
	    }
}
