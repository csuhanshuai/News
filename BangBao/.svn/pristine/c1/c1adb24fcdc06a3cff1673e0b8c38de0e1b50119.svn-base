package net.bangbao.network;

import net.bangbao.base.BaseApiClient;

public class CommonHttpClient extends BaseApiClient{

	
	public <T> void  getProDetail(int id,Object tag,Class<T> clazz,final CallBack<T> callBack){
		maps.clear();
		maps.put("cmd", 1105);
		maps.put("id", id);
		sendGsonRequest(maps,tag,clazz,callBack);
	}
	
	public <T> void resetPass(String name,int pro_id,String prob_ans,String new_pwd
			,Object tag,Class<T> clazz,final CallBack<T> callBack){
		maps.put("cmd", 1005);
		maps.put("name", name);
		maps.put("prob_id", pro_id);
		maps.put("prob_ans", prob_ans);
		maps.put("new_pwd", new_pwd);
		sendGsonRequest(maps,tag,clazz,callBack);
	}
}
