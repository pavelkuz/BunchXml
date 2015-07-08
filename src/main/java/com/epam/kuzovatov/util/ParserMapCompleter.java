package com.epam.kuzovatov.util;

import com.epam.kuzovatov.entity.Flower;
import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParserMapCompleter {
    private static final Logger log = Logger.getLogger(ParserMapCompleter.class);
    //private Properties properties;
    private List<String> fieldList = new ArrayList<>();
    private List<Method> methodList = new ArrayList<>();
    private Map<String, Method> flowerFieldsToMethodMap = new HashMap<>();
    Class resultClass=null;

//    public ParserPropertyManager(String propertyFileName){
//        properties = new Properties();
//        log.info("Load properties for parse.");
//        try {
//            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propertyFileName);
//            properties.load(inputStream);
//            log.info("Properties loaded!");
//        } catch (IOException e) {
//            log.error("Something wrong with loading properties: " + e);
//        }
//    }

    public ParserMapCompleter() {
    }

    public List<String> getFields(Class cc) {
        Field[] fields = cc.getDeclaredFields();
        for (Field f:fields)fieldList.add(f.getName());
        resultClass = cc.getSuperclass();
        if (resultClass!= Object.class) {
            getFields(resultClass);
        }
        return fieldList;
    }

    public List<Method> getMethods(){
        Flower flower = new Flower();
        Method[] methods = flower.getClass().getDeclaredMethods();
        for (Method m: methods)methodList.add(m);
        return methodList;
    }

    public Map<String, Method> getFieldToMethodMap(List<String> fieldList, List<Method> methodList){
        for (Method m: methodList){
            for (String s:fieldList){
                if (m.getName().equals(("set" + s.substring(0, 1).toUpperCase() + s.substring(1)))) {
                    flowerFieldsToMethodMap.put(s,m);
                }
            }
        }
        return flowerFieldsToMethodMap;
    }

//    public String getProperty(String propertyName){
//        return properties.getProperty(propertyName);
//    }
}