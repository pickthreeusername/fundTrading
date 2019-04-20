package com.zl.fund.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.zl.fund.pojo.Asset;
import com.zl.fund.pojo.PersonalFund;
import com.zl.fund.pojo.PersonalInvest;
import com.zl.fund.pojo.Query;
import com.zl.fund.pojo.User;
import com.zl.fund.pojo.UserAsset;
import com.zl.fund.service.AssetService;
import com.zl.fund.service.PersonalFundService;
import com.zl.fund.service.PersonalInvestService;
import com.zl.fund.service.UserAssetService;

@Controller
@RequestMapping("personalFund")
public class PersonalFundController {
	@Autowired
	private PersonalFundService personalFundService;
	@Autowired
	private PersonalInvestService personalInvestService;
	@Autowired
	private AssetService as;
	@Autowired
	private UserAssetService uas;
	
	@RequestMapping("toPersonal.do")
	public String toPersonal(Model model,HttpSession session,@RequestParam(value="pageNum",defaultValue="1")Integer pageNum) {
		User user = (User) session.getAttribute("loginUser");
		PageInfo<PersonalFund> pageInfo  = personalFundService.queryFundByUser(user.getId(),pageNum);
		model.addAttribute("pageInfo", pageInfo);
		Integer size = pageInfo.getList().size();
		model.addAttribute("size", size);
		
		String m = personalFundService.calculateFund(user.getId());
		model.addAttribute("m", m);
		return "personalFund";
	}
	
	@RequestMapping("toTrade.do")
	public String toTrade(Model model,HttpSession session,Query query,@RequestParam(value="pageNum",defaultValue="1")Integer pageNum) {
		User user = (User) session.getAttribute("loginUser");
		query.setQueryUserId(user.getId());
		PageInfo<PersonalFund> pageInfo = personalFundService.queryAllFundRecord(query, pageNum);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("query", query);
		Integer size = pageInfo.getList().size();
		model.addAttribute("size", size);
		
		return "tradeRecord";
	}
	
	@RequestMapping("toInvest.do")
	public String toInvest(Model model,HttpSession session,@RequestParam(value="pageNum",defaultValue="1")Integer pageNum) {
		User user = (User) session.getAttribute("loginUser");
		PageInfo<PersonalInvest> pageInfo = personalInvestService.queryAllInvest(user.getId(), pageNum);
		model.addAttribute("pageInfo", pageInfo);
		Integer size = pageInfo.getList().size();
		model.addAttribute("size", size);
		
		return "personalInvest";
	}
	
	@RequestMapping("toOnWay.do")
	public String toOnWay(Model model,HttpSession session,@RequestParam(value="pageNum",defaultValue="1")Integer pageNum) {
		User user = (User) session.getAttribute("loginUser");
		PageInfo<PersonalFund> pageInfo = personalFundService.queryOnwayFundByUser(user.getId(), pageNum);
		model.addAttribute("pageInfo", pageInfo);
		Integer size = pageInfo.getList().size();
		model.addAttribute("size", size);
		return "onWay";
	}
	
	@RequestMapping("cancelTrade.do")
	@ResponseBody
	public Map<String,Object> cancelTrade(Integer id,HttpSession session){
		User user = (User) session.getAttribute("loginUser");
		Map<String,Object> json=new HashMap<String,Object>();
		int num = personalFundService.cencelTrade(id);
		if(num==0) {
			json.put("flag", false);
		}else {
			json.put("flag", true);
			PersonalFund pf = personalFundService.queryPersonalFundById(id);
			if(pf.getTradetype().getId()==1) {  //申购撤单退钱
				Asset asset = as.queryAssetByUserIDd(user.getId());
				BigDecimal money =  asset.getSurplus();
				asset.setSurplus(pf.getPortion().multiply(pf.getFund().getUnitvalue()).add(money));
				as.updateSurplus(asset);      //返回资产
				//记录一条交易记录
				UserAsset ua = new UserAsset();
				ua.setUser(user);
				ua.setInmoney(pf.getPortion().multiply(pf.getFund().getUnitvalue()));
				ua.setOperation(Short.parseShort("4"));
				ua.setStatus(Short.parseShort("4"));
				ua.setRemark(pf.getFund().getFundname());
				ua.setTradetime(new Date());
				uas.insertUserAsset(ua);
			}
		}
		return json;
	}
}
