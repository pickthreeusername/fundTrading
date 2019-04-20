package com.zl.fund.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zl.fund.mapper.FundMapper;
import com.zl.fund.pojo.Fund;
import com.zl.fund.pojo.Paging;
import com.zl.fund.service.FundService;
@Service
public class FundServiceImpl implements FundService{
	@Autowired
	FundMapper fm;
	
	@Override
	public Fund queryFundById(Integer id) {
		return fm.queryFundById(id);
	}

	@Override
	public List<Fund> queryFundByHot() {
		return fm.queryFundByHot();
	}

	@Override
	public List<Fund> queryFundPaging(Paging p) {
		if(p.getPage()==null) {
			p.setPage(1);
		}
		fm.queryFundPaging(p);
		return p.getList();
	}

}
