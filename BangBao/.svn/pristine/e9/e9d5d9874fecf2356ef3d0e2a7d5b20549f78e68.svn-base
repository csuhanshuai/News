package net.bangbao.dao;

import android.util.Log;
import io.rong.imlib.RongIMClient.AddToBlackCallback;
import io.rong.imlib.RongIMClient.RemoveFromBlacklistCallback;
import net.bangbao.AppConfig;
import net.bangbao.UserConfig;
import net.bangbao.base.BaseApi;
import net.bangbao.base.BaseApiClient;
import net.bangbao.network.AgentApiClient;
import net.bangbao.network.RongYunClient;

/**
 * @author mosl
 * 顾客的一些业务逻辑
 */
public class CustomerHandler {
	
	private UserConfig mUserConfig = AppConfig.getUser();;
	private AgentApiClient agentClient = new AgentApiClient();
	
	/**
	 * @param agentId
	 * @param callback
	 * 客户关注顾问(同时要从融云黑名单中除去)
	 */
	public void addFocus(final int agentId,final BaseApiClient.CallBack<BaseApi> callback){
		
		agentClient.addAgentFriends(mUserConfig.getUserId(),
				mUserConfig.getUserToken(), agentId, this, BaseApi.class,
				new BaseApiClient.CallBack<BaseApi>(){

					@Override
					public void success(final BaseApi api) {
						
						callback.success(api);
						RongYunClient.getInstance().removeFromBlackList(String.valueOf(agentId),
								new RemoveFromBlacklistCallback(){

							@Override
							public void onError(ErrorCode arg0) {
								if(arg0 == ErrorCode.UNKNOWN){
									
								}
								Log.d("aa","error");
								callback.success(api);
							}

							@Override
							public void onSuccess() {
							}
							
						});
					}

					@Override
					public void fail(String error) {
						callback.fail("服务器端操作失败");
					}
			
		});
	}
	
	/**
	 * @param agentId
	 * @param callback
	 * 客户取消关注顾问(同时要加入融云黑名单中)
	 */
	public void cancelFocus(final int agentId,final BaseApiClient.CallBack<BaseApi> callback){
		agentClient.cancelAgentFriends(mUserConfig.getUserId(),
				mUserConfig.getUserToken(), agentId, this, BaseApi.class, new BaseApiClient.CallBack<BaseApi>(){

					@Override
					public void success(final BaseApi api) {
						callback.success(api);
						RongYunClient.getInstance().addToBlackList(String.valueOf(agentId),new AddToBlackCallback(){

							@Override
							public void onError(ErrorCode arg0) {
								callback.fail("融云操作失败");
							}

							@Override
							public void onSuccess() {
							}
							
						});
					}

					@Override
					public void fail(String error) {
						callback.fail("服务器端操作失败");
					}
			
		});
	}
	
}
