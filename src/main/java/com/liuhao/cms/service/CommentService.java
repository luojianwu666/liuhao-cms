package com.liuhao.cms.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.liuhao.cms.pojo.Comment;
import com.liuhao.cms.pojo.ShouCang;

public interface CommentService {
	/**
	 * 
	 * @Title: add 
	 * @Description: 添加评论 
	 * @param @param comment
	 * @param @return    设定文件 
	 * @return boolean    返回类型 
	 * @throws
	 */
	public boolean add(Comment comment);
	/**
	 * 
	 * @Title: getPageInfo 
	 * @Description: 评论列表 
	 * @param @param pageNum
	 * @param @param comment
	 * @param @return    设定文件 
	 * @return PageInfo<Comment>    返回类型 
	 * @throws
	 */
	public PageInfo<Comment> getPageInfo(Integer pageNum,Integer articleId);
	/**
	 * 
	 * @Title: getPageInfoByUid 
	 * @Description: 评论列表查询及分页
	 * @param @param comment
	 * @param @param pageNum
	 * @param @param num
	 * @param @return    设定文件 
	 * @return PageInfo<Comment>    返回类型 
	 * @throws
	 */
	public PageInfo<Comment> getPageInfoByUid(Comment comment, Integer pageNum);
	/**
	 * 
	 * @Title: delete 
	 * @Description: TODO根据id删除数据
	 * @param @param id
	 * @param @return    设定文件 
	 * @return boolean    返回类型 
	 * @throws
	 */
	public boolean delete(Integer id);
	public List<ShouCang> shoucangList(Integer id);
	public boolean deleteshoucang(Integer id);
	
}
