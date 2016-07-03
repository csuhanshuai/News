package net.bangbao.network;

import android.content.Intent;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.RongIMClient.AddToBlackCallback;
import io.rong.imlib.RongIMClient.ConnectCallback;
import io.rong.imlib.RongIMClient.ConversationType;
import io.rong.imlib.RongIMClient.RemoveFromBlacklistCallback;
import io.rong.imlib.RongIMClient.SendMessageCallback;
import io.rong.message.TextMessage;
import net.bangbao.AppConfig;
import net.bangbao.AppInit;
import net.bangbao.service.RongYunService;


/**
 * @author mosl
 *  封装一下对融云的操作
 *  单例模式
 */
public class RongYunClient {
	
   private static RongYunClient single=null;  
	 //静态工厂方法   
   public synchronized  static RongYunClient getInstance() {  
		 if (single == null) {
			 single = new RongYunClient();  
			 }   
		 return single; 
		 }  
	private RongYunClient(){
		
	}
	
	/**
	 * @param type
	 * @param userId
	 * @param text
	 * @param callBack
	 * 发送文本消息
	 */
	public void sendTextMessage(ConversationType type,
			String userId,String text,SendMessageCallback callBack){
		TextMessage textMessage = new TextMessage(text);
		AppConfig.IMClient.sendMessage(type,userId, textMessage, callBack);
	}

	/**
	 * @param userId
	 * @param callback
	 * 将用户加入黑名单
	 */
	public void addToBlackList(String userId,AddToBlackCallback callback){
		if( AppConfig.IMClient != null)
		AppConfig.IMClient.addToBlacklist(userId, callback);
	}
	
	/**
	 * @param userId
	 * @param callback
	 * 将用户从黑名单移除
	 */
	public void removeFromBlackList(String userId,RemoveFromBlacklistCallback callback){
		if( AppConfig.IMClient != null)
		AppConfig.IMClient.removeFromBlacklist(userId, callback);
	}
	
	/**
	 *  登出融云，不接收消息
	 */
	public void loginout(){
		if(AppConfig.IMClient != null){
			Intent intent = new Intent(AppInit.getContext(),RongYunService.class);
			AppInit.getContext().stopService(intent);
			AppConfig.IMClient.disconnect(false);
			AppConfig.IMClient = null;
		}
	}
	
	/**
	 * @param callback
	 * 登陆融云服务器
	 */
	public void loginRongYun(ConnectCallback callback){
		try {

			AppConfig.IMClient = RongIMClient.connect(
					AppConfig.getRongYunToken(), callback);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
