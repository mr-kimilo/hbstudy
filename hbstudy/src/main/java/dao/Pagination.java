package dao;

import java.io.Serializable;
import java.util.List;

public class Pagination<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * previous page
	 */
	private long preIndex;
	
	/**
	 * current page
	 */
	private long curIndex;
	
	/**
	 * next page
	 */
	private long nextIndex;
	
	/**
	 * page size
	 */
	private long pageSize;
	
	/**
	 * page row count
	 */
	private long rowsCount;
	
	/**
	 * total page
	 */
	private long pagesCount;
	
	/**
	 * object items list
	 */
	private List<T> items;
	
	
	public Pagination() {
        updateInfo(0, 0, 0);
    }
	
	public Pagination(long pageIndex, long pageSize) {
        updateInfo(pageIndex, pageSize, 0);
    }
	
	public Pagination(long pageIndex, long pageSize, long rowsCount) {
        updateInfo(pageIndex, pageSize, rowsCount);
    }

	public long getPreIndex() {
		return preIndex;
	}

	public void setPreIndex(long preIndex) {
		this.preIndex = preIndex;
	}

	public long getCurIndex() {
		return curIndex;
	}

	public void setCurIndex(long curIndex) {
		this.curIndex = curIndex;
	}

	public long getNextIndex() {
		return nextIndex;
	}

	public void setNextIndex(long nextIndex) {
		this.nextIndex = nextIndex;
	}

	public long getPageSize() {
		return pageSize;
	}

	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
	}

	public long getRowsCount() {
		return rowsCount;
	}

	public void setRowsCount(long rowsCount) {
		 updateInfo(curIndex, pageSize, rowsCount);
	}
	
	 public long getPagesCount() {
		return pagesCount;
	}

	public void setPagesCount(long pagesCount) {
		this.pagesCount = pagesCount;
	}

	public List<T> getItems() {
		return items;
	}

	public void setItems(List<T> items) {
		this.items = items;
	}

	private void updateInfo(long pageIndex, long pageSize, long rowsCount) {

	        if (pageSize > 0) {

	            this.curIndex = pageIndex;
	            this.rowsCount = rowsCount;
	            this.pageSize = pageSize;

	            // 确定页数
	            pagesCount = (rowsCount + pageSize - 1) / pageSize;
	            // 确定当前页码
	            if (curIndex <= 0)
	                curIndex = 1;
	            if (curIndex > pagesCount)
	                curIndex = pagesCount;
	            // 确定下一页码
	            nextIndex = curIndex + 1;
	            if (nextIndex > pagesCount)
	                nextIndex = pagesCount;
	            // 确定上一页码
	            preIndex = curIndex - 1;
	            if (preIndex <= 0)
	                preIndex = 1;
	        } else {
	            this.preIndex = 1;
	            this.curIndex = 1;
	            this.nextIndex = 1;
	            this.pageSize = 0;
	            this.pagesCount = 1;
	        }
	    }

}
