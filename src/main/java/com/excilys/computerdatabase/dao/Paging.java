package com.excilys.computerdatabase.dao;

import java.util.List;

import com.excilys.computerdatabase.modele.Computer;

public class Paging <T> {
	
	public int offset;
	public List<T> actualList;
	public int indexPage;
	public int totalSize;
	
	
	public Paging(int offset, List<T> actualList, int indexPage, int totalSize) {
		super();
		this.offset = offset;
		this.actualList = actualList;
		this.indexPage = indexPage;
		this.totalSize = totalSize;
	}
	

	public int getLimit(){
		return actualList.size();
	}
	
}
