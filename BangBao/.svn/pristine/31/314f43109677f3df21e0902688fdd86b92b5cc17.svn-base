package net.bangbao.network;

import net.bangbao.base.BaseApiClient;


/**
 * @author mosl
 * 代理人客户端
 */
public class AgentApiClient extends BaseApiClient{

	
    /**
     * @param user_id
     * @param token
     * @param dest_user_id
     * @param tag
     * @param clazz
     * @param callBack
     */
    public  <T> void addAgentFriends(int user_id,String token,int dest_user_id,Object tag,Class<T> clazz,
    		final CallBack<T> callBack){
    	maps.clear();
		maps.put("cmd", 1015);
		maps.put("user_id", user_id);
		maps.put("token", token);
		maps.put("dest_user_id", dest_user_id);
		sendGsonRequest(maps,tag,clazz,callBack);
    }
    
    
    /** 获取我的客户
     * @param user_id
     * @param token
     * @param id_index
     * @param page
     * @param tag
     * @param clazz
     * @param callBack
     */
    public <T> void getMyCustomer(int user_id,String token,int id_index,Page page,Object tag,Class<T> clazz
    		,final CallBack<T> callBack){
    	maps.clear();
    	maps.put("cmd", 1010);
    	maps.put("user_id",user_id);
    	maps.put("token", token);
    	maps.put("id_index", id_index);
    	maps.put("page_index", page.page_index);
    	maps.put("page_size", page.page_size);
    	maps.put("page_total", 222);
    	sendGsonRequest(maps, tag, clazz, callBack);
    	
    }
    
    
    /** 获取代理人基本信息
     * @param dest_user_id
     * @param tag
     * @param clazz
     * @param callback
     */
    public <T> void getConsultDetail(int dest_user_id,Object tag,Class<T> clazz,final CallBack<T> callback){
    	maps.clear();
    	maps.put("cmd",1013);
    	maps.put("dest_user_id", dest_user_id);
    	sendGsonRequest(maps, tag, clazz, callback);
    }
    
    
    /** 取消关注
     * @param user_id
     * @param token
     * @param dest_user_id
     * @param tag
     * @param clazz
     * @param callBack
     */
    public  <T> void cancelAgentFriends(int user_id,String token,int dest_user_id,Object tag,Class<T> clazz,
    		final CallBack<T> callBack){
    	maps.clear();
		maps.put("cmd", 1016);
		maps.put("user_id", user_id);
		maps.put("token", token);
		maps.put("dest_user_id", dest_user_id);
		sendGsonRequest(maps,tag,clazz,callBack);
    }
}
