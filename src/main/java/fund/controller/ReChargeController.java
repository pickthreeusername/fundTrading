package com.zl.fund.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zl.fund.pojo.Asset;
import com.zl.fund.pojo.User;
import com.zl.fund.pojo.UserAsset;
import com.zl.fund.pojo.UserBankCard;
import com.zl.fund.pojo.UserIdAndCardid;
import com.zl.fund.service.AssetService;
import com.zl.fund.service.UserAssetService;
import com.zl.fund.service.UserBankCardService;
import com.zl.fund.util.CheckStatusUtil;

@Controller
public class ReChargeController {
	@Autowired
	private AssetService as;
	@Autowired
	private UserBankCardService ubcs;
	@Autowired
	private UserAssetService uas;
	@Autowired
	private CheckStatusUtil statusUtil;
	
	//跳转充值页面
	@RequestMapping("toRecharge.do")
	public String toRecharge(Model model, HttpSession session) {
		User user = (User)session.getAttribute("loginUser");
		//查询用户资产余额
		Asset asset = as.queryAssetByUserIDd(user.getId());
		//查询用户的银行卡信息
		List<UserBankCard> list_ubc = ubcs.queryByUserId(user.getId());
		model.addAttribute("asset", asset);
		model.addAttribute("list_ubc", list_ubc);
		return "recharge";
	}
	
	//校验充值信息
	@RequestMapping("checkRecharge.do")
	@ResponseBody
	public String checkRecharge(HttpSession session, String money, String id) {
		User user = (User)session.getAttribute("loginUser");
		//查询用户当天充值记录
		UserIdAndCardid uab = new UserIdAndCardid();
		uab.setCardid(id);
		uab.setUserid(user.getId());
		List<UserAsset> list_us = uas.queryDayRecharge(uab);
		//计算当日充值金额
		BigDecimal bd = new BigDecimal(0);
		if(list_us!=null && list_us.size()>0) {
			for(UserAsset ua : list_us) {
				bd = bd.add(ua.getInmoney());
			}
		}
		// 查询用户银行卡信息
		UserBankCard userBankCard = ubcs.queryByTwoId(uab);
		BigDecimal bd_money = new BigDecimal(money);
		if(userBankCard != null) {
			//校验银行卡余额
			if(userBankCard.getBalances().compareTo(bd_money) == -1) {
				return "银行卡余额不足，请重新输入！";
			}
			//校验输入充值金额
			if(bd_money.compareTo(new BigDecimal(userBankCard.getBankCard().getTimequota())) == 1) {
				return "超过单笔充值限额，请重新输入！";
			}
			//校验每日充值限额
			BigDecimal dayQuota = new BigDecimal(userBankCard.getBankCard().getDayquota());
			//BigDecimal比较大小  1：大于， 0：等于， -1：小于
			if(bd.add(new BigDecimal(money)).compareTo(dayQuota) == 1) {
				return "超过每日充值限额，请重新输入！";
			}
		}
		return "1";
	}
	
	//跳转充值页面
	@RequestMapping("toRechargeTwo.do")
	public String toRechargeTwo(Model model, HttpSession session, String money, String cardid) {
		User user = (User)session.getAttribute("loginUser");
		UserIdAndCardid uab = new UserIdAndCardid();
		uab.setCardid(cardid);
		uab.setUserid(user.getId());
		// 查询用户银行卡信息
		UserBankCard userBankCard = ubcs.queryByTwoId(uab);
		model.addAttribute("money", money);
		model.addAttribute("userBankCard", userBankCard);
		return "doReCharge";
	}
	
	//执行充值操作
	@RequestMapping("doRecharge.do")
	@ResponseBody
	@Transactional 
	public String doRecharge(HttpSession session, String money, String cardid, String pad) {
		//判断交易密码是否正确
		User user = (User)session.getAttribute("loginUser");
		//查看账号是否被锁
		Integer id = user.getId();
		Integer lockTime = statusUtil.ifLock(id);
		if (lockTime == 0) {
			//没有被锁，查看密码是否正确
			String regex = "^[0-9]{8}$";
			if (pad.matches(regex) && user.getPadPay() == Integer.parseInt(pad)) {
				//密码正确
				return as.chongzhi(cardid, user, money);
			}else {
				//不正确，记录错误次数
				int rest = 4-statusUtil.getIncorrectVerifyTimes(id);
				statusUtil.setIncorrectVerifyTimes(id);
				//返回剩余次数
				return "密码输入错误，剩余" + rest+"次"; 
			}
		}else {
			//账号被锁，返回剩余锁定时间
			return "账户被锁，剩余"+String.valueOf(lockTime)+"分钟。";
		}
		
	}
	
}
