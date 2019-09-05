package utils;

import java.sql.*;
import java.util.*;

public class DBUtility {

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(ConfigurationReader.getProperty("oracledb.url"),
                ConfigurationReader.getProperty("oracledb.user"),
                ConfigurationReader.getProperty("oracledb.password"));
    }

    private static Connection connection;
    private static PreparedStatement statement;
    private static ResultSet resultSet;

    public static void establishConnection(DBType dbType)  {
        try {
            switch(dbType) {
                case ORACLE:
                    connection = DriverManager.getConnection(ConfigurationReader.getProperty("oracledb.url"),
                            ConfigurationReader.getProperty("oracledb.user"),
                            ConfigurationReader.getProperty("oracledb.password"));
                    break;
                default:
                    connection = null;

            }
        }catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static int getRowsCount(String sql)  {

        try {
            statement = connection.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            resultSet = statement.executeQuery();
            resultSet.last();
            return resultSet.getRow();
        }catch(Exception e) {
            throw new RuntimeException(e);
        }


    }

    public static List<Map<String,Object>> runSQLQuery(String sql){
        try {
            statement = connection.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            resultSet = statement.executeQuery();

            List<Map<String,Object>> list = new ArrayList<>();
            ResultSetMetaData rsMdata = resultSet.getMetaData();

            int colCount = rsMdata.getColumnCount();

            while(resultSet.next()) {
                Map<String,Object> rowMap = new HashMap<>();

                for(int col = 1; col <= colCount; col++) {
                    rowMap.put(rsMdata.getColumnName(col), resultSet.getObject(col));
                }


                list.add(rowMap);
            }

            return list;
        }catch(Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static void closeConnections() {
        try{
            if(resultSet != null && !resultSet.isClosed()) {
                resultSet.close();
            }
            if(statement != null && !statement.isClosed()) {
                statement.close();
            }
            if(connection != null && !connection.isClosed()) {
                connection.close();
            }
        }catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

}
