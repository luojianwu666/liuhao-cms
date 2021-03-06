package com.liuhao.cms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.liuhao.cms.pojo.Article;

public interface ArticleDao {
	/**
	 * @Title: selectById   
	 * @Description: 根据Id，查询对象   
	 * @param: @param id
	 * @param: @return      
	 * @return: User      
	 * @throws
	 */
	Article selectById(@Param("id") Integer id);
	/**
	 * @Title: select   
	 * @Description: 根据User查询列表  
	 * @param: @param user
	 * @param: @return      
	 * @return: List<User>      
	 * @throws
	 */
	List<Article> select(@Param("article") Article article);
	/**
	 * @Title: count   
	 * @Description: 查询数据条数   
	 * @param: @param user
	 * @param: @return      
	 * @return: int      
	 * @throws
	 */
	int count(@Param("article") Article article);
	/**
	 * @Title: insert   
	 * @Description: 插入一条记录   
	 * @param: @param user
	 * @param: @return      
	 * @return: int      
	 * @throws
	 */
	int insert(@Param("article") Article article);
	/**
	 * @Title: update   
	 * @Description: 根据Id更新记录 
	 * @param: @param user
	 * @param: @return      
	 * @return: int      
	 * @throws
	 */
	int update(@Param("article") Article article);
	/**
	 * @Title: deleteById   
	 * @Description: 根据Id删除记录   
	 * @param: @param id
	 * @param: @return      
	 * @return: int      
	 * @throws
	 */
	int deleteById(@Param("id") Integer id);
	/**
	 * @Title: deleteByIds   
	 * @Description: 根据Ids批量删除记录   
	 * @param: @param ids "1,2,3"
	 * @param: @return      
	 * @return: int      
	 * @throws
	 */
	int deleteByIds(@Param("ids") String ids);
	/**
	 * @Title: updateStatus   
	 * @Description: 修改文章状态   
	 * @param: @param id
	 * @param: @param status
	 * @param: @return      
	 * @return: int      
	 * @throws
	 */
	int updateStatus(@Param("id") Integer id,@Param("status") Integer status);
	/**
	 * @Title: addHot   
	 * @Description: 热点字段+1   
	 * @param: @param id
	 * @param: @return      
	 * @return: int      
	 * @throws
	 */
	int addHot(@Param("id") Integer id);
	/**
	 * 
	 * @Title: selectByIds 
	 * @Description: 根据ids查询文章
	 * @param @param ids
	 * @param @return    设定文件 
	 * @return List<Article>    返回类型 
	 * @throws
	 */
	List<Article> selectByIds(@Param("ids")String ids);
	/**
	 * 
	 * @Title: selectListByChannelId 
	 * @Description: 根据频道id查询文章列表及分页
	 * @param @param channelId
	 * @param @param aritcleId
	 * @param @param num
	 * @param @return    设定文件 
	 * @return List<Article>    返回类型 
	 * @throws
	 */
	List<Article> selectListByChannelId(@Param("channelId")Integer channelId, @Param("articleId")Integer articleId, @Param("num")int num);
	/**
	 * 
	 * @Title: selectByHot 
	 * @Description: 根据热点查询文章
	 * @param @return    设定文件 
	 * @return List<Article>    返回类型 
	 * @throws
	 */
	List<Article> selectByHot();
	/**
	 * 
	 * @Title: selectListByChannelIdAndCateId 
	 * @Description: 根据频道id和分类id查询文章列表
	 * @param @param channelId
	 * @param @param cateId
	 * @param @return    设定文件 
	 * @return List<Article>    返回类型 
	 * @throws
	 */
	List<Article> selectListByChannelIdAndCateId(@Param("channelId")Integer channelId, @Param("cateId")Integer cateId);
	/**
	 * 
	 * @Title: selectNewList 
	 * @Description: 查询最新文章
	 * @param @param i
	 * @param @return    设定文件 
	 * @return List<Article>    返回类型 
	 * @throws
	 */
	List<Article> selectNewList(@Param("num")int num);
	/**
	 * 
	 * @Title: addHits 
	 * @Description: TODO添加点击量
	 * @param @param id    设定文件 
	 * @return void    返回类型 
	 * @throws
	 */
	void addHits(@Param("id")Integer id);
}