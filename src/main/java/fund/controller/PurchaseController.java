package com.zl.fund.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zl.fund.pojo.Asset;
import com.zl.fund.pojo.Fund;
import com.zl.fund.pojo.PersonalFund;
import com.zl.fund.pojo.TradeState;
import com.zl.fund.pojo.TradeType;
import com.zl.fund.pojo.User;
import com.zl.fund.pojo.UserAsset;
import com.zl.fund.pojo.UserBankCard;
import com.zl.fund.pojo.UserIdAndCardid;
import com.zl.fund.service.AssetService;
import com.zl.fund.service.BankCardService;
import com.zl.fund.service.PersonalFundService;
import com.zl.fund.service.PurchaseService;
import com.zl.fund.service.UserAssetService;
import com.zl.fund.service.UserBankCardService;
import com.zl.fund.service.UserService;
import com.zl.fund.util.TimeNum;

@Controller
@RequestMapping("purchase")
public class PurchaseController {
   @Autowired
   private PurchaseService ps;
   @Autowired
   private BankCardService bs;
   @Autowired
   private UserService us;
   @Autowired
   private UserBankCardService ubcs;
   @Autowired
   private PersonalFundService pfs;
   @Autowired
   private AssetService as;
   @Autowired
   private UserAssetService uas;


   
   @RequestMapping("toPurchase")
   public String toPurchase(Model model,HttpSession session,
		   @RequestParam("id") Integer id ) {
	   Fund fund = ps.queryFundById(id);
	   //****************
/*	   User user = new User();
	   user.setMobilePhone("13632565335");
	   user.setPassword("123456");
	   User loginUser = us.customerLogin(user);
	   session.setAttribute("loginUser", loginUser);*/
	   //****************
	   User loginUser = (User) session.getAttribute("loginUser");
	   List<UserBankCard> UserBankCards = ubcs.queryByUserId(loginUser.getId());
       model.addAttribute("fund", fund);
       model.addAttribute("bankCards", UserBankCards);
       model.addAttribute("UserBankCards", TimeNum.yinHang(UserBankCards));
       List<UserBankCard> bcs = ubcs.queryByUserId(loginUser.getId());
		if (loginUser.getIdCard() == null) {
			return "identityVerify";			
		}else if(loginUser.getPadPay()==null){
			return "setTransCode";
		}else if(bcs.size() == 0) {
			return "bankCardAdd";
		}
	   return "purchase";
   }

