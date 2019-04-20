package com.zl.fund.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.zl.fund.pojo.Asset;
import com.zl.fund.pojo.Fund;
import com.zl.fund.pojo.PersonalFund;
import com.zl.fund.pojo.PersonalInvest;
import com.zl.fund.pojo.TradeState;
import com.zl.fund.pojo.TradeType;
import com.zl.fund.pojo.User;
import com.zl.fund.pojo.UserAsset;
import com.zl.fund.pojo.UserBankCard;
import com.zl.fund.pojo.UserIdAndCardid;
import com.zl.fund.service.AssetService;
import com.zl.fund.service.PersonalFundService;
import com.zl.fund.service.PersonalInvestService;
import com.zl.fund.service.UserAssetService;
import com.zl.fund.service.UserBankCardService;
import com.zl.fund.util.TimeNum;

@Service
public class Abcdef {
	   @Autowired
	   private PersonalInvestService pis;
	   @Autowired
	   private UserBankCardService ubcs;
	   @Autowired
	   private PersonalFundService pfs;
	   @Autowired
	   private UserAssetService uas;
	   @Autowired
	   private AssetService as;
//	@Scheduled(cron = "0 0 1 * * ?")
	   //每天凌晨1点
	//@Scheduled(cron = "0 * * * * *")
	   //测试用每分钟
	public void hello() {
		System.out.println("hhhhhhh~");
		
	    
		List<PersonalInvest> personalInvests =  pis.getAllInvest();// 得到所有定投
		//得到今日日期
		String today = TimeNum.today();
		Integer orderid = TimeNum.timeNum();
		System.out.println(today);
		for (PersonalInvest personalInvest : personalInvests) {
			if(personalInvest.getWhichday().equals(today)&&personalInvest.getInveststate().getId()==2) {
				System.out.println("定投生效");
				Fund fund = personalInvest.getFund();
				User user = personalInvest.getUser();
				BigDecimal portion = BigDecimal.valueOf(personalInvest.getEachpay().doubleValue());
				if(!personalInvest.getCardid().equals("0")) {
					  UserIdAndCardid uac = new UserIdAndCardid();
					  uac.setUserid(user.getId());
					  uac.setCardid(personalInvest.getCardid());
					  UserBankCard ubc = ubcs.queryByTwoId(uac);
					  if(portion.compareTo(ubc.getBalances()) == -1||portion.compareTo(ubc.getBalances()) == 0){
						    //支付成功
						   PersonalFund pf = new PersonalFund();
						   pf.setCardid(personalInvest.getCardid());
						   pf.setFund(fund);
						   pf.setOrderdate(new Date());
						   pf.setOrderid(orderid);
						   BigDecimal gushu = portion.divide(fund.getUnitvalue(), 2, RoundingMode.HALF_UP);
						   pf.setPortion(gushu);
						   ubc.setBalances(ubc.getBalances().subtract(portion));
						   ubcs.updateBalances(ubc);
						   TradeState ts = new TradeState();
						   ts.setId(1);
						   pf.setTradestate(ts);
						   TradeType tt = new TradeType();
						   tt.setId(1);
						   pf.setTradetype(tt);
						   pf.setUser(user);
						   pfs.addPersonalFund(pf);
						   UserAsset userAsset = new UserAsset();
							userAsset.setUser(user);
							userAsset.setOperation((short) 4);
							userAsset.setOutmoney(portion);
							userAsset.setRemark(fund.getFundname()+fund.getFundid());
							userAsset.setTradetime(new Date());
							userAsset.setStatus((short) 3);
							uas.insertUserAsset(userAsset);
							}
				}else {
					 Asset a = as.queryAssetByUserIDd(user.getId());
					    if(portion.compareTo(a.getSurplus()) == -1||portion.compareTo(a.getSurplus()) == 0) {
						  PersonalFund pf = new PersonalFund();
						  pf.setCardid("0");
						  pf.setFund(fund);
						  pf.setOrderdate(new Date());
						  pf.setOrderid(orderid);
						  BigDecimal gushu = portion.divide(fund.getUnitvalue(), 2, RoundingMode.HALF_UP);
						  pf.setPortion(gushu);
						  Asset a2 = new Asset();
						  System.out.println(a.getSurplus().subtract(portion));
						  a2.setSurplus(a.getSurplus().subtract(portion));
						  a2.setUser(user);
						  as.updateSurplus(a2);
						  TradeState ts = new TradeState();
						  ts.setId(1);
						  pf.setTradestate(ts);
						  TradeType tt = new TradeType();
						  tt.setId(1);
						  pf.setTradetype(tt);
						  pf.setUser(user);
						  pfs.addPersonalFund(pf);
						  UserAsset userAsset = new UserAsset();
						  userAsset.setUser(user);
						  userAsset.setOperation((short) 4);
						  userAsset.setOutmoney(portion);
						  userAsset.setRemark(fund.getFundname()+fund.getFundid());
						  userAsset.setTradetime(new Date());
						  userAsset.setStatus((short) 3);
						  uas.insertUserAsset(userAsset);
				}
				}
			}else {
				System.out.println("定投不生效");
			}
		}

	}

}
