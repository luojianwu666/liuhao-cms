package com.liuhao.cms.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.liuhao.cms.pojo.Collect;

public interface CollectService {
    //在方法中需要使用上面的isHttpUrl方法判断传入的收藏夹地址是否合法，如果合法则保存
	boolean saveHttpUrl(String str);
	
	//按时间倒排序查询且限定返回条数的方法。
	List<Collect>  collectList();

	PageInfo<Collect> getPageInfoByUid(Collect collect, Integer pageNum);

	boolean delete(Integer id);

	boolean add(Collect collect);
}
