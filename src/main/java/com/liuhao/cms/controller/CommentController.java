package com.liuhao.cms.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.liuhao.cms.common.CmsConstant;
import com.liuhao.cms.common.JsonResult;
import com.liuhao.cms.pojo.Comment;
import com.liuhao.cms.pojo.ShouCang;
import com.liuhao.cms.pojo.User;
import com.liuhao.cms.service.ArticleService;
import com.liuhao.cms.service.CommentService;

@Controller
@RequestMapping("/comment/")
public class CommentController {

	@Autowired
	private CommentService commentService;
	@Autowired
	private ArticleService articleService;
	
	@RequestMapping("shouCanglist")
	public String article(ShouCang shouCang,Model model,HttpSession session
			) {
		
		User userInfo = (User) session.getAttribute(CmsConstant.UserSessionKey);
		
	  List<ShouCang> list= commentService.shoucangList(userInfo.getId());
		
		model.addAttribute("list", list);
		for (ShouCang shouCang2 : list) {
			System.out.println(shouCang2);
		}
		
		
		
		return "user/shoucang";
	}
	
	
	
	@RequestMapping(value = "add",method = RequestMethod.POST)
	public @ResponseBody JsonResult add(Comment comment,HttpSession session) {
		//获取用户的对象
		User userInfo = (User) session.getAttribute(CmsConstant.UserSessionKey);
		if(userInfo==null) {
			return JsonResult.fail(CmsConstant.unLoginErrorCode, "用户未登录");
		}
		comment.setUserid(userInfo.getId());
		
		boolean result = commentService.add(comment);
		if(result) {
			return JsonResult.sucess();
		}
		return JsonResult.fail(10000, "未知错误");
	}
	
	
	@RequestMapping(value = "list",method = RequestMethod.GET)
    public String getPageInfo(@RequestParam(value = "articleId")Integer articleId,
    		@RequestParam(value = "pageNum",defaultValue = "1")int pageNum,Model model) {
		
		PageInfo<Comment> pageInfo = commentService.getPageInfo(pageNum, articleId);
		
		model.addAttribute("pageInfo", pageInfo);
		return "comment/list";
	}	
	@ResponseBody
	@RequestMapping(value = "del",method = RequestMethod.GET)
	public JsonResult delComment(Integer id) {
		
		boolean flag = commentService.delete(id);
		if(flag) {
			return JsonResult.sucess();
		}
		return JsonResult.fail(500, "未知错误");
	}
	
	@ResponseBody
	@RequestMapping(value = "shoucangdel",method = RequestMethod.GET)
	public JsonResult delshoucang(Integer id) {
		System.out.println(id+"----------------------");
		
		boolean flag = commentService.deleteshoucang(id);
		if(flag) {
			return JsonResult.sucess();
		}
		return JsonResult.fail(500, "未知错误");
	}
	
}
