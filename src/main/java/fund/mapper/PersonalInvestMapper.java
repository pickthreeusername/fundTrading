package com.zl.fund.mapper;

import java.util.List;

import com.zl.fund.pojo.PersonalInvest;

public interface PersonalInvestMapper {
	/**
	 * 个人定投列表
	 * @return
	 */
    List<PersonalInvest> queryAllInvest(Integer userid);
    
    int updateBB(PersonalInvest personalInvest);
    /**
     * 增加个人定投
     */
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