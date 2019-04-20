package com.zl.fund.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.zl.fund.pojo.PersonalInvest;

public interface PersonalInvestService {
	/**
	 * 个人定投管理
	 * @param userid
	 * @param pageNum
	 * @return
	 */
	PageInfo<PersonalInvest> queryAllInvest(Integer userid,Integer pageNum);
	int addPersonalInvest(PersonalInvest pi);
	/**
     * 修改定投状态
     */
    int upDataInveststate(Integer orderId);
    /**
     * 通过id查定投
     */
    PersonalInvest queryPIByOrderID(Integer orderid);
    /**
     * 查看所有定投
     */
    List<PersonalInvest> getAllInvest();
}
