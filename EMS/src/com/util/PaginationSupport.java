package com.util;

public class PaginationSupport{
	
	public final static int PAGESIZE = 10;

	private int pageSize = PAGESIZE;
	
	private int totalCount;

	private int startIndex;

	private int[] indexes = new int[0];

	public PaginationSupport(int startIndex) {
		setStartIndex(startIndex);
	}

	public void setTotalCount(int totalCount) {
		if (totalCount > 0) {
			this.totalCount = totalCount;
			int count = totalCount / pageSize;
			if (totalCount % pageSize > 0)
				count++;
			indexes = new int[count];
			for (int i = 0; i < count; i++) {
				indexes[i] = pageSize * i;
			}
		}else{
			this.totalCount = 0;
		}
	}
	
	public int getTotalCount() {
		return totalCount;
	}
	
	public void setIndexes(int[] indexes) {
		this.indexes = indexes;
	}
	
	public int[] getIndexes() {
		return indexes;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}
	
	public int getStartIndex() {
		return startIndex;
	}
	
	public int getPageCount() {
		int count = totalCount / pageSize;
		if (totalCount % pageSize > 0)
			count++;
		return count;
	}

	public int getCurrentPage() {
		return getStartIndex() / pageSize + 1;
	}
	
	public int getLastIndex() {
		if(totalCount > 0){
			return indexes[indexes.length-1];
		}else{
			return 0;
		}
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public int getPreviousIndex() {
		int previousIndex = getStartIndex() - pageSize;
		if (previousIndex < 0)
			return 0;
		else
			return previousIndex;
	}
	
	public int getNextIndex() {
		int nextIndex = getStartIndex() + pageSize;
		if (nextIndex >= totalCount)
			return getStartIndex();
		else
			return nextIndex;
	}
}
