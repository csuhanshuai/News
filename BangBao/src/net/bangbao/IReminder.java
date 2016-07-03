package net.bangbao;

import net.bangbao.common.PreferenceUtil;

/**
 * @author mosl
 * 设置消息提醒接口
 */
public interface IReminder {

	
	/**
	 * @param hasVoice
	 * 设置是否应该接收声音
	 */
	public void setVoiceEnable(boolean hasVoice);
	
	/**
	 * @param hasVirbor
	 * 设置是否应该接收时震动
	 */
	public void setVirborEnable(boolean hasVirbor);
	
	
	/**
	 * @return
	 * 是否有声音
	 */
	public boolean isVoice();
	
	/**
	 * @return
	 * 是否震动
	 */
	public boolean isVirbor();
	
}
