package net.bangbao.common;

import java.io.InputStream;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

public class ImageUtil {
	
	public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight)
	{
		final int height = options.outHeight;
	    final int width = options.outWidth;
	    int inSampleSize = 1;

	    if (height > reqHeight || width > reqWidth) {

	        final int halfHeight = height / 2;
	        final int halfWidth = width / 2;

	        while ((halfHeight / inSampleSize) > reqHeight
	                && (halfWidth / inSampleSize) > reqWidth) {
	            inSampleSize *= 2;
	        }
	    }

	    return inSampleSize;
	}
	
	//����Դ�ж�ȡbitmap
	public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
	        int reqWidth, int reqHeight) {

	    final BitmapFactory.Options options = new BitmapFactory.Options();
	    options.inJustDecodeBounds = true;
	    BitmapFactory.decodeResource(res, resId, options);
	    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

	    options.inJustDecodeBounds = false;
	    return BitmapFactory.decodeResource(res, resId, options);
	}
	
	//���������ж�ȡbitmap , �����Ҫ�ĳ����
	public static Bitmap decodeBitmapFrominputStream(InputStream is,
	        int reqWidth, int reqHeight) {

	    final BitmapFactory.Options options = new BitmapFactory.Options();
	    options.inJustDecodeBounds = true;
	    BitmapFactory.decodeStream(is, null, options);
	    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

	    options.inJustDecodeBounds = false;
	    return BitmapFactory.decodeStream(is, null, options);
	}
	
	public static Bitmap decodeBitmapFrominputStream(InputStream is)
	{
		return BitmapFactory.decodeStream(is);
	}
	
	public static Bitmap scaleToWidth(Bitmap bitmap,float width)
	{
		float scale = width / bitmap.getWidth();
		Matrix matrix = new Matrix();
		matrix.postScale(scale,scale); //���Ϳ�Ŵ���С�ı���
		Bitmap newbitmap = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
		//bitmap.recycle();
	    return newbitmap;
	}
}
