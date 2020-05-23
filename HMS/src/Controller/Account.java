package Controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Model.DB;
import View.Ui;
import View.UiUtils;

public class Account { 
    public static int token;
    public static Connection conn = DB.conn;

    // singup controller
    public static int signUp(String identity, String userName, String password) {
        try {
            String sql;
            String role;
            Statement statement = conn.createStatement();
            sql = "USE Robin_HMS";
            statement.execute(sql);
            if (identity.toString() == "1") {
                role = "stuff";
            } else {
                role = "guest";
            }
            sql = "INSERT INTO " + role + " (name, password) VALUES (" + "\"" + userName + "\"" + ", " + "\"" + password
                    + "\"" + ")";
            statement.execute(sql);
            ResultSet rs = statement.executeQuery("SELECT id FROM " + role + " WHERE name=" + "\"" + userName + "\"");
            rs.next();
            token = rs.getInt("id");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return token;
    }

    // login controller
    public static int login( String identity, String userName, String password) {
        String role;
        try {
            Statement statement = conn.createStatement();
            String sql;
            sql = "USE Robin_HMS";
            statement.execute(sql);
            if (identity.toString() == "1") {
                role = "stuff";
            } else {
                role = "guest";
            }
            sql = "SELECT id, name, password FROM " + role + " WHERE name=" + "\"" + userName + "\"";
            ResultSet rs = statement.executeQuery(sql);
            // check if has this username
            if (rs.next() == false) {
                UiUtils.print("User name is not exist, please check or sign up");
                Ui.signUpOrLogin();
            }
            // check password is correct.
            String passwordDB = rs.getString("password");
            if (!password.equals(passwordDB)) {
                UiUtils.print("Password is incorrect, please try again from start");
                Ui.signUpOrLogin();
            }
            token = rs.getInt("id");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        UiUtils.print("Hello, " + userName + ", welcome to Robin_HMS!");
        return token;
    }
}