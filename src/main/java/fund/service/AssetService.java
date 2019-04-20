package com.zl.fund.service;

import com.alibaba.fastjson.JSONObject;
import com.zl.fund.pojo.Asset;
import com.zl.fund.pojo.User;

public interface AssetService {
	/**
	 * 通过用户id查询账户信息
	 * @param userid
	 * @return
	 */
	public Asset queryAssetByUserIDd(Integer userid);
	/**
	 * 修改账户余额
	 * @param asset
	 */
	public void updateSurplus(Asset asset);
	/**
	 * 充值业务操作
	 * @param cardid 银行卡号
	 * @param user 用户信息
	 * @param money 充值金额
	 * @return
	 */
	public String chongzhi(String cardid,User user, String money);
	/**
	 * 取现业务操作
	 * @param json 取现相关参数
	 * @param user 用户信息
	 * @return
	 */
	public String quxian(JSONObject json, User user);
}
