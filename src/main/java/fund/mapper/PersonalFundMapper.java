package com.zl.fund.mapper;

import java.util.List;

import com.zl.fund.pojo.Card;
import com.zl.fund.pojo.PersonalFund;
import com.zl.fund.pojo.Query;

public interface PersonalFundMapper {

    List<PersonalFund> queryFundByUser(Integer userid);
    /**
     * 查询用户在途资金
     * @param userid
     * @return
     */
    List<PersonalFund> queryOnwayFundByUser(Integer userid);
    /**
     * 查询交易记录
     * @param query
     * @return
     */
    List<PersonalFund> queryAllFundRecord(Query query);
    /**
     * 增加个人基金
     */
    int addPersonalFund(PersonalFund pf);
    /**
     * 通过订单号查个人基金
     */
    PersonalFund queryFundRecord(Integer orderid);
    /**
     * 查询用户银行卡是否有记录
     */
    int findRecords(Integer userid,String cardid);
    /**
     * 修改银行卡
     */
    int updateCardid(String newCardid,Integer userid,String oldCardid);
    /**
     * 基金赎回,更改订单状态
     * 
     */
    int upDateType(Integer orderid);
    /**
     * 查询银行卡基金
     */
    Card findCardValue(Integer userid, String cardid);
    
    int cencelTrade(Integer id);
    
    PersonalFund queryPersonalFundById(Integer id);
}