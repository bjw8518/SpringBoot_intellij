package com.bjw.d10;

import org.sqlite.SQLiteConfig;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;

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

        String fields = "";
        String values = "";
        for (Field field : dataClassFields) {
            String fieldType = field.getType().toString();
            String fieldName = field.getName();
            if (fieldName.matches("idx") && fieldType.matches("int")) {
                continue;
            }

            if (!fields.isEmpty()) {
                fields = fields + ",";
            }
            fields = fields + fieldName;
            if (!values.isEmpty()) {
                values = values + ",";
            }
            values = values + "?";
        }
        String queryString = "INSERT INTO " + this.tableName + " (" + fields + ")" + " VALUES (" + values + ")";

        this.open();
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(queryString);
            int valueIndex = 1;
            for (Field field : dataClassFields) {
                String fieldType = field.getType().toString();
                String fieldName = field.getName();
                if (fieldName.matches("idx") && fieldType.matches("int")) {
                    continue;
                }
                String getterName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                if (fieldType.matches("int")) {
                    int getValue = 0;
                    try {
                        Method getter = dataClass.getDeclaredMethod(getterName);
                        getValue = (int) getter.invoke(insertObject);
                    } catch (NoSuchMethodException e) {
                        getValue = field.getInt(insertObject);
                    }
                    preparedStatement.setInt(valueIndex, getValue);
                } else if (fieldType.matches("float")) {
                    float getValue = 0;
                    try {
                        Method getter = dataClass.getDeclaredMethod(getterName);
                        getValue = (float) getter.invoke(insertObject);
                    } catch (NoSuchMethodException e) {
                        getValue = field.getFloat(insertObject);
                    }
                    preparedStatement.setFloat(valueIndex, getValue);
                } else if (fieldType.matches("double")) {
                    double getValue = 0;
                    try {
                        Method getter = dataClass.getDeclaredMethod(getterName);
                        getValue = (double) getter.invoke(insertObject);
                    } catch (NoSuchMethodException e) {
                        getValue = field.getDouble(insertObject);
                    }
                    preparedStatement.setDouble(valueIndex, getValue);
                } else if (fieldType.matches(".*String")) {
                    String getValue = "";
                    try {
                        Method getter = dataClass.getDeclaredMethod(getterName);
                        getValue = (String) getter.invoke(insertObject);
                    } catch (NoSuchMethodException e) {
                        getValue = (String) field.get(insertObject);
                    }
                    preparedStatement.setString(valueIndex, getValue);
                } else {
                    preparedStatement.setString(valueIndex, "");
                }
                valueIndex++;
            }
            preparedStatement.execute();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.close();
    }

    public void updateData(T updateObject) {
        Class<?> dataClass = t.getClass();
        Field[] dataClassFields = dataClass.getDeclaredFields();

        String setValue = "";
        int whereIdx = -1;
        for (Field field : dataClassFields) {
            String fieldType = field.getType().toString();
            String fieldName = field.getName();
            if (fieldName.matches("idx") && fieldType.matches("int")) {
                try {
                    whereIdx = field.getInt(updateObject);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                continue;
            }

            if (!setValue.isEmpty()) {
                setValue = setValue + ",";
            }
            setValue = setValue + fieldName + "=?";
        }
        String queryString = "UPDATE " + this.tableName + " SET " + setValue + " WHERE idx=" + whereIdx;

        this.open();
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(queryString);
            int valueIndex = 1;
            for (Field field : dataClassFields) {
                String fieldType = field.getType().toString();
                String fieldName = field.getName();
                if (fieldName.matches("idx") && fieldType.matches("int")) {
                    continue;
                }
                String getterName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                if (fieldType.matches("int")) {
                    int getValue = 0;
                    try {
                        Method getter = dataClass.getDeclaredMethod(getterName);
                        getValue = (int) getter.invoke(updateObject);
                    } catch (NoSuchMethodException e) {
                        getValue = field.getInt(updateObject);
                    }
                    preparedStatement.setInt(valueIndex, getValue);
                } else if (fieldType.matches("float")) {
                    float getValue = 0;
                    try {
                        Method getter = dataClass.getDeclaredMethod(getterName);
                        getValue = (float) getter.invoke(updateObject);
                    } catch (NoSuchMethodException e) {
                        getValue = field.getFloat(updateObject);
                    }
                    preparedStatement.setFloat(valueIndex, getValue);
                } else if (fieldType.matches("double")) {
                    double getValue = 0;
                    try {
                        Method getter = dataClass.getDeclaredMethod(getterName);
                        getValue = (double) getter.invoke(updateObject);
                    } catch (NoSuchMethodException e) {
                        getValue = field.getDouble(updateObject);
                    }
                    preparedStatement.setDouble(valueIndex, getValue);
                } else if (fieldType.matches(".*String")) {
                    String getValue = "";
                    try {
                        Method getter = dataClass.getDeclaredMethod(getterName);
                        getValue = (String) getter.invoke(updateObject);
                    } catch (NoSuchMethodException e) {
                        getValue = (String) field.get(updateObject);
                    }
                    preparedStatement.setString(valueIndex, getValue);
                } else {
                    preparedStatement.setString(valueIndex, "");
                }
                valueIndex++;
            }
            preparedStatement.execute();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.close();
    }

    public boolean deleteData(T deleteObject) {
        Class<?> dataClass = t.getClass();
        Field[] dataClassFields = dataClass.getDeclaredFields();

        int whereIdx = -1;
        for (Field field : dataClassFields) {
            String fieldType = field.getType().toString();
            String fieldName = field.getName();
            if (fieldName.matches("idx") && fieldType.matches("int")) {
                try {
                    whereIdx = field.getInt(deleteObject);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                continue;
            }
        }
        String queryString = "DELETE FROM " + this.tableName + " WHERE idx=" + whereIdx;

        this.open();
        int result = 0;
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(queryString);
            result = preparedStatement.executeUpdate();
            System.out.println(result);
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.close();

        if (result > 0) {
            return true;
        }
        return false;
    }

    public ArrayList<T> selectData() {
        Class<?> dataClass = t.getClass();
        Field[] dataClassFields = dataClass.getDeclaredFields();

        String queryString = "SELECT * FROM " + this.tableName;
        ArrayList<T> list = new ArrayList<T>();

        this.open();
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(queryString);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                T fieldData = (T) dataClass.getDeclaredConstructor().newInstance();
                for (Field field : dataClassFields) {
                    String fieldType = field.getType().toString();
                    String fieldName = field.getName();
                    if (fieldType.matches("int")) {
                        field.setInt(fieldData, resultSet.getInt(fieldName));
                    } else if (fieldType.matches("float")) {
                        field.setFloat(fieldData, resultSet.getFloat(fieldName));
                    } else if (fieldType.matches("double")) {
                        field.setDouble(fieldData, resultSet.getDouble(fieldName));
                    } else {
                        field.set(fieldData, resultSet.getString(fieldName));
                    }
                }
                list.add(fieldData);
            }
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.close();

        return list;
    }

    public T detailData(T detailData) {
        Class<?> dataClass = t.getClass();
        Field[] dataClassFields = dataClass.getDeclaredFields();

        int whereIdx = -1;
        for (Field field : dataClassFields) {
            String fieldType = field.getType().toString();
            String fieldName = field.getName();
            if (fieldName.matches("idx") && fieldType.matches("int")) {
                try {
                    whereIdx = field.getInt(detailData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
        }
        String queryString = "SELECT * FROM " + this.tableName + " WHERE idx=" + whereIdx;
        T resultData = null;

        this.open();
        try {
            resultData = (T) dataClass.getDeclaredConstructor().newInstance();

            PreparedStatement preparedStatement = this.connection.prepareStatement(queryString);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                for (Field field : dataClassFields) {
                    String fieldType = field.getType().toString();
                    String fieldName = field.getName();
                    if (fieldType.matches("int")) {
                        field.setInt(resultData, resultSet.getInt(fieldName));
                    } else if (fieldType.matches("float")) {
                        field.setFloat(resultData, resultSet.getFloat(fieldName));
                    } else if (fieldType.matches("double")) {
                        field.setDouble(resultData, resultSet.getDouble(fieldName));
                    } else {
                        field.set(resultData, resultSet.getString(fieldName));
                    }
                }
            }
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.close();

        return resultData;
    }
}
