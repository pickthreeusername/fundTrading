package com.zl.fund.Interceptor;
 
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.zl.fund.pojo.User;

public class LoginInterceptor extends HandlerInterceptorAdapter{

	//在控制器执行前调用
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		User user = (User) request.getSession().getAttribute("loginUser");
		String operUrl = request.getServletPath().toString();
		if(user == null&&!operUrl.equals("/user/toLogin.do")&&!operUrl.equals("/user/login.do")&&!operUrl.equals("/scripts/login.js")) {
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
		    out.println("<html>");    
		    out.println("<script>");  
		    out.println("alert('您还未登录，请登录后再操作。')"); 
		    out.println("location.href='"+request.getContextPath()+"/user/toLogin.do'");    
		    out.println("</script>");    
		    out.println("</html>");
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