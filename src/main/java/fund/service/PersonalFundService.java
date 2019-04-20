package com.zl.fund.service;

import com.github.pagehelper.PageInfo;
import com.zl.fund.pojo.Card;
import com.zl.fund.pojo.PersonalFund;
import com.zl.fund.pojo.Query;

public interface PersonalFundService {
	/**
	 * 个人基金列表，分页
	 * @param Id 用户id
	 * @param pageNo 页数
	 * @return
	 */
	PageInfo<PersonalFund> queryFundByUser(Integer Id,Integer pageNum);
	/**
	 * 个人基金总资产计算（已购）
	 * @param Id 用户id
	 * @return
	 */
	String calculateFund(Integer Id);
	/**
	 * 在途资金计算
	 * @param Id
	 * @return
	 */
	String calculateOnwayFund(Integer Id);
	/**
	 * 查询交易记录
	 * @param query
	 * @return
	 */
	PageInfo<PersonalFund> queryAllFundRecord(Query query,Integer pageNum);
	/**
	 * 增加个人基金
	 */
	int addPersonalFund(PersonalFund pf);
	/**
	 * 通过订单号查个人基金
	 * @param orderid
	 * @return
	 */
    PersonalFund queryFundRecord(Integer orderid);
    
    /**
     * 查询用户银行卡是否有记录
     */
    int findRecords(Integer userid,String cardid);
    
    /**
     * 修改银行卡
     */
    int updateCardid(String newCardid,Integer userid, String oldCardid);
    /**
     * 基金赎回,更改订单状态
     * 
     */
    int upDateType(Integer orderid);
    /**
     * 在途资金列表
     * @param userid
     * @param pageNum
     * @return
     */
    PageInfo<PersonalFund> queryOnwayFundByUser(Integer userid,Integer pageNum);
    /**
     * 查询银行卡资产
     * @return
     */
    Card findCardValue(Integer userid, String cardid);
    
    int cencelTrade(Integer id);
    
    PersonalFund queryPersonalFundById(Integer id);
}
