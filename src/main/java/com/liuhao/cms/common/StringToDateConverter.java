package com.liuhao.cms.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;
/**
 * 
 * @author 刘浩
 * @Title: StringToDateConverter.java 
 * @Package com.liuhao.cms.common 
 * @Description:对传入的数据进行类型转换  转换为Date类型
 * @date 2019年12月17日 下午3:17:30
 */
public class StringToDateConverter implements Converter<String,Date>{

	@Override
	public Date convert(String source) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = simpleDateFormat.parse(source);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

}
