package com.liuhao.cms.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.liuhao.cms.pojo.Links;

public interface LinksService {
    /**
     * 
     * @Title: getPageInfo 
     * @Description: 链接表分页+模糊查询 
     * @param @param links
     * @param @param pageNum
     * @param @return    设定文件 
     * @return PageInfo<Links>    返回类型 
     * @throws
     */
	PageInfo<Links> getPageInfo(Links links, Integer pageNum);
    /**
     * 
     * @Title: selectById 
     * @Description: 根据id查询 进行回显
     * @param @param linksId
     * @param @return    设定文件 
     * @return Links    返回类型 
     * @throws
     */
	Links selectById(Integer linksId);
	/**
	 * 
	 * @Title: save 
	 * @Description: 添加或者修改
	 * @param @param links
	 * @param @return    设定文件 
	 * @return boolean    返回类型 
	 * @throws
	 */
	boolean save(Links links);
	/**
	 * 
	 * @Title: batchDel 
	 * @Description: 批量删除
	 * @param @param ids
	 * @param @return    设定文件 
	 * @return boolean    返回类型 
	 * @throws
	 */
	boolean batchDel(String ids);
	/**
	 * 
	 * @Title: select 
	 * @Description: 查询收藏链接列表
	 * @param @return    设定文件 
	 * @return List<Links>    返回类型 
	 * @throws
	 */
	List<Links> select();

}
