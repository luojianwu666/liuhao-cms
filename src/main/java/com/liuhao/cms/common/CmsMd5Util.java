package com.liuhao.cms.common;

import com.bawei.luojianwu.Md5Util;

/**
 * 
 * @author 刘浩
 * @Title: CmsMd5Util.java 
 * @Package com.liuhao.cms.common 
 * @Description: 用于对用户的密码进行+字段后加密，加强密码的安全性
 * @date 2019年12月17日 下午3:07:19
 */
public class CmsMd5Util {
	/**
	 * @Title: stringToMd5   
	 * @Description: 用于对用户的密码进行+字段后加密，加强密码的安全性
	 * @param: @param str
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	public static String string2MD5(String str) {
		return Md5Util.string2MD5(str+"_cmsAdmin");
	}
}
