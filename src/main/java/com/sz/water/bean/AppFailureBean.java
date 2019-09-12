package com.sz.water.bean;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AppFailureBean implements ErrorController {

	@RequestMapping(value="/error")
    public String actionFailure() {
        return getErrorPath();
    }
	
	/** 设置错误跳转页面路径 */
	public String getErrorPath() {
		return "user/failure";
	}
}
