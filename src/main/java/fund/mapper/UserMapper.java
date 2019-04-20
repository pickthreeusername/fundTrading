package com.zl.fund.mapper;

import java.util.List;

import com.zl.fund.pojo.User;

public interface UserMapper {
	/**
	 * 手机号是否存在
	 * @param phone
	 * @return
	 */
	int checkPhoneExist(String phone);
	/**
	 * 用户注册
	 * @param user
	 * @return
	 */
	void userRegister(User user);
	/**
	 * 修改手机号
	 * @param user
	 * @return
	 */
	int updatePhone(User user);
	/**
	 * 修改登录密码
	 * @param user
	 * @return
	 */
	int updatePassword(User user);
	/**
	 * 修改交易密码
	 * @param user
	 * @return
	 */
	int updateTransCode(User user);
	/**
	 * 修改个人信息
	 * @param user
	 * @return
	 */
	int updateDetailInfo(User user);
	/**
	 * 修改地址
	 * @param user
	 * @return
	 */
	int updateAddress(User user);
	
	/**
	 * 修改邮箱
	 * @param user
	 * @return
	 */
	int updateEmail(User user);
	
	/**
	 * 修改风险等级
	 * @param user
	 * @return
	 */
	int updateRisk(User user);
	/**
	 * 查看身份证号是否已存在
	 * @param idCard
	 * @return
	 */
	int checkIdCardExist(String idCard);
	/**
	 * 插入身份证号和名字
	 * @param user
	 * @return
	 */
	int updateUserIdCard(User user);
	/**
	 * 根据手机号查
	 * @param phone
	 * @return
	 */
	User selectByPhone(String phone);
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    User selectByPrimaryKey(Integer id);

    List<User> selectAll();

    int updateByPrimaryKey(User record);
    
    User customerLogin(User user);
    
    
}