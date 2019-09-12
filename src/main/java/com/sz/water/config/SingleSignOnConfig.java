/**
 * 
 */
package com.sz.water.config;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.shiro.session.Session;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sz.water.util.AppUtils;

/** 
* @author 作者 梁泽祥: 
* @version 创建时间：2019年8月2日 下午5:33:22 
* 类说明 
*/
/**
 * @Title: SingleSignOnConfig
 * @Description:
 * @author 梁泽祥
 * @date 2019年8月2日
 */
@Scope(value = "singleton")
@Component(value = "applicationContext")
public class SingleSignOnConfig implements HttpSessionListener {

	private Session tempSession = null;
	public Map<String, Session> map = new HashMap<>();
	public Map<String, String> maps = new HashMap<>();

	public boolean isLogin(String name) {
		HttpSession session = AppUtils.getHttpSession();
		try {

			if (maps.get(name) != null)
				return true;

		} catch (Exception e) {
			// 结束异常
		}
		if (maps.get(name) != null)
			maps.remove(name);

		maps.put(name, session.getId());
		return false;
	}

	public void pushMap(String name, Session session) {
		try {
			if ((tempSession = map.get(name)) != null)
				tempSession.stop();
		} catch (Exception e) {
			// 结束异常
		}
		map.put(name, session);
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
		HttpSession session = httpSessionEvent.getSession();
		if (session != null) {
			String name = "";
			for (Map.Entry<String, String> entry : maps.entrySet()) {
				if (session.getId().equals(entry.getValue())) {
					name = entry.getKey();
				}
			}
			maps.remove(name);
		}
	}
}
