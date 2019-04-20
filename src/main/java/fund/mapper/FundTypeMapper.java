package com.zl.fund.mapper;

import java.util.List;

import com.zl.fund.pojo.FundType;

public interface FundTypeMapper {
    public FundType queryFundTypeById(Integer id);
    
    public List<FundType>queryFundType();
}