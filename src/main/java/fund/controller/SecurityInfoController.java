package com.zl.fund.controller;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zl.fund.pojo.User;
import com.zl.fund.service.UserService;
import com.zl.fund.util.CheckStatusUtil;
import com.zl.fund.util.ExternalService;
import com.zl.fund.util.PhoneVerifyUtil;

@Controller
@RequestMapping("security")
public class SecurityInfoController {
	@Autowired
	private PhoneVerifyUtil verifyUtil;
	@Autowired
	private UserService userService;
	@Autowired
	private CheckStatusUtil statusUtil;
	/**
	 * 跳转安全管理页面
	 * @return
	 */
	@RequestMapping("toSecurityMg.do")
	public String toSecutrityMg() {
		return "securityMg";
	}
	/**
	 * 跳转设置交易密码页面
	 * @return
	 */
	@RequestMapping("toSetTransCode.do")
	public String toSetTradingCode() {
		return "setTransCode";
	}
	/**
	 * 跳转修改交易密码页面
	 * @return
	 */
	@RequestMapping("toAlterTransCode.do")
	public String toAlterTradingCode() {
		return "alterTransCode";
	}
	
	/**
	 * 跳转修改登录密码页面
	 * @return
	 */
	@RequestMapping("toAlterPassword.do")
	public String toAlterPassword(){
		return "alterPassword";
	}
	/**
	 * 跳转修改手机号页面
	 * @return
	 */
	@RequestMapping("toAlterPhone.do")
	public String toAlterPhone(){
		return "alterPhone";
	}
	@RequestMapping("toIdentityVerify.do")
	public String toIdentityVerify() {
		return "identityVerify";
	}
	/**
	 * 更新手机号   1：成功  0：验证码不正确   -1：系统故障
	 * @param phone
	 * @param phoneCode
	 * @param session
	 * @return
	 */
	@RequestMapping("updatePhone.do")
	@ResponseBody
	public int updatePhone(String phone, String phoneCode, HttpSession session) {
		int verifyResult = verifyUtil.checkCode(phone, phoneCode);
		if (verifyResult == 1) {
			//短信验证码正确
			User user = (User) session.getAttribute("loginUser");
			user.setMobilePhone(phone);
			int row = userService.updatePhone(user);
			if (row == 1) {
				StringBuilder sb = new StringBuilder(user.getMobilePhone());
		        sb.replace(3, 7, "****");
		        session.setAttribute("mobileMe", sb);
				session.setAttribute("loginUser", user);
				return 1;
			}else {
				return -1;
			}
		}else {
			return 0;
		}
	}
	/**
	 * 实名验证页面
	 * @return
	 */
	@RequestMapping("toIdVerify.do")
	public String toIdVerify(){
		return "identityVerify";
	}
	/**
	 * 核对原登录密码
	 * @param session
	 * @param pwd
	 * @return
	 */
	@RequestMapping("checkOldPwd.do")
	@ResponseBody
	public int checkOldPwd(HttpSession session, String pwd) {
		String password = ((User)session.getAttribute("loginUser")).getPassword();
		if (password.equals(pwd)) {
			return 1;
		}
		return 0;
	}
	/**
	 * 修改密码
	 * @param session
	 * @param pwd
	 * @return
	 */
	@RequestMapping("updatePwd.do")
	@ResponseBody
	public int updatePwd(HttpSession session, String pwd) {
		User user = (User) session.getAttribute("loginUser");
		user.setPassword(pwd);
		return userService.updatePassword(user);
	}
	/**
	 * 核对原交易密码
	 * @param pwd
	 * @param session
	 * @return
	 */
	@RequestMapping("checkOldTransCode.do")
	@ResponseBody
	public String checkOldTransCode(String pwd, HttpSession session) {
		User user = (User)session.getAttribute("loginUser");
		int id = user.getId();
		//查看账号是否被锁
		int lockTime = statusUtil.ifLock(id);
		if (lockTime == 0) {
			//没有被锁，查看密码是否正确
			String regex = "^[0-9]{8}$";
			if (pwd.matches(regex) && user.getPadPay() == Integer.parseInt(pwd)) {
				//密码正确
				return "correct";
			}else {
				//不正确，记录错误次数
				int rest = 4-statusUtil.getIncorrectVerifyTimes(id);
				statusUtil.setIncorrectVerifyTimes(id);
				//返回剩余次数
				return "incorrect/" + rest; 
			}
		}else {
			//账号被锁，返回剩余锁定时间
			return String.valueOf(lockTime);
		}
		
	}
	/**
	 * 修改交易密码
	 * @param pwd
	 * @param session
	 * @return
	 */
	@RequestMapping("updateTransCode.do")
	@ResponseBody
	public int updateTransCode(String pwd, HttpSession session) {
		User user = (User)session.getAttribute("loginUser");
		user.setPadPay(Integer.parseInt(pwd));
		return userService.updateTransCode(user);
	}
	/**
	 * 身份证实名验证
	 * @param name
	 * @param idCard
	 * @return 1:success  others:fail
	 */
	@RequestMapping("idAuthentication.do")
	@ResponseBody
	public int authentication(String name, String idCard, HttpSession session) {
		//"01":通过   "02":不通过
		String status = ExternalService.checkIdentityCode(idCard, name);
		if ("01".equals(status)) {
			
			User user = (User)session.getAttribute("loginUser");
			user.setRealName(name);
			user.setIdCard(idCard);
			int row = userService.updateUserIdCard(user);
			if (row == 1) {
				session.setAttribute("loginUser", user);
				return 1;
			}
			return 0;
		}
//		if (name.equals("陈钰橙") && idCard.equals("441521199609126547")) {
//			User user = (User)session.getAttribute("loginUser");
//			user.setRealName(name);
//			user.setIdCard(idCard);
//			int row = userService.updateUserIdCard(user);
//			if (row == 1) {
//				session.setAttribute("loginUser", user);
//				return 1;
//			}
//			return 0;
//		}
		return 0;
		
	}
	@RequestMapping("ifIdCardExist.do")
	@ResponseBody
	public int ifIdCardExist(String idCard) {
		return userService.checkIdCardExist(idCard);
	}
	
}
