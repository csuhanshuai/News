package net.bangbao.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 加密相关
 * 
 * @author Spartacus26
 * @since 2015.3.18
 */
public class EncryptionUtils {

	/**
	 * 放这里或者和其他key放一起
	 */
	private final static String BANGBAO_SECRET_KEY = "abcdefg";

	public static String getSign(String method, String path, String content) {
		StringBuffer signSb = new StringBuffer();
		signSb.append(method);
		signSb.append(path);
		signSb.append(content);
		signSb.append(BANGBAO_SECRET_KEY);
		
		return signSb.toString();
	}

	/**
	 * 一个String 经过MD5加密，得到32位长度
	 * 
	 * @author Spartacus26
	 * @throws NoSuchAlgorithmException
	 * @since 2015.3.18
	 */
	public static String string2MD5(String inStr)
			throws NoSuchAlgorithmException {
		// 创建十六进制形式的sb
		StringBuffer hexValue = new StringBuffer();

		MessageDigest md5 = MessageDigest.getInstance("MD5");
		char[] charArray = inStr.toCharArray();
		// 创建二进制数组
		byte[] byteArray = new byte[charArray.length];
		// 把密码字符数组转为二进制数组
		for (int i = 0; i < charArray.length; i++)
			byteArray[i] = (byte) charArray[i];
		// 计算得到的MD5二进制数组
		byte[] md5Bytes = md5.digest(byteArray);

		// 把MD5的二进制数组转为16进制的sb
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}

		return hexValue.toString().toUpperCase();
	}
}
