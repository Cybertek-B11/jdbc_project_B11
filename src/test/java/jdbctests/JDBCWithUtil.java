package jdbctests;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import utils.ConfigurationReader;
import utils.DBType;
import utils.DBUtility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

import static org.junit.Assert.*;

public class JDBCWithUtil {
    @BeforeClass
    public static void setUp(){
        //connect to database using utility
        DBUtility.establishConnection(DBType.ORACLE);
    }

    @Test
    public void testEmployeesCount() {
        int employeesCount = DBUtility.getRowsCount("SELECT * FROM employees");
        System.out.println("employeesCount: " + employeesCount);
        int expectedEmpCount = 107;
        assertEquals(expectedEmpCount, employeesCount);
    }

    @Test
    public void testEmployees() {
        String sql = "SELECT first_name, last_name, job_id, salary "+
                     "FROM employees "+
                     "WHERE salary BETWEEN 5000 AND 6000";

        int empCount = DBUtility.getRowsCount(sql);
        List<Map<String, Object>> empDBData = DBUtility.runSQLQuery(sql);

        System.out.println("employees count:" + empCount);
        System.out.println(empDBData.toString());

        Map<String, Object> firstEmployee = empDBData.get(0);
        System.out.println("firstEmployee: " + firstEmployee);

        System.out.println("firstName: " + firstEmployee.get("FIRST_NAME"));
        System.out.println("lastName: " + firstEmployee.get("LAST_NAME"));
        System.out.println("job id: " + firstEmployee.get("JOB_ID"));
        System.out.println("salary: " +firstEmployee.get("SALARY"));
    }

    @Test
    public void regionsPojoTest() throws Exception{


        Connection connection = DriverManager.getConnection(ConfigurationReader.getProperty("oracledb.url"),
                                                            ConfigurationReader.getProperty("oracledb.user"),
                                                             ConfigurationReader.getProperty("oracledb.password"));
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM regions",
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

        ResultSet resultSet = preparedStatement.executeQuery();

        List<Region> regions = new ArrayList<>();

        while (resultSet.next()) {
            int regionID = resultSet.getInt("REGION_ID");
            String regionName = resultSet.getString("REGION_NAME");
            regions.add(new Region(regionID,regionName ));
        }

        System.out.println(regions.toString());

        resultSet.close();
        preparedStatement.close();
        connection.close();
    }



    @AfterClass
    public static void cleanUp() {
        DBUtility.closeConnections();
    }

}
