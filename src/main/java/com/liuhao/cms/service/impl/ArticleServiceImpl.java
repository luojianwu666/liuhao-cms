package com.liuhao.cms.service.impl;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.druid.sql.dialect.oracle.ast.expr.OracleSizeExpr.Unit;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liuhao.cms.dao.ArticleDao;
import com.liuhao.cms.dao.ArticleRepository;
import com.liuhao.cms.dao.CategoryDao;
import com.liuhao.cms.dao.ChannelDao;
import com.liuhao.cms.pojo.Article;
import com.liuhao.cms.pojo.Category;
import com.liuhao.cms.pojo.Channel;
import com.liuhao.cms.service.ArticleService;
@Service
public class ArticleServiceImpl implements ArticleService {
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
	
	
	@Override
	public PageInfo<Article> getPageInfo(Article article, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<Article> articleList = articleDao.select(article);
		return new PageInfo<>(articleList);
	}
	/*文章状态设置
	 * @Override public boolean updateStatus(Integer id, int status) { return
	 * articleDao.updateStatus(id, status)>0; }
	 */
	//使用reids后的文章状态设置
	@Override 
	public boolean updateStatus(Integer id, int status) { 
		
		boolean flag = articleDao.updateStatus(id, status)>0;
		//发布新文章时，审核文章通过则清空reids中的内容，重新进行添加
		//1.状态是必须审核通过  2.必须操作成功
		if(flag && status==1) {
		  //redis中的审核
		   redisTemplate.delete("article_new");
		  //es中的审核
		     Article article = articleDao.selectById(id);
		   // article.setStatus(1);
		   // articleRepository.save(article);
		  //kafka中的审核通过
		    String jsonString = JSON.toJSONString(article);
		    kafkaTemplate.sendDefault("updateStatus",jsonString);
		    System.err.println("审核通过");
		}
				
		return flag;
		}

	/*原来的文章加热
	 * @Override public boolean addHot(Integer id) { return articleDao.addHot(id)>0;
	 * }
	 */
	@Override
	public boolean addHot(Integer id) {
		
		boolean flag = articleDao.addHot(id)>0;
	    //对文章进行加热时，清空redis中的数据，重新对数据库进行添加
		//加热成功，清空redis
		if(flag) {
			redisTemplate.delete("article_hot");
		}
		
	
	  return flag;
	}

