package com.zl.fund.util;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
/**
 * 查看账户是否锁定的工具类
 * @author THINK
 *
 */
@Component
public class CheckStatusUtil {
	@Autowired
	private StringRedisTemplate redisTemplate;
	private String surfix = "verifyTimes";
	/**
	 * 获取 2min内  核对交易密码错误的  次数 
	 * 2min内只能输错5次
	 * @return
	 */
	public int getIncorrectVerifyTimes(int id) {
		String times = redisTemplate.opsForValue().get(id + surfix);
		if (times == null) {
			return 0;
		}
		return Integer.parseInt(times);
	}
	/**
	 * 设置 核对交易密码错误的次数
	 */
	public void setIncorrectVerifyTimes(int id) {
		boolean ifExist = redisTemplate.hasKey(id + surfix);
		if (ifExist) {
			//如果2min内存过错误次数
			//错了5次
			if (redisTemplate.opsForValue().get(id + surfix) .equals("4")) {
				//锁号三分钟，移除错误次数
				lockAccount(id);
				redisTemplate.delete(id + surfix);
			}else {
				redisTemplate.boundValueOps(id + surfix).increment(1);
			}
		}else {
			//如果之前没有存过，设置次数,120s过期
			redisTemplate.opsForValue().set( id + surfix, "1", 60 * 2, TimeUnit.SECONDS);
		}
	}
	/**
	 * 查看用户锁定状态  0：没有被锁     其他数字：剩余锁定时间
	 * @param id
	 * @return
	 */
	public int ifLock(int id) {
		String lockInfo = redisTemplate.opsForValue().get(id + "lock");
		if (lockInfo == null) {
			//没有被锁
			return 0;
		}else {
			long min = redisTemplate.getExpire(id + "lock",TimeUnit.MINUTES);
			return (int)min + 1;
		}
	}
	/**
	 * 锁定账号3min
	 * @param id
	 */
	public void lockAccount(int id) {
		redisTemplate.opsForValue().set( id + "lock", "1", 60 * 3, TimeUnit.SECONDS);
	}
}
