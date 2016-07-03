package net.bangbao;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;

/*
 * author:mosl
 * 管理Activity的栈
 */
public class AppManager {
	
	private List<Activity> mActivityList = new LinkedList<Activity>();
	private static AppManager instance;

    private AppManager(){}
    /**
     * 单一实例
     */
    public static AppManager getAppManager(){
        if(instance==null){
            instance=new AppManager();
        }
        return instance;
    }
    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity){
        mActivityList.add(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity){
        if(activity!=null){
            mActivityList.remove(activity);
            activity.finish();
            activity=null;
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity(){
    	
        while(mActivityList.size() > 0) {
            Activity activity = mActivityList.get(mActivityList.size() - 1);
            mActivityList.remove(mActivityList.size() - 1);
            activity.finish();
        }
    }
    /**
     * 退出应用程序
     */
    public void AppExit() {
        try {
            finishAllActivity();
        } catch (Exception e) { }
    }
}
