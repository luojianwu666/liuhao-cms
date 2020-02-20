package com.liuhao.cms.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.liuhao.cms.common.CmsConstant;
import com.liuhao.cms.common.JsonResult;
import com.liuhao.cms.pojo.Collect;
import com.liuhao.cms.pojo.User;
import com.liuhao.cms.service.CollectService;

@Controller
@RequestMapping("/collect/")
public class CollectController {

	
	@Autowired
	private CollectService collectService;
	
	@RequestMapping(value = "add",method = RequestMethod.POST)
	public @ResponseBody JsonResult add(Collect collect,HttpSession session) {
		collect.setText("123456");
		//获取用户的对象
		User userInfo = (User) session.getAttribute(CmsConstant.UserSessionKey);
		if(userInfo==null) {
			return JsonResult.fail(CmsConstant.unLoginErrorCode, "用户未登录");
		}
		collect.setUserId(userInfo.getId());
		
		boolean result = collectService.add(collect);
		if(result) {
			return JsonResult.sucess();
		}
		return JsonResult.fail(10000, "未知错误");
	}
	@ResponseBody
	@RequestMapping(value = "checkUrl",method = RequestMethod.POST)
	public boolean checkUrl(String url) {
		System.err.println(url);
	 return  collectService.saveHttpUrl(url);
	
	}
	
	
	@ResponseBody
	@RequestMapping(value = "del",method = RequestMethod.GET)
	public JsonResult delCollect(Integer id,HttpSession session) {
		User userInfo = (User) session.getAttribute(CmsConstant.UserSessionKey);
		
		boolean flag = collectService.delete(id);
		if(flag) {
			return JsonResult.sucess();
		}
		return JsonResult.fail(500, "未知错误");
	}
}
