package com.liuhao.cms.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.liuhao.cms.dao.ArticleDao;
import com.liuhao.cms.dao.ArticleRepository;
import com.liuhao.cms.pojo.Article;
import com.liuhao.cms.service.ArticleService;
import com.liuhao.cms.service.impl.ArticleServiceImpl;
@Component
public class KafkaConsumerListener implements MessageListener<String, String> {

	@Autowired
	private ArticleDao articleDao;
	@Autowired
	private ArticleServiceImpl articleServiceImpl;
	@Autowired
	private RedisTemplate<String, Article> redisTemplate;
	@Autowired
	private ArticleRepository articleRepository;
	@Override
	public void onMessage(ConsumerRecord<String, String> data) {
		// TODO Auto-generated method stub
		
		String key = data.key();
		System.err.println(key);
		if("add".equals(key)) {
			System.err.println("--------------------------------------------------------------------------------------------");
			String value = data.value();
			Article article = JSON.parseObject(value, Article.class);
			
			articleDao.insert(article);
			System.err.println("添加成功");
			
		}
		else if("addCount".equals(key)) {
			
			String value = data.value();
			Article article = JSON.parseObject(value, Article.class);
			articleDao.addHits(article.getId());
			System.err.println("点击量加一");
		}
		else if("kafkaSaveArticle".equals(key)) {
			System.out.println("-------------------------------------------");
			//获取redis的key
			String redisKey = data.value();
			//从redis中获取article
			Article article = redisTemplate.opsForValue().get(redisKey);
			//保存到Mysql数据库
			boolean flag = articleServiceImpl.save(article);
			//保存成功后，方法内部就删除Redis中文章
			if(flag) {
				redisTemplate.delete(redisKey);
				System.err.println(article.getTitle()+"已经导入完毕");
			}
		}
		else if("updateStatus".equals(key)) {
		   //获取审核是的kafka
			String value = data.value();
			Article article = JSON.parseObject(value, Article.class);
			//将审核完成的数据添加到es
			articleRepository.save(article);
		}
	}

}
