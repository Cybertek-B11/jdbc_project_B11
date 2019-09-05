package jdbctests;

import org.junit.Test;
import utils.DBUtility;

import java.sql.*;

public class MetaData {

    @Test
    public void metaDataExamples(){
        Connection connection = null;
        PreparedStatement prepStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBUtility.getConnection();
            prepStatement = connection.prepareStatement("SELECT * FROM countries");
            resultSet = prepStatement.executeQuery();

            DatabaseMetaData dbmd = connection.getMetaData();
            System.out.println("Database type: " + dbmd.getDatabaseProductName());
            System.out.println("Database version: " + dbmd.getDatabaseProductVersion());
            System.out.println("User: " + dbmd.getUserName());

            ResultSetMetaData rsMD = resultSet.getMetaData();
            System.out.println(rsMD.getColumnCount());
            System.out.println(rsMD.getColumnName(1));
            System.out.println(rsMD.getColumnName(2));
            System.out.println(rsMD.getColumnName(3));


        }catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            try{
                if (resultSet != null && !resultSet.isClosed()){
                    resultSet.close();
                }
                if (prepStatement != null && !prepStatement.isClosed()){
                    prepStatement.close();
                }
                if (connection != null && !connection.isClosed()){
                    connection.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
