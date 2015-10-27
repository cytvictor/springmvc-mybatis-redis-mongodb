package com.codyy.rrt.commons;

import com.codyy.rrt.commons.entity.LoginUser;

public class LoginUserThreadLocal extends ThreadLocal<LoginUser> {

	private static LoginUserThreadLocal threadLocal = null;

	private LoginUserThreadLocal() {
	}

	public static synchronized LoginUserThreadLocal getInstance() {
		if (threadLocal == null) {
			threadLocal = new LoginUserThreadLocal();
		}
		return threadLocal;
	}

	public LoginUser getLoginUser() {
		return super.get();
	}

	public void setLoginUser(LoginUser user) {
		super.set(user);
	}
}
