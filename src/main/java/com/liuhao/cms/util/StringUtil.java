package com.liuhao.cms.util;

import java.io.UnsupportedEncodingException;
import java.util.Random;
import java.util.regex.Pattern;
/**
 * 
 * @author 刘浩
 * @Title: SpringUtil.java 
 * @Package com.liuhao.util 
 * @Description: TODO(字符串工具类，用于判断字符串是否为空以及该字符串是否为手机号)    
 * @date 2019年12月5日 下午1:48:28
 */
public class StringUtil {

	/**
	 * 验证是否是URL
	 * @param url
	 * @return
	 */
	public static boolean isHttpUrl(String str){

		
		
		return str.startsWith("http");
	}
	
}
