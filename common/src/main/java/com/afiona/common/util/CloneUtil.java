//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.afiona.common.util;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.ConvertException;
import com.github.pagehelper.Page;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CloneUtil {
    private CloneUtil() {
    }

    public static <T> T clone(Object source, Class<T> clazz) {
        try {
            if (source == null) {
                return null;
            } else {
                T bean = clazz.getDeclaredConstructor().newInstance();
                BeanUtil.copyProperties(source, bean);
                return bean;
            }
        } catch (Exception var3) {
            throw new ConvertException(var3);
        }
    }

    public static <T> List<T> cloneList(List source, Class<T> clazz) {
        List<T> resultList = new ArrayList();
        if (!CollUtil.isEmpty(source)) {
            if (source instanceof Page) {
                Page<T> targetPage = new Page();
                BeanUtil.copyProperties(source, targetPage);
                Iterator var7 = source.iterator();

                while(var7.hasNext()) {
                    Object ele = var7.next();
                    targetPage.add(clone(ele, clazz));
                }

                return targetPage;
            }

            Iterator var3 = source.iterator();

            while(var3.hasNext()) {
                Object ele = var3.next();
                resultList.add(clone(ele, clazz));
            }
        }

        return resultList;
    }
}
