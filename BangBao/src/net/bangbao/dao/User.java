package net.bangbao.dao;

public class User {

	private String name;

	public static final class RegisterStruct {

		public String name;
		public String pwd;
		public int type;
		public int prob_id;
		public String prob_ans;
		public String nm_en;
		public int co_id;
		public String ctt;
		public String orig_user_nm;
	}

	public static final class FeedbackStruct {
		public String sugg;
		public String ctt;

	}

	public static final class DeliverMessageStruct {
		public Integer sex;
		public Integer prov_id;
		public String bday;
		public Integer edu_id;
		public Long bgn_tmtp;
		public String cert_no;
		public Integer co_id;
		public String ctt;
		public String ins_say;
		public String honor_say;
		public String image_url;
		public String nick_nm;
		public Integer in_watch;
	}

	public static final class CustomerMessageStruct {
		public String nick_nm;
		public String image_url;
	}
	
	//hs--原密码验证字段

	public static final class PasswordMessageStruct {
		public String old_pwd;
		
	}
	//hs--新密码确认字段
	public static final class NewPasswordStruct {
        public String new_pwd;
        public String code;
        
    }

	/**
	 * 第三方登陆信息
	 * 
	 * @author Spartacus26
	 * 
	 */
	public static final class ThirdLoginMessageStruct {
		/** 第三方用户唯一标识码 */
		public String third_uid;
		/** 第三方ID 1.微信       2.QQ    3.新浪微博 */
		public int third_id;
		/** 用户昵称 */
		public String nick_nm;
	}
}
