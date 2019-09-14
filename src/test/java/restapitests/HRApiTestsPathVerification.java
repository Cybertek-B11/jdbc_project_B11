package restapitests;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.BeforeClass;
import org.junit.Test;
import utils.ConfigurationReader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.*;
public class HRApiTestsPathVerification {

    @BeforeClass
    public static void setUp() {
        //set base uri that is defined in properties file
        RestAssured.baseURI = ConfigurationReader.getProperty("hrapi.baseuri");
    }
/*
		- Given accept type is Json
		- Path param value- US
		- When users sends request to /countries
		- Then status code is 200
		- And country_id is US
		- And Country_name is United States of America
		- And Region_id is 2
 */
    @Test
    public void verifyCountryDetailsUsing_Path_method() {
        Response response = given().accept("application/json")
                .and().pathParam("country_id" , "US")
                .when().get("/countries/{country_id}");

        int statusCode = response.statusCode();
        String countryID = response.path("country_id");
        String countryName = response.path("country_name");
        int regionID = response.path("region_id");

        assertEquals(200, statusCode);
        assertEquals("US", countryID);
        assertEquals("United States of America", countryName);
        assertEquals(2 , regionID);
    }
/*
    - Given accept type is Json
    - Query param value - q={"department_id":80}
    - When users sends request to /employees
    - Then status code is 200
    - And all job_ids start with 'SA'
    - And all department_ids are 80
    - Count is 25
 */
    @Test
    public void verify_multiple_employee_details_using_path() {
        Response response = given().accept("application/json")
                .and().queryParam("q" , "{\"department_id\":80}")
                .when().get("/employees");

        //validations
        assertEquals(200, response.statusCode());
        //extract values from json and store into list, vars
        List<String> jobIDs = response.path("items.job_id");
        List<Integer> deptIDs = response.path("items.department_id");
        int count = response.path("count");

        //do assertions
        for (String jobID : jobIDs) {
            assertTrue(jobID.startsWith("SA"));
            assertEquals("SA" , jobID.substring(0,2));
        }

        deptIDs.forEach( id -> assertEquals(new Integer(80),id));

        assertEquals(25, count);
    }




}