	@Override
	public List<Channel> getChannelList() {
		return channelDao.select(null);
	}

	
	/*
	 * @Override public Article getById(Integer id) { //当用户进行访问该页面是，将该篇文章的阅读量+1
	 * Article article = articleDao.selectById(id);
	 * 
	 * return article; }
	 */
	
	
	@Override
	public Article getById(Integer id,String remoteAddr) {
		//当用户进行访问该页面是，将该篇文章的阅读量+1
		Article article = articleDao.selectById(id);
		
		  boolean flag = redisTemplate.hasKey("Hits_$"+id+"_$"+remoteAddr);
		  //flag为true 则有key
		  if(!flag) {
			//将文章的点击量+1
			  articleDao.addHits(id);
			  ValueOperations<String, Article> opsForValue = redisTemplate.opsForValue();
			 //设定有效时长
			  opsForValue.set("Hits_$"+id+"_$"+remoteAddr, null, 10, TimeUnit.SECONDS);
		  }
  		  		
		/*
		 * if(article!=null) { //将文章的点击量+1
		 * String jsonString = JSON.toJSONString(article);
		 * kafkaTemplate.sendDefault("addCount",jsonString); }
		 */
		
		return article;
	}
     //保存文章中的内容
	@Override
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
			//articleDao.insert(article);
			//article.setId(article.getId());
			String jsonString = JSON.toJSONString(article);
			 kafkaTemplate.sendDefault("add", jsonString);
			//给es中添加数据
			//articleRepository.save(article);
		}else {
			//如果有值 则对文章进行修改 且修改  修改时间
			article.setUpdated(new Date());
			articleDao.update(article);
			
		}
		return true;
	}
     //根据频道id查询样式表集合
	@Override
	public List<Category> getCateListByChannelId(Integer channelId) {
		return categoryDao.selectListByChannelId(channelId);
	}

	@Override
	public boolean delByIds(String ids) {
		
		int i = articleDao.deleteByIds(ids);
		//sql数据库已删除
		if(i>0) {
			//es数据库进行操作
			String[] split = ids.split(",");
			for (String s : split) {
				Integer id = Integer.valueOf(s);
				articleRepository.deleteById(id);
				System.err.println("删除成功");
			}
			
			
		}
		
		return i>0;
				
	}

	@Override
	public boolean isAllCheck(String ids) {
        
		List<Article> articleList = articleDao.selectByIds(ids);
		for (Article article:articleList) {
			if(article.getStatus()==1) {
				return false;
			}
		}
		return true;
	}

	@Override
	public List<Article> getListByChannelId(Integer channelId, Integer aritcleId, int num) {
		
		return articleDao.selectListByChannelId(channelId,aritcleId,num);
	}

	/*原来的获取加热文章的列表
	 * @Override public PageInfo<Article> getHotList(int pageNum) {
	 * 
	 * PageHelper.startPage(pageNum, 1); List<Article> articleList =
	 * articleDao.selectByHot(); return new PageInfo<>(articleList); }
	 */
	//使用redis缓存热点文章
	/*
	 * @Override public PageInfo<Article> getHotList(int pageNum) {
	 * 
	 * //设置每页显示的条数 int pageSize = 6; //获取模板redis模板 ListOperations<String, Article>
	 * opsForList = redisTemplate.opsForList();
	 * 
	 * PageInfo<Article> pageInfo = null;
	 * 
	 * //如何判断第一次，判断redis中有没有对应的key值，没有，则为第一次
	 * if(!redisTemplate.hasKey("article_hot")) { List<Article> articleList =
	 * articleDao.selectByHot(); opsForList.rightPushAll("article_hot",
	 * articleList); //从mysql查询分页数据 PageHelper.startPage(pageNum, pageSize);
	 * List<Article> list = articleDao.selectByHot();
	 * 
	 * pageInfo = new PageInfo<>(list); } else {
	 * 
	 * List<Article> list = opsForList.range("article_hot",(pageNum - 1) * pageSize,
	 * pageNum * pageSize - 1); //将list数据封装到page对象中 Page<Article> page_list = new
	 * Page(pageNum,pageSize); page_list.addAll(list); //获取数据总条数 Long total =
	 * opsForList.size("article_hot"); page_list.setTotal(total);
	 * 
	 * //封装pageInfo对象 pageInfo = new PageInfo<>(page_list);
	 * 
	 * }
	 * 
	 * return pageInfo; }
	 */
	 //月考的获取加热文章的列表
	  @Override 
	  public PageInfo<Article> getHotList(int pageNum) {
	  
	  PageHelper.startPage(pageNum, 5);
	  List<Article> articleList =articleDao.selectByHot(); 
	//实现首页热门文章第一页记录Redis缓存功能，有效期5分钟
	  if(pageNum==1) {
		  for (int i = 0; i < articleList.size(); i++) {
			  redisTemplate.opsForValue().set("hotArticle_"+i, articleList.get(i), 5, TimeUnit.MINUTES);
		}
		  }
	  return new PageInfo<>(articleList); 
	  }
	 
	
	  

	@Override
	public PageInfo<Article> getListByChannelIdAndCateId(Integer channelId, Integer cateId, Integer pageNo) {
		PageHelper.startPage(pageNo, 1);
		List<Article> articleList = articleDao.selectListByChannelIdAndCateId(channelId,cateId);
		
		return new PageInfo<>(articleList);
	}

	/*原来的最新文章
	 * @Override public List<Article> getNewList(int i) { // TODO Auto-generated
	 * method stub return articleDao.selectNewList(i); }
	 */
	//使用redis缓存最新文章
	@Override
	public List<Article> getNewList(int i) {
		List<Article> list = null;
		ListOperations<String, Article> opsForList = redisTemplate.opsForList();
		//第一次访问从mysql数据库查出存入redis中
		//如何判断第一次，判断redis中有没有对应的key值，没有，则为第一次
		if(!redisTemplate.hasKey("article_new")) {
			//从mysql中获取数据
		    list = articleDao.selectNewList(i);
		    //存入redis中
		    opsForList.rightPushAll("article_new", list);
		}
		else {
			//从数据中存入redis中
			//非第一次，直接从redis中获取数据（如果新增了文章，则清空redis，在审核通过处写）
		    list = opsForList.range("article_new", 0, -1);
		}
		return list;
	}

}
