package com.example.fileUpload;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import jakarta.inject.Singleton;

@Singleton
public class ConnectionProvider {
 
	Connection con;
	public Connection getcon()
	{
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
		 con=DriverManager.getConnection("jdbc:sqlserver://localhost","sa","sqlserver");
		 return con;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("connection found"+con);
		return null;

	}


	
 }

