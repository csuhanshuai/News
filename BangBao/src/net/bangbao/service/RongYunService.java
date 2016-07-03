package net.bangbao.service;

import io.rong.imlib.RongIMClient.Message;
import io.rong.imlib.RongIMClient.OnReceiveMessageListener;
import io.rong.message.TextMessage;
import net.bangbao.AppConfig;
import net.bangbao.ReminderConfigHandler;
import net.bangbao.ui.MainActivity;
import net.bangbao.utils.Utils;
import android.app.Service;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;

/**
 * @author mosl
 * 启动一个Service去监听融云发送的消息
 */
public class RongYunService extends Service implements OnReceiveMessageListener{

	public static final String TAG = RongYunService.class.getName();
	private ReminderConfigHandler rongYunHandler = new ReminderConfigHandler();
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		try{
			if(AppConfig.IMClient != null)
			AppConfig.IMClient.setOnReceiveMessageListener(this);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
	@Override
	public void onReceived(Message msg, int count) {
		
		Intent intent = new Intent();
		intent.setAction(MainActivity.MSGACTION);
		sendBroadcast(intent);
		
		//将融云发送过来的消息存储起来
		AppConfig.msgContents.add(msg);
		
		if(rongYunHandler.isVirbor())
		   Utils.Vibrate(100);
		
		//发送给聊天
		Intent intentChat = new Intent();
		intentChat.setAction("net.bangbao.chat_to_consult");
		TextMessage textMessage = (TextMessage)msg.getContent();
		intentChat.putExtra("chat",textMessage.getContent());
		intentChat.putExtra("sender_id", msg.getSenderUserId());
		sendBroadcast(intentChat);
		
		
	}
	
}
