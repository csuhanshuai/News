package net.bangbao.network;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * Volley adapter for JSON requests that will be parsed into Java objects by
 * Gson. 将返回的json解析成object
 */
public class GsonRequest<T> extends Request<T> {

	private final Gson gson = new Gson();
	private final Class<T> clazz;
	private final Map<String, String> headers;
	private final Listener<T> listener;
	private Map maps;
	private String list_item;

  private JsonCacheCallBack mCacheCallBack;
 /**
     * @Description 获取json字符串的回调接口
     * @Author Leo
     * @Since 2015-3-19 上午9:38:14
     */
    public interface JsonCacheCallBack {
        void jsonCache(String jsonStr);
    }
	  public GsonRequest(JsonCacheCallBack cacheCallBack, int method, String url, Class<T> clazz,
            Map<String, String> headers, Listener<T> listener, ErrorListener errorListener, Map map) {
        super(method, url, errorListener);
        this.clazz = clazz;
        this.headers = headers;
        this.listener = listener;
        this.maps = map;
        this.mCacheCallBack = cacheCallBack;
    }
	/**
	 * Make a GET request and return a parsed object from JSON.
	 * 
	 * @param url
	 *            URL of the request to make
	 * @param clazz
	 *            Relevant class object, for Gson's reflection
	 * @param headers
	 *            Map of request headers
	 */
	public GsonRequest(int method, String url, Class<T> clazz,
			Map<String, String> headers, Listener<T> listener,
			ErrorListener errorListener, Map map) {
		super(method, url, errorListener);
		this.clazz = clazz;
		this.headers = headers;
		this.listener = listener;
		this.maps = map;
	}

	@Override
	public RetryPolicy getRetryPolicy() {
		RetryPolicy retryPolicy = new DefaultRetryPolicy(20 * 1000,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
		return retryPolicy;
	}

	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Charset", "UTF-8");
		// TODO null处为path内容，发布前修改
//		String spliceStr = EncryptionUtils.getSign("POST", null,
//				gson.toJson(maps));
//		Log.d("spliceStr", spliceStr);
//		try {
//			String signStr = EncryptionUtils.string2MD5(spliceStr);
//			Log.d("MD5", signStr);
//			headers.put("sign", signStr);
//		} catch (NoSuchAlgorithmException e) {
//			e.printStackTrace();
//		}

		return (headers != null) ? headers : super.getHeaders();
	}

	@Override
	public byte[] getBody() throws AuthFailureError {
		Log.d("json", gson.toJson(maps));
		return maps != null ? gson.toJson(maps).getBytes() : super.getBody();
	}

	@Override
	protected void deliverResponse(T response) {
		listener.onResponse(response);
	}

	@Override
	protected Response<T> parseNetworkResponse(NetworkResponse response) {
		try {
			String json = new String(response.data,
					HttpHeaderParser.parseCharset(response.headers));
			String utf = new String(json.getBytes("ISO-8859-1"), "UTF-8");
  if (mCacheCallBack != null)
                mCacheCallBack.jsonCache(utf);// 将获取到的json数据通过接口回调的方式返回给调用处.
			Log.d("utf", utf);
			return Response.success(gson.fromJson(utf, clazz),
					HttpHeaderParser.parseCacheHeaders(response));
		} catch (UnsupportedEncodingException e) {
			return Response.error(new ParseError(e));
		} catch (JsonSyntaxException e) {
			return Response.error(new ParseError(e));
		}
	}
}