package com.zl.fund.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.zl.fund.mapper.FundTypeMapper;
import com.zl.fund.pojo.FundType;
import com.zl.fund.service.FundTypeService;
@Service
public class FundTypeServiceImpl implements FundTypeService{
	@Autowired
	FundTypeMapper ftm;
	
//	@Cacheable(cacheNames="FundType")
	@Override
	public List<FundType> queryFundType() {
		return ftm.queryFundType();
	}

}
