package net.bangbao;

import net.bangbao.common.PreferenceUtil;


/**
 * @author mosl
 * 处理融云的配置的业务逻辑
 */
public class ReminderConfigHandler implements IReminder{
	
	
	/**
	 * @param hasVoice
	 * 设置是否应该接收声音
	 */
	public void setVoiceEnable(boolean hasVoice){
		
		PreferenceUtil.setHasVoice(AppInit.getContext(), hasVoice);
	}
	
	/**
	 * @param hasVirbor
	 * 设置是否应该接收时震动
	 */
	public void setVirborEnable(boolean hasVirbor){
		
		PreferenceUtil.setHasVirbor(AppInit.getContext(), hasVirbor);
	}
	
	
	/**
	 * @return
	 * 是否有声音
	 */
	public boolean isVoice(){
		
		return PreferenceUtil.hasVoice(AppInit.getContext());
	}
	
	/**
	 * @return
	 * 是否震动
	 */
	public boolean isVirbor(){
		
		return PreferenceUtil.hasVirbor(AppInit.getContext());
	}
}
