package com.zl.fund.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zl.fund.mapper.FundMapper;
import com.zl.fund.pojo.Fund;
import com.zl.fund.service.PurchaseService;
@Service
public class PurchaseServiceImpl implements PurchaseService {
 
	@Autowired
	private FundMapper fm;
	@Override
	public Fund queryFundById(Integer id) {
		// TODO Auto-generated method stub
		return fm.queryFundById(id);
	}

}
