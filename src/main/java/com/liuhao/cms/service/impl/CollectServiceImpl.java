package com.liuhao.cms.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liuhao.cms.dao.CollectDao;
import com.liuhao.cms.pojo.Collect;
import com.liuhao.cms.pojo.Comment;
import com.liuhao.cms.service.CollectService;
import com.liuhao.cms.util.StringUtil;
@Service
public class CollectServiceImpl implements CollectService {

	@Autowired
	private CollectDao collectDao;
	
	
	@Override
	public boolean saveHttpUrl(String str) {
		
		return StringUtil.isHttpUrl(str);
		
		
	}

	@Override
	public List<Collect> collectList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageInfo<Collect> getPageInfoByUid(Collect collect, Integer pageNum) {
		PageHelper.startPage(pageNum, 2);
		List<Collect> collectList = collectDao.selectByUserId(collect);
		return new PageInfo<>(collectList);
	}

	@Override
	public boolean delete(Integer id) {
		// TODO Auto-generated method stub
		
		
		return collectDao.deleteById(id)>0;
	}

	public boolean add(Collect collect) {
		collect.setCreated(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		return collectDao.insert(collect)>0;
	}
	
}
