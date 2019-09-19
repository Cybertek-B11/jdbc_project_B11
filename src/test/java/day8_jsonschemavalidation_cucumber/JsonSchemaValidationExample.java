package day8_jsonschemavalidation_cucumber;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.BeforeClass;
import org.junit.Test;
import io.restassured.http.ContentType;
import utils.BookItApiUtil;
import utils.ConfigurationReader;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;

public class JsonSchemaValidationExample {

    @Test
    public void jsonSchemValidationOfSingleSpartanAPI() {
        given().accept(ContentType.JSON)
                .pathParam("id", 300)
                .when().get("http://54.164.195.86:8000/api/spartans/{id}")
                .then().assertThat().body(matchesJsonSchemaInClasspath("SingleSpartanSchema.json"));
        /*
        {
            "id": 300,
            "name": "Spartacus",
            "gender": "Male",
            "phone": 5552059955
        }
         */
        given().accept(ContentType.JSON)
                .pathParam("id", 300)
                .when().get("http://54.164.195.86:8000/api/spartans/{id}")
                .then().assertThat().body(matchesJsonSchema(new File("/Users/cybertekschool/Desktop/SingleSpartanSchema.json")));
    }
    
    @Test
    public void testUtil() {
        String bookItToken = BookItApiUtil.generateToken();
        System.out.println("bookItToken = " + bookItToken);
    }

}
