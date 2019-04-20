package com.zl.fund.service.impl;

import java.util.Date;
import java.util.List;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zl.fund.mapper.OrderMapper;
import com.zl.fund.pojo.PersonalFund;
import com.zl.fund.service.OrderService;
@WebService(serviceName="OrderService",
			targetNamespace="http://service.fund.zl.com",
			endpointInterface="com.zl.fund.service.OrderService"
		)
@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	private OrderMapper od;
	@Override
	public List<PersonalFund> queryOrderList(Date date) {
		return od.queryOrderList(date);
	}

}
