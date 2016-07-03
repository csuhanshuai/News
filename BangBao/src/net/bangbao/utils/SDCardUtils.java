package net.bangbao.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import net.bangbao.oath.Constants;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

public class SDCardUtils {

	/**
	 *判断当前的SDcard是否可用
	 * @return
	 */
	public static boolean isUsable() {
		if(Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)){
			return true;
		}
		return false;
	}
	
	/**
	 * 将Http请求地址中包含文件名的数据截取出来
	 * @param url
	 * @return
	 */
	public static String getFileName(String url){
		String fileName=url.substring(url.lastIndexOf("/")+1);
		return fileName;
	}

	/**
	 * 如果SDCard可用那就创建路径
	 */
	public static void initCacheDir(String path) {
		File file = new File(path);
		if (!file.exists()) 
			file.mkdirs();
	}
	/**
	 * 保存图片的方法
	 * @param 
	 * @return
	 */
	public static boolean saveImage(String url,Bitmap bitmap){
		if(isUsable()){
			File file=new File(Constants.IMAGE_PATH);
			if(!file.exists()){
				file.mkdirs();			
			}
			
			File imageFile=new File(Constants.IMAGE_PATH,getFileName(url));
			try{
				if(!imageFile.exists()){
					imageFile.createNewFile();
				}				
				bitmap.compress(CompressFormat.JPEG,50, new FileOutputStream(imageFile));
				return true;
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	/**
	 * 读取指定的图片方法
	 * @param url
	 * @return
	 */
	public static Bitmap readImage(String url){
		try{
			if(!isUsable()){
				new RuntimeException("SDCard is not usable!");
			}
			
			File imageFile=new File(Constants.IMAGE_PATH,getFileName(url));
			if(!imageFile.exists())
				return null;
			Bitmap bitmap=BitmapFactory.decodeFile(imageFile.getAbsolutePath());
			
			return bitmap;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	   /**
     * 在SD卡上创建文件
     * 
     * @param dir 目录路径
     * @param fileName
     * @return
     * @throws IOException
     */
    public static File createFileInSDCard(String dir, String fileName) throws IOException {
        File file = new File(dir + File.separator + fileName);
        file.createNewFile();
        return file;
    }
	
	  /***
     * 获取SD卡的剩余容量,单位是Byte
     * 
     * @return
     */
    public static  long getSDAvailableSize() {
        if (isUsable()) {
            // 取得sdcard文件路径
            File pathFile = android.os.Environment.getExternalStorageDirectory();
            android.os.StatFs statfs = new android.os.StatFs(pathFile.getPath());
            // 获取SDCard上每个block的SIZE
            long nBlocSize = statfs.getBlockSize();
            // 获取可供程序使用的Block的数量
            long nAvailaBlock = statfs.getAvailableBlocks();
            // 计算 SDCard 剩余大小Byte
            long nSDFreeSize = nAvailaBlock * nBlocSize;
            return nSDFreeSize;
        }
        return 0;
    }
	
	 /**
     * 将一个字节数组数据写入到SD卡中
     */
    public static boolean write2SD(String dir, String fileName, byte[] bytes) {
        if (bytes == null) {
            return false;
        }
        OutputStream output = null;
        try {
            // 拥有可读可写权限，并且有足够的容量
            if (isUsable()
                    && bytes.length < getSDAvailableSize()) {
                File file = null;
                file = createFileInSDCard(dir, fileName);
                output = new BufferedOutputStream(new FileOutputStream(file));
                output.write(bytes);
                output.flush();
                return true;
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            try {
                if (output != null) {
                    output.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /***
     * 从sd卡中读取文件，并且以字节流返回
     * 
     * @param dir
     * @param fileName
     * @return
     */
    public static byte[] readFromSD(String dir, String fileName) {
        File file = new File(dir + File.separator + fileName);
        if (!file.exists()) {
             byte[]  byteTemp = new byte[0];
             return byteTemp;
        }
        InputStream inputStream = null;
        try {
            inputStream = new BufferedInputStream(new FileInputStream(file));
            byte[] data = new byte[inputStream.available()];
            inputStream.read(data);
            return data;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 将一个InputStream里面的数据写入到SD卡中 ,从网络上读取图片
     */
    public static File write2SDFromInput(String dir, String fileName, InputStream input) {
        File file = null;
        OutputStream output = null;
        try {
            int size = input.available();
            // 拥有可读可写权限，并且有足够的容量
            if (isUsable()
                    && size < getSDAvailableSize()) {
                file = createFileInSDCard(dir, fileName);
                output = new BufferedOutputStream(new FileOutputStream(file));
                byte buffer[] = new byte[4 * 1024];
                int temp;
                while ((temp = input.read(buffer)) != -1) {
                    output.write(buffer, 0, temp);
                }
                output.flush();
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            try {
                if (output != null) {
                    output.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return file;
    }
	
	
}
