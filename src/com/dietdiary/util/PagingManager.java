package com.dietdiary.util;

public class PagingManager {
	
	private int totalCount;
	private int pageSize = 10;
	private int maxPage;
	private int maxBlock;
	private int blockSize = 10;
	private int currentBlock = 0;
	private int currentPage = 1;
	private int currentItemSize;
	private int currentPageSize;
	
	public PagingManager(int totalCount) {
		init(totalCount);
	}
	public PagingManager() {
	}
	public void init(int totalCount) {
		currentBlock = 0;
		setCurrentPage(1);
		setTotalCount(totalCount);
		setMaxPage();
		setMaxBlock();
		setCurrentItemSize();
		setCurrentPageSize();
	}
	
	public void setMaxPage() {
		maxPage = (int)Math.ceil(totalCount/(double)pageSize);
	}
	public void setMaxBlock() {
		maxBlock = (int)Math.ceil(maxPage/(double)blockSize) - 1;
	}
	public void moveBlockNext() {
		if(currentBlock<maxBlock) {
			currentBlock++;
			setCurrentPage(1);
			cal();
		}
	}
	public void moveBlockPrev() {
		if(currentBlock>0) {
			currentBlock--;
			setCurrentPage(1);
			cal();
		}
	}
	public void setCurrentItemSize() {
		int size = totalCount-(pageSize * currentPage + ((pageSize-1) * blockSize * currentBlock));
		// 101-(10 * 1 + (9 * 10 * 1);
		if(size>pageSize) {
			currentItemSize = pageSize;
		}else {
			currentItemSize = size;
		}
	}
	public void setCurrentPageSize() {
		int size = maxPage - (blockSize * currentBlock);
		//12-(10 * 0)
		if(size>blockSize) {
			currentPageSize = blockSize;
		}else {
			currentPageSize = size;
		}
	}
	public void cal() {
		setCurrentItemSize();
		setCurrentPageSize();
	}
	
	


	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getMaxPage() {
		return maxPage;
	}
	public int getMaxBlock() {
		return maxBlock;
	}
	public int getCurrentBlock() {
		return currentBlock;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
		setCurrentItemSize();
	}
	public int getCurrentItemSize() {
		return currentItemSize;
	}
	public int getBlockSize() {
		return blockSize;
	}
	public void setBlockSize(int blockSize) {
		this.blockSize = blockSize;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getCurrentPageSize() {
		return currentPageSize;
	}
	

	
	
	
//	public static void main(String[] args) {
//		PagingManager p = new PagingManager(55);
//		System.out.println(p.getCurrentPage());
//		System.out.println(p.getCurrentBlock());
//		System.out.println(p.getCurrentItemSize());
//		System.out.println(p.getCurrentPageSize());
//		
//		System.out.println("----");
//		
//		p.setCurrentPage(5);
//		System.out.println(p.getCurrentPage());
//		System.out.println(p.getCurrentBlock());
//		System.out.println(p.getCurrentItemSize());
//		System.out.println(p.getCurrentPageSize());
//	}
}
