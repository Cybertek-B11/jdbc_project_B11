package jdbctests;

import org.junit.Test;

import java.sql.*;
import java.sql.DriverManager;

public class JDBCExamples {
    public final String ORACLE_DB_URL = "jdbc:oracle:thin:@ec2-54-198-62-58.compute-1.amazonaws.com:1521:xe";
    public final String ORACLE_DB_USERNAME = "hr";
    public final String ORACLE_DB_PASSWORD = "hr";

    @Test
    public void readRegionNames() throws Exception{
        Connection connection = DriverManager.getConnection(ORACLE_DB_URL, ORACLE_DB_USERNAME, ORACLE_DB_PASSWORD);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT region_name FROM regions");
        //Loop through region_name column in the resultset.

        while(resultSet.next()){
            String rName = resultSet.getString("region_name");
            System.out.println(rName);
           // System.out.println(resultSet.getString("region_name"));
        }

        //======================
        resultSet = statement.executeQuery("SELECT region_id, region_name FROM regions");
        while(resultSet.next()){
            int rID = resultSet.getInt(1);
            String rName = resultSet.getString(2);

            System.out.println(rID + " | " + rName);

        }

        resultSet.close();
        statement.close();
        connection.close();
    }

    @Test
    public void countAndNavigations() throws Exception{
        Connection connection = DriverManager.getConnection(ORACLE_DB_URL, ORACLE_DB_USERNAME, ORACLE_DB_PASSWORD);
        Statement statement = connection.createStatement(
                                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                                    ResultSet.CONCUR_READ_ONLY);
        //Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM departments");
        //Find out how many records in the resultSet
        resultSet.last(); //goto last record
        int rowCount = resultSet.getRow(); //read row number
        System.out.println("Number of Records: " + rowCount);

        System.out.println("Last Department name:" + resultSet.getString("DEPARTMENT_NAME"));

        resultSet.first(); //goto first department
        System.out.println("First Department name:" + resultSet.getString("DEPARTMENT_NAME"));

        resultSet.beforeFirst();
        while (resultSet.next()) {
            int depID = resultSet.getInt("DEPARTMENT_ID");
            String deptName = resultSet.getString("DEPARTMENT_NAME");

            System.out.println(depID +" | "+ deptName);

        }
        System.out.println("PRINT DEPARTMENT IN REVERSE ORDER");
        while(resultSet.previous()) {
            int depID = resultSet.getInt("DEPARTMENT_ID");
            String deptName = resultSet.getString("DEPARTMENT_NAME");

            System.out.println(depID +" | "+ deptName);
        }

        //goto specific row number in resultset
        resultSet.absolute(5);
        System.out.println("Fifth Department name: " + resultSet.getString("DEPARTMENT_NAME"));

        resultSet.close();
        statement.close();
        connection.close();

    }

}
