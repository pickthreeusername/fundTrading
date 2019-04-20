package com.zl.fund.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.zl.fund.mapper.AssetMapper;
import com.zl.fund.mapper.UserAssetMapper;
import com.zl.fund.mapper.UserBankCardMapper;
import com.zl.fund.pojo.Asset;
import com.zl.fund.pojo.User;
import com.zl.fund.pojo.UserAsset;
import com.zl.fund.pojo.UserBankCard;
import com.zl.fund.pojo.UserIdAndCardid;
import com.zl.fund.service.AssetService;
@Service
public class AssetServiceImpl implements AssetService {

	@Autowired
	private AssetMapper am;
	@Autowired
	private UserAssetMapper uam;
	@Autowired
	private UserBankCardMapper ubcm;
	
	@Override
	public Asset queryAssetByUserIDd(Integer userid) {
		return am.queryAssetByUserId(userid);
	}
	@Override
	public void updateSurplus(Asset asset) {
		am.updateSurplus(asset);
	}
	@Override
	@Transactional
	public String chongzhi(String cardid, User user, String money) {
		//扣除用户银行卡余额
		UserIdAndCardid uab = new UserIdAndCardid();
		uab.setCardid(cardid);
		uab.setUserid(user.getId());
		//查询原先余额
		UserBankCard queryByTwoId = ubcm.queryByTwoId(uab);
		BigDecimal balances = queryByTwoId.getBalances();
		balances = balances.subtract(new BigDecimal(money));
		//执行修改
		queryByTwoId.setBalances(balances);
		ubcm.updateBalances(queryByTwoId);
		//增加用户嘎嘎余额
		Asset asset = am.queryAssetByUserId(user.getId());
		asset.setSurplus(asset.getSurplus().add(new BigDecimal(money)));
		am.updateSurplus(asset);
		//记录账户交易记录表
		UserAsset userAsset = new UserAsset();
		userAsset.setInmoney(new BigDecimal(money));
		userAsset.setOperation((short) 1);
		userAsset.setStatus((short) 1);
		userAsset.setUser(user);
		userAsset.setTradetime(new Date());
		userAsset.setRemark("资金来源："+queryByTwoId.getBankCard().getBankname()+" | 尾号"+queryByTwoId.getCardid().substring(15));
		userAsset.setAa(queryByTwoId.getCardid());
		uam.insertUserAsset(userAsset);
		return "1";
	}
	@Override
	@Transactional
	public String quxian(JSONObject json, User user) {
		BigDecimal money = json.getBigDecimal("money");
		//减少用户嘎嘎余额
		Asset asset = am.queryAssetByUserId(user.getId());
		asset.setSurplus(asset.getSurplus().subtract(money));
		am.updateSurplus(asset);
		//增加用户银行卡余额
		UserIdAndCardid uab = new UserIdAndCardid();
		uab.setCardid(json.getString("cardid"));
		uab.setUserid(user.getId());
		//查询原先银行卡余额
		UserBankCard queryByTwoId = ubcm.queryByTwoId(uab);
		BigDecimal balances = queryByTwoId.getBalances();
		if(balances != null) {
			balances = balances.add(money);
		}
		//执行修改
		queryByTwoId.setBalances(balances);
		ubcm.updateBalances(queryByTwoId);
		//记录账户交易记录表
		UserAsset userAsset = new UserAsset();
		userAsset.setOutmoney(money);
		Integer op = json.getInteger("operation");
		//声明状态a:成功，b：快速取现，c:普通取现
		short a = 1, b = 2, c = 3;
		if(op == 2) {
			userAsset.setOperation(b);
			userAsset.setStatus(a);
		}
		else if(op == 3) {
			userAsset.setOperation(c);
			userAsset.setStatus(a);
		}
		userAsset.setUser(user);
		userAsset.setTradetime(new Date());
		userAsset.setRemark("资金流向："+queryByTwoId.getBankCard().getBankname()+" | 尾号"+queryByTwoId.getCardid().substring(15));
		userAsset.setAa(queryByTwoId.getCardid());
		uam.insertUserAsset(userAsset);
		return "1";
	}

}
