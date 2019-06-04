package com.CNFloWopen.niugou.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.security.Key;
import java.security.SecureRandom;

public class DESUtils {

	private static Key key;
	private static String KEY_STR = "myKey";
	private static String CHARSETNAME = "UTF-8";
	private static String ALGORITHM = "DES";

	static {
		try {
			KeyGenerator generator = KeyGenerator.getInstance(ALGORITHM);
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
//			设置密钥种子
			secureRandom.setSeed(KEY_STR.getBytes());
			generator.init(secureRandom);
			key = generator.generateKey();
			generator = null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String getEncryptString(String str) {
//		基于BASE64编码，接收byte[]并转化成string，输出Binary资料
		BASE64Encoder base64encoder = new BASE64Encoder();
		try {
			byte[] bytes = str.getBytes(CHARSETNAME);
//			获取加密对象
			Cipher cipher = Cipher.getInstance(ALGORITHM);
//			初始化加密对象
			cipher.init(Cipher.ENCRYPT_MODE, key);
//			doFinal方法进行把字节进行加密
			byte[] doFinal = cipher.doFinal(bytes);
			return base64encoder.encode(doFinal);
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		}
	}


	public static String getDecryptString(String str) {
		BASE64Decoder base64decoder = new BASE64Decoder();
		try {
			byte[] bytes = base64decoder.decodeBuffer(str);
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] doFinal = cipher.doFinal(bytes);
			return new String(doFinal, CHARSETNAME);
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) {
		System.out.println(getEncryptString("work"));
		System.out.println(getEncryptString("Zhang12!"));
		System.out.println(getDecryptString("zCKAAEaFQUI="));
		System.out.println(getDecryptString("glkJT4oTra8fJAfVsP+M2w=="));
//		System.out.println(getEncryptString("wxd7f6c5b8899fba83"));
//		System.out.println(getEncryptString("665ae80dba31fc91ab6191e7da4d676d"));
	}

}