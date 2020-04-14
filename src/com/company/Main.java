package com.company;

import com.mysql.cj.protocol.Resultset;

import java.sql.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        Scanner numberScanner;

        boolean isChatting = true;
        boolean input = true;
        int userId = 0;

        System.out.println("Gib deine User_id ein:");
        while (input) {
            try {
                numberScanner = new Scanner(System.in);
                userId = numberScanner.nextInt();
            } catch (InputMismatchException ex) {
                System.out.println("Die User_id muss eine Zahl sein");
                continue;
            }
            input = false;
        }

        System.out.println("Gib deinen Namen ein:");
        String name = scan.nextLine();

        Timer timer = new Timer();
        MyTimerTask task = new MyTimerTask(userId);
        timer.schedule(task, 0, 500);

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


