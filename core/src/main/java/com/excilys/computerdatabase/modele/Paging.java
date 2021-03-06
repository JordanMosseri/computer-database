package com.excilys.computerdatabase.modele;

import java.io.Serializable;
import java.util.List;

/**
 * Contains all informations when transferring part of Ts
 * @author Jordan Mosseri
 *
 * @param <T>
 */
public class Paging <T> implements Serializable {
	
	private static final long serialVersionUID = 6047144449202457970L;
	
	/**
	 * Offset that indicate where to begin
	 */
	private int offset;
	
	/**
	 * Holds the part of Ts
	 */
	private List<T> actualList;
	
	/**
	 * Actual page number
	 */
	private int indexPage;
	
	/**
	 * Total entries in the database
	 */
	private int totalSize;
	
	/**
	 * Number of items in the list
	 */
	private int pageSize;
	
	
	public Paging() { }
	
	public Paging(int offset, List<T> actualList, int indexPage, int totalSize) {
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

	public int getOffset() {
		return offset;
	}

	public int getIndexPage() {
		return indexPage;
	}

	public int getTotalSize() {
		return totalSize;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public void setIndexPage(int indexPage) {
		this.indexPage = indexPage;
	}

	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}
	
	
	
	
}