   @RequestMapping("toPurchase2")
   public String toPurchase2(HttpSession session,Model model,
		   @RequestParam("id") Integer id,@RequestParam("cardid") String cardid,
		   @RequestParam("portion") BigDecimal portion,@RequestParam("daxie") String daxie) {
	   Fund fund = ps.queryFundById(id);
	   String msg = null;
	   User loginUser = (User) session.getAttribute("loginUser");
	   
	   if(!(portion.compareTo(BigDecimal.ZERO)==0)) {
		   if(portion.compareTo(BigDecimal.valueOf(fund.getStartpoint().doubleValue())) == 1||portion.compareTo(BigDecimal.valueOf(fund.getStartpoint().doubleValue())) == 0) {
	   model.addAttribute("fund",fund);  
	   if(!cardid.equals("0")) {

		   UserIdAndCardid uac = new UserIdAndCardid();
		   uac.setUserid(loginUser.getId());
		   uac.setCardid(cardid);
		   UserBankCard ubc =  ubcs.queryByTwoId(uac);
	       model.addAttribute("bankcard",ubc);
	   }
	   model.addAttribute("portion",portion);
	   Integer orderid = TimeNum.timeNum();
	   model.addAttribute("orderid",orderid);
	   model.addAttribute("daxie",daxie);
	   return "purchase2";
	      }else {
	    	  List<UserBankCard> UserBankCards = ubcs.queryByUserId(loginUser.getId());
		       model.addAttribute("fund", fund);
		       model.addAttribute("bankCards", UserBankCards);
		       model.addAttribute("UserBankCards", TimeNum.yinHang(UserBankCards));
		       msg="金额必须高于投资起点"+fund.getStartpoint()+"元";
		       model.addAttribute("msg",msg);
		       model.addAttribute("portion", portion);
			   return "purchase";
	      }
	   }else {
		   List<UserBankCard> UserBankCards = ubcs.queryByUserId(loginUser.getId());
	       model.addAttribute("fund", fund);
	       model.addAttribute("bankCards", UserBankCards);
	       model.addAttribute("UserBankCards", TimeNum.yinHang(UserBankCards));
	       msg="金额不能为0";
	       model.addAttribute("msg",msg);
		   return "purchase";
	   }
   }
   
@RequestMapping("addPersonalFund")
   public String addPersonalFund(Model model,HttpSession session,
		   @RequestParam("padPay") String padPay,
		   @RequestParam("bankcardid") String bankcardid,
		   @RequestParam("portion") BigDecimal portion,
		   @RequestParam("fundid") Integer fundid,
		   @RequestParam("orderid") Integer orderid,
		   @RequestParam("daxie") String daxie) {
	   User loginUser = (User) session.getAttribute("loginUser");
	   String msg = null;
	   String regex = "^[0-9]{8}$";
	   if(padPay.matches(regex)&&padPay!=null&&padPay.equals(loginUser.getPadPay().toString())&&!(portion.compareTo(BigDecimal.ZERO)==0)) {
		   /*
		    * 再判断是余额支付还是银行卡支付,查出对应的余额
		    * 余额-支付金额>=0 才算支付成功
		    */
		   if(!bankcardid.equals("0")) {
			   UserIdAndCardid uac = new UserIdAndCardid();
			   uac.setUserid(loginUser.getId());
			   uac.setCardid(bankcardid);
			   UserBankCard ubc = ubcs.queryByTwoId(uac);
			   //用portion 和 余额相比
			   
			   if(portion.compareTo(ubc.getBalances()) == -1||portion.compareTo(ubc.getBalances()) == 0){
				    //支付成功
				   PersonalFund pf = new PersonalFund();
				   pf.setCardid(bankcardid);
				   pf.setFund(ps.queryFundById(fundid));
				   pf.setOrderdate(new Date());
				   pf.setOrderid(orderid);
				   Fund fund = ps.queryFundById(fundid);
				   BigDecimal gushu = portion.divide(fund.getUnitvalue(), 2, RoundingMode.HALF_UP);
				   pf.setPortion(gushu);
				   ubc.setBalances(ubc.getBalances().subtract(portion));
				   ubcs.updateBalances(ubc);
				   TradeState ts = new TradeState();
				   ts.setId(4);
				   pf.setTradestate(ts);
				   TradeType tt = new TradeType();
				   tt.setId(1);
				   pf.setTradetype(tt);
				   pf.setUser(loginUser);
				   pfs.addPersonalFund(pf);
				   UserAsset userAsset = new UserAsset();
					userAsset.setUser(loginUser);
					userAsset.setOperation((short) 4);
					userAsset.setOutmoney(portion);
					userAsset.setRemark(fund.getFundname()+fund.getFundid());
					userAsset.setTradetime(new Date());
					userAsset.setStatus((short) 3);
					uas.insertUserAsset(userAsset);
				   return "redirect:/personalFund/toPersonal.do";
				   				   
				}else if(portion.compareTo(ubc.getBalances()) == 1){
					msg= "余额不足支付失败";
					model.addAttribute("msg", msg);
				    model.addAttribute("fund",ps.queryFundById(fundid));
					model.addAttribute("bankcard",ubc);
					model.addAttribute("portion",portion);
					model.addAttribute("orderid",orderid);
					model.addAttribute("daxie",daxie);
					return "purchase2";
				}
		   }else {
	             Asset a = as.queryAssetByUserIDd(loginUser.getId());
			    if(portion.compareTo(a.getSurplus()) == -1||portion.compareTo(a.getSurplus()) == 0) {
				  PersonalFund pf = new PersonalFund();
				  pf.setCardid("0");
				  pf.setFund(ps.queryFundById(fundid));
				  pf.setOrderdate(new Date());
				  pf.setOrderid(orderid);
				  Fund fund = ps.queryFundById(fundid);
				  BigDecimal gushu = portion.divide(fund.getUnitvalue(), 2, RoundingMode.HALF_UP);
				  pf.setPortion(gushu);
				  Asset a2 = new Asset();
				  System.out.println(a.getSurplus().subtract(portion));
				  a2.setSurplus(a.getSurplus().subtract(portion));
				  a2.setUser(loginUser);
				  as.updateSurplus(a2);
				  TradeState ts = new TradeState();
				  ts.setId(4);
				  pf.setTradestate(ts);
				  TradeType tt = new TradeType();
				  tt.setId(1);
				  pf.setTradetype(tt);
				  pf.setUser(loginUser);
				  pfs.addPersonalFund(pf);
				  UserAsset userAsset = new UserAsset();
				  userAsset.setUser(loginUser);
				  userAsset.setOperation((short) 4);
				  userAsset.setOutmoney(portion);
				  userAsset.setRemark(fund.getFundname()+fund.getFundid());
				  userAsset.setTradetime(new Date());
				  userAsset.setStatus((short) 3);
				  uas.insertUserAsset(userAsset);
				  return "redirect:/personalFund/toPersonal.do";
				  
			  }else {
				  msg= "余额不足支付失败";
				    model.addAttribute("msg", msg);
				    model.addAttribute("fund",ps.queryFundById(fundid));
					UserIdAndCardid uac = new UserIdAndCardid();
					uac.setUserid(loginUser.getId());
					uac.setCardid(bankcardid);
					UserBankCard ubc = ubcs.queryByTwoId(uac);
					model.addAttribute("bankcard",ubc);
					model.addAttribute("portion",portion);
					model.addAttribute("orderid",orderid);
					model.addAttribute("daxie",daxie);
					return "purchase2";
			  }
		   }
		   return "test";

	   }else if(!(portion.compareTo(BigDecimal.ZERO)==0)){
		    msg= "支付密码错误";
		    model.addAttribute("msg", msg);
		    model.addAttribute("fund",ps.queryFundById(fundid));
			UserIdAndCardid uac = new UserIdAndCardid();
			uac.setUserid(loginUser.getId());
			uac.setCardid(bankcardid);
			UserBankCard ubc = ubcs.queryByTwoId(uac);
			model.addAttribute("bankcard",ubc);
			model.addAttribute("portion",portion);
			model.addAttribute("orderid",orderid);
			model.addAttribute("daxie",daxie);
		    return "purchase2";
		   /*
		    *失败 返回基金id 购买金额,支付方式,大写金额 到purchase2 页面 
		    */
		   //return "purchase2";
	   }else {
		   msg= "支付金额不能为0";
		    model.addAttribute("msg", msg);
		    model.addAttribute("fund",ps.queryFundById(fundid));
			UserIdAndCardid uac = new UserIdAndCardid();
			uac.setUserid(loginUser.getId());
			uac.setCardid(bankcardid);
			UserBankCard ubc = ubcs.queryByTwoId(uac);
			model.addAttribute("bankcard",ubc);
			model.addAttribute("portion",portion);
			model.addAttribute("orderid",orderid);
			model.addAttribute("daxie",daxie);
		    return "purchase2";
	   }
   }

