package com.pritam.fabrication.ecommerce.test;

import java.sql.*;

public class Metadata {

        // JDBC URL, username, and password
        String url = "jdbc:mysql://localhost:3306/pf-ecom";
        String username = "pf-user";
        String password = "dummypassword";
        
        public Metadata() {
        	// Establish a connection to the database
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                // Get metadata
                DatabaseMetaData metaData = connection.getMetaData();
                
                // Get all tables' information
                ResultSet tables = metaData.getTables(null, null, "%", new String[] {"TABLE"});
                
                // Iterate through tables
                while (tables.next()) {
                    String tableName = tables.getString("TABLE_NAME");
                    System.out.println("Table: " + tableName);
                    
                    // Get columns for each table
                    ResultSet columns = metaData.getColumns(null, null, tableName, "%");
                    
                    // Iterate through columns
                    while (columns.next()) {
                        String columnName = columns.getString("COLUMN_NAME");
                        System.out.println("  Column: " + columnName);
                    }
                    
                    // Close columns ResultSet
                    columns.close();
                }
                
                // Close tables ResultSet
                tables.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    
}
