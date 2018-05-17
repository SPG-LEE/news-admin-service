package sq.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Paging implements Serializable {

	private static final long serialVersionUID = -7928210350544816044L;

	public static final int PAGE_SIZE = 15;

	private int pageSize = PAGE_SIZE;

	private int pageNumber = 1;

	private int totalElements = 0;

	public Paging(int pageNumber, int pageSize, int totalElements) {
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.totalElements = totalElements;
		recomputePageNumber();
	}

	public Paging(int pageNumber, int totalElements) {
		this.pageNumber = pageNumber;
		this.totalElements = totalElements;
		recomputePageNumber();
	}

	public Paging(int totalElements) {
		this.totalElements = totalElements;
		recomputePageNumber();
	}

	public boolean getNeedPage() {
		return totalElements > pageSize;
	}

	public Paging() {
	}

	protected void recomputePageNumber() {
		int lastPageNumber = getLastPageNumber();
		if (Integer.MAX_VALUE == pageNumber || pageNumber > lastPageNumber) { // last
																				// page
			if (lastPageNumber < 0) {
				pageNumber = 1;
			} else {
				pageNumber = lastPageNumber == 0 ? 1 : lastPageNumber;
			}
		}
	}

	public int getStart() {
		return getThisPageFirstElementNumber() - 1;
	}

	public boolean isFirstPage() {
		return getThisPageNumber() == 1;
	}

	public boolean isLastPage() {
		return getThisPageNumber() >= getLastPageNumber();
	}

	public boolean hasNextPage() {
		return getLastPageNumber() > getThisPageNumber();
	}

	public boolean getHasNextPage() {
		return getLastPageNumber() > getThisPageNumber();
	}

	public boolean getNoNextPage() {
		return getLastPageNumber() <= getThisPageNumber();
	}

	public boolean hasPreviousPage() {
		return getThisPageNumber() > 1;
	}

	public boolean getNoPreviousPage() {
		return getThisPageNumber() <= 1;
	}

	public boolean getHasPreviousPage() {
		return getThisPageNumber() > 1;
	}

	public int getLastPageNumber() {
		return totalElements % pageSize == 0 ? totalElements / pageSize : totalElements / pageSize + 1;
	}

	public int getTotalPageNumber() {
		int lastPageNumber = getLastPageNumber();
		return lastPageNumber == 0 ? 1 : lastPageNumber;
	}

	public int getTotalNumberOfElements() {
		return totalElements;
	}

	public int getThisPageFirstElementNumber() {
		int number = (getThisPageNumber() - 1) * getPageSize() + 1;
		return number > 0 ? number : 0;
	}

	public int getThisPageLastElementNumber() {
		int fullPage = getThisPageFirstElementNumber() + getPageSize() - 1;
		return getTotalNumberOfElements() < fullPage ? getTotalNumberOfElements() : fullPage;
	}

	public int getNextPageNumber() {
		return getThisPageNumber() + 1;
	}

	public int getPreviousPageNumber() {
		return getThisPageNumber() - 1;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getThisPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
		recomputePageNumber();
	}

	public int getPageNumber() {
		return this.pageNumber;
	}

	public void setTotalElements(int totalElements) {
		this.totalElements = totalElements;
		recomputePageNumber();
	}

	public int getTotalElements() {
		return totalElements;
	}

	public void next() {
		setPageNumber(getNextPageNumber());
	}

	public void previous() {
		setPageNumber(getPreviousPageNumber());
	}

	public boolean getShowFirstNumber() {
		return pageNumber > 10;
	}

	public int getFirstNumber() {
		return pageNumber / 10 * 10;
	}

	public boolean getShowLastNumber() {
		if (pageNumber > 0 && pageNumber % 10 == 0) {
			return getLastPageNumber() > pageNumber;
		}
		return getLastPageNumber() > getLastNumber();
	}

	public int getLastNumber() {
		if (pageNumber > 0 && pageNumber % 10 == 0) {
			return pageNumber + 1;
		}
		return (pageNumber / 10 + 1) * 10 + 1;
	}

	public int[] getNumberList() {
		int number = pageNumber;
		int[] returnArray = null;
		if (number > 0 && number % 10 == 0) {
			returnArray = new int[10];
			for (int i = 10; i > 0; i--) {
				returnArray[i - 1] = number--;
			}
			return returnArray;
		}
		int min = number / 10 * 10;
		int max = (number / 10 + 1) * 10;
		int lastPageNumber = getLastPageNumber();

		if (lastPageNumber <= max) {
			int right = lastPageNumber - min;
			returnArray = new int[right];
			for (int i = 0; i < right; i++) {
				returnArray[i] = ++min;
			}
		} else {
			returnArray = new int[10];
			for (int i = 0; i < 10; i++) {
				returnArray[i] = ++min;
			}
		}
		// ArrayUtils.reverse(returnArray);
		return returnArray;
	}

	public List<Integer> getBeginList() {
		int lastPageNumber = getLastPageNumber();
		List<Integer> beginList = new ArrayList<Integer>();
		if (lastPageNumber < 10) {
			for (int i = 1; i <= lastPageNumber; i++) {
				beginList.add(i);
			}
			return beginList;
		}
		if (pageNumber < 5) {
			for (int i = 1; i <= pageNumber; i++) {
				beginList.add(i);
			}
			beginList.add(pageNumber + 1);
			beginList.add(pageNumber + 2);
			return beginList;
		}
		if (pageNumber > lastPageNumber - 4) {
			beginList.add(1);
			beginList.add(2);
			return beginList;
		}
		if (pageNumber >= 5 && pageNumber <= (lastPageNumber - 4)) {
			beginList.add(1);
			beginList.add(2);
			return beginList;
		}
		return beginList;
	}

	public List<Integer> getMiddleList() {
		List<Integer> middleList = new ArrayList<Integer>();

		int lastPageNumber = getLastPageNumber();
		if (lastPageNumber < 10) {
			return middleList;
		}
		if (pageNumber >= 5 && pageNumber <= (lastPageNumber - 4)) {
			middleList.add(pageNumber - 2);
			middleList.add(pageNumber - 1);
			middleList.add(pageNumber);
			middleList.add(pageNumber + 1);
			middleList.add(pageNumber + 2);
		}
		return middleList;

	}

	public List<Integer> getEndList() {
		int lastPageNumber = getLastPageNumber();

		List<Integer> endList = new ArrayList<Integer>();
		if (lastPageNumber < 10) {
			return endList;
		}
		if (pageNumber > lastPageNumber - 4) {
			endList.add(pageNumber - 2);
			endList.add(pageNumber - 1);
			for (int i = pageNumber; i <= lastPageNumber; i++) {
				endList.add(i);
			}
		} else {
			endList.add(lastPageNumber - 1);
			endList.add(lastPageNumber);
		}
		return endList;

	}

	public boolean getShowFirstDot() {
		if (getLastPageNumber() < 10) {
			return false;
		}
		return true;
	}

	public boolean getShowLastDot() {
		if (pageNumber >= 5 && pageNumber <= (getLastPageNumber() - 4)) {
			return true;
		}
		return false;
	}

	public void reset() {
		pageNumber = 1;
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + pageNumber;
		result = PRIME * result + pageSize;
		result = PRIME * result + totalElements;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Paging other = (Paging) obj;
		if (pageNumber != other.pageNumber) {
			return false;
		}
		if (pageSize != other.pageSize) {
			return false;
		}
		if (totalElements != other.totalElements) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Paging [pageSize=" + pageSize + ", pageNumber=" + pageNumber + ", totalElements=" + totalElements + "]";
	}

}
