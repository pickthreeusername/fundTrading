package com.zl.fund.mapper;

import java.util.List;

import com.zl.fund.pojo.BankCard;

public interface BankCardMapper {
	/**
	 * 查询所有银行卡
	 * @return
	 */
	public List<BankCard> queryBankCardAll();
    /**
     * 
     * 通过id查
     */
	public BankCard selectByPrimaryKey(Integer id);
}