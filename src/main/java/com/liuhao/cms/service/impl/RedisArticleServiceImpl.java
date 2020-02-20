package com.liuhao.cms.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.liuhao.cms.dao.ArticleDao;
import com.liuhao.cms.dao.ArticleRepository;
import com.liuhao.cms.dao.CategoryDao;
import com.liuhao.cms.dao.ChannelDao;
import com.liuhao.cms.pojo.Article;
import com.liuhao.cms.pojo.Category;
import com.liuhao.cms.pojo.Channel;
import com.liuhao.cms.service.ArticleService;
@Service("articleService")
public class RedisArticleServiceImpl implements ArticleService{
	@Autowired
	private ArticleDao articleDao;
	@Autowired
	private ChannelDao channelDao;
	@Autowired
	private CategoryDao categoryDao;
	@Autowired
	private  RedisTemplate<String, Article> redisTemplate;
	@Autowired
	private ArticleRepository articleRepository;
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
     //保存文章中的内容

	public boolean save(Article article) {
		//做验证  当新添加文章时  删除  发布时间  等状态设为默认值
		if(article.getId()==null) {
			article.setDeleted(0);
			article.setCreated(new Date());
			article.setUpdated(new Date());
			article.setCommentcnt(0);
			article.setHits(0);
			article.setHot(0);
			//添加文章
			//将文章保存到redis中，还要往Kafka发送消息Redis的key。
			redisTemplate.opsForValue().set(article.getTitle(), article);
			kafkaTemplate.sendDefault("kafkaSaveArticle",article.getTitle());
			
			//articleDao.insert(article);
			//article.setId(article.getId());
			//给es中添加数据
			//articleRepository.save(article);
		}else {
			//如果有值 则对文章进行修改 且修改  修改时间
			article.setUpdated(new Date());
			articleDao.update(article);
			
		}
		return true;
	}

	@Override
	public PageInfo<Article> getPageInfo(Article article, int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updateStatus(Integer id, int status) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addHot(Integer id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Channel> getChannelList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Category> getCateListByChannelId(Integer channelId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delByIds(String ids) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAllCheck(String ids) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Article> getListByChannelId(Integer channelId, Integer id, int i) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageInfo<Article> getHotList(int pageNum) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageInfo<Article> getListByChannelIdAndCateId(Integer channelId, Integer cateId, Integer pageNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Article> getNewList(int i) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Article getById(Integer id, String remoteAddr) {
		// TODO Auto-generated method stub
		return null;
	}

}

