package net.bangbao.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;

public class FileUtil {
	
	public static File updateDir = null;
	public static File updateFile = null;
	/***********保存升级APK的目录***********/
	public static final String Bangbao = "BangBao";
	
	public static boolean isCreateFileSucess;

	/** 
	* 方法描述：createFile方法
	* @param   String app_name
	* @return 
	* @see FileUtil
	*/
	public static void createFile(String app_name) {
		
		if (android.os.Environment.MEDIA_MOUNTED.equals(android.os.Environment.getExternalStorageState())) {
			isCreateFileSucess = true;
			
			updateDir = new File(Environment.getExternalStorageDirectory()+ "/" + Bangbao +"/");
			updateFile = new File(updateDir + "/" + app_name + ".apk");

			if (!updateDir.exists()) {
				updateDir.mkdirs();
			}
			if (!updateFile.exists()) {
				try {
					updateFile.createNewFile();
				} catch (IOException e) {
					isCreateFileSucess = false;
					e.printStackTrace();
				}
			}

		}else{
			isCreateFileSucess = false;
		}
	}
	
	public static final String readFileFromAssets(Context context,String file){
		AssetManager assetManager = context.getAssets();
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		try{
			InputStream input = assetManager.open(file);
			
			byte[] buffer = new byte[1024];
			int length = 0;
			while ((length = input.read(buffer)) != -1) {
				output.write(buffer, 0, length);
			}
			output.close();
			input.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return output.toString();

	}
}
