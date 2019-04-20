package com.zl.fund.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zl.fund.pojo.Fund;
import com.zl.fund.pojo.FundType;
import com.zl.fund.pojo.Paging;
import com.zl.fund.pojo.User;
import com.zl.fund.service.FundService;
import com.zl.fund.service.FundTypeService;

@Controller
public class IndexController {
	@Autowired
	FundService fs;
	@Autowired
	FundTypeService fts;
//	@Autowired
//	@Qualifier("fundTemplate")
	
	
	@RequestMapping("/index")
	public String toIndex(Model model, Integer id,HttpSession seesion) {
		List<Fund>hotList=fs.queryFundByHot();
		model.addAttribute("hotList",hotList);
		List<FundType>ftList=fts.queryFundType();
		model.addAttribute("ftList",ftList);
		User loginUser=(User) seesion.getAttribute("loginUser");
		model.addAttribute("loginUser", loginUser);
		if(id!=null) {
			Paging p=new Paging();
			p.setFundTypeId(id);
			List<Fund>fundList= fs.queryFundPaging(p);
			model.addAttribute("fundList",fundList);
		}else {
			Paging p=new Paging();
			p.setFundTypeId(1);
			List<Fund>fundList= fs.queryFundPaging(p);
			model.addAttribute("fundList",fundList);
		}
		return "index";
	}
	@RequestMapping("/toDetails/{id}")
	public String toDetails(@PathVariable("id") Integer id, Model model) {
		Fund fund=fs.queryFundById(id);
		model.addAttribute("fund",fund);
		return "details";
	}
	@RequestMapping("/fundMarket")
	public String index( Integer id,Model model) {
		List<FundType>ftList=fts.queryFundType();
		model.addAttribute("ftList",ftList);
		if(id!=null) {
			Paging p=new Paging();
			p.setFundTypeId(id);
			List<Fund>fundList= fs.queryFundPaging(p);
			model.addAttribute("fundList",fundList);
		}else {
			Paging p=new Paging();
			p.setFundTypeId(1);
			List<Fund>fundList= fs.queryFundPaging(p);
			model.addAttribute("fundList",fundList);
		}
		return "fundMarket";
	}
	@ResponseBody
	@RequestMapping("/pagingByAjax")
	public Map<String,Object> pagingByAjax(Integer fundTypeId,Paging p) {
		Map<String,Object> map=new HashMap<String,Object> ();
		p.setFundTypeId(fundTypeId);
		List<Fund>fundList=fs.queryFundPaging(p);
		map.put("fundList", fundList);
		System.out.println("babab----------------------");
		return map;
	}
}
