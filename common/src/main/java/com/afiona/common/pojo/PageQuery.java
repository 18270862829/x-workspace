package com.afiona.common.pojo;

import java.io.Serializable;

/**
 * @author ZhangShuaiQiang
 * @date 2019/12/26 下午4:47
 */
public class PageQuery implements Serializable {

    /**
     * 页码,传-1代表不分页查询
     */
    private Integer page = 1;

    /**
     * 每页数量
     */
    private Integer size = 10;

    public void setPage(Integer page) {
        this.page = page;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getPage() {
        return page == null ? 1 : Math.max(1, page);
    }

    public Integer getSize() {
        return size == null ? 10 : size;
    }
}
