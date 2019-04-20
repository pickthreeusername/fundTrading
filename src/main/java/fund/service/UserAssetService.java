package com.zl.fund.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.zl.fund.pojo.Query;
import com.zl.fund.pojo.UserAsset;
import com.zl.fund.pojo.UserIdAndCardid;

public interface UserAssetService {

	/**
	 * 查询用户银行卡当日充值记录
	 * @param userid
	 * @return
	 */
	List<UserAsset> queryDayRecharge(UserIdAndCardid uab);
	/**
	 * 添加用户资产记录
	 * @param userAsset
	 */
	void insertUserAsset(UserAsset userAsset);
	/**
	 * 查询用户银行卡当日取现记录
	 * @param uab
	 * @return
	 */
	List<UserAsset> queryDayCash(UserIdAndCardid uab);
	/**
	 * 分页查询交易记录
	 * @param query
	 * @return
	 */
	PageInfo<UserAsset> queryUserAssetByPage(Query query,Integer pageNum);
}
