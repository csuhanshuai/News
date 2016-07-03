package net.bangbao.dao;

import net.bangbao.base.BaseApi;

public class CustomerMessApi extends BaseApi {
	private int sex;
	private String nick_nm;
	private String image_url;
	
	public String getImage_url() {
		return image_url;
	}
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}
	public String getNick_nm() {
		return nick_nm;
	}
	public void setNick_nm(String nick_nm) {
		this.nick_nm = nick_nm;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	
	@Override
	public String toString() {
		return "CustomerMessApi [cmd=" + cmd + ", sex="
				+ sex + ", nick_nm=" + nick_nm + "]";
	}
	

}
