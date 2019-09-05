package jdbctests;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) throws Exception{
        String oracleDbUrl = "jdbc:oracle:thin:@ec2-54-198-62-58.compute-1.amazonaws.com:1521:xe";
        String oracleDbUsername = "hr";
        String oracleDbPassword = "hr";

        Connection connection = DriverManager.getConnection(oracleDbUrl,oracleDbUsername,oracleDbPassword);

        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("SELECT * FROM regions");

        resultSet.next();//move it to first row
        //print first row data from columns
        System.out.println(resultSet.getString("REGION_ID"));
        System.out.println(resultSet.getString("REGION_NAME"));

        //CLOSE CONNECTIONS
        resultSet.close();
        statement.close();
        connection.close();

    }
}
