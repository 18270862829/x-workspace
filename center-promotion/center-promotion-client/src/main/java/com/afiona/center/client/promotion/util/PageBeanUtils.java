package com.afiona.center.client.promotion.util;

import com.afiona.common.util.DpBeanUtils;
import com.deepexi.util.pageHelper.PageBean;

import java.util.List;

/**
 * PageBean工具类
 *
 * @author LiJinXing
 * @date 2020/3/19
 */
public class PageBeanUtils {

    public static <T>PageBean<T> pageBeanCopy(List<T> list, PageBean pageBean){
        PageBean<T> page = new PageBean<>();
        DpBeanUtils.copyProperties(pageBean,page,"content");
        page.setContent(list);
        return page;
    }
}
