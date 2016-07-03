package net.bangbao.dao;

public class Msg {

	public static final int TYPE_RECEIVED = 0;
	public static final int TYPE_SENT = 1;
	private CharSequence content;
	private String username;
	private String imageUrl;
	private long timetmtp;
	private int type;
	private String myselfId;
	
	public Msg(CharSequence content, int type,long timetmtp,String username,String imageUrl) {
		this.content = content;
		this.type = type;
		this.timetmtp = timetmtp;
		this.username = username;
		this.imageUrl = imageUrl;
	}

	public String getMyselfId() {
		return myselfId;
	}


	public void setMyselfId(String myselfId) {
		this.myselfId = myselfId;
	}


	public long getTimetmtp() {
		return timetmtp;
	}


	public void setTimetmtp(long timetmtp) {
		this.timetmtp = timetmtp;
	}


	public CharSequence getContent() {
		return content;
	}

	public int getType() {
		return type;
	}

}
