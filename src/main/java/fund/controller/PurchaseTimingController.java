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
import com.zl.fund.pojo.InvestState;
import com.zl.fund.pojo.PersonalFund;
import com.zl.fund.pojo.PersonalInvest;
import com.zl.fund.pojo.TradeState;
import com.zl.fund.pojo.TradeType;
import com.zl.fund.pojo.User;
import com.zl.fund.pojo.UserBankCard;
import com.zl.fund.pojo.UserIdAndCardid;
import com.zl.fund.service.AssetService;
import com.zl.fund.service.BankCardService;
import com.zl.fund.service.PersonalFundService;
import com.zl.fund.service.PersonalInvestService;
import com.zl.fund.service.PurchaseService;
import com.zl.fund.service.UserBankCardService;
import com.zl.fund.service.UserService;
import com.zl.fund.util.TimeNum;

@Controller
@RequestMapping("purchaseTiming")
public class PurchaseTimingController {
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
   private PersonalInvestService pis;
   
   @RequestMapping("toPurchaseTiming")
   public String toPurchaseTiming(Model model,HttpSession session,
		   @RequestParam("id") Integer id) {
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
	   return "purchasetiming";
   }
   @RequestMapping("toPurchaseTiming2")
   public String toPurchaseTiming2(HttpSession session,Model model,
		   @RequestParam("id") Integer id,@RequestParam("cardid") String cardid,
		   @RequestParam("portion") BigDecimal portion,
		   @RequestParam("whichday") String whichday) {
	      Fund fund = ps.queryFundById(id);
		   User loginUser = (User) session.getAttribute("loginUser");
	      String msg = null;
		   
	      if(!(portion.compareTo(BigDecimal.ZERO)==0)) {
	      model.addAttribute("fund",fund);  
	      if(portion.compareTo(BigDecimal.valueOf(fund.getStartpoint().doubleValue())) == 1||portion.compareTo(BigDecimal.valueOf(fund.getStartpoint().doubleValue())) == 0) {
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
	   model.addAttribute("whichday",whichday);
	   return "purchasetiming2";
	       }else {
	    	   List<UserBankCard> UserBankCards = ubcs.queryByUserId(loginUser.getId());
		       model.addAttribute("fund", fund);
		       model.addAttribute("bankCards", UserBankCards);
		       model.addAttribute("UserBankCards", TimeNum.yinHang(UserBankCards));
		       msg="金额必须高于投资起点"+fund.getStartpoint()+"元";
		       model.addAttribute("msg",msg);
		       model.addAttribute("portion", portion);
			   return "purchasetiming";
	       }
	   }else {
		   List<UserBankCard> UserBankCards = ubcs.queryByUserId(loginUser.getId());
	       model.addAttribute("fund", fund);
	       model.addAttribute("bankCards", UserBankCards);
	       model.addAttribute("UserBankCards", TimeNum.yinHang(UserBankCards));
	       msg="金额不能为0";
	       model.addAttribute("msg",msg);
		   return "purchasetiming";
	   }
   }
   
@RequestMapping("addPersonalInvest")
   public String addPersonalInvest(Model model,HttpSession session,
		   @RequestParam("padPay") String padPay,
		   @RequestParam("bankcardid") String bankcardid,
		   @RequestParam("portion") BigDecimal portion,
		   @RequestParam("fundid") Integer fundid,
		   @RequestParam("orderid") Integer orderid,
		   @RequestParam("whichday") String whichday) {
	   User loginUser = (User) session.getAttribute("loginUser");
	   String msg = null;
	   String regex = "^[0-9]{8}$";
	   if(padPay.matches(regex)&&padPay!=null&&padPay.equals(loginUser.getPadPay().toString())&&!(portion.compareTo(BigDecimal.ZERO)==0)) {

				   PersonalInvest pi = new PersonalInvest();
				   pi.setCardid(bankcardid);
				   pi.setFund(ps.queryFundById(fundid));
				   pi.setInvestdate(new Date());
				   pi.setOrderid(orderid);
				   pi.setEachpay(portion.intValue());
				   InvestState is = new InvestState();
				   is.setId(1);
				   pi.setInveststate(is);				  
				   pi.setUser(loginUser);
				   pi.setWhichday(whichday);
				   pis.addPersonalInvest(pi);
				   return "redirect:/personalFund/toPersonal.do";
				  


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
			model.addAttribute("whichday",whichday);
		    return "purchasetiming2";
		   /*
		    *失败 返回基金id 购买金额,支付方式,大写金额 到purchase2 页面 
		    */
		   //return "purchase2";
	   
	   }else {
		   msg= "金额不能为0";
		    model.addAttribute("msg", msg);
		    model.addAttribute("fund",ps.queryFundById(fundid));
			UserIdAndCardid uac = new UserIdAndCardid();
			uac.setUserid(loginUser.getId());
			uac.setCardid(bankcardid);
			UserBankCard ubc = ubcs.queryByTwoId(uac);
			model.addAttribute("bankcard",ubc);
			model.addAttribute("portion",portion);
			model.addAttribute("orderid",orderid);
			model.addAttribute("whichday",whichday);
		    return "purchasetiming2";
	   }
   }

     @RequestMapping("toComeBack2")
     public String toComeBack2(Model model,
    		 @RequestParam("orderid") Integer orderid) {
    	PersonalInvest pi = pis.queryPIByOrderID(orderid);
    	System.out.println(pi);
    	model.addAttribute("PersonalInvest", pi);
    	model.addAttribute("eachpay", pi.getEachpay());
    	model.addAttribute("whichday", pi.getWhichday());
    	 return "comeback2";
     }
     @RequestMapping("comeBack2")
     public String comeBack2(Model model,HttpSession session,
    		 @RequestParam("padPay") String padPay,
    		 @RequestParam("orderid") Integer orderid,
    		 @RequestParam("eachpay") Integer eachpay,
    		 @RequestParam("whichday") String whichday
    		 ) {
    	 PersonalInvest pi = pis.queryPIByOrderID(orderid);
         User loginUser = pi.getUser();
         session.setAttribute("loginUser", loginUser);
  	     String msg = null;
  	     String regex = "^[0-9]{8}$";
    	 if(padPay.matches(regex)&&padPay!=null&&padPay.equals(loginUser.getPadPay().toString())) {
    		 pis.upDataInveststate(orderid);
    		 return "redirect:/personalFund/toPersonal.do";
    	 }else {
    		msg= "支付密码错误";
            model.addAttribute("PersonalInvest", pi);       
            model.addAttribute("eachpay", eachpay);
            model.addAttribute("whichday", whichday);
		    model.addAttribute("msg", msg);
       	    return "comeback2";
    	 }

     }
     
}
