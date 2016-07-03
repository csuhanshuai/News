package net.bangbao.ui;

import java.security.NoSuchAlgorithmException;

import net.bangbao.AppConfig;
import net.bangbao.R;
import net.bangbao.UserConfig;
import net.bangbao.base.BaseActivity;
import net.bangbao.base.BaseApi;
import net.bangbao.common.UIHelper;
import net.bangbao.dao.User.NewPasswordStruct;
import net.bangbao.network.ApiClient;
import net.bangbao.utils.EncryptionUtils;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class NewPasswordActivity extends BaseActivity {
    private RelativeLayout ret;
    private String strBeforeRecommend;
    private Button bt;
    private EditText new_pwd,re_pwd;
    NewPasswordStruct newpass = new NewPasswordStruct();
    UserConfig userConfig = AppConfig.getUser();
    private String ps = "";
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.new_password);
        initView();
    }

    private void initView() {
        new_pwd = (EditText) findViewById(R.id.pwd_new);
        new_pwd.addTextChangedListener(new TextWatcher() {
            
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                    int after) {
                strBeforeRecommend = s.toString();
            }
            
            @Override
            public void afterTextChanged(Editable s) {
                
                if(s.toString().equals(strBeforeRecommend)){  
                    return;  
                }  
                int editStart = new_pwd.getSelectionStart();
                int offset = s.toString().length() - strBeforeRecommend.length();
                if (s.toString().endsWith(" ")){
                    UIHelper.showToastMessage("不能输入空格");
                    s.delete(s.toString().length() - offset, s.toString().length());
                    int tempSelection = editStart - offset;
                    new_pwd.setText(s);
                    new_pwd.setSelection(tempSelection);
                }
            }
        });
        re_pwd = (EditText) findViewById(R.id.pwd_renew);
        re_pwd.addTextChangedListener(new TextWatcher() {
            
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                    int after) {
                strBeforeRecommend = s.toString();
            }
            
            @Override
            public void afterTextChanged(Editable s) {
                
                if(s.toString().equals(strBeforeRecommend)){  
                    return;  
                }  
                int editStart = re_pwd.getSelectionStart();
                int offset = s.toString().length() - strBeforeRecommend.length();
                if (s.toString().endsWith(" ")){
                    UIHelper.showToastMessage("不能输入空格");
                    s.delete(s.toString().length() - offset, s.toString().length());
                    int tempSelection = editStart - offset;
                    re_pwd.setText(s);
                    re_pwd.setSelection(tempSelection);
                }
            }
        });
        ret = (RelativeLayout) findViewById(R.id.go_back9);
        ret.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        bt = (Button) findViewById(R.id.end);
        bt.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                send1();
            }
        });
        
       
        
    }
    
    public void send1(){
      String ne = new_pwd.getText().toString().trim();
      try {
          ps = EncryptionUtils.string2MD5(ne);
      } catch (NoSuchAlgorithmException e) {
          e.printStackTrace();
      }
      newpass.new_pwd = ps;
      //确认密码
      String re = re_pwd.getText().toString().trim();
      if(!re.equals(ne)){
          re_pwd.setText(null);
          UIHelper.showToastMessage("密码不一致");
          return;
      }
      Intent in = getIntent();
      String co = in.getStringExtra("code");
      newpass.code = co;
      
      if(new_pwd.getText().toString().trim().length()!=0&&(new_pwd.getText().toString().trim().length() < 3 || new_pwd.getText().toString().trim().length() > 16)) {
          UIHelper.showToastMessage("密码不能低于3位，或高于16位");
          return;
      }
        new ApiClient().setPassword(newpass, userConfig.getUserName(), this, BaseApi.class,
            new ApiClient.CallBack<BaseApi>(){

                @Override
                public void success(BaseApi api) {
                    if(api == null) return;
                    if(api.getRet_cd() == 0){
                        UIHelper.showToastMessage("密码修改成功");
                        new_pwd.setText(null);
                        re_pwd.setText(null);
                        Intent intent = new Intent(NewPasswordActivity.this,SettingActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    if(api.getRet_cd()!= 0){
                        UIHelper.showToastMessage(api.getRet_txt());
                    }
                }

                @Override
                public void fail(String error) {
                    UIHelper.showToastMessage(
                            "当前网络有问题！");
                }
        
    });
    }

}
