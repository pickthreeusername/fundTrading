package com.zl.fund.mapper;

import java.util.List;

import com.zl.fund.pojo.Fund;
import com.zl.fund.pojo.Paging;

public interface FundMapper {
    public Fund queryFundById(Integer id);
    
    public List<Fund>queryFundByHot();
    
    public List<Fund>queryFundPaging(Paging p);
}