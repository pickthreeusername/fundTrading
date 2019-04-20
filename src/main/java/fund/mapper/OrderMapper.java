package com.zl.fund.mapper;

import java.util.Date;
import java.util.List;

import javax.jws.WebService;

import com.zl.fund.pojo.PersonalFund;

public interface OrderMapper {
	List<PersonalFund> queryOrderList(Date date);
}
