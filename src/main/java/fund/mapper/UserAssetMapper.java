package com.zl.fund.mapper;

import java.util.List;

import com.zl.fund.pojo.Query;
import com.zl.fund.pojo.User;
import com.zl.fund.pojo.UserAsset;
import com.zl.fund.pojo.UserIdAndCardid;

public interface UserAssetMapper {

	/**
	 * 查询用户银行卡每日充值记录
	 * @param uab 用户id和银行卡id
	 * @return
	 */
	List<UserAsset> queryDayRecharge(UserIdAndCardid uab);
    /**
     * 添加用户账户资产记录
     * @param asset
     */
	void insertUserAsset(UserAsset ua);
	/**
	 * 查询用户银行卡当日取现记录
	 * @param uab
	 * @return
	 */
	List<UserAsset> queryDayCash(UserIdAndCardid uab);
	/**
	 * 查询用户账户交易记录
	 * @param query
	 * @return
	 */
	List<UserAsset> queryByUser(Query query);
	/**
	 * 修改交易记录状态（申请成功---成功）
	 * @param user
	 */
	int updateStatus(Integer id);
}