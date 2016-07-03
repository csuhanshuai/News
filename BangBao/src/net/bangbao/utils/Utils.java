package net.bangbao.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import net.bangbao.AppInit;
import net.bangbao.R;
import net.bangbao.common.UIHelper;
import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Vibrator;
import android.telephony.TelephonyManager;

public class Utils {
    /**
     * 将毫秒数转换成yyyy-MM-dd-HH-mm-ss的格式
     * 
     * @param milliseconds
     * @return
     */
    public static String paserTime(long milliseconds) {
        System.setProperty("user.timezone", "Asia/Shanghai");
        TimeZone tz = TimeZone.getTimeZone("Asia/Shanghai");
        TimeZone.setDefault(tz);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String times = format.format(new Date(milliseconds));

        return times;
    }

    /**
     * 
     * 
     * @param Activity
     * @category 检测网络
     * @return false 网络没连接
     */
    public static boolean internetDdetect(Context act) {
        ConnectivityManager manager =
                (ConnectivityManager) act.getSystemService(
                        Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null) {
            return networkInfo.isAvailable();
        }
        return false;
    }

    public static void openNetword(Context context) {
        // 没有网络，提示用户打开网络
        UIHelper.showToastMessage("请打开网络");


        // 判断手机系统的版本 即API大于10 就是3.0或以上版本
//       Intent intent = null;
//        if (android.os.Build.VERSION.SDK_INT > 10) {
//            intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
//        } else {
//            intent = new Intent();
//            ComponentName component =
//                    new ComponentName("com.android.settings",
//                            "com.android.settings.WirelessSettings");
//            intent.setComponent(component);
//            intent.setAction("android.intent.action.VIEW");
//        }
//        context.startActivity(intent);
        if(android.os.Build.VERSION.SDK_INT > 10 ){
            //3.0以上打开设置界面
            context.startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
        }else{
            context.startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
        }
    }

    /**
     * 根据保险的catg_id获得显示的文字
     * 
     * @author Spartacus26
     * @param catg_id
     * @return 保险种类
     */
    public static String catg_id2String(int id) {
        String categoryStr = null;
        switch (id) {
            case 1:
                categoryStr = "意外险";
                break;
            case 2:
                categoryStr = "危疾险";
                break;
            case 3:
                categoryStr = "医疗险";
                break;
            case 4:
                categoryStr = "人寿险";
                break;
            case 5:
                categoryStr = "理财险";
                break;
            default:
                break;
        }

        return categoryStr;

    }

    /**
     * 根据保险的co_id获得显示的文字
     * 
     * @author Spartacus26
     * @param co_id
     * @return 图片的地址
     */
    public static int co_id2PicUrl(int id) {
        int picUrlStr = 0;
        switch (id) {
            case 1:
                picUrlStr = R.drawable.choose_insurance_id_1;
                break;
            case 2:
                picUrlStr = R.drawable.choose_insurance_id_2;
                break;
            case 3:
                picUrlStr = R.drawable.choose_insurance_id_3;
                break;
            case 4:
                picUrlStr = R.drawable.choose_insurance_id_4;
                break;
            case 5:
                picUrlStr = R.drawable.choose_insurance_id_5;
                break;
            default:
                break;
        }

        return picUrlStr;

    }

    /**
     * 登陆的时候用到，得到Android的设备唯一标识码
     * 
     * @author Spartacus26
     * @since 2015.3.19
     * */
    public static String getOnlyId(Context context) {
        TelephonyManager tm =
                (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String imeiStr = tm.getDeviceId();
        // 这个有些手机得不到
        @SuppressWarnings("unused")
        String imsiStr = tm.getSubscriberId();
        return imeiStr;
    }

    /**
     * @return
     * 获取时间戳
     */
    public static long getTimetmtp() {

        return System.currentTimeMillis();
    }

    /**
     *  手机震动
     */
    public static void vibrator() {
        Vibrator mVibrator01 =
                (Vibrator) AppInit.getContext().getSystemService(Service.VIBRATOR_SERVICE);
        mVibrator01.vibrate(new long[] {100, 10, 100, 100}, -1);
    }

    public static void Vibrate(long milliseconds) { 
        Vibrator vib = (Vibrator) AppInit.getContext().getSystemService(Service.VIBRATOR_SERVICE); 
        vib.vibrate(milliseconds); 
        } 

    public static Bitmap createScaleBitmap(Bitmap tempBitmap, int desiredWidth, int desiredHeight) {
        // If necessary, scale down to the maximal acceptable size.
        if (tempBitmap != null
                && (tempBitmap.getWidth() > desiredWidth || tempBitmap.getHeight() > desiredHeight)) {
            // 如果是放大图片，filter决定是否平滑，如果是缩小图片，filter无影响
            Bitmap bitmap =
                    Bitmap.createScaledBitmap(tempBitmap, desiredWidth, desiredHeight, true);
            //tempBitmap.recycle(); // 释放Bitmap的native像素数组
            return bitmap;
        } else {
            return tempBitmap; // 如果没有缩放，那么不回收
        }
    }
}
