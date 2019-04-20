package com.zl.fund.controller;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zl.fund.pojo.Asset;
import com.zl.fund.pojo.BankCard;
import com.zl.fund.pojo.Card;
import com.zl.fund.pojo.User;
import com.zl.fund.pojo.UserBankCard;
import com.zl.fund.service.AssetService;
import com.zl.fund.service.PersonalFundService;
import com.zl.fund.service.UserBankCardService;
import com.zl.fund.util.BankCardUtil;

@Controller
@RequestMapping("/bank")
public class BankCardController {
	@Autowired
	private UserBankCardService us;
	@Autowired
	private PersonalFundService ps;
	@Autowired
	private AssetService as;

	@RequestMapping("/toAdd.do")
	public ModelAndView toAdd(HttpSession session) {
		User user = (User) session.getAttribute("loginUser");
		Integer pay = user.getPadPay();
		System.out.println("pay=="+pay);
		ModelAndView mv = new ModelAndView();
		mv.addObject("pay",pay);
		mv.setViewName("bankCardAdd");
		return mv;
    }
	
	@RequestMapping("/toChange.do")
	@ResponseBody
	public ModelAndView toChange(Integer id) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("cid",id);
		mv.setViewName("bankCardChange");
		return mv;
    }
	/**
	 * 逻辑删除银行卡 
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/delCard.do")
	public ModelAndView delCard(Integer id,Integer userid,HttpSession session) {
		User user = (User) session.getAttribute("loginUser");
	    Integer uid = user.getUserId();
	    Asset asset = as.queryAssetByUserIDd(uid);
	    BigDecimal surplus = asset.getSurplus();
	    System.out.println("余额=="+surplus);
		String msg="";
		String cardid  = us.findCardidById(id);
		int records = ps.findRecords(userid, cardid);
		if(surplus==null) {
			if(records==0) {
				int num1 = us.delCard(id);	
				if(num1>0) {
					msg="succeed";
				}else {
					msg="fail";
				}
			}else {
				msg="undone";
			}			
		}else {
			msg="sur";
		}		
		ModelAndView mv = new ModelAndView("redirect:cards.do?msg="+msg);
		return mv ;
	}
  /**
   * 查询该用户的所有银行卡
   */
	@RequestMapping("/cards.do")
	@ResponseBody
	public ModelAndView selectCards(HttpSession session,String msg) {
    	User user = (User) session.getAttribute("loginUser");
        int userid = user.getId();
		ModelAndView mv = new ModelAndView();
		List<UserBankCard> cardList = us.queryByUserId(userid);
		mv.addObject("cards",cardList);		
		mv.setViewName("bankCardManage");
		mv.addObject("msg",msg);
		Integer pay = user.getPadPay();
		if(pay==null) {
			pay=0;
		}
		mv.addObject("pay",pay);
		System.out.println("999="+user.getPadPay());
		return mv;
	}
	/**
	 * 查询关联资产
	 */
	@RequestMapping("/cardsValue.do")
	@ResponseBody
	public Double cardsValue(Integer id,Integer userid) {
		System.out.println("id="+id+"userid=="+userid);
		Double money=0.0;
		String cardid = us.findCardidById(id);    //获取银行卡号
		System.out.println("cardid="+cardid);
		Card card = ps.findCardValue(userid, cardid);
		if(card==null) {
			money=0.0;
		}else {
			int portion = card.getPortion();
			Double unit = card.getUnitValue();
			money = portion*unit;
			System.out.println("money="+money);
		}
	    return money;
	}
	
	@RequestMapping("/encode.do")
	public String ts(String phone) {
		String code = BankCardUtil.phoneMsg(phone);		
		System.out.println("code=="+code);
		return "ts";
	}
	/**
	 * 添加银行卡
	 * @param pro
	 * @param cit
	 * @param uc
	 * @param bankcardid
	 * @return
	 */
	@RequestMapping("/add.do")
	@ResponseBody
	public Integer addCard(@RequestParam(value="province") String pro,@RequestParam(value="city") String cit,
		UserBankCard uc,@RequestParam(value="bankcardid") Integer bankcardid,HttpSession session){
		User user = (User) session.getAttribute("loginUser");
		BankCard bc = new BankCard();
		bc.setId(bankcardid);
		int num = 0;
		String str=pro+cit;
		uc.setBankDeposit(str);	
		uc.setBankCard(bc);
		uc.setUserid(user.getId());
		uc.setBalances(new BigDecimal(100000.00));
		num = us.insert(uc);
		return num;	    
	}	
	
	@RequestMapping("/yan.do")
	@ResponseBody
	public Map<String,Object> addCard2(HttpSession session,String cardid){
		User user = (User) session.getAttribute("loginUser");
//		String idcard= user.getIdCard();    //获得用户的身份证号
//		String name = user.getRealName();
//		String info = BankCardUtil.bankCard(cardid, idcard, name);
		String info = "成功";
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("info", info);
		return map;	    
	}
	/**
	 * 验证银行卡是否存在
	 */
	@RequestMapping("/exist.do")
	@ResponseBody
	public Integer exist(String cardid) {
		int count = us.findCount(cardid);
		return count;
	}
	
}