     @RequestMapping("toComeBack")
     public String toComeBack(Model model,
    		 @RequestParam("orderid") Integer orderid) {
    	 PersonalFund pf = pfs.queryFundRecord(orderid);
    	 BigDecimal zong = pf.getFund().getUnitvalue().multiply(pf.getPortion());
    	 zong = zong.setScale(2,BigDecimal.ROUND_HALF_UP);
         BigDecimal feie = pf.getFund().getManagerate().multiply(zong);
         feie = feie.setScale(2,BigDecimal.ROUND_HALF_UP);
         BigDecimal cha = zong.subtract(feie);

         model.addAttribute("zong", zong);
         model.addAttribute("PersonalFund", pf);       
         model.addAttribute("feie", feie);
         model.addAttribute("cha", cha);
    	 return "comeback";
     }
     @RequestMapping("comeBack")
     public String comeBack(Model model,HttpSession session,
    		 @RequestParam("padPay") String padPay,
    		 @RequestParam("orderid") Integer orderid,
    		 @RequestParam("zong") BigDecimal zong,
    		 @RequestParam("feie") BigDecimal feie,
    		 @RequestParam("cha") BigDecimal cha
    		 ) {
         PersonalFund pf = pfs.queryFundRecord(orderid);
         User loginUser = pf.getUser();
         session.setAttribute("loginUser", loginUser);
  	     String msg = null;
  	     String regex = "^[0-9]{8}$";
    	 if(padPay.matches(regex)&&padPay!=null&&padPay.equals(loginUser.getPadPay().toString())) {
    		 pfs.upDateType(orderid);
    		 return "redirect:/personalFund/toPersonal.do";
    	 }else {
    		msg= "支付密码错误";
            model.addAttribute("zong", zong);
            model.addAttribute("PersonalFund", pf);       
            model.addAttribute("feie", feie);
            model.addAttribute("cha", cha);
		    model.addAttribute("msg", msg);
       	    return "comeback";
    	 }

     }
     
}
