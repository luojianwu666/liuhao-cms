package com.liuhao.cms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.liuhao.cms.pojo.Collect;

public interface CollectDao {

	List<Collect> selectByUserId(@Param("collect")Collect collect);

	int deleteById(Integer id);

	int insert(@Param("collect")Collect collect);

	
}
