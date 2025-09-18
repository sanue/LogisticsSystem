package com.logistics.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 数据库连接工具类
 * 使用Oracle数据库连接
 */
public class DBConnection {
    private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521/XEPDB1";
    private static final String DB_USER = "logistics_app";
    private static final String DB_PASSWORD = "000000";
    
    static {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 获取数据库连接
     * @return Connection对象
     * @throws SQLException 数据库连接异常
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}
