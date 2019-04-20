package com.zl.fund.service;

import java.util.List;

import com.zl.fund.pojo.Fund;
import com.zl.fund.pojo.Paging;

public interface FundService {
public Fund queryFundById(Integer id);
    
    public List<Fund>queryFundByHot();
    
    public List<Fund>queryFundPaging(Paging p);


}
