package net.bangbao.dao;

import net.bangbao.base.BaseApi;

public class ConsultMessApi extends BaseApi {

	private int sex;
	private String nm_en;
	private String nm_cn;
	private String nick_nm;

	private int co_id;
	private String ctt;
	private int prov_id;
	private String bday;
	private int edu_id;
	private int bgn_tmtp;
	private String cert_no;
	private int image_id;
	private String image_url;
	private String ins_say;
	private String honor_say;
	// from hanshuai
	private int in_watch;

	public int getIn_watch() {
		return in_watch;
	}

	public void setIn_watch(int in_watch) {
		this.in_watch = in_watch;
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

	public String getNm_en() {
		return nm_en;
	}

	public void setNm_en(String nm_en) {
		this.nm_en = nm_en;
	}

	public String getNm_cn() {
		return nm_cn;
	}

	public void setNm_cn(String nm_cn) {
		this.nm_cn = nm_cn;
	}

	public int getCo_id() {
		return co_id;
	}

	public void setCo_id(int co_id) {
		this.co_id = co_id;
	}

	public String getCtt() {
		return ctt;
	}

	public void setCtt(String ctt) {
		this.ctt = ctt;
	}

	public int getProv_id() {
		return prov_id;
	}

	public void setProv_id(int prov_id) {
		this.prov_id = prov_id;
	}

	public String getBday() {
		return bday;
	}

	public void setBday(String bday) {
		this.bday = bday;
	}

	public int getEdu_id() {
		return edu_id;
	}

	public void setEdu_id(int edu_id) {
		this.edu_id = edu_id;
	}

	public int getBgn_tmtp() {
		return bgn_tmtp;
	}

	public void setBgn_tmtp(int bgn_tmtp) {
		this.bgn_tmtp = bgn_tmtp;
	}

	public String getCert_no() {
		return cert_no;
	}

	public void setCert_no(String cert_no) {
		this.cert_no = cert_no;
	}

	public int getImage_id() {
		return image_id;
	}

	public void setImage_id(int image_id) {
		this.image_id = image_id;
	}

	public String getImage_url() {
		return image_url;
	}

	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}

	public String getIns_say() {
		return ins_say;
	}

	public void setIns_say(String ins_say) {
		this.ins_say = ins_say;
	}

	public String getHonor_say() {
		return honor_say;
	}

	public void setHonor_say(String honor_say) {
		this.honor_say = honor_say;
	}

	@Override
	public String toString() {
		return "ConsultMessApi [cmd=" + cmd + ", sex=" + sex + ", nm_en="
				+ nm_en + ", nm_cn=" + nm_cn + ", co_id=" + co_id + ", ctt"
				+ ctt + " prov_id" + prov_id + " bday" + bday + " edu_id"
				+ edu_id + " bgn_tmtp" + bgn_tmtp + " cert_no" + cert_no
				+ " image_id" + image_id + " image_url" + image_url
				+ " ins_say" + ins_say + " honor_say" + honor_say + "]";
	}

}
