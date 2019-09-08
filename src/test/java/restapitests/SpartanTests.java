package restapitests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import static io.restassured.RestAssured.*;
import static org.junit.Assert.*;

public class SpartanTests {
    String spartanAllURL = "http://ec2-54-164-195-86.compute-1.amazonaws.com:8000/api/spartans/";

    @Test
    public void viewAllSpartansTest(){
      Response response = RestAssured.get(spartanAllURL);
      System.out.println(response.statusCode());
     //System.out.println(response.body().asString());
      response.body().prettyPrint();
    }

    /*
      Given accept type is Json
      When user sends a get request to spartanAllURL
      Then response status code is 200
      And response body should json
      And response should contain "name": "Chipotle"
     */
    @Test
    public void viewAllSpartansTest2(){
      //  Response response = RestAssured.get(spartanAllURL);
        Response response = RestAssured.given().accept(ContentType.JSON).
                                        when().get(spartanAllURL);
        Assert.assertEquals(200, response.statusCode());
        Assert.assertEquals("application/json;charset=UTF-8", response.contentType());
        Assert.assertTrue(response.body().asString().contains("Chipotle"));
        //Assert.assertEquals(true, response.body().asString().contains("Chipotle"));
    }

    @Test
    public void viewAllSpartansTest3(){
        //request part
        given().accept(ContentType.JSON)
        .when().get(spartanAllURL)
         //response verification part
        .then().statusCode(200)
        .and().contentType("application/json;charset=UTF-8");
    }

    /*
        When users sends a get request to /spartans/4
        Then status code should be 200
        And content type should be application/json;charset=UTF-8
        And json body should contain "John Wick"
     */

    @Test
    public void getASpartanTest(){
      Response response = when().get(spartanAllURL+4);
      assertEquals(200, response.statusCode());
      assertEquals("application/json;charset=UTF-8", response.contentType());
      assertTrue(response.body().asString().contains("John Wick"));
    }











}













