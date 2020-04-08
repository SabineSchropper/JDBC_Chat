package com.company;

import com.mysql.cj.protocol.Resultset;

import java.sql.*;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Main {

    public static void main(String[] args) {

            Scanner scan = new Scanner(System.in);
            System.out.println("Gib deine Nachricht ein:");

        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                String messageFromUser = scan.nextLine();
                Connection connection = null;
                String url = "jdbc:mysql://localhost:3306/codingcampus?user=root";

                try {
                    connection = DriverManager.getConnection(url);
                    Statement statement = connection.createStatement();
                    String sql = "INSERT INTO chat (note) VALUES ('" + messageFromUser + "')";
                    statement.executeUpdate(sql);
                } catch (SQLException ex) {
                    ex.fillInStackTrace();
                }

                try {
                    connection = DriverManager.getConnection(url);
                    Statement statement = connection.createStatement();
                    String sql = "SELECT * FROM chat ORDER BY (id) DESC LIMIT 1";
                    ResultSet rs = statement.executeQuery(sql);
                    while (rs.next()) {
                        String chat = rs.getString("note");
                        System.out.println(chat);
                    }
                } catch (SQLException ex) {
                    ex.fillInStackTrace();
                }
            }
        },2000,5000);

    }

}
