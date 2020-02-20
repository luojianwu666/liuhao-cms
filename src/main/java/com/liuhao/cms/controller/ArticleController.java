package com.liuhao.cms.controller;

import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.liuhao.cms.common.CmsConstant;
import com.liuhao.cms.common.JsonResult;
import com.liuhao.cms.pojo.Article;
import com.liuhao.cms.pojo.Category;
import com.liuhao.cms.pojo.Channel;
import com.liuhao.cms.pojo.User;
import com.liuhao.cms.service.impl.ArticleServiceImpl;
/**
 * 
 * @author 刘浩
 * @Title: ArticleController.java 
 * @Package com.liuhao.cms.controller 
 * @Description: 前台的文章控制器
 * @date 2019年12月17日 下午3:19:46
 */
@Controller
@RequestMapping("/article/")
public class ArticleController {
	@Autowired
	private ArticleServiceImpl articleService;
	private Logger logger = LoggerFactory.getLogger(getClass());

	/*
	 * @Autowired private ThreadPoolExecutor executor;
	 */
	/**
	 * @Title: add   
	 * @Description: 前台用户  发布文章   
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	@RequestMapping(value="add",method=RequestMethod.GET)
	public String add(Integer id,Model model) {
		logger.info("articleId:{}",id);
		/*
		 * executor.execute(new Runnable() {
		 * 
		 * @Override public void run() { // TODO Auto-generated method stub
		 * 
		 * } });
		 */
		if(id!=null) {
			Article article = articleService.getById(id,null);
			logger.info(article.toString());
			model.addAttribute("article", article);
			//根据频道的id对样式表进行查询
			List<Category> cateList = articleService.getCateListByChannelId(article.getChannelId());
			model.addAttribute("cateList", cateList);
		}
		List<Channel> channelList = articleService.getChannelList();
		model.addAttribute("channelList", channelList);
		return "article/add";
	}
	/**
	 * 
	 * @Title: add 
	 * @Description: 前台用户  发布文章   添加文章
	 * @param @param article
	 * @param @param model
	 * @param @param session
	 * @param @return    设定文件 
	 * @return JsonResult    返回类型 
	 * @throws
	 */
	@RequestMapping(value="add",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult add(Article article,Model model,HttpSession session) {
		//根据前台session中的对象查询user
		User userInfo = (User)session.getAttribute(CmsConstant.UserSessionKey);
		//session的用户对象为空时，请重新登录
		if(userInfo==null) {
			return JsonResult.fail(CmsConstant.unLoginErrorCode, "未登录");
		}
		//将查询到的用户id赋给article中的user_id
		article.setUserId(userInfo.getId());
		//保存文章
		boolean result = articleService.save(article);
		return JsonResult.sucess(result);
	}
	/**
	 * @Title: getCateList   
	 * @Description: 根据频道Id查询分类列表   
	 * @param: @param channelId
	 * @param: @param model
	 * @param: @param session
	 * @param: @return      
	 * @return: JsonResult      
	 * @throws
	 */
	@RequestMapping(value="getCateList",method=RequestMethod.GET)
	@ResponseBody
	public JsonResult getCateList(Integer channelId,Model model,HttpSession session) {
		return JsonResult.sucess(articleService.getCateListByChannelId(channelId));
	}
	/**
	 * @Title: delByIds   
	 * @Description: 修改文章   
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	@RequestMapping("delByIds")
	public @ResponseBody JsonResult delByIds(String ids) {
		if(ids==null) {
			return JsonResult.fail(10001, "请选择删除的文章");
		}
		//已审核判断
		boolean isCheck = articleService.isAllCheck(ids);
		if(!isCheck) {
			return JsonResult.fail(10001, "请选择未审核的文章删除");
		}
		//删除
		boolean flag = articleService.delByIds(ids);
		if(flag) {
			return JsonResult.sucess();
		}
		
		return JsonResult.fail(500, "未知错误");
	}
}
