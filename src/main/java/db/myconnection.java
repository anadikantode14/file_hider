package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class myconnection {

        public static Connection connection;
        public static Connection getConnection(){
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/file_hider_db?useSSL=false", "root", "Anadi@123");
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
            return connection;
        }
        public static void closeConnection(){
            if(connection!=null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

//    public static void main(String[] args) {
//        myconnection.getConnection();
//    }
    }

