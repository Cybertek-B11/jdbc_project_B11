package day7_put_delete_authorization_schemavalidation;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import org.junit.BeforeClass;
import org.junit.Test;
import io.restassured.http.ContentType;
import utils.ConfigurationReader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class SpartanDeletePut {


    @BeforeClass
    public static void setUp() {
        baseURI = ConfigurationReader.getProperty("spartan.baseuri");
    }

    @Test
    public void updateExistingSpartan_PUT_request_test() {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("name" , "Marufjon");
        requestMap.put("gender","Male");
        requestMap.put("phone", 12023615117L);

        given().contentType(ContentType.JSON)
                .and().body(requestMap)
                .and().pathParam("id", 6)
                .when().put("/spartans/{id}")
                .then().assertThat().statusCode(204);
    }

    @Test
    public void deleteExistingSpartan_DELETE_request_test() {
        Random random = new Random();
        int idToDelete = random.nextInt(351) + 4; //1+4 ==> 5
        System.out.println("idToDelete = " + idToDelete);

        given().pathParam("id", idToDelete)
                .when().delete("/spartans/{id}")
                .then().assertThat().statusCode(204);

        expect().that().statusCode(404)
                .given().pathParam("id", idToDelete)
                .when().delete("/spartans/{id}");
    }






}










