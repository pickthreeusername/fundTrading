package com.zl.fund.pojo;

import java.util.List;

import org.springframework.stereotype.Component;
@Component
public class Paging {
	private Integer stratCount;
	private Integer endCount;
	private Integer page;
	private Integer pageSize=5;
	private Integer totalCount;
	private Integer totalPage;
	private Integer fundTypeId;
	private List<Fund>list;
	
	
	public List<Fund> getList() {
		return list;
	}
	public void setList(List<Fund> list) {
		this.list = list;
	}
	public Integer getStratCount() {
		stratCount=(getPage()-1)*getPageSize();
		return stratCount;
	}
	public void setStratCount(Integer stratCount) {
		this.stratCount = stratCount;
	}
	public Integer getEndCount() {
		endCount=getPage()*getPageSize();
		return endCount;
	}
	public void setEndCount(Integer endCount) {
		this.endCount = endCount;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	public Integer getTotalPage() {
		if(getTotalCount()/getPageSize()==0) {
			totalPage=getTotalCount()/getPageSize();
		}else {
			totalPage=getTotalCount()/getPageSize()+1;
		}
		return totalPage;
	}
	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
	public Integer getFundTypeId() {
		return fundTypeId;
	}
	public void setFundTypeId(Integer fundTypeId) {
		this.fundTypeId = fundTypeId;
	}
	
}
