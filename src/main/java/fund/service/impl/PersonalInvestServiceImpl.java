package com.zl.fund.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zl.fund.mapper.PersonalInvestMapper;
import com.zl.fund.pojo.PersonalInvest;
import com.zl.fund.service.PersonalInvestService;
@Service
public class PersonalInvestServiceImpl implements PersonalInvestService {
	@Autowired
	private PersonalInvestMapper pim;
	@Override
	public PageInfo<PersonalInvest> queryAllInvest(Integer userid, Integer pageNum) {
		//PageHelper为分页对象
		PageHelper.startPage(pageNum,4);
		List<PersonalInvest> investList = pim.queryAllInvest(userid);
		//计算当前日期，拿到今天是哪天
		 Calendar c = Calendar.getInstance();
		 c.setTime(new Date(System.currentTimeMillis()));
		 String year = String.format(Locale.CHINA, "%04d", c.get(Calendar.YEAR));
		 String month = String.format(Locale.CHINA, "%02d", c.get(Calendar.MONTH));
		 String day = String.format(Locale.CHINA, "%02d", c.get(Calendar.DAY_OF_MONTH));
		 Integer thisyear = Integer.valueOf(year);
		 Integer thismonth = Integer.valueOf(month)+1;
		 Integer today = Integer.valueOf(day);
		 System.out.println(year+thismonth+day);
		 //遍历我的定投列表，今天与定投日期比较
		 for(PersonalInvest pi:investList) {
			String a = pi.getWhichday();
			Integer investday = Integer.valueOf(a);
			if(today>investday) {   //定投日期在今天之前，那就是下次定投是下个月
				if(thismonth<12) {    //如果这个月不是12月
					Integer nextmonth = thismonth+1;
					if(nextmonth==2&&investday>28) {    //如果下一个月是2月定投日在28号以后，下期定投顺延到3月
						Integer nextmonth2 = nextmonth+1;
						pi.setBb(thisyear+"-"+nextmonth2+"-"+investday);
					}if(nextmonth==2&&investday<28) {    //如果下一个月是2月定投日在28号以前，2月正常定投
						pi.setBb(thisyear+"-"+nextmonth+"-"+investday);
						pim.updateBB(pi);
					}if(nextmonth!=2) {            //如果下个月不是2月份，正常定投
						pi.setBb(thisyear+"-"+nextmonth+"-"+investday);
						pim.updateBB(pi);
					}
				}if(thismonth==12){              //如果这个月是12月
					Integer nextyear = thisyear+1;     //明年
					Integer nextmonth = 1;				//一月
					pi.setBb(nextyear+"-"+nextmonth+"-"+investday);
					pim.updateBB(pi);
				}
			}if(today<=investday) {  //定投日期在今天之后或是今天，那就是下次定投是这个月
				pi.setBb(thisyear+"-"+thismonth+"-"+investday);
				pim.updateBB(pi);
			}
			
		}
		//将查询结果放入PageHelper
		PageInfo<PersonalInvest> pageInfo = new PageInfo<PersonalInvest>(investList);
		return pageInfo;
	}
	@Override
	public int addPersonalInvest(PersonalInvest pi) {
		// TODO Auto-generated method stub
		return pim.addPersonalInvest(pi);
	}
	@Override
	public int upDataInveststate(Integer orderId) {
		// TODO Auto-generated method stub
		return pim.upDataInveststate(orderId);
	}
	@Override
	public PersonalInvest queryPIByOrderID(Integer orderid) {
		// TODO Auto-generated method stub
		return pim.queryPIByOrderID(orderid);
	}
	@Override
	public List<PersonalInvest> getAllInvest() {
		// TODO Auto-generated method stub
		return pim.getAllInvest();
	}

}
