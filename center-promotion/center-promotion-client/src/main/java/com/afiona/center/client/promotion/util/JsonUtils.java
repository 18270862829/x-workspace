package com.afiona.center.client.promotion.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * json转换工具类
 *
 * @author LiJinXing
 * @date 2020/3/23
 */
public class JsonUtils {

    private static ObjectMapper objectMapper =new ObjectMapper();

    public static  String objToJson(Object obj){

        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw  new RuntimeException("json转换失败");
        }
    }

    public static <T>T  jsonTopojo(String json,Class<T> pojo){
        try {
            return objectMapper.readValue(json,pojo);
        } catch (IOException e) {
            e.printStackTrace();
            throw  new RuntimeException("json转换失败");
        }
    }

    public static <T>List<T> jsonToList(String json,Class<T> pojo){
        JavaType javaType = getJavaType(List.class, pojo);
        try {
            return objectMapper.readValue(json,javaType);
        } catch (IOException e) {
            e.printStackTrace();
            throw  new RuntimeException("json转换失败");
        }
    }

    public static <T>Set<T> jsonToSet(String json, Class<T> pojo){
        JavaType javaType = getJavaType(Set.class, pojo);
        try {
            return objectMapper.readValue(json,javaType);
        } catch (IOException e) {
            e.printStackTrace();
            throw  new RuntimeException("json转换失败");
        }
    }

    public static <T,E> Map<T,E> jsonToMap(String json, Class<T> pojo1,Class<E> pojo2){
        JavaType javaType = getJavaType(Map.class, pojo1,pojo2);
        try {
            return objectMapper.readValue(json,javaType);
        } catch (IOException e) {
            e.printStackTrace();
            throw  new RuntimeException("json转换失败");
        }
    }

    private static JavaType getJavaType(Class returnType,Class...pojo){
        return objectMapper.getTypeFactory().constructParametricType(returnType, pojo);
    }



}
