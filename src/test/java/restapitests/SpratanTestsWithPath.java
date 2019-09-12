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

public class SpratanTestsWithPath {
    @BeforeClass
    public static void setUp() {
        //set base uri that is defined in properties file
        RestAssured.baseURI = ConfigurationReader.getProperty("spartan.baseuri");
    }

    /*
    Given accept type is json
    And path param id is 10
    When user sends a get request to "/spartans/{id}"
    Then status code is 200
    And content-type is "application/json;char"
    And response payload values match the following:
            id is 10,
            name is "Lorenza",
            gender is "Female",
            phone is 3312820936
     */
    @Test
    public void spartanGetWithID_Json_Test_using_path() {
       Response response =  given().accept(ContentType.JSON)
                           .and().pathParam("id", 10)
                           .when().get("/spartans/{id}");
        //print response json body values:
        System.out.println(response.body().path("id").toString());
        System.out.println(response.body().path("name").toString());
        System.out.println(response.path("gender").toString());
        System.out.println(response.path("phone").toString());

        int id = response.body().path("id");
        String firstName = response.body().path("name");
        String spartanGender = response.path("gender");
        long phoneNumber = response.path("phone");

        System.out.println("id: " + id);
        System.out.println("firstName: " + firstName);
        System.out.println("spartanGender : " + spartanGender);
        System.out.println("phoneNumber: " + phoneNumber);

        assertEquals(10, id);
        assertEquals("Lorenza", firstName);
        assertEquals("Female" , spartanGender);
        assertEquals(3312820936L , phoneNumber);

        assertEquals(200, response.statusCode());

    }

    @Test
    public void getAllSpartans_using_path_with_list() {
        Response response = get("/spartans/");

        //extract first id
        int firstId = response.path("id[0]");
        System.out.println("firstID: " + firstId);

        //extract first name
        String first1stName = response.path("name[0]");
        System.out.println("first1stName = " + first1stName);

        //get last firstname
        String lastFirstName = response.path("name[-1]");
        System.out.println("lastFirstName = " + lastFirstName);

        //extract all firstNames and print out
        List<String> names = response.path("name");
        System.out.println("number of names: " + names.size());
        System.out.println("names = " + names);

        //extract all phone numbers
        List<Object> phoneNumbers = response.path("phone");
        for(Object ph : phoneNumbers) {
            System.out.println(ph);
        }
    }

    @Test
    public void getCountries_And_Extract_nested_key_values_using_path() {
        RestAssured.baseURI = ConfigurationReader.getProperty("hrapi.baseuri");
        Response response = given().queryParam("q","{\"region_id\":2}")
                              .when().get("/countries");

        String firstCountryID = response.path("items.country_id[0]");
        String firstCountryName = response.path("items.country_name[0]");

        System.out.println("firstCountryID = " + firstCountryID);
        System.out.println("firstCountryName = " + firstCountryName);

        //get all country names and print them out
        List<String> countries = response.path("items.country_name");
        System.out.println("countries = " + countries);

        //assert that all region ids equal to 2
        List<Integer> regionIDs = response.path("items.region_id");
        for (int regionID : regionIDs) {
            assertEquals(2, regionID);
        }
    }





}
