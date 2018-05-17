package sq.util;


import org.springframework.data.domain.AbstractPageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.lang.Nullable;

/**
 * Basic Java Bean implementation of {@code Pageable}.
 *
 * @author Oliver Gierke
 * @author Thomas Darimont
 */
public class PageRequestLimit extends AbstractPageRequest {

    private static final long serialVersionUID = -4541509938956089562L;

    private final Sort sort;

    /**
     * Creates a new {@link PageRequestLimit}. Pages are zero indexed, thus providing 0 for {@code page} will return the first
     * page.
     *
     * @param page zero-based page index.
     * @param size the size of the page to be returned.
     * @deprecated use {@link #of(int, int)} instead.
     */
    @Deprecated
    public PageRequestLimit(int page, int size) {
        this(page, size, Sort.unsorted());
    }

    /**
     * Creates a new {@link PageRequestLimit} with sort parameters applied.
     *
     * @param page zero-based page index.
     * @param size the size of the page to be returned.
     * @param direction the direction of the {@link Sort} to be specified, can be {@literal null}.
     * @param properties the properties to sort by, must not be {@literal null} or empty.
     * @deprecated use {@link #of(int, int, Direction, String...)} instead.
     */
    @Deprecated
    public PageRequestLimit(int page, int size, Direction direction, String... properties) {
        this(page, size, Sort.by(direction, properties));
    }

    /**
     * Creates a new {@link PageRequestLimit} with sort parameters applied.
     *
     * @param page zero-based page index.
     * @param size the size of the page to be returned.
     * @param sort can be {@literal null}.
     * @deprecated since 2.0, use {@link #of(int, int, Sort)} instead.
     */
    @Deprecated
    public PageRequestLimit(int page, int size, Sort sort) {

        super(page, size);

        this.sort = sort;
    }

    /**
     * Creates a new unsorted {@link PageRequestLimit}.
     *
     * @param page zero-based page index.
     * @param size the size of the page to be returned.
     * @since 2.0
     */
    public static PageRequestLimit of(int page, int size) {
        return of(page, size, Sort.unsorted());
    }

    /**
     * Creates a new {@link PageRequestLimit} with sort parameters applied.
     *
     * @param page zero-based page index.
     * @param size the size of the page to be returned.
     * @param sort must not be {@literal null}.
     * @since 2.0
     */
    public static PageRequestLimit of(int page, int size, Sort sort) {
        return new PageRequestLimit(page, size, sort);
    }

    /**
     * Creates a new {@link PageRequestLimit} with sort direction and properties applied.
     *
     * @param page zero-based page index.
     * @param size the size of the page to be returned.
     * @param direction must not be {@literal null}.
     * @param properties must not be {@literal null}.
     * @since 2.0
     */
    public static PageRequestLimit of(int page, int size, Direction direction, String... properties) {
        return of(page, size, Sort.by(direction, properties));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.domain.Pageable#getSort()
     */
    public Sort getSort() {
        return sort;
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.domain.Pageable#next()
     */
    public Pageable next() {
        return new PageRequestLimit(getPageNumber() + 1, getPageSize(), getSort());
    }
    private int pageStart;

    public int getPageStart() {
        return pageStart;
    }

    public void setPageStart(int pageStart) {
        this.pageStart = pageStart;
    }

    @Override
    public long getOffset(){
      return pageStart+getPageNumber()*getPageSize();
    }
    /*
     * (non-Javadoc)
     * @see org.springframework.data.domain.AbstractPageRequest#previous()
     */
    public PageRequestLimit previous() {
        return getPageNumber() == 0 ? this : new PageRequestLimit(getPageNumber() - 1, getPageSize(), getSort());
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.domain.Pageable#first()
     */
    public Pageable first() {
        return new PageRequestLimit(0, getPageSize(), getSort());
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(@Nullable Object obj) {

        if (this == obj) {
            return true;
        }

        if (!(obj instanceof PageRequestLimit)) {
            return false;
        }

        PageRequestLimit that = (PageRequestLimit) obj;

        return super.equals(that) && this.sort.equals(that.sort);
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return 31 * super.hashCode() + sort.hashCode();
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format("Page request [number: %d, size %d, sort: %s]", getPageNumber(), getPageSize(), sort);
    }
}