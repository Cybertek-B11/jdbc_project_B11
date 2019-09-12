package restapitests;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.BeforeClass;
import org.junit.Test;
import utils.ConfigurationReader;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.*;
public class HRAPiTestsWithParameters {

    @BeforeClass
    public static void setUp() {
        //set base uri that is defined in properties file
        RestAssured.baseURI = ConfigurationReader.getProperty("hrapi.baseuri");
    }

    /*
        Given accept type is Json
        And parameters: q = region_id=2
        When users sends a GET request to "/countries"
        Then status code is 200
        And Content type is application/json
        And Payload should contain "United States of America"

        {"region_id":2}
     */
    @Test
    public void getCountries_using_queryParameter() {
       Response response= given().accept(ContentType.JSON)
                          .and().queryParam("q", "{\"region_id\":2}")
                          .when().get("/countries");

       response.prettyPrint();
       assertEquals(200, response.statusCode());
       assertEquals("application/json", response.contentType());
       assertTrue(response.body().asString().contains("United States of America"));
    }

    @Test
    public void getEmployees_using_queryParameter() {
        Response response= given().accept(ContentType.JSON)
                .and().queryParam("q", "{\"job_id\":\"IT_PROG\"}")
                .when().get("/employees");

        response.prettyPrint();
        assertEquals(200, response.statusCode());
        assertEquals("application/json", response.contentType());
        assertTrue(response.body().asString().contains("IT_PROG"));
    }



}
