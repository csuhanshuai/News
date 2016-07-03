package net.bangbao.dao;

import java.util.List;

import net.bangbao.base.BaseApi;
/**
 * 
 * @author Administrator
 *
 */
public class InsuCatgApi extends  BaseApi{
	
	private List<InsuCatgInfo> item;
	
	public List<InsuCatgInfo> getItem() {
		return item;
	}

	public void setItem(List<InsuCatgInfo> item) {
		this.item = item;
	}

	public static final class InsuCatgInfo{
		private int id;
		private String tit;
		private String cntt_url;
		private int co_id;
		private int catg_id;
		private String intro;
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getTit() {
			return tit;
		}
		public void setTit(String tit) {
			this.tit = tit;
		}
		public String getCntt_url() {
			return cntt_url;
		}
		public void setCntt_url(String cntt_url) {
			this.cntt_url = cntt_url;
		}
		public int getCo_id() {
			return co_id;
		}
		public void setCo_id(int co_id) {
			this.co_id = co_id;
		}
		public int getCatg_id() {
			return catg_id;
		}
		public void setCatg_id(int catg_id) {
			this.catg_id = catg_id;
		}
		public String getIntro() {
			return intro;
		}
		public void setIntro(String intro) {
			this.intro = intro;
		}
		
	}
}
