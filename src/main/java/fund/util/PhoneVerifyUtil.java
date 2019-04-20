package com.zl.fund.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
/**
 * 短信验证码工具类
 * @author THINK
 *
 */
@Component
public class PhoneVerifyUtil {
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	public boolean checkFrequency(String phone) {
		//60s内算频繁操作
		long time = System.currentTimeMillis();
		String msg = redisTemplate.opsForValue().get(phone);
		//已经发送过
		if (msg != null) {
			String[] array = msg.split("/");
			int gap = (int)((time - new Long(array[1])) / 1000);
			if (gap < 60) {
				//频繁操作
				return false;
			}else {
				return true;
			}
		}else {
			return true;
		}
	}
	/**
	 * 发送验证码
	 * @param phone
	 * @return
	 */
	public int sendCode(String phone) {
		if (checkFrequency(phone)) {
			//6位随机数
			int num = (int) ((Math.random() * 9 + 1 ) * 100000);
			String code = Integer.toString(num);
			System.out.println("--------------------------" + code);
			//时间
			String time = Long.toString(System.currentTimeMillis());
			redisTemplate.opsForValue().set(phone, code + "/" + time);
			//成功的话
			String returnCode = ExternalService.sendVerifyCode(phone, String.valueOf(num));
			if (returnCode.equals("00000")) {
				return 1;
			}
			//0:发送失败
			return 0;
			//return 1;
		}else {
			//-1：操作频繁
			return -1;
		}
	}
	/**
	 * 校验
	 * @param phone
	 * @param code
	 * @return
	 */
	public int checkCode(String phone, String code) {
		//时间
		long time = System.currentTimeMillis();
		String msg = redisTemplate.opsForValue().get(phone);
		if (msg != null) {
			String[] array = msg.split("/");
			int gap = (int)((time - new Long(array[1])) / 1000);
			//60s有效
			if (code.equals(array[0]) && gap < 60) {
				//验证通过
				redisTemplate.delete(phone);
				return 1;
			}else {
				return 0;
			}
		}else {
			return 0;
		}
	}
}
