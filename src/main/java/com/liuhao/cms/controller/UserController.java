package com.liuhao.cms.controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bawei.luojianwu.StringUtil;
import com.github.pagehelper.PageInfo;
import com.liuhao.cms.common.CmsConstant;
import com.liuhao.cms.common.CmsMd5Util;
import com.liuhao.cms.common.CookieUtil;
import com.liuhao.cms.common.JsonResult;
import com.liuhao.cms.pojo.Article;
import com.liuhao.cms.pojo.Channel;
import com.liuhao.cms.pojo.Collect;
import com.liuhao.cms.pojo.Comment;
import com.liuhao.cms.pojo.User;
import com.liuhao.cms.service.ArticleService;
import com.liuhao.cms.service.CollectService;
import com.liuhao.cms.service.CommentService;
import com.liuhao.cms.service.UserService;
import com.liuhao.cms.service.impl.ArticleServiceImpl;

@Controller
@RequestMapping("/user/")
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private ArticleServiceImpl articleService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private CollectService collectService;
	/**
	 * @Title: login   
	 * @Description: 用户登录界面   
	 * @param: @return      
	 * @return: Object      
	 * @throws
	 */
	@RequestMapping(value="login",method=RequestMethod.GET)
	public Object login() {
		
		return "/user/login";
	}
	/**
	 * @Title: login   
	 * @Description: 用户登录接口   
	 * @param: @param user
	 * @param: @param session
	 * @param: @return      
	 * @return: Object    
	 * @throws
	 */
	@RequestMapping(value="login",method=RequestMethod.POST)
	@ResponseBody
	public Object login(User user,HttpSession session,HttpServletResponse response) {
		//判断用户名和密码
		if(StringUtil.isBlank(user.getUsername()) || StringUtil.isBlank(user.getPassword())) {
			return JsonResult.fail(1000, "用户名和密码不能为空");
		}
		//查询用户
		User userInfo = userService.getByUsername(user.getUsername());
		//用户为空
		if(userInfo==null) {
			return JsonResult.fail(1000, "用户名或密码错误");
		}
		//判断密码
		String string2md5 = CmsMd5Util.string2MD5(user.getPassword());
		if(string2md5.equals(userInfo.getPassword())) {
			//注册成功，将用户信息存进session对象
			session.setAttribute(CmsConstant.UserSessionKey, userInfo);
            //频道是否勾选记住登录
			if("1".equals(user.getIsMima())) {
				CookieUtil.addCookie(response, "username", user.getUsername(), null, null, 1*60*60*24);
			}
			
			return JsonResult.sucess();
		}
		return JsonResult.fail(500, "未知错误");
	}
	/**
	 * @Title: logout   
	 * @Description: 注销功能
	 * @param: @param response
	 * @param: @param session
	 * @param: @return      
	 * @return: Object      
	 * @throws
	 */
	@RequestMapping("logout")
	public Object logout(HttpServletResponse response,HttpSession session) {
		session.removeAttribute(CmsConstant.UserSessionKey);
		//退出时消除cookie
		CookieUtil.addCookie(response, "username", null, null, null, 0);
		return "redirect:/";
	}
	
	/**
	 * @Title: register   
	 * @Description: 用户注册页面   
	 * @param: @return      
	 * @return: Object      
	 * @throws
	 */
	@RequestMapping(value="register",method=RequestMethod.GET)
	public Object register() {
		return "/user/register";
	}
	/**
	 * @Title: register   
	 * @Description: 用户注册接口   
	 * @param: @param user
	 * @param: @param session
	 * @param: @return      
	 * @return: Object      
	 * @throws
	 */
	@RequestMapping(value="register",method=RequestMethod.POST)
	public @ResponseBody Object register(User user,HttpSession session) {
		//判断用户是否存在
		boolean result = userService.isExist(user.getUsername());
		if(result) {
			return JsonResult.fail(10001, "用户名已存在");
		}
		//用户注册
		boolean register = userService.register(user);
		if(register) {
			return JsonResult.sucess();
		}
		return JsonResult.fail(500, "未知错误");
	}
	
	@RequestMapping("center")
	public String center(HttpServletResponse response,HttpSession session) {
		return "user/center";
	}
	
	/**
	 * @Title: settings   
	 * @Description: 设置用户信息   
	 * @param: @param response
	 * @param: @param session
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	@RequestMapping(value="settings",method=RequestMethod.GET)
	public String settings(HttpServletResponse response,HttpSession session,Model model) {
		User userInfo = (User)session.getAttribute(CmsConstant.UserSessionKey);
		/** 查询用户信息 **/
		User user = userService.getByUsername(userInfo.getUsername());
		model.addAttribute("user", user);
		return "user/settings";
	}
	/**
	 * @Title: settings   
	 * @Description: 保存用户信息  
	 * @param: @param user
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	@RequestMapping(value="settings",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult settings(User user,HttpSession session) {
	//修改用户信息
			boolean result = userService.update(user);
			if(result) {
				//跟新session中的用户信息
				User userInfo = userService.getById(user.getId());
				session.setAttribute(CmsConstant.UserSessionKey, userInfo);
				return JsonResult.sucess();
			}
			return JsonResult.fail(100002, "修改失败");
	}
	//评论
	@RequestMapping("comment")
	public String comment(Comment comment,HttpSession session,Model model,
			@RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum) {
		     //设置用户id
		     User userInfo = (User) session.getAttribute(CmsConstant.UserSessionKey);
             comment.setUserid(userInfo.getId());		     
		   //查询评论
			PageInfo<Comment> pageInfo = commentService.getPageInfoByUid(comment,pageNum);
			model.addAttribute("pageInfo", pageInfo);
		
		return "user/comment";
	}
	//我的收藏
	@RequestMapping("collect")
	public String collect(Collect collect,HttpSession session,Model model,@RequestParam(value = "pageNum",defaultValue = "1")Integer PageNum) {
		  //获取用户id
		  User user = (User) session.getAttribute(CmsConstant.UserSessionKey);
		  collect.setUserId(user.getId());
		  //查询收藏
		  PageInfo<Collect> pageInfo =collectService.getPageInfoByUid(collect,PageNum);
		  
		  model.addAttribute("pageInfo", pageInfo);
		  return "user/collect";
	}
	
	
	
	@RequestMapping("article")
	public String article(Article article,Model model,HttpSession session,
			@RequestParam(value="pageNum",defaultValue="1") int pageNum,@RequestParam(value="pageSize",defaultValue="3") int pageSize) {
	    //设置用户Id
		User userInfo = (User)session.getAttribute(CmsConstant.UserSessionKey);
		article.setUserId(userInfo.getId());
		//查询文章
		PageInfo<Article> pageInfo = articleService.getPageInfo(article,pageNum,pageSize);
		model.addAttribute("pageInfo", pageInfo);
		List<Channel> channelList = articleService.getChannelList();
		model.addAttribute("channelList", channelList);
		return "user/article";
	}
	
	
	
	
	
	
}
