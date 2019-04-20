package com.zl.fund.mapper;

import com.zl.fund.pojo.Employee;

public interface EmpDao {
	
	/**
	 * 查询个人资料
	 * @param empId
	 * @return
	 */
	public Employee queryEmpById(Integer empId);
	
}
