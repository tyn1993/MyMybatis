package com.tyn.test;

import com.tyn.pojo.User;

import java.lang.reflect.Field;

public class Test {
    public static void main(String[] args) throws Exception{
        User user = new User();
        user.setUsername("张三");
        Field declaredField = user.getClass().getDeclaredField("username");
        declaredField.setAccessible(true);
        Object o = declaredField.get(user);
        System.out.println(o);
    }
}
