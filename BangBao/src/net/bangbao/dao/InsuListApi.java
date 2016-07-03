package net.bangbao.dao;

import java.util.List;

import net.bangbao.base.BaseApi;

public class InsuListApi extends BaseApi {
	
	public List<InsuStr> item;
	
	
	public List<InsuStr> getItem() {
		return item;
	}

	public void setItem(List<InsuStr> item) {
		this.item = item;
	}

	public static class InsuStr{
		
		private int id;
		private String nm;
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getNm() {
			return nm;
		}
		public void setNm(String nm) {
			this.nm = nm;
		}
		
	}
}
