package net.bangbao.base;


//基础api
/**
 * 
 * @author Administrator
 *
 */
public class BaseApi{
	
	protected String cmd;
	protected int ret_cd;
	protected String ret_txt;
	protected int id_index;
	protected int page_index;
	protected int page_size;
	protected int page_total;
	
	public int getPage_total() {
		return page_total;
	}
	public void setPage_total(int page_total) {
		this.page_total = page_total;
	}
	public int getId_index() {
		return id_index;
	}
	public void setId_index(int id_index) {
		this.id_index = id_index;
	}
	public int getPage_index() {
		return page_index;
	}
	public void setPage_index(int page_index) {
		this.page_index = page_index;
	}
	public int getPage_size() {
		return page_size;
	}
	public void setPage_size(int page_size) {
		this.page_size = page_size;
	}
	public String getCmd() {
		return cmd;
	}
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	public int getRet_cd() {
		return ret_cd;
	}
	public void setRet_cd(int ret_cd) {
		this.ret_cd = ret_cd;
	}
	public String getRet_txt() {
		return ret_txt;
	}
	public void setRet_txt(String ret_txt) {
		this.ret_txt = ret_txt;
	}
	
	@Override
	public String toString() {
		return "BaseApi [cmd=" + cmd + ", ret_cd=" + ret_cd + ", ret_txt="
				+ ret_txt + ", id_index=" + id_index + ", page_index="
				+ page_index + ", page_size=" + page_size + "]";
	}
	
}
