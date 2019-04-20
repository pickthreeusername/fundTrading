package com.zl.fund.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zl.fund.pojo.User;
import com.zl.fund.service.UserService;
import com.zl.fund.util.ExternalService;
import com.zl.fund.util.PhoneVerifyUtil;
import com.zl.fund.util.VerifyCodeUtils;


@Controller
@RequestMapping("user")
public class LoginController {
	@Autowired
	private PhoneVerifyUtil verifyUtil;
	@Autowired
	private UserService userService;
	/**
	 * 用户登录 0：失败   1：成功
	 * @param user
	 * @param session
	 * @return
	 */
	@RequestMapping("login.do")
	@ResponseBody
	public int customerLogin(User user, HttpSession session) {
		user = userService.customerLogin(user);
		if (user != null) {
			session.setAttribute("loginUser", user);
			StringBuilder sb = new StringBuilder(user.getMobilePhone());
	        sb.replace(3, 7, "****");
	        session.setAttribute("mobileMe", sb);
			return 1;
		}else {
			return 0;
		}
	}
	@RequestMapping("logout.do")
	public String customerLogout(HttpSession session) {
		session.removeAttribute("loginUser");
		return "login";
	}
	/**
	 * 去登录页面
	 * @return
	 */
	@RequestMapping("toLogin.do")
	public String toLogin() {
		//ExternalService.sendVerifyCode("15302771575", "223456");
		//ExternalService.checkIdentityCode("441521199609126547", "陈钰橙");
		return "login";
	}
	//关于注册
	/**
	 * 去注册页
	 * @return
	 */
	@RequestMapping("toRegister.do")
	public String toRegister() {
		return "register";
	}
	/**
	 * 生成验证码图片
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("getImg.do")
	public void authImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");
		// 生成随机字串
		String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
		// 存入会话session
		HttpSession session = request.getSession(true);
		// 删除以前的
//		session.removeAttribute("verCode");
//		session.removeAttribute("codeTime");
		session.setAttribute("verCode", verifyCode.toLowerCase());
		session.setAttribute("codeTime", LocalDateTime.now());
		// 生成图片
		int w = 80, h = 35;
		OutputStream out = response.getOutputStream();
		VerifyCodeUtils.outputImage(w, h, out, verifyCode);
    }
	/**
	 * 手机号是否存在
	 * @param phone
	 * @return
	 */
	@RequestMapping("checkPhone.do")
	@ResponseBody
	public int checkPhoneExist(String phone) {
		return userService.checkPhoneExist(phone);
	}
	/**
	 * 发送验证码
	 * @param phone
	 * @return
	 */
	@RequestMapping("sendCode.do")
	@ResponseBody
	public int sendCode(String phone, HttpSession session) {
		if ("0".equals(phone)) {
			phone = ((User)session.getAttribute("loginUser")).getMobilePhone();
		}
		return verifyUtil.sendCode(phone);
	}
	/**
	 * 验证码是否正确  5分钟失效
	 * @param request
	 * @param session
	 * @return
	 */
	 @RequestMapping("checkVerifyCode.do")
	 @ResponseBody
	    public String validImage(String verifyCode,HttpSession session){
	    	//String code = request.getParameter("code");
	    	Object verCode = session.getAttribute("verCode");
	    	if (null == verCode) {
	    		return "验证码无效";
	    	}
	    	//系统验证码
	    	String verCodeStr = verCode.toString();
	    	//验证码时间
	    	LocalDateTime localDateTime = (LocalDateTime)session.getAttribute("codeTime");
	    	long past = localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
	    	
	    	long now = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
	    	if(verCodeStr == null  || !verCodeStr.equalsIgnoreCase(verifyCode)){
	    		return "验证码错误";
	    	} else if((now-past)/1000/60>5){
	    		return "验证码已过期";
	    	} else {
	    		//验证成功，删除存储的验证码
	    		session.removeAttribute("verCode");
	    		return "200";
	    	}
	    }
	 /**
	  * 手机校验码 验证   1：通过 0：失败
	  * @param phone
	  * @param phoneCode
	  * @return
	  */
	 @RequestMapping("checkPhoneCode.do")
	 @ResponseBody
	 public int checkPhoneCode(String phone, String phoneCode, HttpSession session) {
		 if ("0".equals(phone)) {
			 phone = ((User)session.getAttribute("loginUser")).getMobilePhone();
		 }
		 return verifyUtil.checkCode(phone, phoneCode);
	 }
	 @RequestMapping("register.do")
	 @ResponseBody
	 public int userRegister(User user, HttpSession session) {
		 try {
			 userService.userRegister(user);
			 user = userService.selectByPhone(user.getMobilePhone());
			 session.setAttribute("loginUser", user);
			 StringBuilder sb = new StringBuilder(user.getMobilePhone());
		     sb.replace(3, 7, "****");
		     session.setAttribute("mobileMe", sb);
			 return 1;
		 }catch(Exception e) {
			 e.printStackTrace();
			 return 0;
		 }
	 }
}
