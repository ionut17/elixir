package app.model;

import app.model.dto.UserDto;
import org.springframework.data.domain.Page;

/**
 * Created by Ionut on 11-Feb-17.
 */
public class Pager<T> {

    private int currentPage;

    private int currentPageSize;

    private int defaultPageSize;

    private long totalElements;

    private int totalPages;

    private boolean hasContent;

    private boolean hasNextPage;

    private boolean hasPreviousPage;

    private boolean isFirstPage;

    private boolean isLastPage;

    public Pager(Page<T> paged) {
        this.currentPage = paged.getNumber();
        this.currentPageSize = paged.getNumberOfElements();
        this.defaultPageSize = paged.getSize();
        this.totalElements = paged.getTotalElements();
        this.totalPages = paged.getTotalPages();
        this.hasContent = paged.hasContent();
        this.hasNextPage = paged.hasNext();
        this.hasPreviousPage = paged.hasPrevious();
        this.isFirstPage = paged.isFirst();
        this.isLastPage = paged.isLast();
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getCurrentPageSize() {
        return currentPageSize;
    }

    public int getDefaultPageSize() {
        return defaultPageSize;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public boolean isHasContent() {
        return hasContent;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public boolean isHasPreviousPage() {
        return hasPreviousPage;
    }

    public boolean isFirstPage() {
        return isFirstPage;
    }

    public boolean isLastPage() {
        return isLastPage;
    }

}
