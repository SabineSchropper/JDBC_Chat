package com.company;

import java.util.ArrayList;

public class Chat {
    ArrayList<User> users = new ArrayList<User>();

    public void addUser(User user){
        users.add(user);
    }
    public boolean checkUser(int userId){
        boolean isInArrayList = false;
        for(User user: users){
            if (user.userId == userId){
                isInArrayList = true;
            }
        }
        return isInArrayList;
    }
}

