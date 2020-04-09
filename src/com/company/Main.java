package com.company;

import com.mysql.cj.protocol.Resultset;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Main {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Scanner numberScanner = new Scanner(System.in);
        Timer timer = new Timer();
        String url = "jdbc:mysql://localhost:3306/codingcampus?user=root";

        System.out.println("Gib deine User_id ein:");
        int userId = numberScanner.nextInt();

        System.out.println("Gib deinen Namen ein:");
        String name = scan.nextLine();

        User user = new User(userId,name);


        
        System.out.println("Gib deine Nachricht ein:");
        System.out.println("Du kannst den Chat mit x beenden.");


        boolean isChatting = true;

        while (isChatting) {
            try {
                Connection connection = DriverManager.getConnection(url);
                Statement statement = connection.createStatement();
                String sql = "SELECT max(id) FROM chat";
                ResultSet rs1 = statement.executeQuery(sql);
                rs1.next();
                int number = rs1.getInt(1);
                //Select and print the second last note
                sql = "SELECT * FROM chat WHERE id = "+(number-1)+"";
                ResultSet rs = statement.executeQuery(sql);
                while (rs.next()) {
                    String chat = rs.getString("note");
                    System.out.println(chat);
                }
            } catch (SQLException ex) {
                ex.fillInStackTrace();
            }
            //timer.schedule(new TimerTask() {
            //public void run() {
            String messageFromUser = scan.nextLine();
            if (messageFromUser.equalsIgnoreCase("x")) {
                isChatting = false;
                continue;
            }
            try {
                url = "jdbc:mysql://localhost:3306/codingcampus?user=root";
                Connection connection = DriverManager.getConnection(url);
                Statement statement = connection.createStatement();
                String sql = "INSERT INTO chat (note) VALUES ('" + messageFromUser + "')";
                statement.executeUpdate(sql);
            } catch (SQLException ex) {
                ex.fillInStackTrace();
            }

            //}, 5000, 5000);

        }
    }
}
//}

