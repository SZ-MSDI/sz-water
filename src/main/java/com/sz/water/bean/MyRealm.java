package com.sz.water.bean;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.context.annotation.Scope;

import com.sz.water.config.SingleSignOnConfig;
import com.sz.water.util.AppUtils;

@Scope(value = "prototype")
public class MyRealm extends AuthorizingRealm {

//	@Autowired
//	@Lazy
//	private UserBiz userBiz;
//	@Autowired
//	@Lazy
//	private RoleBiz roleBiz;
//	@Autowired
//	@Lazy
//	private UserRoleBiz userRoleBiz;
//	@Autowired
//	@Lazy
//	private RolePermisBiz rolePermisBiz;
	@Resource
	private SingleSignOnConfig singleSignOnConfig;

	private Map<String, Object> map = null;

	/** 执行授权逻辑 */
	public AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection collection) {
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//		User user = (User) collection.getPrimaryPrincipal();
//
//		Set<String> roles = new HashSet<String>();
//		List<UserRole> rolesByUserID = userRoleBiz.getRolesByUserID(user.getId());
//		List<Integer> roleidList = new ArrayList<Integer>();
//
//		for (UserRole userRole : rolesByUserID) {
//			roleidList.add(userRole.getRoleId());
//			roles.add(userRole.getRoleCode());
//		}
//		List<RolePermis> rolePermis = rolePermisBiz.getRolePermisByRoleID(roleidList);
//		Set<String> permisSet = new HashSet<String>();
//		for (RolePermis permis : rolePermis) {
//			permisSet.add(permis.getPermisCode());
//		}
//		info.addStringPermissions(permisSet);
//		info.setRoles(roles);

		return info;
	}

	/** 执行认证逻辑 */
	public AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) {
		AuthenticationInfo info = null;
		UsernamePasswordToken tempToken = (UsernamePasswordToken) token;
		String username = tempToken.getUsername();
		String password = new String((char[]) tempToken.getCredentials());
		map = AppUtils.getMap("username", username, "password", password);
//		User user = userBiz.findInfoUser(map);
//		if (StringUtils.isEmpty(user))
//			throw new IncorrectCredentialsException();
//		Subject currentUser = SecurityUtils.getSubject();
//		if (singleSignOnConfig.isLogin(user.getUsername()))
//			throw new IncorrectCredentialsException();
//		AppUtils.pushMap("user", user);

//		info = new SimpleAuthenticationInfo(user, password, "");
		return info;
	}

}
