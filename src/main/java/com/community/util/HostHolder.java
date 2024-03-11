package com.community.util;

import com.community.entity.User;
import org.springframework.stereotype.Component;

/**
 * 保存用户信息，用于代替session对象
 */
@Component
public class HostHolder {

    //考虑到并发的问题，用多线程的方式，当前使用的是不同的线程，就将信息存入不同线程的map
    private ThreadLocal<User> users = new ThreadLocal<>();

    public void setUser(User user){
        users.set(user);
    }

    public User getUser(){
        return users.get();
    }

    public void clear(){
        users.remove();
    }
}
