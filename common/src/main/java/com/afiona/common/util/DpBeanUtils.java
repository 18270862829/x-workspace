package com.afiona.common.util;

/**
 * Created by Shang fei on 2020/1/7 17:20
 */

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.ParserConfig;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;

public class DpBeanUtils extends BeanUtils {
    public static final String setMethodModify = "set";
    private static ConcurrentHashMap<String, List<Method>> methodMap = new ConcurrentHashMap();

    public DpBeanUtils() {
    }

    public static <T> List<T> copyListDTOProperties(List<? extends Object> dataList, Class<T> classzz) {
        ArrayList result = null;

        try {
            if (CollectionUtils.isNotEmpty(dataList) && classzz != null) {
                result = new ArrayList();
                T t = null;
                Iterator var4 = dataList.iterator();

                while (var4.hasNext()) {
                    Object obj = var4.next();
                    if (obj != null) {
                        t = classzz.newInstance();
                        copyDTOProperties(obj, t);
                        result.add(t);
                    }
                }
            }

            return result;
        } catch (Exception var6) {
            throw new RuntimeException(var6);
        }
    }

    public static <T> List<T> copyListProperties(List<? extends Object> dataList, Class<T> classzz) {
        ArrayList result = null;

        try {
            if (CollectionUtils.isNotEmpty(dataList) && classzz != null) {
                result = new ArrayList();
                T t = null;
                Iterator var4 = dataList.iterator();

                while (var4.hasNext()) {
                    Object obj = var4.next();
                    if (obj != null) {
                        t = classzz.newInstance();
                        copyProperties(obj, t);
                        result.add(t);
                    }
                }
            }

            return result;
        } catch (Exception var6) {
            throw new RuntimeException(var6);
        }
    }

    public static void copyDTOProperties(Object source, Object target) {
        if (source instanceof Collection) {
            throw new RuntimeException("不支持集合复制对象拷贝.需要拷贝集合复制对象请参考com.deepexi.jzw.vehicle.tools.DpBeanUtils.copyListDTOProperties");
        } else {
            String json = JSONObject.toJSONString(source);
            ParserConfig config = new ParserConfig();
            config.setAsmEnable(false);
            BeanUtils.copyProperties(JSONObject.parseObject(json, target.getClass(), config, new Feature[0]), target);
        }
    }

    public static void copyProperties(Object source, Object target) {
        if (source instanceof Collection) {
            throw new RuntimeException("不支持集合简单对象拷贝.需要拷贝集合简单对象请参考com.deepexi.jzw.vehicle.tools.DpBeanUtils.copyListProperties");
        } else {
            BeanUtils.copyProperties(source, target);
        }
    }

    public static Map<String, Object> convertBeanToMap(Object bean) {
        try {
            Class<? extends Object> type = bean.getClass();
            Map<String, Object> returnMap = new HashMap();
            BeanInfo beanInfo = Introspector.getBeanInfo(type);
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            PropertyDescriptor descriptor = null;
            String propertyName = null;
            Method readMethod = null;
            Object result = null;

            for (int i = 0; i < propertyDescriptors.length; ++i) {
                descriptor = propertyDescriptors[i];
                propertyName = descriptor.getName();
                if (!propertyName.equals("class")) {
                    readMethod = descriptor.getReadMethod();
                    if (null != readMethod) {
                        result = readMethod.invoke(bean);
                        returnMap.put(propertyName, result);
                    }
                }
            }

            return returnMap;
        } catch (Exception var10) {
            throw new RuntimeException(var10);
        }
    }

    public static void filterValIsBlank(List<Map<String, Object>> params) {
        if (CollectionUtils.isNotEmpty(params)) {
            Iterator<Map<String, Object>> it = params.iterator();
            Map map = null;

            while (it.hasNext()) {
                map = (Map) it.next();
                if (map == null) {
                    it.remove();
                } else {
                    filterValIsBlank(map);
                }
            }
        }

    }

    public static void filterValIsBlank(Map<String, Object> param) {
        if (param != null && !param.isEmpty()) {
            Iterator<Entry<String, Object>> it = param.entrySet().iterator();
            Entry entry = null;

            while (true) {
                do {
                    do {
                        if (!it.hasNext()) {
                            return;
                        }

                        entry = (Entry) it.next();
                    } while (entry == null);
                } while (entry.getValue() != null && !"".equals(entry.getValue()));

                it.remove();
            }
        }
    }

