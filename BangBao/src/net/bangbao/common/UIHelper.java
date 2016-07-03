package net.bangbao.common;

import net.bangbao.AppInit;
import android.widget.Toast;

public class UIHelper {
	
	
	
	public static final void showToastMessage(String message){
		Toast.makeText(AppInit.getContext(), 
				message, Toast.LENGTH_SHORT).show();
	}
}
