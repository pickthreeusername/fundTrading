package com.zl.fund.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zl.fund.pojo.RiskBear;
import com.zl.fund.pojo.User;
import com.zl.fund.service.UserService;

@Controller
@RequestMapping("info")
public class PersonalInfoController {
	@Autowired
	private UserService userService;
	
	@RequestMapping("toInfo.do")
	public String toPersonalInfo() {
		return "personalInfoMg";
	}
	@RequestMapping("toUpdateInfo.do")
	public String toUpdateInfo() {
		return "updatePersonalInfo";
	}
	@RequestMapping("toRiskEndure.do")
	public String toRiskEndure() {
		return "riskEndureTesting";
	}
	/**
	 * 修改详细信息
	 * @param user
	 * @param session
	 * @return
	 */
	@RequestMapping("updateDetailInfo.do")
	@ResponseBody
	public int updateDetailInfo(User user, HttpSession session) {
		int userId = ((User)session.getAttribute("loginUser")).getId();
		user.setId(userId);
		if (userService.updateDetailInfo(user) == 1) {
			session.setAttribute("loginUser", userService.selectByPrimaryKey(userId));
			return 1;
		}
		return 0;
	}
	/**
	 * 修改地址
	 * @param address
	 * @param session
	 * @return
	 */
	@RequestMapping("updateAddress.do")
	@ResponseBody
	public int updateAddress(String address, HttpSession session) {
		User user = (User)session.getAttribute("loginUser");
		if ("".equals(address)) {
			address = null;
		}
		user.setAddress(address);
		if (userService.updateAddress(user) == 1) {
			session.setAttribute("loginUser", user);
			return 1;
		}
		return 0;
	}
	@RequestMapping("updateEmail.do")
	@ResponseBody
	public int updateEmail(String email, HttpSession session) {
		User user = (User)session.getAttribute("loginUser");
		if ("".equals(email)) {
			email = null;
		}
		user.setEmail(email);
		if (userService.updateEmail(user) == 1) {
			session.setAttribute("loginUser", user);
			return 1;
		}
		return 0;
	}
	@RequestMapping("updateRisk.do")
	@ResponseBody
	public int updateRisk(String score, HttpSession session) {
		User user = (User)session.getAttribute("loginUser");
		Integer so = Integer.parseInt(score);
		RiskBear rb = new RiskBear();
		//根据得分判定用户承受风险等级
		int level = 0;
		if (so>=7 && so<=13) {
			level = 1;
		}else if(so>=14 && so<=20) {
			level = 2;
		}else if(so>=21 && so<=27) {
			level = 3;
		}else {
			level = 4;
		}
		rb.setId(level);
		user.setRiskBear(rb);
		if (userService.updateRisk(user) == 1) {
			session.setAttribute("loginUser", userService.selectByPrimaryKey(user.getId()));
			return level;
		}
		return 0;
	}
}
