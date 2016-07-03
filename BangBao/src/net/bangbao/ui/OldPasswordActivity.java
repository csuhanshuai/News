package net.bangbao.ui;

import java.security.NoSuchAlgorithmException;

import net.bangbao.AppConfig;
import net.bangbao.R;
import net.bangbao.UserConfig;
import net.bangbao.base.BaseActivity;
import net.bangbao.common.UIHelper;
import net.bangbao.dao.OldPasswordApi;
import net.bangbao.dao.User.PasswordMessageStruct;
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

public class OldPasswordActivity extends BaseActivity {
    
    private RelativeLayout re;
    private String strBeforeRecommend;
    private Button btn;
    private EditText old_pwd;
    private String old_ps = "";
    PasswordMessageStruct pass = new PasswordMessageStruct();
    UserConfig userConfig = AppConfig.getUser();
    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        setContentView(R.layout.old_password);
        initViews();
    }
    private void initViews() {
        old_pwd = (EditText) findViewById(R.id.pwd_old);
        old_pwd.addTextChangedListener(new TextWatcher() {
          
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
              int editStart = old_pwd.getSelectionStart();
              int offset = s.toString().length() - strBeforeRecommend.length();
              if (s.toString().endsWith(" ")){
                  UIHelper.showToastMessage("不能输入空格");
                  s.delete(s.toString().length() - offset, s.toString().length());
                  int tempSelection = editStart - offset;
                  old_pwd.setText(s);
                  old_pwd.setSelection(tempSelection);
              }
          }
      });
        re = (RelativeLayout) findViewById(R.id.go_back8);
        re.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
     
        
        btn = (Button) findViewById(R.id.next);
        btn.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                send();
            }
        });
        
    }
//    FIXME 验证原密码
    public void send(){
        
        String old = old_pwd.getText().toString().trim();
        try {
            old_ps = EncryptionUtils.string2MD5(old);
        } catch (NoSuchAlgorithmException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        pass.old_pwd = old_ps;
        
        new ApiClient().changePassword(pass, userConfig.getUserName(), this, OldPasswordApi.class,
            new ApiClient.CallBack<OldPasswordApi>(){

                @Override
                public void success(OldPasswordApi api) {
                    if(api == null) return;
                    if(api.getRet_cd() == 0){
                        UIHelper.showToastMessage("密码验证成功");
                        old_pwd.setText(null);
                        Intent intent = new Intent(OldPasswordActivity.this,NewPasswordActivity.class);
                        intent.putExtra("code",api.getCode());
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
