package com.company;

import com.mysql.cj.protocol.Resultset;

import java.sql.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        Chat chat = new Chat();

        Scanner scan = new Scanner(System.in);
        Scanner numberScanner;

        boolean isChatting = true;
        boolean input = true;
        int userId = 0;
        String choice;
        String name = "";
        User user;
        boolean isRegistrationInProgress = true;

        while(isRegistrationInProgress) {
            System.out.println("Melde dich neu an (1) oder \nlogge dich ein (2)");
            choice = scan.nextLine();
            if (choice.equalsIgnoreCase("1")) {
                System.out.println("Gib deinen Namen ein:");
                name = scan.nextLine();
                user = new User(name);
                chat.addUser(user);
                userId = chat.getUserId();
                System.out.println("Deine User_id:" + userId);
                isRegistrationInProgress = false;
            } else if (choice.equalsIgnoreCase("2")) {
                while (input) {
                    System.out.println("Deine User_id:");
                    try {
                        numberScanner = new Scanner(System.in);
                        userId = numberScanner.nextInt();
                    } catch (InputMismatchException ex) {
                        System.out.println("Die User_id muss eine Zahl sein");
                        continue;
                    }
                    input = false;
                }
                name = chat.getUsername(userId);

                if (name == "") {
                    System.out.println("User wurde nicht gefunden.");
                    continue;
                } else {
                    System.out.println("Hallo " + name);
                    isRegistrationInProgress = false;
                }
            } else {
                System.out.println("Versuch es nochmal.");
            }
        }

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
                String sql = "INSERT INTO `chat`(`user_id`, `note`)" +
                        " VALUES (" + userId + ",'" + messageFromUser + "')";
                statement.executeUpdate(sql);
                connection.close();
            } catch (SQLException ex) {
                ex.fillInStackTrace();
            }
        }
    }
}


