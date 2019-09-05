package jdbctests;

import org.junit.Assert;
import org.junit.Test;
import utils.DBUtility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class PreparedStatementExamples {

    @Test
    public void preparedStatementExample() {
        List<String> countries = new ArrayList<>();

        Connection connection = null;
        PreparedStatement prepStatement = null;
        ResultSet resultSet = null;
        try{
            connection = DBUtility.getConnection();
            prepStatement = connection.prepareStatement("SELECT * FROM countries");
            resultSet = prepStatement.executeQuery();

            while (resultSet.next()) {
                countries.add(resultSet.getString("country_name"));
            }
        }catch (Exception e) {
            e.printStackTrace();
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

        Assert.assertEquals(25,countries.size());
        Assert.assertTrue(countries.contains("Zimbabwe"));
        System.out.println(countries.toString());
    }


    @Test
    public void preparedStatementWithIndex() {
        List<String> countries = new ArrayList<>();

        Connection connection = null;
        PreparedStatement prepStatement = null;
        ResultSet resultSet = null;
        try{
            connection = DBUtility.getConnection();
            prepStatement = connection.prepareStatement("SELECT * FROM countries WHERE region_id = ?");
            prepStatement.setInt(1, 2);
            resultSet = prepStatement.executeQuery();

            while (resultSet.next()) {
                countries.add(resultSet.getString("country_name"));
            }
        }catch (Exception e) {
            e.printStackTrace();
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

        List<String> expectedCountries = Arrays.asList("Argentina", "Brazil", "Canada", "Mexico", "United States of America");

        Assert.assertEquals(5,countries.size());
        Assert.assertTrue(countries.contains("Canada"));
        Assert.assertEquals(expectedCountries, countries);
        System.out.println(countries.toString());
    }

    @Test
    public void resultSetToMap() {
        Map<String,String> countries = new HashMap<>();

        Connection connection = null;
        PreparedStatement prepStatement = null;
        ResultSet resultSet = null;
        String sql = "SELECT * FROM countries WHERE region_id IN (?,?)";
        try{
            connection = DBUtility.getConnection();
            prepStatement = connection.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            prepStatement.setInt(1, 2);
            prepStatement.setInt(2,4);

            resultSet = prepStatement.executeQuery();

            //print how many rows returned
            resultSet.last();
            int rowsCount = resultSet.getRow();

            System.out.println("ROWS COUNT: " + rowsCount);

            resultSet.beforeFirst();

            while(resultSet.next()) {
                countries.put(resultSet.getString("COUNTRY_ID"),
                              resultSet.getString("COUNTRY_NAME")) ;
            }
        }catch (Exception e) {
            e.printStackTrace();
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

        List<String> expectedCountries = Arrays.asList("Argentina", "Brazil", "Canada", "Mexico", "United States of America");

       Assert.assertEquals("Canada", countries.get("CA") );
       System.out.println(countries.toString());

        for (String cID: countries.keySet()) {
            System.out.println(cID + " | "  + countries.get(cID));
        }

    }


}
