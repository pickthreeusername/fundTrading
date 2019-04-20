package com.zl.fund.mapper;


import com.zl.fund.pojo.Asset;

public interface AssetMapper {

    int insert(Asset record);

    Asset selectByPrimaryKey(Integer id);

    /**
     * 通过用户id查询账户
     * @param userid
     * @return
     */
	Asset queryAssetByUserId(Integer userid);
	
	/**
	 * 修改用户账户余额
	 * @param record
	 * @return
	 */
	int updateSurplus(Asset record);
}