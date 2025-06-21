package com.giotto.bean;

import java.util.ArrayList;

public class UserSerive {
    //定义静态集合，存储用户名和密码
    private static ArrayList<User> allUsers= new ArrayList<>();
    static {
        allUsers.add(new User("成绩管理员", "123456", "admin"));
    }
    public static void addUser(User user) {
        allUsers.add(user);
    }

    public static User getUserByLoginName(String loginName) {
        for (User user : allUsers) {
            if (user.getLoginName().equals(loginName)) {
                return user;
            }
        }
        return null;
    }

    public static boolean isExist(String loginName) {
        for (User user : allUsers) {
            if (user.getLoginName().equals(loginName)) {
                return true;
            }
        }
        return false;
    }

}
