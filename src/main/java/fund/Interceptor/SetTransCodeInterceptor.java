package com.zl.fund.Interceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.zl.fund.pojo.User;
/**
 * 去设置交易密码页面的拦截
 * @author THINK
 *
 */
public class SetTransCodeInterceptor extends HandlerInterceptorAdapter {
	//在控制器执行前调用
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		User user = (User) request.getSession().getAttribute("loginUser");
		//String operUrl = request.getServletPath().toString();
		if(user.getIdCard() == null) {
			response.sendRedirect(request.getContextPath() + "/security/toIdentityVerify.do");
			return false;
		}
		return true;
	}
	//在后端控制器执行后调用
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
	}
	//整个请求执行完成后调用
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		super.afterCompletion(request, response, handler, ex);
	}
}
