package net.bangbao.network;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.ImageLoader.ImageContainer;

public class RequestManager {

	private RequestQueue mRequestQueue = null;
	private volatile static RequestManager instance = null;
	// 取运行内存阈值的1/3作为图片缓存
    private  int MEM_CACHE_SIZE;
    private ImageLoader mImageLoader ;
    
	private RequestManager() {

	}
	public void init(Context context) {
		this.mRequestQueue = Volley.newRequestQueue(context);
		MEM_CACHE_SIZE = 1024 * 1024 * ((ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass() / 3;
		mImageLoader = new ImageLoader(mRequestQueue, new BitmapLruCache(
                MEM_CACHE_SIZE));
	}

	public static RequestManager getInstance() {
		if (null == instance) {
			synchronized (RequestManager.class) {
				if (null == instance) {
					instance = new RequestManager();
				}
			}
		}
		return instance;
	}
	
	public void cancelAll(Object tag) {
		mRequestQueue.cancelAll(tag);
		}
	 public void loadImage(String requestUrl,final ImageView imageView){
		 
		 loadImage(requestUrl,new ImageLoader.ImageListener() {
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				
			}
			
			@Override
			public void onResponse(ImageContainer arg0, boolean arg1) {
				if(arg0 != null && arg0.getBitmap() != null)
				imageView.setImageBitmap(arg0.getBitmap());
			}
		});
	 }
	 
     public void loadImage(String requestUrl,final ImageView imageView,final Bitmap errorBitmap){
		 
		 loadImage(requestUrl,new ImageLoader.ImageListener() {
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				
			}
			
			@Override
			public void onResponse(ImageContainer arg0, boolean arg1) {
				if(arg0 != null && arg0.getBitmap() != null)
				imageView.setImageBitmap(arg0.getBitmap());
				else
					imageView.setImageBitmap(errorBitmap);
			}
		});
	 }
	 public  ImageLoader.ImageContainer loadImage(String requestUrl,
	            ImageLoader.ImageListener imageListener) {
	        return loadImage(requestUrl, imageListener, 0, 0);
	    }
	 public  ImageLoader.ImageContainer loadImage(String requestUrl,
	            ImageLoader.ImageListener imageListener, int maxWidth, int maxHeight) {
	        return mImageLoader.get(requestUrl, imageListener, maxWidth, maxHeight);
	    }
	 public void addRequest(Request request, Object tag) {
	        if (tag != null) {
	            request.setTag(tag);
	        }
	        mRequestQueue.add(request);
	    }
	
	 
	public RequestQueue getRequestQueue() {
		return this.mRequestQueue;
	}
	
	
}
