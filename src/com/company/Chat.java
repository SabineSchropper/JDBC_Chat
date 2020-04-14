package com.company;

import java.sql.*;
import java.util.ArrayList;

public class Chat {

    public void addUser(User user){
        try {
            String url = "jdbc:mysql://localhost:3306/codingcampus?user=root";
            Connection connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            String sql = "INSERT INTO `users`(`user_name`)" +
                    " VALUES ('"+ user.name + "')";
            statement.executeUpdate(sql);
            connection.close();
        } catch (SQLException ex) {
            ex.fillInStackTrace();
        }
    }

    public String getUsername(int id){
        String name = "";
        try {
            String url = "jdbc:mysql://localhost:3306/codingcampus?user=root";
            Connection connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM users WHERE user_id = "+id+"";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()){
                name = rs.getString("user_name");
            }
            connection.close();
        } catch (SQLException ex) {
            ex.fillInStackTrace();
        }
        return name;
    }
    public int getUserId(){
        int id = 0;
        try {
            String url = "jdbc:mysql://localhost:3306/codingcampus?user=root";
            Connection connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            String sql = "SELECT max(user_id) FROM users";
            ResultSet rs = statement.executeQuery(sql);
            rs.next();
            id = rs.getInt(1);
            connection.close();
        } catch (SQLException ex) {
            ex.fillInStackTrace();
        }
        return id;
    }
}

