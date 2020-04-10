package com.company;

import com.mysql.cj.protocol.Resultset;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Main {

    public static void main(String[] args) {

        Chat chat = new Chat();
        Scanner scan = new Scanner(System.in);
        Scanner numberScanner = new Scanner(System.in);

        boolean isChatting = true;

        System.out.println("Gib deine User_id ein:");
        int userId = numberScanner.nextInt();

        System.out.println("Gib deinen Namen ein:");
        String name = scan.nextLine();

        Timer timer = new Timer();
        MyTimerTask task = new MyTimerTask(userId);
        timer.schedule(task, 0, 1500);

        boolean isUserInArrayList = chat.checkUser(userId);
        if (!isUserInArrayList) {
            User user = new User(userId, name);
            chat.addUser(user);
        }

        System.out.println("Gib deine Nachricht ein:");

        while (isChatting) {

            String messageFromUser = scan.nextLine();
            if (messageFromUser.equalsIgnoreCase("x")) {
                isChatting = false;
                continue;
            }
            try {
                String url = "jdbc:mysql://localhost:3306/codingcampus?user=root";
                Connection connection = DriverManager.getConnection(url);
                Statement statement = connection.createStatement();
                String sql = "INSERT INTO `chat`(`user_id`, `note`, `user_name`)" +
                        " VALUES (" + userId + ",'" + messageFromUser + "','" + name + "')";
                statement.executeUpdate(sql);
            } catch (SQLException ex) {
                ex.fillInStackTrace();
            }
        }
    }
}


