package com.liuhao.cms.controller.admin;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.liuhao.cms.pojo.Article;
import com.liuhao.cms.pojo.Channel;
import com.liuhao.cms.pojo.Links;
import com.liuhao.cms.pojo.User;
import com.liuhao.cms.service.ArticleService;
import com.liuhao.cms.service.LinksService;
import com.liuhao.cms.service.UserService;
import com.liuhao.cms.service.impl.ArticleServiceImpl;
/**
 * 
 * @author 刘浩
 * @Title: AdminController.java 
 * @Package com.liuhao.cms.controller.admin 
 * @Description: 后台管理的控制器
 * @date 2019年12月17日 下午3:09:32
 */
@Controller
@RequestMapping("/admin/")
public class AdminController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private ArticleServiceImpl articleService;
	
	@Autowired
	private LinksService linksService;
	
	
	/**
	 * @Title: login   
	 * @Description: 跳转到后台登录 页面
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	@RequestMapping("/")
	public String login() {
		return "admin/login";
	}
	/**
	 * @Title: home   
	 * @Description: 跳转到后台首页  
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	@RequestMapping("/home")
	public String home() {
		return "admin/home";
	}
	/**
	 * @Title: welcome   
	 * @Description: 跳转到后台欢迎页面   
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	@RequestMapping("/welcome")
	public String welcome() {
		return "admin/welcome";
	}
	/**
	 * @Title: user   
	 * @Description: 用户管理 列表
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	@RequestMapping("/user")
	public String user(User user,Model model,
			@RequestParam(value="pageNum",defaultValue="1") int pageNum,@RequestParam(value="pageSize",defaultValue="3") int pageSize) {
		PageInfo<User> pageInfo = userService.getPageInfo(user,pageNum,pageSize);
		model.addAttribute("pageInfo", pageInfo);
		return "admin/user";
	}
	/**
	 * @Title: locked   
	 * @Description: 用户管理 列表中禁用用户   
	 * @param: @param userId
	 * @param: @return      
	 * @return: boolean      
	 * @throws
	 */
	@RequestMapping("/user/locked")
	@ResponseBody
	public boolean locked(Integer userId) {
		boolean locked = userService.locked(userId);
		return locked;
	}
	/**
	 * @Title: unLocked   
	 * @Description:用户管理 列表中启用  
	 * @param: @param userId
	 * @param: @return      
	 * @return: boolean      
	 * @throws
	 */
	@RequestMapping("/user/unLocked")
	@ResponseBody
	public boolean unLocked(Integer userId) {
		boolean locked = userService.unLocked(userId);
		return locked;
	}
	
	/**
	 * @Title: article   
	 * @Description: 文章管理页面    
	 * @param: @param article
	 * @param: @param model
	 * @param: @param pageNum
	 * @param: @param pageSize
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	@RequestMapping("/article")
	public String article(Article article,Model model,
			@RequestParam(value="pageNum",defaultValue="1") int pageNum,@RequestParam(value="pageSize",defaultValue="3") int pageSize) {
		//设置文章状态
		article.setStatusIds("0,-1,1");
		PageInfo<Article> pageInfo = articleService.getPageInfo(article,pageNum,pageSize);
		model.addAttribute("pageInfo", pageInfo);
		List<Channel> channelList = articleService.getChannelList();
		model.addAttribute("channelList", channelList);
		return "admin/article";
	}
	
	/**
	 * @Title: updateArticleStatus   
	 * @Description: 文章管理页面   修改文章状态  审核   
	 * @param: @param article
	 * @param: @return      
	 * @return: boolean      
	 * @throws
	 */
	@RequestMapping("/article/update/status")
	@ResponseBody
	public boolean updateArticleStatus(Article article) {
		return articleService.updateStatus(article.getId(), article.getStatus());
	}
	/**
	 * @Title: addHot  
	 * @Description: 文章管理页面 对文章加热
	 * @param: @param article
	 * @param: @return      
	 * @return: boolean      
	 * @throws
	 */
	@RequestMapping("/article/addHot")
	@ResponseBody
	public boolean addHot(Article article) {
		return articleService.addHot(article.getId());
	}
	/**
	 * 
	 * @Title: links 
	 * @Description: 友情链接地址
	 * @param @return    设定文件 
	 * @return String    返回类型 
	 * @throws
	 */
	@RequestMapping("/links")
	public String links(Links links,@RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum,Model model) {
		
	PageInfo<Links> pageInfo = linksService.getPageInfo(links,pageNum);
	model.addAttribute("pageInfo", pageInfo);
		return "/admin/links";
	}
	

}
