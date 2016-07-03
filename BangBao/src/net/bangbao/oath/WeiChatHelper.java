package net.bangbao.oath;


import net.bangbao.macro.VenterConstants;
import net.bangbao.utils.Utils;
import android.content.Context;
import android.graphics.Bitmap;

import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXTextObject;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WeiChatHelper {

	private IWXAPI api;
	
	public WeiChatHelper(Context context) {
		api = WXAPIFactory.createWXAPI(context, 
				VenterConstants.WeiX_App_ID);
	}
	
	public void sendText(String text,int type){ //0 微信  1 朋友圈
		// 初始化一个WXTextObject对象
		WXTextObject textObj = new WXTextObject();
		textObj.text = text;

		// 用WXTextObject对象初始化一个WXMediaMessage对象
		WXMediaMessage msg = new WXMediaMessage();
		msg.mediaObject = textObj;
		// 发送文本类型的消息时，title字段不起作用
		// msg.title = "Will be ignored";
		msg.description = text;

		// 构造一个Req
		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = buildTransaction("text"); // transaction字段用于唯一标识一个请求
		req.message = msg;
		req.scene = type == 0 ?SendMessageToWX.Req.WXSceneSession:SendMessageToWX.Req.WXSceneTimeline;
		
		// 调用api接口发送数据到微信
		api.sendReq(req);
	}
	
	public void sendMedia(Context context,String title,String desc,String url,Bitmap bitmap,int type){
		
		WXWebpageObject webpage = new WXWebpageObject();  
	    webpage.webpageUrl = url;  
	    WXMediaMessage msg = new WXMediaMessage(webpage);  
	    msg.title = title;  
	    msg.description = desc;
	    if(bitmap != null)
	    {
	    	Bitmap newbitmap = Utils.createScaleBitmap(bitmap, bitmap.getWidth() / 2, bitmap.getHeight() / 2);
	    	msg.setThumbImage(newbitmap);
	    }
	    SendMessageToWX.Req req = new SendMessageToWX.Req();  
	    req.transaction = String.valueOf(System.currentTimeMillis());  
	    req.message = msg;  
	    req.scene = type ==0?SendMessageToWX.Req.WXSceneSession:SendMessageToWX.Req.WXSceneTimeline;  
	    api.sendReq(req);  
	}
	
	private String buildTransaction(final String type) {
		return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
	}
}
