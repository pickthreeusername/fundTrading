package com.zl.fund.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zl.fund.mapper.UserAssetMapper;
import com.zl.fund.pojo.PersonalFund;
import com.zl.fund.pojo.Query;
import com.zl.fund.pojo.UserAsset;
import com.zl.fund.pojo.UserIdAndCardid;
import com.zl.fund.service.UserAssetService;
@Service
public class UserAssetServiceImpl implements UserAssetService {

	@Autowired
	UserAssetMapper uam;
	@Override
	public List<UserAsset> queryDayRecharge(UserIdAndCardid uab) {
		return uam.queryDayRecharge(uab);
	}
	@Override
	public void insertUserAsset(UserAsset userAsset) {
		uam.insertUserAsset(userAsset);
	}
	@Override
	public List<UserAsset> queryDayCash(UserIdAndCardid uab) {
		return uam.queryDayCash(uab);
	}
	@Override
	public PageInfo<UserAsset> queryUserAssetByPage(Query query,Integer pageNum) {
		//PageHelper为分页对象
		PageHelper.startPage(pageNum,5);
		List<UserAsset> list = uam.queryByUser(query);
		//将查询结果放入PageHelper
		PageInfo<UserAsset> pageInfo = new PageInfo<UserAsset>(list);
		return pageInfo;
	}

}
