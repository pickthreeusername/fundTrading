package com.zl.fund.controller;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.zl.fund.pojo.Asset;
import com.zl.fund.pojo.Query;
import com.zl.fund.pojo.User;
import com.zl.fund.pojo.UserAsset;
import com.zl.fund.service.AssetService;
import com.zl.fund.service.PersonalFundService;
import com.zl.fund.service.UserAssetService;

@Controller
public class MyAssetController {

	@Autowired
	private PersonalFundService pfs;
	
	@Autowired
	private AssetService as;
	
	@Autowired
	private UserAssetService uas;
	
	//跳转资产管理页面
	@RequestMapping("toMyAsset.do")
	public String toMyAsset(Model model, HttpSession session) {
		User user = (User)session.getAttribute("loginUser");
		Integer userid = user.getId();
		//查询用户资产余额
		Asset asset = as.queryAssetByUserIDd(userid);
		DecimalFormat df = new DecimalFormat("####.00");
		BigDecimal surplus = BigDecimal.ZERO;
		if(asset != null) {
			surplus = asset.getSurplus();
		}
		//查询用户关联基金余额
		String yigou = pfs.calculateFund(userid);
		//查询用户在途基金
		String zaitu = pfs.calculateOnwayFund(userid);
		//计算我的资产
		BigDecimal totle = surplus.add(new BigDecimal(yigou)).add(new BigDecimal(zaitu));
		String myZichan = "0.00";
		if(totle.compareTo(BigDecimal.ZERO) == 1) {
			myZichan = df.format(totle);
		}
		String sur = "0.00";
		if(surplus.compareTo(BigDecimal.ZERO) == 1) {
			sur = df.format(surplus);
		}
		model.addAttribute("sur", sur);
		model.addAttribute("asset", asset);
		model.addAttribute("yigou", yigou);
		model.addAttribute("myZichan", myZichan);
		model.addAttribute("zaitu", zaitu);
		return "myAsset";
	}
	
	//查询用户资产交易记录
	@RequestMapping("toMyListAsset.do")
	public String toListUserAsset(Model model, HttpSession session, Query query, String pageNum) {
		User user = (User)session.getAttribute("loginUser");
		query.setQueryUserId(user.getId());
		if(pageNum == null) {
			pageNum = "1";
		}
		PageInfo<UserAsset> list_ua = uas.queryUserAssetByPage(query, Integer.parseInt(pageNum));
		model.addAttribute("list_ua", list_ua);
		return "listUserAsset";
	}
	
	//ajax查询用户资产交易记录
//	@RequestMapping("ajaxListAsset.do")
//	@ResponseBody
//	public Map<String,Object> ajaxListUserAsset(HttpSession session, Query query) {
//		Map<String,Object> map = new HashMap<>();
//		User user = (User)session.getAttribute("loginUser");
//		query.setQueryUserId(user.getId());
//		PageInfo<UserAsset> list_ua = uas.queryUserAssetByPage(query, 1);
//		map.put("list_ua", list_ua);
//		return map;
//	}
}