    public static List<Method> getAllSetMethods(Class classzz) {
        List<Method> setMethods = new ArrayList();
        if (classzz != null) {
            String key = classzz.getName().trim();
            if (methodMap.containsKey(key)) {
                return (List) methodMap.get(key);
            }

            String methodName = "";
            Method[] methods = classzz.getMethods();
            Method[] var5 = methods;
            int var6 = methods.length;

            for (int var7 = 0; var7 < var6; ++var7) {
                Method method = var5[var7];
                methodName = method.getName();
                if (methodName.startsWith("set")) {
                    setMethods.add(method);
                }
            }

            methodMap.put(key, setMethods);
        }

        return setMethods;
    }

    public static <T> T convertMapToBean(Map<String, Object> map, Class<T> classzz) {
        try {
            if (classzz != null && map != null && !map.isEmpty()) {
                String propertyName = "";
                String methodName = "";
                Object value = null;
                T t = classzz.newInstance();
                List<Method> methods = getAllSetMethods(classzz);
                Iterator var7 = methods.iterator();

                while (var7.hasNext()) {
                    Method method = (Method) var7.next();
                    if (method != null) {
                        methodName = method.getName();
                        propertyName = methodName.substring(3, 4).toLowerCase(Locale.getDefault()) + methodName.substring(4);

                        try {
                            if (map.containsKey(propertyName)) {
                                value = map.get(propertyName);
                                method.invoke(t, value);
                            }
                        } catch (Exception var10) {
                            throw var10;
                        }
                    }
                }

                return t;
            } else {
                throw new NullPointerException("class 为空或map为空.");
            }
        } catch (Exception var11) {
            throw new RuntimeException(var11);
        }
    }

    public static <T> List<T> convertToBeans(List<Map<String, Object>> dataList, Class<T> classzz) {
        try {
            List<T> rst = new ArrayList();
            if (CollectionUtils.isNotEmpty(dataList)) {
                T rst_obj = null;
                Iterator var4 = dataList.iterator();

                while (var4.hasNext()) {
                    Map<String, Object> map = (Map) var4.next();
                    if (map != null && !map.isEmpty()) {
                        rst_obj = convertMapToBean(map, classzz);
                        if (rst_obj != null) {
                            rst.add(rst_obj);
                            rst_obj = null;
                        }
                    }
                }
            }

            return rst;
        } catch (Exception var6) {
            throw new RuntimeException(var6);
        }
    }

    public static <T> List<Map<String, Object>> convertBeansToMaps(List<? extends Object> dataList) {
        try {
            List<Map<String, Object>> rst = new ArrayList();
            if (CollectionUtils.isNotEmpty(dataList)) {
                Map<String, Object> rst_obj = null;
                Iterator var3 = dataList.iterator();

                while (var3.hasNext()) {
                    Object obj = var3.next();
                    if (obj != null) {
                        rst_obj = convertBeanToMap(obj);
                        if (rst_obj != null) {
                            rst.add(rst_obj);
                            rst_obj = null;
                        }
                    }
                }
            }

            return rst;
        } catch (Exception var5) {
            throw new RuntimeException(var5);
        }
    }

    public static void bean2Map(Object sourceObj, Map<String, Object> targetMap) {
        Assert.notNull(sourceObj, "Source Object must not be null");
        Assert.notNull(targetMap, "Target Map must not be null");
        Class<?> sourceObjClazz = sourceObj.getClass();
        PropertyDescriptor[] sourcePds = getPropertyDescriptors(sourceObjClazz);
        PropertyDescriptor[] var4 = sourcePds;
        int var5 = sourcePds.length;

        for (int var6 = 0; var6 < var5; ++var6) {
            PropertyDescriptor sourcePd = var4[var6];
            Method readMethod = sourcePd.getReadMethod();

            try {
                if (Modifier.isPublic(readMethod.getDeclaringClass().getModifiers()) && !"class".equals(sourcePd.getName())) {
                    Object value = readMethod.invoke(sourceObj);
                    targetMap.put(sourcePd.getName(), value);
                }
            } catch (Exception var10) {
                throw new RuntimeException("Could not copy property '" + sourcePd.getName() + "' from sourceObj to targetMap", var10);
            }
        }
    }
}