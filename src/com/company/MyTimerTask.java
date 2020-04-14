package com.company;

import java.sql.*;
import java.util.TimerTask;

public class MyTimerTask extends TimerTask {
    int userId;
    int id;
    int idBefore;

    public MyTimerTask(int userId){
        this.userId = userId;
    }
    @Override
    public void run() {

        try {

            String url = "jdbc:mysql://localhost:3306/codingcampus?user=root";
            Connection connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            String sql = "SELECT max(id) FROM `chat`";
            ResultSet rs = statement.executeQuery(sql);
            rs.next();
            id = rs.getInt(1);
            if(idBefore != id) {
                String sql1 = "SELECT * FROM chat WHERE id = " + id+ " AND user_id != " + userId + "";
                ResultSet rs1 = statement.executeQuery(sql1);
                while (rs1.next()) {
                    String note = rs1.getString("note");
                    System.out.println(note);
                }
            }
        } catch (SQLException ex) {
            ex.fillInStackTrace();
        }
        idBefore = id;
    }
}