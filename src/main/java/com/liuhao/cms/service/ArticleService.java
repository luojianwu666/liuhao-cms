package com.liuhao.cms.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.liuhao.cms.pojo.Article;
import com.liuhao.cms.pojo.Category;
import com.liuhao.cms.pojo.Channel;

public interface ArticleService {
	/**
	 * @Title: getPageInfo   
	 * @Description: 分页列表   
	 * @param: @param user
	 * @param: @param pageNum
	 * @param: @param pageSize
	 * @param: @return      
	 * @return: PageInfo<Article>      
	 * @throws
	 */
	PageInfo<Article> getPageInfo(Article article, int pageNum,int pageSize); 
	/**
	 * @Title: updateStatus   
	 * @Description: 修改文章状态   
	 * @param: @param id
	 * @param: @param status
	 * @param: @return      
	 * @return: boolean      
	 * @throws
	 */
	public boolean updateStatus(Integer id,int status);
	/**
	 * @Title: addHot   
	 * @Description: 加热，每次给hot值加1   
	 * @param: @param id
	 * @param: @return      
	 * @return: boolean      
	 * @throws
	 */
	public boolean addHot(Integer id);
	/**
	 * @Title: getChannelList   
	 * @Description: 查询频道列表   
	 * @param: @return      
	 * @return: List<Channel>      
	 * @throws
	 */
	public List<Channel> getChannelList();
	/**
	 * @Title: getById   
	 * @Description: 根据id查询文章 
	 * @param: @param id
	 * @param: @return      
	 * @return: Article      
	 * @throws
	 */
	//public Article getById(Integer id);
	/**
	 * @Title: save   
	 * @Description: 保存或修改文章 
	 * @param: @param article
	 * @param: @return      
	 * @return: boolean      
	 * @throws
	 */
	boolean save(Article article);
	/**
	 * @Title: getCateListByChannelId   
	 * @Description: 根据频道Id查询分类列表  
	 * @param: @param channelId
	 * @param: @return      
	 * @return: List<Category>      
	 * @throws
	 */
	List<Category> getCateListByChannelId(Integer channelId);
	
	/**
	 * 
	 * @Title: delByIds 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @param ids
	 * @param @return    设定文件 
	 * @return boolean    返回类型 
	 * @throws
	 */
	boolean delByIds(String ids);
	/**
	 * 
	 * @Title: isAllCheck 
	 * @Description: 根据ids判断文章是否已审核
	 * @param @param ids    设定文件 
	 * @return void    返回类型 
	 * @throws
	 */
	boolean  isAllCheck(String ids);
	/**
	 * 
	 * @Title: getListByChannelId 
	 * @Description: 根据频道id查询文章列表
	 * @param @param channelId
	 * @param @param id
	 * @param @param i
	 * @param @return    设定文件 
	 * @return List<Article>    返回类型 
	 * @throws
	 */
	List<Article> getListByChannelId(Integer channelId, Integer id, int i);

	/**
	 * 
	 * @Title: getHotList 
	 * @Description: 热点文章及分页
	 * @param @param pageNum
	 * @param @return    设定文件 
	 * @return PageInfo<Article>    返回类型 
	 * @throws
	 */
	PageInfo<Article> getHotList(int pageNum);

    /**
     *  
     * @Title: getListByChannelIdAndCateId 
     * @Description: 根据频道id和分类id查询文章列表及分页
     * @param @param channelId
     * @param @param cateId
     * @param @param pageNo
     * @param @return    设定文件 
     * @return PageInfo<Article>    返回类型 
     * @throws
     */
	PageInfo<Article> getListByChannelIdAndCateId(Integer channelId, Integer cateId, Integer pageNo);
	/**
	 * 
	 * @Title: getNewList 
	 * @Description: 查询最新的文章
	 * @param @param i
	 * @param @return    设定文件 
	 * @return List<Article>    返回类型 
	 * @throws
	 */
	List<Article> getNewList(int i);
	
	Article getById(Integer id, String remoteAddr);
}
