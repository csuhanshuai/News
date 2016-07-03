package net.bangbao.base;

import java.util.HashMap;
import java.util.Map;

import net.bangbao.macro.HttpMacro;
import net.bangbao.network.GsonRequest;
import net.bangbao.network.GsonRequest.JsonCacheCallBack;
import net.bangbao.network.RequestManager;
import android.util.Log;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

public class BaseApiClient{

	protected Map<String, Object> maps = new HashMap<String, Object>();

	public static class Page {

		public int id_index;
		public int page_index;
		public int page_size = 10;
		public int page_total;
	}

	// 请求的回调接口
	public static interface CallBack<T> {

		public void success(T api);

		public void fail(String error);
	}

	// 发送gson请求的总方法
	protected <T> void sendGsonRequest(JsonCacheCallBack  cacheCallBack,Map<String, Object> maps, Object tag, Class<T> clazz,
			final CallBack<T> callback) {

		GsonRequest<T> gsonRequest = new GsonRequest<T>(cacheCallBack,Method.POST, HttpMacro.HOST,
				clazz, null, new Listener<T>() {

					@Override
					public void onResponse(T arg0) {
						callback.success(arg0);
					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						Log.d("error", arg0.toString());
						callback.fail(arg0.toString());
					}
				}, maps);
		RequestManager.getInstance().addRequest(gsonRequest, tag);
	}
	// 发送gson请求的总方法
	protected <T> void sendGsonRequest(Map<String, Object> maps, Object tag, Class<T> clazz,
			final CallBack<T> callback) {

		GsonRequest<T> gsonRequest = new GsonRequest<T>(Method.POST, HttpMacro.HOST,
				clazz, null, new Listener<T>() {

					@Override
					public void onResponse(T arg0) {
						callback.success(arg0);
					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						Log.d("error", arg0.toString());
						callback.fail(arg0.toString());
					}
				}, maps);
		RequestManager.getInstance().addRequest(gsonRequest, tag);
	}

	public static void httpGet(String URL,Object tag,final CallBack<String> callback){
		StringRequest request = new StringRequest(URL,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("请求结果:" + response);
                callback.success(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("请求错误:" + error.toString());
            }
        });
		RequestManager.getInstance().addRequest(request, tag);
	}

	/**
	 * 发送微信请求的方法
	 * 
	 * @param URL
	 * @param tag
	 * @param clazz
	 * @param callback
	 * @author Spartacus26
	 */
	protected <T> void getWeixinMsg(String URL, Object tag, Class<T> clazz,
			final CallBack<T> callback) {

		GsonRequest<T> gsonRequest = new GsonRequest<T>(Method.GET, URL, clazz,
				null, new Listener<T>() {

					@Override
					public void onResponse(T arg0) {
						callback.success(arg0);
					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						Log.d("error", arg0.toString());
						callback.fail(arg0.toString());
					}
				}, maps);
		RequestManager.getInstance().addRequest(gsonRequest, tag);

	}

}
