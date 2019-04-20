package com.zl.fund.service;

import java.util.Date;
import java.util.List;

import javax.jws.WebService;

import com.zl.fund.pojo.PersonalFund;
@WebService(name="OrderService", 
targetNamespace="http://service.fund.zl.com")
public interface OrderService {
	List<PersonalFund> queryOrderList(Date date);
}
