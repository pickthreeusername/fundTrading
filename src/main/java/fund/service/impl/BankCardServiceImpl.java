package com.zl.fund.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zl.fund.mapper.BankCardMapper;
import com.zl.fund.pojo.BankCard;
import com.zl.fund.service.BankCardService;
@Service
public class BankCardServiceImpl implements BankCardService {

	@Autowired
	private BankCardMapper bcm;
	@Override
	public List<BankCard> queryBankCardAll() {
		// TODO Auto-generated method stub
		return bcm.queryBankCardAll();
	}
	@Override
	public BankCard selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return bcm.selectByPrimaryKey(id);
	}

}
