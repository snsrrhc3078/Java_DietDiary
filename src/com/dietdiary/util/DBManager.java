package com.dietdiary.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


//오라클과 mysql 둘 다 처리하는 DB 매니저 정의
public class DBManager {
	
	String oracle_driver = "oracle.jdbc.driver.OracleDriver";
	String oracle_url = "jdbc:oracle:thin:@3.37.216.159:1521:XE";
	String oracle_user = "dietdiary";
	String oracle_pass = "1234";
	
	String mysql_driver = "com.mysql.jdbc.Driver";
	String mysql_url = "jdbc:mysql://localhost:3306/javase?characterEncording=utf-8&verifyServerCertificate=false&useSSL=true";
	String mysql_user = "root";
	String mysql_pass = "1234";
	
	private static DBManager instance;
	Connection connection; 
	private DBManager() {
		connectOracle();
	}
	//오라클 접속
	public void connectOracle() {
		try {
			Class.forName(oracle_driver);
			connection = DriverManager.getConnection(oracle_url, oracle_user, oracle_pass);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//mysql 접속
	public void connectMysql() {
		try {
			Class.forName(mysql_driver);
			connection = DriverManager.getConnection(mysql_url, mysql_user, mysql_driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static DBManager getInstance() {
		if(instance == null) instance = new DBManager();
		return instance;
	}
	
	public Connection getConnection() {
		return connection;
	}
	
	public void release(Connection connection) {
		if(connection!=null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public void release(PreparedStatement pst) {
		if(pst!=null) {
			try {
				pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public void release(PreparedStatement pst, ResultSet rs) {
		if(rs!=null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(pst!=null) {
			try {
				pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
