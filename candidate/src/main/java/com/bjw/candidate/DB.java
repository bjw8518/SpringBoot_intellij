//DB.java
package com.bjw.candidate;

import org.springframework.stereotype.Component;
import org.sqlite.SQLiteConfig;

import java.sql.*;
import java.util.ArrayList;


@Component
public class DB {
    Connection connection;

    private void open() {
        try {
            Class.forName("org.sqlite.JDBC");
            SQLiteConfig config = new SQLiteConfig();
            this.connection = DriverManager.getConnection("jdbc:sqlite:/C:\\DB\\candidate.db", config.toProperties());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void close() {
        if (this.connection != null) {
            try {
                this.connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.connection = null;
    }

    public void createTable() {
        this.open();
        String query = "CREATE TABLE shop (idx INTEGER PRIMARY KEY AUTOINCREMENT, sex TEXT, age INTEGER, vote TEXT)";
        try {
            Statement statement = this.connection.createStatement();
            statement.execute(query);
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.close();
    }

    public ArrayList<Candidate> selectData() {
        ArrayList<Candidate> result = new ArrayList<>();
        this.open();
        try {
            String query = "SELECT * FROM shop";
            //String query = "SELECT * FROM shop ORDER BY created DESC";
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int idx = resultSet.getInt("idx");
                String sex = resultSet.getString("sex");
                int age = resultSet.getInt("age");
                String vote = resultSet.getString("vote");
                result.add(new Candidate(idx, sex, age,vote));
            }
            resultSet.close();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.close();
        return result;
    }

    public void insertData(String sex, int age, String vote) {
        this.open();
        String query = "INSERT INTO shop (sex, age, vote) VALUES (?, ?, ?)";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setString(1, sex);
            preparedStatement.setInt(2, age);
            preparedStatement.setString(3, vote);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.close();
    }


    public void deleteData(int idx) {
        this.open();
        String query = "DELETE FROM shop WHERE idx = ?";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, idx);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.close();
    }





}
