package com.liuhao.cms.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liuhao.cms.dao.LinksDao;
import com.liuhao.cms.pojo.Links;
import com.liuhao.cms.service.LinksService;
@Service
public class LinksServiceImpl implements LinksService {

	@Autowired
	private LinksDao linksDao;

	@Override
	public PageInfo<Links> getPageInfo(Links links, Integer pageNum) {
		PageHelper.startPage(pageNum, 3);
		
		List<Links> linksList = linksDao.select(links);
		
		return new PageInfo<>(linksList);
	}

	@Override
	public Links selectById(Integer linksId) {
		// TODO Auto-generated method stub
		return linksDao.selectById(linksId);
	}

	@Override
	public boolean save(Links links) {
         //等于null 添加   不等于 修改
		 if(links.getId()!=null) {
			return  linksDao.update(links)>0;
		 }
		 Links link = new Links();
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		link.setCreated(new Date());
		return linksDao.insert(links)>0;
	}

	@Override
	public boolean batchDel(String ids) {
		// TODO Auto-generated method stub
		
		return linksDao.deleteByIds(ids);
	}

	@Override
	public List<Links> select() {
		// TODO Auto-generated method stub
		Links links = new Links();
		return linksDao.select(links);
	}
}
