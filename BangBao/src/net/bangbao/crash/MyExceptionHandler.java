package net.bangbao.crash;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;

import net.bangbao.utils.Utils;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.widget.Toast;

/**
 * 异常保存到本地
 * 
 * @author Spartacus26
 * 
 */
public class MyExceptionHandler implements UncaughtExceptionHandler {

	private static MyExceptionHandler mHandler;
	private static Context mContext;

	private MyExceptionHandler() {
	}

	public synchronized static MyExceptionHandler getInstance(Context context) {
		if (mHandler == null) {
			mHandler = new MyExceptionHandler();
			mContext = context;
		}
		return mHandler;
	}

	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		try {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			System.out.println("错误信息" + sw.toString());
			// 先新建目录文件夹
			File fileDir = new File(Environment.getExternalStorageDirectory()
					+ "/BangBao");
			if (!fileDir.exists()) {
				fileDir.mkdirs();
			}
			// 再在这个文件夹下新建文件
			File file = new File(fileDir +"//" +"BangbaoException.txt");
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(("time:" + Utils.paserTime(System.currentTimeMillis()) + "\n")
					.getBytes());
			fos.flush();

			fos.write(sw.toString().getBytes());
			fos.flush();

			// U880

			// 获取手机的版本信息
			Field[] fields = Build.class.getFields();
			for (Field field : fields) {
				field.setAccessible(true); // 暴力反射
				String key = field.getName();
				String value = field.get(null).toString();

				fos.write((key + "=" + value + "\n").getBytes());
				fos.flush();

			}

			new Thread() {
				public void run() {
					Looper.prepare();
					Toast.makeText(mContext, "程序发生了异常,但是被哥捕获了", 0).show();
					Looper.loop();

				};
			}.start();
			new Thread() {
				public void run() {
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					android.os.Process.killProcess(android.os.Process.myPid());
				};
			}.start();

			fos.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
