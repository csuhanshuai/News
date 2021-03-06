package net.bangbao;

import io.rong.imlib.RongIMClient;
import io.rong.imlib.RongIMClient.Message;

import java.util.ArrayList;
import java.util.List;

import net.bangbao.common.PreferenceUtil;
import net.bangbao.service.RongYunService;
import android.content.Intent;

/*
 * author:mosl
 * descrtion:app config
 */
public class AppConfig {

    public static RongIMClient IMClient;
    public static int msgCount;

    public static UserConfig userconfig;

    //存储未读消息
    /**设置 是否震动 ，是否有声音**/
    public static boolean isVirbrator = true;
    public static boolean isVoice = true;

    /**是否完善信息**/
    public static boolean isCompleteMess = false;

    /** 我的顾问 **/
    public static List<Message> msgContents = new ArrayList<Message>();


    public static final void login() {
        PreferenceUtil.login(AppInit.getContext());
    }

    public static final void loginout() {
        userconfig = null;
        PreferenceUtil.loginout(AppInit.getContext());
        PreferenceUtil.putUserConfig(AppInit.getContext(), null);
        PreferenceUtil.saveRonYunToken(AppInit.getContext(), null);

        if (IMClient != null) {
            Intent intent = new Intent(AppInit.getContext(), RongYunService.class);
            AppInit.getContext().stopService(intent);
            IMClient.disconnect();
        }
    }

    public static final void firstStarted() {
        PreferenceUtil.startEd(AppInit.getContext());
    }

    public static final boolean isLogin() {
        return PreferenceUtil.isLogin(AppInit.getContext());
    }

    public static final boolean isStartEd() {
        return PreferenceUtil.isFirstStart(AppInit.getContext());
    }

    public static final void saveRongYunToken(String rongYunToken) {
        PreferenceUtil.saveRonYunToken(AppInit.getContext(), rongYunToken);
    }

    public static final String getRongYunToken() {
        return PreferenceUtil.getRongYunToken(AppInit.getContext());
    }

    public static final void putUser(UserConfig user) {
        PreferenceUtil.putUserConfig(AppInit.getContext(), user);
    }
    public static final UserConfig getUser() {

        if (userconfig != null) return userconfig;
        userconfig = PreferenceUtil.getUserConfig(AppInit.getContext());
        return userconfig;
    }
}
