package com.epam.kuzovatov.util;

import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;

public class ParserConfigurator {
    private static final Logger log = Logger.getLogger(ParserConfigurator.class);
    private List<String> fieldList = new ArrayList<>();
    private List<Method> methodList = new ArrayList<>();
    private Map<String, Method> flowerFieldsToMethodMap;
    private Map<Class,Function> functionAdapterMap;

    private Map<String, Map<Method,Function>> methodFunctionAdapterMap;

    Class resultClass=null;

    public ParserConfigurator() {
    }

    public Map<Class, Function> getFunctionAdapterMap(){
        functionAdapterMap=new HashMap<>();
        Function<String, Date> dateFunctionAdapter = (s) -> {
            Date parsedDate = new Date();
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            try {
                parsedDate = format.parse(s);
            } catch (ParseException e) {
                log.error("Error occurred at date parse: " + e);
            }
            return parsedDate;
        };
        functionAdapterMap.put(Date.class, dateFunctionAdapter);
        Function<String, String> stringFunctionAdapter = String::new;
        functionAdapterMap.put(String.class, stringFunctionAdapter);
        Function<String, Double> doubleFunctionAdapter = Double::parseDouble;
        functionAdapterMap.put(Double.class, doubleFunctionAdapter);
        Function<String, UUID> uuidFunctionAdapter = UUID::fromString;
        functionAdapterMap.put(UUID.class, uuidFunctionAdapter);
        return functionAdapterMap;
    }

    public List<String> getFields(Class clazz) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field f:fields)fieldList.add(f.getName());
        resultClass = clazz.getSuperclass();
        if (resultClass!= Object.class) {
            getFields(resultClass);
        }
        return fieldList;
    }

    public List<Method> getMethods(Class clazz){
        Method[] methods = clazz.getDeclaredMethods();
        for (Method m: methods)methodList.add(m);
        return methodList;
    }

    public Map<String, Method> getFieldToMethodMap(List<String> fieldList, List<Method> methodList){
        flowerFieldsToMethodMap = new HashMap<>();
        for (Method m: methodList){
            for (String s:fieldList){
                if (m.getName().equals(("set" + s.substring(0, 1).toUpperCase() + s.substring(1)))) {
                    flowerFieldsToMethodMap.put(s, m);
                }
            }
        }
        return flowerFieldsToMethodMap;
    }

    public Map<String, Map<Method,Function>> getMethodFunctionAdapterMap(List<String> fieldList, List<Method> methodList){
        methodFunctionAdapterMap = new HashMap<>();
        Map<Class,Function> subMap = getFunctionAdapterMap();
        for (Method m: methodList){
            for (String s:fieldList){
                if (m.getName().equals(("set" + s.substring(0, 1).toUpperCase() + s.substring(1)))) {
                    Map<Method,Function> subMethodMap = new HashMap<>();
                    subMethodMap.put(m, subMap.get(m.getParameterTypes()[0]));
                    methodFunctionAdapterMap.put(s, subMethodMap);
                }
            }
        }
        return methodFunctionAdapterMap;
    }
}