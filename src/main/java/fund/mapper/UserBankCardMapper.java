package com.zl.fund.mapper;


import java.util.List;

import com.zl.fund.pojo.UserBankCard;
import com.zl.fund.pojo.UserIdAndCardid;

public interface UserBankCardMapper {
	
	public int insert(UserBankCard uc);
	
	public int delCard(Integer id);
	/**
	 * 通过用户id查询用户银行卡信息
	 * @param userid
	 * @return
	 */
	public List<UserBankCard> queryByUserId(Integer userid);

	/**
     * 通过用户id and 银行卡号  查银行卡信息
     */
	public UserBankCard queryByTwoId(UserIdAndCardid uac);
	
	/**
	 * 修改用户银行卡余额
	 */
	public void updateBalances(UserBankCard ub);
	
	/**
	 * 查询银行卡号
	 */
	public String findCardidById(Integer id);
	/**
	 * 
	 */
	public int findCount(String cardid);
}