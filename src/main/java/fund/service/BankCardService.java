package com.zl.fund.service;

import java.util.List;

import com.zl.fund.pojo.BankCard;
import com.zl.fund.pojo.Fund;

public interface BankCardService {

	public List<BankCard> queryBankCardAll();
	
	public BankCard selectByPrimaryKey(Integer id);
}
