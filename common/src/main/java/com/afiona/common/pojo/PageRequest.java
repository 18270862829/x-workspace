package com.afiona.common.pojo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.session.RowBounds;

/**
 * TODO
 *
 * @author shangfei
 * @date 2020/1/15
 */
public class PageRequest {

    public static final long DEFAULT_PAGE_SIZE = 10L;
    private Long page;
    private Long size;

    public PageRequest() {
        this.page = 1L;
        this.size = 10L;
    }

    public PageRequest(Long page) {
        this();
        page = page == null ? 1L : page;
        this.size = 10L;
        this.page = page;
    }

    public PageRequest(Long page, Long size) {
        page = page == null ? 1L : page;
        size = size == null ? 10L : size;
        this.page = page;
        this.size = size;
    }

    public Long getPage() {
        return this.page;
    }

    public void setPage(long page) {
        this.page = page;
    }

    public Long getSize() {
        return this.size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public RowBounds toRowBounds() {
        return new RowBounds(this.page > 0L ? (int) ((this.page - 1L) * this.size) : 0, this.size > 0L ? this.size.intValue() : 0);
    }

    public Page toPage() {
        return new Page(this.getPage(), this.getSize());
    }

    @Override
    public String toString() {
        return "PageRequest{page=" + this.page + ", size=" + this.size + '}';
    }
}
