package net.bangbao.dao;

import java.util.List;

import net.bangbao.base.BaseApi;

public class MyCustomerListApi extends BaseApi {
	
	private long update_tmtp;
	private List<User> item;
	
	
	public long getUpdate_tmtp() {
		return update_tmtp;
	}

	public void setUpdate_tmtp(long update_tmtp) {
		this.update_tmtp = update_tmtp;
	}

	public List<User> getItem() {
		return item;
	}


	public void setItem(List<User> item) {
		this.item = item;
	}


	public static class User{
		public String nick_nm;
		public long user_id;
	}
	
}
