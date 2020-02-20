package com.liuhao.cms.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.liuhao.cms.common.JsonResult;
import com.liuhao.cms.pojo.Links;
import com.liuhao.cms.service.LinksService;

@Controller
@RequestMapping("/admin/links/")
public class LinksController {

	@Autowired
	private LinksService linksService;
	
	@RequestMapping(value = "edit",method = RequestMethod.GET)
	public String edit(Integer linksId,Model model) {
		//id为空，则进行添加功能，不为空则进行回显功能
		if(linksId!=null) {
		 Links links = linksService.selectById(linksId);
		 model.addAttribute("link", links);
		}
		
		return "admin/edit";
	}
	
	@RequestMapping(value = "save",method =RequestMethod.POST )
	public @ResponseBody JsonResult save(Links links,Model model) {
		
		 boolean flag = linksService.save(links);
		 if(flag) {
			 return JsonResult.sucess();
		 }
		 return JsonResult.fail(500, "未知错误");
	}
	
	
	@RequestMapping(value = "delByIds",method = RequestMethod.GET)
	public @ResponseBody JsonResult batchDel(@RequestParam("ids")String ids,Model model) {
		  System.out.println(ids+"-------------------------------------------");
		if(ids==""|| ids==null) {
			return JsonResult.fail(500, "你还没有选择数据");
		}
	    boolean result = linksService.batchDel(ids);
		if(result) {
			return JsonResult.sucess();
		}
	    return JsonResult.fail(10000, "未知错误");
	}
}
