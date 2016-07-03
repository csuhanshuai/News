package net.bangbao.dao;

import net.bangbao.base.BaseApi;

public class CustomApi extends BaseApi {
	private int ser_uid;
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "CustomApi [cmd=" + cmd + ", ret_cd="
		+ ret_cd + ", ret_txt=" + ret_txt + ", ser_uid="
		+ ser_uid  + "]";
	}

	public int getSer_uid() {
		return ser_uid;
	}

	public void setSer_uid(int ser_uid) {
		this.ser_uid = ser_uid;
	}
	

}
