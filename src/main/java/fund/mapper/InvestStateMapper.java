package com.zl.fund.mapper;

import com.zl.fund.pojo.InvestState;

public interface InvestStateMapper {
	/**
	 * 按id查状态
	 * @param id
	 * @return
	 */
	InvestState queryInvestStateById(Integer id); 
}
