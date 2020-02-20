package com.liuhao.cms.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.liuhao.cms.common.JsonResult;
import com.liuhao.cms.pojo.Settings;
import com.liuhao.cms.service.SettingsService;
/**
 * 
 * @author 刘浩
 * @Title: SettingController.java 
 * @Package com.liuhao.cms.controller.admin 
 * @Description:  后台管理中系统设置
 * @date 2019年12月17日 下午3:14:21
 */
@Controller
@RequestMapping("/admin/")
public class SettingController {
	@Autowired
	private SettingsService settingsService;
	/**
	 * @Title: settings   
	 * @Description: 系统设置   
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	@RequestMapping("/settings")
	public String settings(Model model) {
		Settings settings = settingsService.get();
		model.addAttribute("settings", settings);
		return "admin/settings";
	}
	/**
	 * 
	 * @Title: save 
	 * @Description: 对修改的系统设置进行保存
	 * @param @param model
	 * @param @param settings
	 * @param @return    设定文件 
	 * @return Object    返回类型 
	 * @throws
	 */
	@RequestMapping("/settings/save")
	@ResponseBody
	public Object save(Model model,Settings settings) {
        System.out.println(settings);
		boolean result = settingsService.save(settings);
		if(result) {
			return JsonResult.sucess();
		}
		return JsonResult.fail(500, "修改失败");
	}
}
