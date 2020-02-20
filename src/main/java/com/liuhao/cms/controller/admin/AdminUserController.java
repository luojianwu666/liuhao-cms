package com.liuhao.cms.controller.admin;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bawei.luojianwu.StringUtil;
import com.liuhao.cms.common.CmsConstant;
import com.liuhao.cms.common.CmsMd5Util;
import com.liuhao.cms.common.JsonResult;
import com.liuhao.cms.pojo.User;
import com.liuhao.cms.service.UserService;
/**
 * 
 * @author 刘浩
 * @Title: AdminUserController.java 
 * @Package com.liuhao.cms.controller.admin 
 * @Description: 后台管理中用户（user）的控制器
 * @date 2019年12月17日 下午3:04:27
 */
@Controller
@RequestMapping("/admin/user/")
public class AdminUserController {
	@Autowired
	private UserService userService;
	/**
	 * @Title: login   
	 * @Description: 后台登录接口   
	 * @param: @param user
	 * @param: @return      
	 * @return: Object      
	 * @throws
	 */
	@RequestMapping("login")
	@ResponseBody
	public Object login(User user,HttpSession session) {
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
		//是否管理员
		if(!userInfo.isAdmin()) {
			return JsonResult.fail(1000, "权限不够");
		}
		//判断密码
		String string2md5 = CmsMd5Util.string2MD5(user.getPassword());
		if(string2md5.equals(userInfo.getPassword())) {
			session.setAttribute(CmsConstant.UserAdminSessionKey, userInfo);
			return JsonResult.sucess();
		}
		return JsonResult.fail(500, "未知错误");
	}
	/**
	 * 
	 * @Title: logout 
	 * @Description: 用户注销功能
	 * @param @param response
	 * @param @param session
	 * @param @return    设定文件 
	 * @return Object    返回类型 
	 * @throws
	 */
	@RequestMapping("logout")
	public Object logout(HttpServletResponse response,HttpSession session) {
		//session对象存储
		session.removeAttribute(CmsConstant.UserAdminSessionKey);
		return "redirect:/admin/";
	}
}
