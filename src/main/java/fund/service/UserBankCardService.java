package com.zl.fund.service;

import java.util.List;

import com.zl.fund.pojo.UserBankCard;
import com.zl.fund.pojo.UserIdAndCardid;

public interface UserBankCardService {
	
    public int insert(UserBankCard uc);
	
	public int delCard(Integer id);

	List<UserBankCard> queryByUserId(Integer userid);
	
	/**
	 * 通过用户id和银行卡号查询
	 * @param uab
	 * @return
	 */
	UserBankCard queryByTwoId(UserIdAndCardid uab);

	/**
	 * 修改用户余额
	 * @param queryByTwoId
	 */
	public void updateBalances(UserBankCard queryByTwoId);
	
	/**
	 * 查询银行卡号
	 */
	public String findCardidById(Integer id);
	
	public int findCount(String cardid);
}
