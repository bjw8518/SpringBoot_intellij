package com.bjw.shopping;

import org.sqlite.SQLiteConfig;



import org.springframework.stereotype.Component;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


@Component
public class DB {
    Connection connection;

    private void open() {
        try {
            Class.forName("org.sqlite.JDBC");
            SQLiteConfig config = new SQLiteConfig();
            this.connection = DriverManager.getConnection("jdbc:sqlite:/C:\\DB\\Shop.db", config.toProperties());
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
        String query = "CREATE TABLE shop (idx INTEGER PRIMARY KEY AUTOINCREMENT, article TEXT, category TEXT, price INTEGER, created TEXT)";
        try {
            Statement statement = this.connection.createStatement();
            statement.execute(query);
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.close();
    }

    public ArrayList<Shopping> selectData() {
        ArrayList<Shopping> result = new ArrayList<>();
        this.open();
        try {
            String query = "SELECT * FROM shop ORDER BY created DESC";
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int idx = resultSet.getInt("idx");
                String article = resultSet.getString("article");
                String category = resultSet.getString("category");
                int price = resultSet.getInt("price");
                String created = resultSet.getString("created");
                result.add(new Shopping(idx, article,category, price, created));
            }
            resultSet.close();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.close();
        return result;
    }

    public void insertData(String article, String category, int price) {
        this.open();
        String query = "INSERT INTO shop (article, category, price, created) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setString(1, article);
            preparedStatement.setString(2, category);
            preparedStatement.setInt(3, price);
            java.util.Date now = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedNow = formatter.format(now);
            preparedStatement.setString(4, formattedNow);
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
