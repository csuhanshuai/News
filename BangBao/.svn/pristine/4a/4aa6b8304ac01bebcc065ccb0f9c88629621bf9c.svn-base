package net.bangbao.utils;


import net.bangbao.oath.Constants;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader.ImageCache;

public class MyImageCache implements ImageCache {

	private LruCache<String, Bitmap> lruCache;

	public MyImageCache() {
		// 初始化SDCard缓存路径
		if (SDCardUtils.isUsable()) {
			SDCardUtils.initCacheDir(Constants.IMAGE_PATH);
		}

		// 图片缓存 一级 LruCache,二级SDCard
		int maxSize = 10 * 1024 * 1024;
		lruCache = new LruCache<String, Bitmap>(maxSize) {
			@Override
			protected int sizeOf(String key, Bitmap value) {
				int size = value.getRowBytes() * value.getHeight() ;// 单位M
				return size;
			}

			@Override
			protected void entryRemoved(boolean evicted, String key,
					Bitmap oldValue, Bitmap newValue) {
				// 将oldValue存入SDCard缓存中
				if (SDCardUtils.isUsable())// 存储到二级缓存
					SDCardUtils.saveImage(key, oldValue);
				super.entryRemoved(evicted, key, oldValue, newValue);
			}
		};
	}

	public void putBitmap(String url, Bitmap bitmap) {
		//存入缓存中
		lruCache.put(url, bitmap);
	}
	@Override
	public Bitmap getBitmap(String url) {
	    
		//从缓存中获取 图片
		Bitmap bitmap=lruCache.get(url);
		if(bitmap==null&&SDCardUtils.isUsable()){
			//从SDCard中获取
			bitmap=SDCardUtils.readImage(url);
			//Bitmap roundBitmap=BitmapUtils.toRoundCorner(bitmap);
			return bitmap;
		}
		return null;
	}
}
