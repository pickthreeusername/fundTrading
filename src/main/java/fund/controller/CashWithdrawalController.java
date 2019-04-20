package com.zl.fund.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
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
public class CashWithdrawalController {

	@Autowired
	private AssetService as;
	@Autowired
	private UserBankCardService ubcs;
	@Autowired
	private UserAssetService uas;
	@Autowired
	private CheckStatusUtil statusUtil;
	
	//跳转取现页面
	@RequestMapping("toCash.do")
	public String toRecharge(Model model, HttpSession session) {
		User user = (User)session.getAttribute("loginUser");
		//查询用户资产余额
		Asset asset = as.queryAssetByUserIDd(user.getId());
		//查询用户的银行卡信息
		List<UserBankCard> list_ubc = ubcs.queryByUserId(user.getId());
		model.addAttribute("asset", asset);
		model.addAttribute("list_ubc", list_ubc);
		return "cashWithdrawal";
	}
	
	//验证取现信息
	@RequestMapping("checkCash.do")
	@ResponseBody
	public String checkCash(HttpSession session, @RequestBody JSONObject query) {
		//查询账户余额
		User user = (User)session.getAttribute("loginUser");
		Asset asset = as.queryAssetByUserIDd(user.getId());
		BigDecimal surplus = asset.getSurplus();
		//验证取现金额是否大于账户余额
		String money = query.getString("money");
		BigDecimal bd_money = new BigDecimal(money);
		if(surplus.compareTo(bd_money) == -1) {
			return "取现金额不能大于账户余额！";
		}else {
			//快速取现需要验证限额
			if(query.getInteger("cashWay").equals(2)) {
				//查询用户银行卡当天取现记录
				UserIdAndCardid uab = new UserIdAndCardid();
				uab.setCardid(query.getString("cardid"));
				uab.setUserid(user.getId());
				List<UserAsset> list_ua = uas.queryDayCash(uab);
				//计算当日已取现金额
				BigDecimal bd = new BigDecimal(0);
				if(list_ua!=null && list_ua.size()>0) {
					for(UserAsset ua : list_ua) {
						bd = bd.add(ua.getOutmoney());
					}
				}
				// 查询用户银行卡信息
				UserBankCard userBankCard = ubcs.queryByTwoId(uab);
				if(userBankCard != null) {
					//校验输入取现金额
					if(bd_money.compareTo(new BigDecimal(userBankCard.getBankCard().getTimequota())) == 1) {
						return "超过单笔取现限额，请重新输入！";
					}
					//校验每日充值限额
					BigDecimal dayQuota = new BigDecimal(userBankCard.getBankCard().getDayquota());
					//BigDecimal比较大小  1：大于， 0：等于， -1：小于
					if(bd.add(bd_money).compareTo(dayQuota) == 1) {
						return "超过每日取现限额，请重新输入！";
					}
				}
				return "1";
			}else {
				return "1";
			}
		}
	}
	
	//跳转取现页面
	@RequestMapping("toCashTwo.do")
	public String toRechargeTwo(Model model, HttpSession session, String money, String cardid, String cashWay, String cashTime) {
		User user = (User)session.getAttribute("loginUser");
		UserIdAndCardid uab = new UserIdAndCardid();
		uab.setCardid(cardid);
		uab.setUserid(user.getId());
		// 查询用户银行卡信息
		UserBankCard userBankCard = ubcs.queryByTwoId(uab);
		model.addAttribute("money", money);
		model.addAttribute("cashWay", cashWay);
		model.addAttribute("cashTime", cashTime);
		model.addAttribute("userBankCard", userBankCard);
		return "doCashWithdrawal";
	}
	
	//执行取现操作
	@RequestMapping("doCash.do")
	@ResponseBody
	@Transactional 
	public String doRecharge(HttpSession session,  @RequestBody JSONObject json) {
		//判断交易密码是否正确
		User user = (User)session.getAttribute("loginUser");
		String pad = json.getString("pad");
		//查看账号是否被锁
		Integer id = user.getId();
		Integer lockTime = statusUtil.ifLock(id);
		if(lockTime == 0) {
			//没有被锁，查看密码是否正确
			String regex = "^[0-9]{8}$";
			if(json.getInteger("cashWay").equals(2)) {
				if (pad.matches(regex) && user.getPadPay() == Integer.parseInt(pad)) {
					return as.quxian(json, user);
				}else {
					//不正确，记录错误次数
					int rest = 4-statusUtil.getIncorrectVerifyTimes(id);
					statusUtil.setIncorrectVerifyTimes(id);
					//返回剩余次数
					return "密码输入错误，剩余" + rest+"次"; 
				}
			}else if(json.getInteger("cashWay").equals(3)) {
				if (pad.matches(regex) && user.getPadPay() == Integer.parseInt(pad)) {
					Timer timer = new Timer();
					//延迟10000ms执行程序
			        timer.schedule(new TimerTask() {
			            @Override
			            public void run() {
			            	as.quxian(json, user);
			            }
			        }, new Date(System.currentTimeMillis() + 10000));
					return "普通取现申请成功。";
				}else {
					//不正确，记录错误次数
					int rest = 4-statusUtil.getIncorrectVerifyTimes(id);
					statusUtil.setIncorrectVerifyTimes(id);
					//返回剩余次数
					return "密码输入错误，剩余" + rest+"次"; 
				}
			}
			return "操作失败。";
		}else {
			//账号被锁，返回剩余锁定时间
			return "账户被锁，剩余"+String.valueOf(lockTime)+"分钟。";
		}
	}	
	
}
