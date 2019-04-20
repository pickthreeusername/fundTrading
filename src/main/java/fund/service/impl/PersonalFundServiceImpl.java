package com.zl.fund.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zl.fund.mapper.PersonalFundMapper;
import com.zl.fund.pojo.Card;
import com.zl.fund.pojo.PersonalFund;
import com.zl.fund.pojo.Query;
import com.zl.fund.service.PersonalFundService;
@Service
public class PersonalFundServiceImpl implements PersonalFundService {
	@Autowired
	private PersonalFundMapper personalFundMapper;
	
	@Override
	public PageInfo<PersonalFund> queryFundByUser(Integer Id,Integer pageNum) {
		//PageHelper为分页对象
		PageHelper.startPage(pageNum,4);
		List<PersonalFund> fundList = personalFundMapper.queryFundByUser(Id);
		//将查询结果放入PageHelper
		PageInfo<PersonalFund> pageInfo = new PageInfo<PersonalFund>(fundList);
		
		return pageInfo;
	}

	@Override
	public String calculateFund(Integer Id) {
		List<PersonalFund> fundList = personalFundMapper.queryFundByUser(Id);
		BigDecimal i = new BigDecimal(0);
		if(fundList!=null && fundList.size()>0) {
			for(PersonalFund pf:fundList) {
				i = i.add((pf.getPortion().multiply(pf.getFund().getUnitvalue())));
	//			- pf.getPortion().doubleValue()*pf.getFund().getUnitvalue().doubleValue()*pf.getFund().getManagerate().doubleValue() - pf.getPortion().doubleValue()*pf.getFund().getUnitvalue().doubleValue()*pf.getFund().getTraderate().doubleValue()
			}
		}
		DecimalFormat df = new DecimalFormat("####.00");
		//如果精度为零
		if(i.scale()==0) {
			return "0.00";
		}else {
			String m = df.format(i);
			return m;
		}
	}

	@Override
	public String calculateOnwayFund(Integer Id) {
		List<PersonalFund> fundList = personalFundMapper.queryOnwayFundByUser(Id);
		BigDecimal i = new BigDecimal(0);
		if(fundList!=null && fundList.size()>0) {
			for(PersonalFund pf:fundList) {
				i = i.add((pf.getPortion().multiply(pf.getFund().getUnitvalue())));
			}
		}
		DecimalFormat df = new DecimalFormat("####.00");
		//如果精度为零
		if(i.scale()==0) {
			return "0.00";
		}else {
			String m = df.format(i);
			return m;
		}
	}

	@Override
	public PageInfo<PersonalFund> queryAllFundRecord(Query query,Integer pageNum) {
		//PageHelper为分页对象
		PageHelper.startPage(pageNum,4);
		List<PersonalFund> fundList = personalFundMapper.queryAllFundRecord(query);
		//将查询结果放入PageHelper
		PageInfo<PersonalFund> pageInfo = new PageInfo<PersonalFund>(fundList);
		return pageInfo;
	}

	@Override
	public int addPersonalFund(PersonalFund pf) {
		// TODO Auto-generated method stub
		return personalFundMapper.addPersonalFund(pf);
	}

	@Override
	public PersonalFund queryFundRecord(Integer orderid) {
		// TODO Auto-generated method stub
		return personalFundMapper.queryFundRecord(orderid);
	}

	@Override
	public int findRecords(Integer userid, String cardid) {
		return personalFundMapper.findRecords(userid, cardid);
	}

	@Override
	public int updateCardid(String newCardid, Integer userid, String oldCardid){
		return personalFundMapper.updateCardid(newCardid, userid, oldCardid);
	}

	@Override
	public int upDateType(Integer orderid) {
		// TODO Auto-generated method stub
		return personalFundMapper.upDateType(orderid);
	}

	@Override
	public PageInfo<PersonalFund> queryOnwayFundByUser(Integer userid, Integer pageNum) {
		//PageHelper为分页对象
		PageHelper.startPage(pageNum,4);
		List<PersonalFund> onWayList = personalFundMapper.queryOnwayFundByUser(userid);
		for(PersonalFund pf:onWayList) {
			String str = new SimpleDateFormat("yyyy年MM月dd日").format(pf.getOrderdate());
			String nian = str.substring(0,4); //取年
		    String yue = str.substring(str.indexOf("年")+1,str.indexOf("月")); //取月
		    String ri = str.substring(str.indexOf("月")+1,str.indexOf("日")); //取日
		    Integer year = Integer.valueOf(nian);
		    Integer month = Integer.valueOf(yue); 
		    Integer day = Integer.valueOf(ri);
		    if(month==1||month==3||month==5||month==7||month==8||month==10||month==12) {  //有31天的月
		    	if(month!=12) {        //不是最后一个月
		    		if(day<=28) {		//不是最后三天
		    			Integer expectday = day+3;
		    			pf.setBb(year+"-"+month+"-"+expectday);
		    		}if(day>28) {       //是最后三天
		    			Integer testday = day+3;
		    			Integer expectday = testday-31;
		    			Integer expectmonth = month+1;
		    			pf.setBb(year+"-"+expectmonth+"-"+expectday);
		    		}
		    	}if(month==12) {     //是最后一个月
		    		if(day<=28) {		//不是最后三天
		    			Integer expectday = day+3;
		    			pf.setBb(year+"-"+month+"-"+expectday);
		    		}if(day>28) {
		    			Integer expectyear = year+1;
		    			Integer expectmonth = 1;
		    			Integer testday = day+3;
		    			Integer expectday = testday-31;
		    			pf.setBb(expectyear+"-"+expectmonth+"-"+expectday);
		    		}
		    	}
		    }if(month==4||month==6||month==9||month==11) {   //30天的月份
		    	if(day<=27) {
		    		Integer expectday = day+3;
	    			pf.setBb(year+"-"+month+"-"+expectday);
		    	}if(day>27) {
		    		Integer testday = day+3;
	    			Integer expectday = testday-30;
	    			Integer expectmonth = month+1;
	    			pf.setBb(year+"-"+expectmonth+"-"+expectday);
		    	}
		    }if(month==2) {
		    	if(day<=25) {
		    		Integer expectday = day+3;
	    			pf.setBb(year+"-"+month+"-"+expectday);
		    	}if(day>25) {
		    		Integer testday = day+3;
	    			Integer expectday = testday-28;
	    			Integer expectmonth = month+1;
	    			pf.setBb(year+"-"+expectmonth+"-"+expectday);
		    	}
		    }
		}
		PageInfo<PersonalFund> pageInfo = new PageInfo<PersonalFund>(onWayList);
		return pageInfo;
	}
/**
 * 查询银行卡关联资产
 */
	@Override
	public Card findCardValue(Integer userid, String cardid) {
		return personalFundMapper.findCardValue(userid, cardid);
	}

	@Override
	public int cencelTrade(Integer id) {
		
		return personalFundMapper.cencelTrade(id);
	}

	@Override
	public PersonalFund queryPersonalFundById(Integer id) {
		
		return personalFundMapper.queryPersonalFundById(id);
	}
}
