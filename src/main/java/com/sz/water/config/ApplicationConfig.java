package com.sz.water.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.sz.water.bean.MyRealm;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;

@Configuration
public class ApplicationConfig implements WebMvcConfigurer {

	@Bean
	public ServletListenerRegistrationBean listenerRegist() {
		ServletListenerRegistrationBean srb = new ServletListenerRegistrationBean();
		srb.setListener(new SingleSignOnConfig());
		System.out.println("listener");
		return srb;
	}

	// 下面两个方法对 注解权限起作用有很大的关系，请把这两个方法，放在配置的最上面
	@Bean(name = "lifecycleBeanPostProcessor")
	public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	@Bean
	public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator autoProxyCreator = new DefaultAdvisorAutoProxyCreator();
		autoProxyCreator.setProxyTargetClass(true);
		return autoProxyCreator;
	}

	// 将自己的验证方式加入容器
	@Bean
	public MyRealm myRealm() {
		System.out.println("注入 realm");
		MyRealm myRealm = new MyRealm();
		return myRealm;
	}

	// 配置shiro session 的一个管理器
	@Bean(name = "sessionManager")
	public DefaultWebSessionManager getDefaultWebSessionManager() {
		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
		// 设置session过期时间
		sessionManager.setGlobalSessionTimeout(60 * 60 * 1000);
		return sessionManager;
	}

	@Bean(name = "securityManager")
	public DefaultWebSecurityManager getDefaultWebSecurityManager() {
		DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
		defaultWebSecurityManager.setRealm(myRealm());
		// defaultWebSecurityManager.setSessionManager( getDefaultWebSessionManager() );
		return defaultWebSecurityManager;
	}

	@Bean
	public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor(
			DefaultWebSecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
		advisor.setSecurityManager(securityManager);
		return advisor;
	}

	@Bean
	public ShiroFilterFactoryBean getShiroFilterFactoryBean(SecurityManager securityManager) {
		ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
		factoryBean.setSecurityManager(securityManager);

		// 设置安全管理器
		Map<String, String> filterMap = new HashMap<>();
		// 权限
		filterMap.put("/pipe/makepipe", "perms[personal_Projects]");
		filterMap.put("/pipe/showpipe", "perms[Projects:view]");
		filterMap.put("/pipe/showpipeforapi", "perms[censuim3D_show]");
		filterMap.put("/pipe/insert", "perms[personal_Projects]");
		filterMap.put("/pipe/update", "perms[personal_Projects]");
		filterMap.put("/pipe/delete", "perms[personal_Projects]");

		filterMap.put("/person/**", "authc");
		filterMap.put("/device/**", "authc");
		filterMap.put("/company/**", "authc");
		filterMap.put("/user/userManage/**", "authc");
		filterMap.put("/project/**", "authc");
		filterMap.put("/item/**", "authc");
		filterMap.put("/file/**", "authc");
		filterMap.put("/user/center", "authc");
		filterMap.put("/pipeGenom/**", "authc");
		filterMap.put("/product/**", "authc");
		filterMap.put("/permis/**", "authc");
		filterMap.put("/userserial/**", "authc");
		filterMap.put("/role/**", "authc");

		factoryBean.setFilterChainDefinitionMap(filterMap);
		// 配置跳转的登录页面
		factoryBean.setLoginUrl("/user/login");
		// 设置未授权提示页面
		factoryBean.setUnauthorizedUrl("/comm/unauthorized");
		return factoryBean;
	}

	/** 定义识图控制器 */
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/person/insertview").setViewName("person/insert");
		registry.addViewController("/device/insertview").setViewName("device/insert");
		/** 设置注册和登录跳转页面 */
		registry.addViewController("*/loginview").setViewName("user/login");
		registry.addViewController("*/logonview").setViewName("user/logon");
		/** 设置操作成功和失败跳转页面 */
		registry.addViewController("*/complete").setViewName("user/complete");
		registry.addViewController("*/success").setViewName("user/success");
		registry.addViewController("*/failure").setViewName("user/failure");
		registry.addViewController("/success").setViewName("user/success");
		registry.addViewController("/failure").setViewName("user/failure");
	}

	@Bean
	public ShiroDialect shiroDialect() {
		ShiroDialect dialect = new ShiroDialect();
		return dialect;
	}

}
