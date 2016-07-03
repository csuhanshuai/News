package net.bangbao.common;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.WindowManager;

public class DeviceUtil {
	
	public static final float Width = 640;
	public static final float Height = 1136;
	
	//按照所给的图标进行缩放
	public static Bitmap bitmapScale(Context context,int imageId)
	{
		int screen_witdh = DeviceUtil.getScreenWidth(context);
		int screen_height = DeviceUtil.getScreenHeight(context);
		
		Resources rs = context.getResources();
		TypedValue value = new TypedValue();
		rs.openRawResource(imageId, value);
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inTargetDensity = value.density;
		Bitmap bitmap = BitmapFactory.decodeResource(rs, imageId,opts);
		
		double dex = Math.sqrt((double)(Width * Width + Height * Height));
		double px = Math.sqrt((double)(screen_witdh * screen_witdh + screen_height * screen_height));
		double xx = dex / px;
		float fx = DeviceUtil.getScreenWidth(context) / Width;
		float fy = DeviceUtil.getScreenHeight(context) / Height;
		
		float scaleX = fx * bitmap.getWidth();
		float scaleY = fy * bitmap.getHeight();
		
		Matrix matrix = new Matrix();
		matrix.postScale((float)xx,(float)xx);
		Bitmap newBit = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
		
		return newBit;
	}
	

   public Bitmap decodeResource(Resources resources, int id) {

    TypedValue value = new TypedValue();
    resources.openRawResource(id, value);
    BitmapFactory.Options opts = new BitmapFactory.Options();
    return BitmapFactory.decodeResource(resources, id, opts);
   }
   
	public static final int getScreenWidth(Context context)
	{
		WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics metric = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(metric);
		int width = metric.widthPixels;
		return width;
	}
	
	public static final int getScreenHeight(Context context)
	{
		WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics metric = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(metric);
		int height = metric.heightPixels;
		return height;
	}
	
	// to dip
    public static float getRawSize(Context context,int unit, float value) { 
        Resources res = context.getResources(); 
        return TypedValue.applyDimension(unit, value, res.getDisplayMetrics()); 
    } 
 
}
