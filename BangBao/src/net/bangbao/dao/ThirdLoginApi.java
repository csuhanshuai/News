package net.bangbao.dao;

public class ThirdLoginApi {

	private int cmd;
	private int ret_cd;
	private String ret_txt;
	private String token;
	private String rc_token;
	private int type;
	private int user_id;
	private int info_tmtp;
	private int friend_tmtp;
	private int black_tmtp;
	private int update;

	public int getCmd() {
		return cmd;
	}

	public void setCmd(int cmd) {
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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getRc_token() {
		return rc_token;
	}

	public void setRc_token(String rc_token) {
		this.rc_token = rc_token;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getInfo_tmtp() {
		return info_tmtp;
	}

	public void setInfo_tmtp(int info_tmtp) {
		this.info_tmtp = info_tmtp;
	}

	public int getFriend_tmtp() {
		return friend_tmtp;
	}

	public void setFriend_tmtp(int friend_tmtp) {
		this.friend_tmtp = friend_tmtp;
	}

	public int getBlack_tmtp() {
		return black_tmtp;
	}

	public void setBlack_tmtp(int black_tmtp) {
		this.black_tmtp = black_tmtp;
	}

	public int getUpdate() {
		return update;
	}

	public void setUpdate(int update) {
		this.update = update;
	}

	@Override
	public String toString() {
		return "ThirdLoginApi [cmd=" + cmd + ", ret_cd=" + ret_cd
				+ ", ret_txt=" + ret_txt + ", token=" + token + ", rc_token="
				+ rc_token + ", type=" + type + ", user_id=" + user_id
				+ ", info_tmtp=" + info_tmtp + ", friend_tmtp=" + friend_tmtp
				+ ", black_tmtp=" + black_tmtp + ", update=" + update + "]";
	}

}
