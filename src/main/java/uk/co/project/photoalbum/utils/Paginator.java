package uk.co.project.photoalbum.utils;

import java.util.ArrayList;
import java.util.List;

public abstract class Paginator {

	public static <T> List<T> getListPage(List<T> list, int pageNumber, int pageSize) {
		final int startIndex = pageSize * (pageNumber - 1);
		final int endIndex = startIndex + pageSize - 1;
		final int lastListIndex = list.size() - 1;
		if (startIndex > lastListIndex) {
			throw new IndexOutOfBoundsException();
		}
		List<T> paginatedList = new ArrayList<>();
		for (int i = startIndex; i <= endIndex && i <= lastListIndex; i++) {
			paginatedList.add(list.get(i));
		}
		return paginatedList;
	}
}