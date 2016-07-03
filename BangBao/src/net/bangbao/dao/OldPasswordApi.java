package net.bangbao.dao;

import net.bangbao.base.BaseApi;

public class OldPasswordApi extends BaseApi {
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "CustomApi [cmd=" + cmd + ", ret_cd="
        + ret_cd + ", ret_txt=" + ret_txt + ", code="
        + code  + "]";
    }
    

}
