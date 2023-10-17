package com.bjw.d10;

import org.sqlite.SQLiteConfig;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DB<T> {
    String dbFileName = "";
    String tableName = "";

    Connection connection;
    T t;

    DB(String dbFileName, String tableName, T t) {
        this.dbFileName = dbFileName;
        this.tableName = tableName;
        this.t = t;
    }

    private void open() {
        try {
            Class.forName("org.sqlite.JDBC");
            SQLiteConfig config = new SQLiteConfig();
            this.connection = DriverManager.getConnection("jdbc:sqlite:/" + this.dbFileName, config.toProperties());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void close() {
        if (this.connection != null) {
            try {
                this.connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        this.connection = null;
    }

    public void createTable() {
        String queryString = "";

        Class<?> dataClass = t.getClass();
        Field[] dataClassFields = dataClass.getDeclaredFields();

        for (Field field : dataClassFields) {
            String fieldType = field.getType().toString();
            String fieldName = field.getName();

            if (!queryString.isEmpty()) {
                queryString = queryString + ",";
            }

            if (fieldName.matches("idx") && fieldType.matches("int")) {
                queryString = queryString + fieldName + " INTEGER PRIMARY KEY AUTOINCREMENT";
            } else if (fieldType.matches("int")) {
                queryString = queryString + fieldName + " INTEGER";
            } else if (fieldType.matches("(double|float)")) {
                queryString = queryString + fieldName + " REAL";
            } else if (fieldType.matches(".*String")) {
                queryString = queryString + fieldName + " TEXT";
            }
        }
        queryString = "CREATE TABLE " + this.tableName + "(" + queryString + ");";

        this.open();
        try {
            Statement statement = this.connection.createStatement();
            statement.execute(queryString);
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.close();
    }

    public void insertData(T insertObject) {
        Class<?> dataClass = t.getClass();
        Field[] dataClassFields = dataClass.getDeclaredFields();

        String fieldQuery = "";
        String valueQuery = "";
        for (Field field : dataClassFields) {
            String fieldType = field.getType().toString();
            String fieldName = field.getName();

            if (!fieldQuery.isEmpty()) {
                fieldQuery = fieldQuery + ",";
            }
            if (!valueQuery.isEmpty()) {
                valueQuery = valueQuery + ",";
            }

            try {
                if (fieldName.matches("idx") && fieldType.matches("int")) {
                    continue;
                } else if (fieldType.matches("int")) {
                    fieldQuery = fieldQuery + fieldName;
                    valueQuery = valueQuery + field.get(insertObject);
                } else if (fieldType.matches("(double|float)")) {
                    fieldQuery = fieldQuery + fieldName;
                    valueQuery = valueQuery + field.get(insertObject);
                } else if (fieldType.matches(".*String")) {
                    fieldQuery = fieldQuery + fieldName;
                    valueQuery = valueQuery + "'" + field.get(insertObject) + "'";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String queryString = "INSERT INTO " + this.tableName + " (" + fieldQuery + ")" + " VALUES (" + valueQuery + ")";

        this.open();
        try {
            Statement statement = this.connection.createStatement();
            statement.execute(queryString);
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.close();
    }

}
