/**
 * 
 */
package com.sz.water.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

/** 
* @author 作者 梁泽祥: 
* @version 创建时间：2019年7月27日 上午11:59:49 
* 类说明 
*/
/**
 * @Title: ExceptionConfig
 * @Description:
 * @author 梁泽祥
 * @date 2019年7月27日
 */
@Configuration
public class ExceptionConfig {
	@Bean
	public SimpleMappingExceptionResolver resolver() {
		SimpleMappingExceptionResolver resolver = new SimpleMappingExceptionResolver();
		Properties properties = new Properties();
		properties.setProperty("org.apache.shiro.authz.UnauthorizedException", "/errorpage/401");
		resolver.setExceptionMappings(properties);
		return resolver;
	}
}
