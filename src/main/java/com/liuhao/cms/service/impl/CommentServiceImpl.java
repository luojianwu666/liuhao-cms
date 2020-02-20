package com.liuhao.cms.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liuhao.cms.dao.CommentDao;
import com.liuhao.cms.pojo.Comment;
import com.liuhao.cms.pojo.ShouCang;
import com.liuhao.cms.service.CommentService;
@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentDao commentDao;

	@Override
	public boolean add(Comment comment) {
		comment.setCreated(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		return commentDao.insert(comment)>0;
	}

	@Override
	public PageInfo<Comment> getPageInfo(Integer pageNum,Integer articleId) {
		
		PageHelper.startPage(pageNum, 2);
		Comment comment = new Comment();
		comment.setArticleid(articleId);
		List<Comment> commentList = commentDao.select(comment);
		return new PageInfo<>(commentList);
	}


	@Override
	public PageInfo<Comment> getPageInfoByUid(Comment comment, Integer pageNum) {
		PageHelper.startPage(pageNum, 2);
		List<Comment> commentList = commentDao.selectByUserId(comment);
		return new PageInfo<>(commentList);
	}

	@Override
	public boolean delete(Integer id) {
		
		return commentDao.deleteById(id)>0;
	}

	@Override
	public List<ShouCang> shoucangList(Integer id) {
		// TODO Auto-generated method stub
		List<ShouCang> list=commentDao.shouCangList(id);
		
		return list;
	}

	@Override
	public boolean deleteshoucang(Integer id) {
		// TODO Auto-generated method stub
		return commentDao.deleteshoucang(id);
	}
}
