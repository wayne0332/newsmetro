package com.newsmetro.interceptor;

import com.newsmetro.enumeration.AuthorExceptEnum;
import com.newsmetro.enumeration.UserStatus;
import com.newsmetro.exception.AuthorizationException;
import com.newsmetro.po.User;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignInInterceptor extends HandlerInterceptorAdapter {

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		/*
		 * request.setCharacterEncoding("UTF-8");
		 * response.setCharacterEncoding("UTF-8");
		 * response.setContentType("text/html;charset=UTF-8");
		 */

		// 后台session控制
		String[] noFilters = new String[] { "signin.html", "signup.html",
				"index.html", "signout.html","activeUser.html","addTarget.html","getSignInKeyAjax.html",
				"toSendResetPassMail.html","sendResetPassMail.html","toResetPassword.html","resetPassword.html",
				"page404.html","page500.html","toHelper.html"};
		String uri = request.getRequestURI();

		boolean beFilter = true;
		for (String s : noFilters) {
			if (uri.indexOf(s) != -1) {
				beFilter = false;
				break;
			}
		}
		if(uri.equals("/newsmetro/")){
			beFilter = false;
		}
		if (beFilter) {
			Object obj = request.getSession().getAttribute("user");
			User user = (User)obj;
			if (null == obj) {
				// 未登录
//				request.getRequestDispatcher("/index.html").forward(request,
//						response);
				throw new AuthorizationException(AuthorExceptEnum.NO_SIGNIN);
			}else if(!(user.getStatus().equals(UserStatus.REGULAR))){
				if(user.getStatus().equals(UserStatus.NEW)){
					throw new AuthorizationException(AuthorExceptEnum.NO_ACTIVE);
				}else if(user.getStatus().equals(UserStatus.DENIED)){
					throw new AuthorizationException(AuthorExceptEnum.DENIED);
				}else{
					throw new AuthorizationException(AuthorExceptEnum.UN_KNOW);
				}
			}
		}

		return super.preHandle(request, response, handler);
	}

}
