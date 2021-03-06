package net.bangbao.common;

import com.google.gson.Gson;

import net.bangbao.UserConfig;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.Preference;
import android.util.Log;

public class PreferenceUtil {
	
	public static final String FistStart = "bangbao_First_Start";
	public static final String IsLogin = "bangbao_Is_Login";
	public static final String PreferenceName = "bangbao_prefernce";
	public static final String RongYunToken = "RongYunToken";
	public static final String UserConfigToken = "UserConfigToken";
	public static final String mHasVoice = "HASVOICE";
	public static final String mHasVirbor = "HASVIRBOR";
	
	private static Gson gson = new Gson();
	public static final boolean isLogin(Context context){
		
		SharedPreferences preference = context.getSharedPreferences(PreferenceName,
				Activity.MODE_PRIVATE);
		return preference.getBoolean(IsLogin, false);
	}
	
	public static final boolean isFirstStart(Context context){
		SharedPreferences preference = context.getSharedPreferences(PreferenceName,
				Activity.MODE_PRIVATE);
		return preference.getBoolean(FistStart, false);
	}
	
	public static final void login(Context context){
		SharedPreferences preference = context.getSharedPreferences(PreferenceName,
				Activity.MODE_PRIVATE);
		Editor editor = preference.edit();
		editor.putBoolean(IsLogin, true);
		editor.commit();
	}
	public static final void loginout(Context context){
		SharedPreferences preference = context.getSharedPreferences(PreferenceName,
				Activity.MODE_PRIVATE);
		Editor editor = preference.edit();
		editor.putBoolean(IsLogin, false);
		editor.commit();
	}
	public static final void startEd(Context context){
		SharedPreferences preference = context.getSharedPreferences(PreferenceName,
				Activity.MODE_PRIVATE);
		Editor editor = preference.edit();
		editor.putBoolean(FistStart, true);
		editor.commit();
	}
	
	public static final void saveRonYunToken(Context context,String rongYunToken){
		SharedPreferences preference = context.getSharedPreferences(PreferenceName,
				Activity.MODE_PRIVATE);
		Editor editor = preference.edit();
		editor.putString(RongYunToken, rongYunToken);
		editor.commit();
	}
	
	public static final String getRongYunToken(Context context){
		SharedPreferences preference = context.getSharedPreferences(PreferenceName,
				Activity.MODE_PRIVATE);
		return preference.getString(RongYunToken,null);
	}
	
	public static final void putUserConfig(Context context,UserConfig user){
		SharedPreferences preference = context.getSharedPreferences(PreferenceName,
				Activity.MODE_PRIVATE);
		Editor editor = preference.edit();
		Log.d("aaa",gson.toJson(user));
		editor.putString(UserConfigToken,gson.toJson(user));
		editor.commit();
	}
	
	public static final UserConfig getUserConfig(Context context){
		SharedPreferences preference = context.getSharedPreferences(PreferenceName,
				Activity.MODE_PRIVATE);
		return gson.fromJson(preference.getString(UserConfigToken,null),UserConfig.class);
	}
	
	public static final void setHasVoice(Context context,boolean hasVoice){
		SharedPreferences preference = context.getSharedPreferences(PreferenceName,
				Activity.MODE_PRIVATE);
		Editor editor = preference.edit();
		editor.putBoolean(mHasVoice, hasVoice);
		editor.commit();
	}
	
	public static final boolean hasVoice(Context context){
		
		SharedPreferences preference = context.getSharedPreferences(PreferenceName,
				Activity.MODE_PRIVATE);
		return preference.getBoolean(mHasVoice, false);
	}
	
	public static final void setHasVirbor(Context context,boolean hasVirbor){
		SharedPreferences preference = context.getSharedPreferences(PreferenceName,
				Activity.MODE_PRIVATE);
		Editor editor = preference.edit();
		editor.putBoolean(mHasVirbor, hasVirbor);
		editor.commit();
	}
	
	public static final boolean hasVirbor(Context context){
		
		SharedPreferences preference = context.getSharedPreferences(PreferenceName,
				Activity.MODE_PRIVATE);
		return preference.getBoolean(mHasVirbor,true);
	}
}
