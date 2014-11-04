package com.smf.platform.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncoderByMd5 {
	public static String encodeByMd5(String plainText) { 
		StringBuffer buf = new StringBuffer(""); 
		try { 
		MessageDigest md = MessageDigest.getInstance("MD5"); 
		md.update(plainText.getBytes()); 
		byte b[] = md.digest(); 
		int i = 0; 
		for (int offset = 0; offset < b.length; offset++) { 
		i = b[offset]; 
		if (i < 0) 
		i += 256; 
		if (i < 16) 
		buf.append("0"); 
		buf.append(Integer.toHexString(i)); 
		} 

		} catch (NoSuchAlgorithmException e) { 
		e.printStackTrace(); 
		} 
		return buf.toString(); //32位的加密 
		//return buf.toString().substring(8,24); // 16位的加密 
		} 
}
