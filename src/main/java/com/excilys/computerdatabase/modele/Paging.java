package com.excilys.computerdatabase.modele;

import java.io.Serializable;
import java.util.List;


public class Paging <T> implements Serializable {
	
	public int offset;
	public List<T> actualList;
	public int indexPage;
	public int totalSize;
	
	int pageSize;
	
	/*public Paging(int offset, int limit) {
		Paging<Computer> p = ComputerDAO.getInstance().getAll(offset, limit);
		this.offset = p.offset;
		this.actualList = (List<T>) p.actualList;
		this.
	}*/
	
	public Paging() { }
	
	public Paging(int offset, List<T> actualList, int indexPage, int totalSize) {
		//super();
		this.offset = offset;
		this.actualList = actualList;
		this.indexPage = indexPage;
		this.totalSize = totalSize;
		
		this.pageSize = this.actualList.size();
	}
	

	public int getLimit(){
		return actualList.size();
	}


	@Override
	public String toString() {
		return "Paging [offset=" + offset + ", actualList=" + actualList
				+ ", indexPage=" + indexPage + ", totalSize=" + totalSize + "]";
	}

	
	
	
	
	public List<T> getActualList() {
		return actualList;
	}

	public void setActualList(List<T> actualList) {
		this.actualList = actualList;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	
	
	
}
