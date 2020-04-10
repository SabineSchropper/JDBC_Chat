package com.company;

import com.mysql.cj.protocol.Resultset;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Main {

    public static void main(String[] args) {

        Timer timer = new Timer();

        Chat chat = new Chat();
        Scanner scan = new Scanner(System.in);
        Scanner numberScanner = new Scanner(System.in);

        System.out.println("Gib deine User_id ein:");
        int userId = numberScanner.nextInt();

        System.out.println("Gib deinen Namen ein:");
        String name = scan.nextLine();

        boolean isUserInArrayList = chat.checkUser(userId);
        if (!isUserInArrayList) {
            User user = new User(userId, name);
            chat.addUser(user);
        }

        System.out.println("Gib deine Nachricht ein:");

        boolean isChatting = true;
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


            timer.schedule(new TimerTask() {
                public void run() {
                    try {
                        String url = "jdbc:mysql://localhost:3306/codingcampus?user=root";
                        Connection connection = DriverManager.getConnection(url);
                        Statement statement = connection.createStatement();
                        String sql = "SELECT id FROM `chat` WHERE user_id = " + userId + " ORDER BY id DESC LIMIT 1";
                        ResultSet rs1 = statement.executeQuery(sql);
                        rs1.next();
                        int number = rs1.getInt(1);
                        int idOfLastMessage = 0;
                        //Select and print the notes from the other users with an id greater than the id of my entry
                        sql = "SELECT * FROM chat WHERE user_id != " + userId + " AND id > " + number + " AND id > " + idOfLastMessage + "";
                        ResultSet rs = statement.executeQuery(sql);

                        while (rs.next()) {
                            String chatString = rs.getString("note");
                            idOfLastMessage = rs.getInt("id");
                            System.out.println(chatString);
                        }
                    } catch (SQLException ex) {
                        ex.fillInStackTrace();
                    }
                }
            }, 3000, 3000);
        }
    }
}

